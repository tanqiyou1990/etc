package com.tan.ec.vo;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by Administrator on 2019/4/26/026.
 */
@Data
public class UserLoginVo {

    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    private String userId;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    private String userPwd;


}
