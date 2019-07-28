package com.tan.ec.utils;

import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayConstants;
import com.github.wxpay.sdk.WXPayUtil;
import com.tan.ec.bean.config.MyWXPayConfig;
import com.xiaoleilu.hutool.http.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import sun.misc.BASE64Decoder;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * WXPayClient
 * <p>
 * 对WXPay的简单封装，处理支付密切相关的逻辑.
 *
 * @author Mengday Zhang
 * @version 1.0
 * @since 2018/6/16
 */
@Slf4j
public class WXPayClient extends WXPay {
    private final BigDecimal HUNDRED = new BigDecimal("100");

    /** 密钥算法 */
    private static final String ALGORITHM = "AES";
    /** 加解密算法/工作模式/填充方式 */
    private static final String ALGORITHM_MODE_PADDING = "AES/ECB/PKCS5Padding";

    private MyWXPayConfig config;

    public WXPayClient(MyWXPayConfig config, WXPayConstants.SignType signType, boolean useSandbox) {
        super(config, signType, useSandbox);
        this.config = config;
    }


    //组装预下单的请求数据
    public String getReqStr(String openid,String productId, String tradeNo,String body,String amount){
        Map<String,String> data = new HashMap<String,String>();

        data.put("appid", config.getAppID());
        data.put("mch_id",config.getMchID());
        data.put("nonce_str", WXPayUtil.generateNonceStr());
        data.put("body", body);
        data.put("out_trade_no", tradeNo);
        data.put("fee_type","CNY");
        data.put("total_fee", amount);//1分钱
        data.put("spbill_create_ip", "39.100.114.144");
        data.put("notify_url", config.getNotifyUrl());
        data.put("trade_type", "JSAPI");
        data.put("product_id", productId);
        data.put("openid", openid);
        try {
            String sign = WXPayUtil.generateSignature(data, "lianshangxinxikeji123p123p123p12", WXPayConstants.SignType.MD5);
            data.put("sign", sign);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String reqBody = null;
        try {
            reqBody = WXPayUtil.mapToXml(data);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return reqBody;
    }

    /**
     * 从request的inputStream中获取参数
     * @param request
     * @return
     * @throws Exception
     */
    public Map<String, String> getNotifyParameter(HttpServletRequest request) throws Exception {
        InputStream inputStream = request.getInputStream();
        ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length = 0;
        while ((length = inputStream.read(buffer)) != -1) {
            outSteam.write(buffer, 0, length);
        }
        outSteam.close();
        inputStream.close();

        // 获取微信调用我们notify_url的返回信息
        String resultXml = new String(outSteam.toByteArray(), "utf-8");
        Map<String, String> notifyMap = WXPayUtil.xmlToMap(resultXml);

        return notifyMap;
    }

    /**
     * 退款
     * 注意：调用申请退款、撤销订单接口需要商户证书
     * 注意：沙箱环境响应结果可能会是"沙箱支付金额不正确,请确认验收case"，但是正式环境不会报这个错误
     * 微信支付的最小金额是0.1元，所以在测试支付时金额必须大于0.1元，否则会提示微信支付配置错误，可以将microPay的total_fee大于1再退款
     */
    public Map<String, String> refund(String orderNo, BigDecimal totalFee, BigDecimal refundFee) throws Exception {
        BigDecimal totalFeeAmt = totalFee.multiply(HUNDRED);
        BigDecimal refundFeeAmt = refundFee.multiply(HUNDRED);
        Map<String, String> reqData = new HashMap<>();
        // 商户订单号
        reqData.put("out_trade_no", orderNo);
        // 授权码
        reqData.put("out_refund_no", orderNo);
        // 订单总金额，单位为分，只能为整数
        reqData.put("total_fee", totalFeeAmt.intValue() + "");
        //退款金额
        reqData.put("refund_fee", refundFeeAmt.intValue() + "");
        // TODO 退款后异步通知的相关业务
        // 退款异步通知地址
        reqData.put("notify_url", config.getRefundNotifyUrl());

        reqData.put("refund_fee_type", "CNY");
        reqData.put("op_user_id", config.getMchID());

        return this.refund(reqData);
    }

    /**
     * 微信下單接口
     *
     * @param outTradeNo 订单号
     * @param body       描述
     * @param money      金额
     * @param tradeType  交易类型
     * @return
     */
    public Map<String, String> doUnifiedOrder(String outTradeNo, String body, BigDecimal money,String tradeType) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        //单位(分)
        BigDecimal amt = money.multiply(HUNDRED);
        Map<String, String> data = new HashMap<>();
        data.put("body", body);
        data.put("out_trade_no", outTradeNo);
        data.put("total_fee", amt.intValue() + "");
        data.put("spbill_create_ip", HttpUtil.getClientIP(request));
        data.put("notify_url", config.getNotifyUrl());
        data.put("trade_type", tradeType);
        data.put("product_id", outTradeNo);

        try {
            return this.unifiedOrder(data);
        } catch (Exception e) {
            e.printStackTrace();
            log.debug(e.getMessage());
            return null;
        }
    }

    /**
     * 解密退款通知
     *
     * <a href="https://pay.weixin.qq.com/wiki/doc/api/micropay.php?chapter=9_16&index=11>退款结果通知文档</a>
     * @return
     * @throws Exception
     */
    public Map<String, String> decodeRefundNotify(HttpServletRequest request) throws Exception {
        // 从request的流中获取参数
        Map<String, String> notifyMap = this.getNotifyParameter(request);
        log.info(notifyMap.toString());

        String reqInfo = notifyMap.get("req_info");
        //（1）对加密串A做base64解码，得到加密串B
        byte[] bytes = new BASE64Decoder().decodeBuffer(reqInfo);

        //（2）对商户key做md5，得到32位小写key* ( key设置路径：微信商户平台(pay.weixin.qq.com)-->账户设置-->API安全-->密钥设置 )
        Cipher cipher = Cipher.getInstance(ALGORITHM_MODE_PADDING);
        SecretKeySpec key = new SecretKeySpec(WXPayUtil.MD5(config.getKey()).toLowerCase().getBytes(), ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);

        //（3）用key*对加密串B做AES-256-ECB解密（PKCS7Padding）
        // java.security.InvalidKeyException: Illegal key size or default parameters
        // https://www.cnblogs.com/yaks/p/5608358.html
        String responseXml = new String(cipher.doFinal(bytes),"UTF-8");
        Map<String, String> responseMap = WXPayUtil.xmlToMap(responseXml);
        return responseMap;
    }

}
