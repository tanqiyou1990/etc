package com.tan.ec.vo;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

/**
 * <p>
 * 
 * </p>
 *
 * @author tan
 * @since 2019-05-14
 */
@Data
public class ActivePersonVo {


    private String id;
    /**
     * 订单编号
     */
    private String batchNo;

    /**
     * 联系人姓名
     */
    @NotBlank(message = "联系人姓名不能为空")
    private String name;
    /**
     * 公司名称
     */
    @NotBlank(message = "公司名称不能为空")
    private String company;
    /**
     * 职位
     */
    @NotBlank(message = "职位不能为空")
    private String job;
    /**
     * 联系电话
     */
    @NotBlank(message = "联系电话不能为空")
    private String phone;
    /**
     * 联系邮箱
     */
    @NotBlank(message = "联系邮箱不能为空")
    private String email;

}
