package com.tan.ec.constants;

import java.math.BigDecimal;

public interface CommonConstant {
    String TITLE = "久零区块链";

    /**
     * token请求头名称
     */
    String REQ_HEADER = "apiToken";

    /**
     * 数字资产应用前缀
     */
    String DIGITAL_ASSET = "90BC_DIGITAL_ASSET";

    /**
     * 基础账户地址
     */
    String ASSET_BASEACCOUNT_ADDRESS = "PMLZqiSg8waqdv5f4TQsAMe5qNhPefiTG7";

    /**
     * 基础账户名称
     */
    String ASSET_BASEACCOUNT_ACCOUNT = "90BC_DIGITAL_ASSET_BASEACCOUNT";


    /**
     * 链上资产与线下券的兑换关系
     */
    BigDecimal ASSET_EX_RATE = new BigDecimal("1000");


    /**
     * 溯源上链场景
     */
    String SOURCE_UPLOAD = "90BC_SOURCE_UPLOAD";

    /**
     * 溯源交易金额
     */
    String SOURCE_AMOUNT = "0.1";


    /**
     * 编码
     */
    String UTF8 = "UTF-8";

    /**
     * JSON 资源
     */
    String CONTENT_TYPE = "application/json; charset=utf-8";

    /**
     * 用户名
     */
    String SESSION_LOGIN_NAME = "SESSION_LOGIN_NAME";

}
