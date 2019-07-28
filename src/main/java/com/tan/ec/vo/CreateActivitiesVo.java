package com.tan.ec.vo;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author tan
 * @since 2019-06-09
 */
@Data
public class CreateActivitiesVo {

    private String id;
    /**
     * 活动标题
     */
    @NotBlank(message = "活动名称不能为空")
    private String title;
    /**
     * 简要描述
     */
    @NotBlank(message = "描述信息不能为空")
    private String desc;
    /**
     * 小图URL
     */
    @NotBlank(message = "小图不能为空")
    private String icoUrl;

    /**
     * 轮播图
     */
    @NotBlank(message = "轮播图不能为空")
    private String imgUrl;
    /**
     * 价格
     */
    @NotNull(message = "价格不能为空")
    private BigDecimal price;
    /**
     * 活动内容
     */
    @NotBlank(message = "活动详细内容不能为空")
    private String content;
    /**
     * 发布标识[0-未发布；1-已发布]
     */
    private String publishFlag;

}
