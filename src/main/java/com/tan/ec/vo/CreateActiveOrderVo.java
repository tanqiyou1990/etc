package com.tan.ec.vo;

import com.tan.ec.entity.BsActivePerson;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author tan
 * @since 2019-05-14
 */
@Data
public class CreateActiveOrderVo {


    private String id;

    private String orderType;

    private String openId;
    /**
     * 订单编号
     */
    private String batchNo;

    /**
     * 产品ID
     */
    @NotBlank(message = "活动ID不能为空")
    private String proId;
    /**
     * 小图URL
     */
    private String proIcoUrl;
    /**
     * 商品名称
     */
    private String proTitle;
    /**
     * 商品描述
     */
    private String proDesc;
    /**
     * 大图URL数组
     */
    private String proImgUrl;
    /**
     * 价格
     */
    @NotNull(message = "价格不能为空")
    private BigDecimal price;
    /**
     * 订单数量
     */
    @NotNull(message = "订单数量不能为空")
    private Integer quantity;
    /**
     * 金额
     */
    @NotNull(message = "金额不能为空")
    private BigDecimal amount;


    @Valid
    private ActivePersonVo activePersonVo;

}
