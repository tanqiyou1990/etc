package com.tan.ec.service;

import java.util.Map;

public interface WXPayService {
    /**
     * 处理订单回调
     * @param map
     */
    void doOrderNotify(Map<String, String> map);

    /**
     * 处理充值回调
     * @param map
     */
    void doRechargeNotify(Map<String, String> map);
}
