package com.tan.ec.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

/**
 * Created by Tan on 2019/1/24/024.
 */
public class OrderCodeUtil {
    /** 订单类别头 */
    private static final String ORDER_CODE = "OD";
    /** 退货类别头 */
     private static final String RETURN_ORDER = "TH";
    /** 退款类别头 */
    private static final String REFUND_ORDER = "TK";
    /** 未付款重新支付别头 */
    private static final String AGAIN_ORDER = "ZF";
    /** 充值类别头*/
    private static final String RECHARGE_CODE = "CZ";
    /** 随即编码 */
    private static final int[] r = new int[]{7, 9, 6, 2, 8 , 1, 3, 0, 5, 4};
    /** 用户id和随机数总长度 */
    private static final int maxLength = 8;

    /**
    * 更具id进行加密+加随机数组成固定长度编码
    */
    private static String toCode(Long id) {
        String idStr = id.toString();
        StringBuilder idsbs = new StringBuilder();
        for (int i = idStr.length() - 1 ; i >= 0; i--) {
            idsbs.append(r[idStr.charAt(i)-'0']);
        }
        return idsbs.append(getRandom(maxLength - idStr.length())).toString();
    }

    /**
    * 生成时间戳
    */
    private static String getDateTime(){
        DateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        return sdf.format(new Date());
    }

    /**
    * 生成固定长度随机码
    * @param n    长度
    */
    private static long getRandom(long n) {
        long min = 1,max = 9;
        for (int i = 1; i < n; i++) {
            min *= 10;
            max *= 10;
        }
        long rangeLong = (((long) (new Random().nextDouble() * (max - min)))) + min ;
        return rangeLong;
    }

    /**
    * 生成不带类别标头的编码
    * @param n
    */
    private static synchronized String getCode(Long n){
        n = n == null ? 10000 : n;
        return getDateTime() + toCode(n);
    }

    /**
    * 生成订单单号编码
    */
    public synchronized static String getOrderCode(){
        return ORDER_CODE + getCode(getRandom(6));
    }

    /**
    * 生成退货单号编码
    */
    public synchronized  static String getReturnCode(){
        return RETURN_ORDER + getCode(getRandom(6));
    }

    /**
    * 生成退款单号编码
    */
    public synchronized  static String getRefundCode(){
        return REFUND_ORDER + getCode(getRandom(6));
    }

    /**
    * 未付款重新支付
    */
    public synchronized  static String getAgainCode(){
        return AGAIN_ORDER + getCode(getRandom(6));
    }

    /**
     * 生成充值订单编码
     */
    public synchronized  static String getRechargeCode(){
        return RECHARGE_CODE + getCode(getRandom(6));
    }

}
