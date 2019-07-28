package com.tan.ec.entity;

import lombok.Data;

/**
 * Created by Administrator on 2019/6/2/002.
 */
@Data
public class OrderReturnInfo {
    private String return_code;
    private String return_msg;
    private String result_code;
    private String appid;
    private String mch_id;
    private String nonce_str;
    private String sign;
    private String prepay_id;
    private String trade_type;
}
