package com.tan.ec.entity;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

/**
 * Created by Administrator on 2019/6/2/002.
 */
@Data
public class SignInfo {

    private String appId;
    private String timeStamp;//时间戳
    private String nonceStr;//随机串

    @XStreamAlias("package")
    private String repay_id;
    private String signType;//签名方式
}
