package com.tan.ec.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.github.wxpay.sdk.WXPayConstants;
import com.github.wxpay.sdk.WXPayUtil;
import com.tan.ec.bean.config.MyWXPayConfig;
import com.tan.ec.bean.websocket.MasternodeWebSocket;
import com.tan.ec.entity.BsOrder;
import com.tan.ec.entity.OrderReturnInfo;
import com.tan.ec.entity.SignInfo;
import com.tan.ec.service.IBsOrderService;
import com.tan.ec.service.WXPayService;
import com.tan.ec.utils.*;
import com.tan.ec.vo.CreateActiveOrderVo;
import com.tan.ec.vo.CreateOrderVo;
import com.thoughtworks.xstream.XStream;
import lombok.Data;
import lombok.experimental.var;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 微信支付
 */
@Slf4j
@RestController
@RequestMapping("/wxpay")
public class WXPayController extends BaseController {

    @Autowired
    private WXPayClient wxPayClient;

    @Autowired
    private WXPayService wxPayService;

    @Autowired
    private IBsOrderService bsOrderService;

    @Autowired
    private MyWXPayConfig config;


    /**
     * 获取用户授权 得到openId,accessToken
     *
     * @param code
     * @return
     */
    @GetMapping("/wechat")
    public String[] getOpenId(@RequestParam("code") String code) {
        String appid = "wx9c27e3b920bca5ff";
        String secret = "9710f788cd80cd9ae470beb7a5246a7c";
        String tokenUrl = "https://api.weixin.qq.com/sns/jscode2session?grant_type=authorization_code";
        tokenUrl = tokenUrl + "&appid=" + appid + "&secret=" + secret + "&js_code=" + code;
        log.info(tokenUrl);
        JSONObject json = null;
        try {
            String ret = HttpClientUtil.getInstance().get(tokenUrl);
            log.info(ret);
            json = JSONObject.parseObject(ret);
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        String openId = "";
        String session_key = "";
        String[] s = new String[2];
        if (json != null) {
            try {
                openId = json.getString("openid");
                session_key = json.getString("session_key");
                s[0] = openId;
                s[1] = session_key;
            } catch (JSONException e) {
                String errcode = null;
                try {
                    errcode = json.getString("errcode");
                    System.out.println("errcode================>手机登录 获取用户授权失败" + errcode);
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return s;
    }

    /**
     * 扫码支付 - 统一下单
     * 相当于支付不的电脑网站支付
     */
    @PostMapping("/order")
    public R<Map> precreate(HttpServletResponse response,@RequestBody @Valid CreateOrderVo orderVo) throws Exception {
        //创建新订单
        String oid = OrderCodeUtil.getOrderCode();
        String serialNumber = oid;
        BigDecimal amount = orderVo.getAmount();
        BigDecimal amountFen = amount.multiply(new BigDecimal("100"));
        String paramStr = wxPayClient.getReqStr(orderVo.getOpenId(),orderVo.getProId(),serialNumber,orderVo.getProTitle(),amountFen.stripTrailingZeros().toPlainString());

        String responseStr = HttpClientUtil.post("https://api.mch.weixin.qq.com/pay/unifiedorder",paramStr);
        log.info(responseStr);
        XStream xStream = new XStream();
        xStream.alias("xml", OrderReturnInfo.class);

        OrderReturnInfo returnInfo = (OrderReturnInfo)xStream.fromXML(responseStr);
        Map map = new HashMap();


        // 二次签名
        if ("SUCCESS".equals(returnInfo.getReturn_code()) && returnInfo.getReturn_code().equals(returnInfo.getResult_code())) {
            SignInfo signInfo = new SignInfo();
            signInfo.setAppId(config.getAppID());
            long time = System.currentTimeMillis()/1000;
            signInfo.setTimeStamp(String.valueOf(time));
            signInfo.setNonceStr(WXPayUtil.generateNonceStr());
            signInfo.setRepay_id("prepay_id="+returnInfo.getPrepay_id());
            signInfo.setSignType("MD5");
            //生成签名
            String sign1 = Signature.getSign(signInfo);
            Map payInfo = new HashMap();
            payInfo.put("timeStamp", signInfo.getTimeStamp());
            payInfo.put("nonceStr", signInfo.getNonceStr());
            payInfo.put("package", signInfo.getRepay_id());
            payInfo.put("signType", signInfo.getSignType());
            payInfo.put("paySign", sign1);
            map.put("status", 200);
            map.put("msg", "统一下单成功!");
            map.put("oid",oid);
            map.put("data", payInfo);

            // 此处可以写唤起支付前的业务逻辑
            orderVo.setBatchNo(oid);
            bsOrderService.createByVo(orderVo);

            // 业务逻辑结束
            return new R<>(map);
        }
        map.put("status", 500);
        map.put("msg", "统一下单失败!");
        map.put("data", null);
        return new R<>(map);
    }

    /**
     * 扫码支付 - 统一下单
     * 相当于支付不的电脑网站支付
     */
    @PostMapping("/activeOrder")
    public R<Map> activeOrder(HttpServletResponse response,@RequestBody @Valid CreateActiveOrderVo activeOrderVo) throws Exception {
        //创建新订单
        String oid = OrderCodeUtil.getOrderCode();
        String serialNumber = oid;
        BigDecimal amount = activeOrderVo.getAmount();
        BigDecimal amountFen = amount.multiply(new BigDecimal("100"));

        String paramStr = wxPayClient.getReqStr(activeOrderVo.getOpenId(),activeOrderVo.getProId(),serialNumber,activeOrderVo.getProTitle(),amountFen.stripTrailingZeros().toPlainString());

        String responseStr = HttpClientUtil.post("https://api.mch.weixin.qq.com/pay/unifiedorder",paramStr);
        log.info(responseStr);
        XStream xStream = new XStream();
        xStream.alias("xml", OrderReturnInfo.class);

        OrderReturnInfo returnInfo = (OrderReturnInfo)xStream.fromXML(responseStr);
        Map map = new HashMap();


        // 二次签名
        if ("SUCCESS".equals(returnInfo.getReturn_code()) && returnInfo.getReturn_code().equals(returnInfo.getResult_code())) {
            SignInfo signInfo = new SignInfo();
            signInfo.setAppId(config.getAppID());
            long time = System.currentTimeMillis()/1000;
            signInfo.setTimeStamp(String.valueOf(time));
            signInfo.setNonceStr(WXPayUtil.generateNonceStr());
            signInfo.setRepay_id("prepay_id="+returnInfo.getPrepay_id());
            signInfo.setSignType("MD5");
            //生成签名
            String sign1 = Signature.getSign(signInfo);
            Map payInfo = new HashMap();
            payInfo.put("timeStamp", signInfo.getTimeStamp());
            payInfo.put("nonceStr", signInfo.getNonceStr());
            payInfo.put("package", signInfo.getRepay_id());
            payInfo.put("signType", signInfo.getSignType());
            payInfo.put("paySign", sign1);
            map.put("status", 200);
            map.put("msg", "统一下单成功!");
            map.put("oid",oid);
            map.put("data", payInfo);

            // 此处可以写唤起支付前的业务逻辑
            activeOrderVo.setBatchNo(oid);
            bsOrderService.createByVo(activeOrderVo);

            // 业务逻辑结束
            return new R<>(map);
        }
        map.put("status", 500);
        map.put("msg", "统一下单失败!");
        map.put("data", null);
        return new R<>(map);
    }

    /**
     *
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/notify")
    public void precreateNotify(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, String> reqData = wxPayClient.getNotifyParameter(request);
        log.debug("微信支付回调成功:{}", reqData);

        // 特别提醒：商户系统对于支付结果通知的内容一定要做签名验证,并校验返回的订单金额是否与商户侧的订单金额一致，防止数据泄漏导致出现“假通知”，造成资金损失。
        boolean signatureValid = wxPayClient.isPayResultNotifySignatureValid(reqData);
        if (signatureValid) {
            /**
             * 注意：同样的通知可能会多次发送给商户系统。商户系统必须能够正确处理重复的通知。
             * 推荐的做法是，当收到通知进行处理时，首先检查对应业务数据的状态，
             * 判断该通知是否已经处理过，如果没有处理过再进行处理，如果处理过直接返回结果成功。
             * 在对业务数据进行状态检查和处理之前，要采用数据锁进行并发控制，以避免函数重入造成的数据混乱。
             */
            String returnCode = reqData.get("return_code");
            String resultCode = reqData.get("result_code");
            if (WXPayConstants.SUCCESS.equals(returnCode) && WXPayConstants.SUCCESS.equals(resultCode)) {
                String tradeNo = reqData.get("out_trade_no");
                String bsType = tradeNo.substring(0,2);
                switch (bsType){
                    case "OD":
                        wxPayService.doOrderNotify(reqData);
                        break;
                    case "CZ":
                        wxPayService.doRechargeNotify(reqData);
                        break;
                    default:
                        break;
                }
            }

            //回调成功后,通知前端
            Map<String, String> respMap = new HashMap<>();
            respMap.put(reqData.get("out_trade_no"), "SUCCESS");
            MasternodeWebSocket.sendInfo(JSON.toJSONString(respMap));

            Map<String, String> responseMap = new HashMap<>(2);
            responseMap.put("return_code", "SUCCESS");
            responseMap.put("return_msg", "OK");
            String responseXml = WXPayUtil.mapToXml(responseMap);

            response.setContentType("text/xml");
            response.getWriter().write(responseXml);
            response.flushBuffer();
        }
    }
}
