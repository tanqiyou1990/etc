package com.tan.ec.constants;

/**
 * 异常常量
 */
public interface ExceptionConstant {
    /**
     * 用户名不唯一的报错信息
     */
    String UNIQUE_EXCEPTION_USERNAME = "该用户名已被注册";
    /**
     * 手机号码不唯一的报错信息
     */
    String UNIQUE_EXCEPTION_PHONE = "该手机号已被注册";
    /**
     * 角色标识唯一的报错信息
     */
    String UNIQUE_EXCEPTION_ROLE_CODE = "角色标识必须唯一";
    /**
     *  表中建了用户名唯一
     */
    String UNIQUE_USER_INDEX="user_idx1_username";
    /**
     *  表中建了手机号唯一
     */
    String UNIQUE_PHONE_INDEX="user_idx2_phone";
    /**
     *  表中建了角色标识唯一
     */
    String UNIQUE_ROLE_INDEX="role_idx1_role_code";

    /**
     *  表中建了masternode id标识唯一
     */
    String UNIQUE_MN_ID_INDEX="mq_vps_create_idx2_mnid";
}
