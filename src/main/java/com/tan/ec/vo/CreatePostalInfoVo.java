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
public class CreatePostalInfoVo {


    private String id;
    /**
     * 订单编号
     */
    private String batchNo;
    /**
     * 收件人名字
     */
    @NotBlank(message = "收件人姓名不能为空")
    private String receiveName;
    /**
     * 收件人电话
     */
    @NotBlank(message = "收件人电话不能为空")
    private String receiveTel;
    /**
     * 收件省份
     */
    @NotBlank(message = "省份不能为空")
    private String province;
    /**
     * 市
     */
    @NotBlank(message = "城市不能为空")
    private String city;
    /**
     * 区
     */
    @NotBlank(message = "区县不能为空")
    private String district;
    /**
     * 详细地址
     */
    @NotBlank(message = "详细地址不能为空")
    private String address;


    /**
     * 快递公司ID
     */
    private Integer postalCompanyId;
    /**
     * 快递编号
     */
    private String expressNo;

}
