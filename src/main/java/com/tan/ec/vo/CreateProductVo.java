package com.tan.ec.vo;

import com.baomidou.mybatisplus.annotations.TableField;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Administrator on 2019/4/26/026.
 */
@Data
public class CreateProductVo {

    private String id;

    /**
     * 产品标题
     */
    @NotBlank(message = "商品名称不能为空")
    private String title;
    /**
     * 简要描述
     */
    @NotBlank(message = "描述信息不能为空")
    private String desc;


    @NotBlank(message = "详细描述不能为空")
    private String content;

    /**
     * 小图URL
     */
    @NotBlank(message = "小图不能为空")
    private String icoUrl;
    /**
     * 大图URL数组
     */
    @NotBlank(message = "轮播图片不能为空")
    private String imgUrl;
    /**
     * 价格
     */
    @NotNull(message = "价格不能为空")
    private BigDecimal price;

    /**
     * 是否发布
     */
    private Boolean publishFlag;

}
