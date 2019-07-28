package com.tan.ec.entity;

import java.math.BigDecimal;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author tan
 * @since 2019-05-14
 */
@TableName("bs_order")
public class BsOrder extends Model<BsOrder> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id",type = IdType.INPUT)
    private String id;

    @TableField("order_type")
    private String orderType;
    /**
     * 订单编号
     */
    @TableField("batch_no")
    private String batchNo;

    /**
     * 用户openid
     */
    @TableField("open_id")
    private String openId;
    /**
     * 产品ID
     */
    @TableField("pro_id")
    private String proId;
    /**
     * 小图URL
     */
    @TableField("pro_ico_url")
    private String proIcoUrl;
    /**
     * 商品名称
     */
    @TableField("pro_title")
    private String proTitle;
    /**
     * 商品描述
     */
    @TableField("pro_desc")
    private String proDesc;
    /**
     * 大图URL数组
     */
    @TableField("pro_img_url")
    private String proImgUrl;
    /**
     * 价格
     */
    private BigDecimal price;
    /**
     * 订单数量
     */
    private Integer quantity;
    /**
     * 金额
     */
    private BigDecimal amount;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;
    /**
     * 更新时间
     */
    @TableField("update_time")
    private Date updateTime;
    /**
     * 快递ID
     */
    @TableField("postal_id")
    private String postalId;

    /**
     * 是否已发货[0-未发货;1-已发货]
     */
    @TableField("send_flag")
    private String sendFlag;

    @TableField("pay_flag")
    private String payFlag;
    @TableField("del_flag")
    private String delFlag;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getProId() {
        return proId;
    }

    public void setProId(String proId) {
        this.proId = proId;
    }

    public String getProIcoUrl() {
        return proIcoUrl;
    }

    public void setProIcoUrl(String proIcoUrl) {
        this.proIcoUrl = proIcoUrl;
    }

    public String getProTitle() {
        return proTitle;
    }

    public void setProTitle(String proTitle) {
        this.proTitle = proTitle;
    }

    public String getProDesc() {
        return proDesc;
    }

    public void setProDesc(String proDesc) {
        this.proDesc = proDesc;
    }

    public String getProImgUrl() {
        return proImgUrl;
    }

    public void setProImgUrl(String proImgUrl) {
        this.proImgUrl = proImgUrl;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getPostalId() {
        return postalId;
    }

    public void setPostalId(String postalId) {
        this.postalId = postalId;
    }

    public String getSendFlag() {
        return sendFlag;
    }

    public void setSendFlag(String sendFlag) {
        this.sendFlag = sendFlag;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getPayFlag() {
        return payFlag;
    }

    public void setPayFlag(String payFlag) {
        this.payFlag = payFlag;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "BsOrder{" +
        ", id=" + id +
        ", batchNo=" + batchNo +
        ", proId=" + proId +
        ", proIcoUrl=" + proIcoUrl +
        ", proTitle=" + proTitle +
        ", proDesc=" + proDesc +
        ", proImgUrl=" + proImgUrl +
        ", price=" + price +
        ", quantity=" + quantity +
        ", amount=" + amount +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        ", postalId=" + postalId +
        ", sendFlag=" + sendFlag +
        ", delFlag=" + delFlag +
        "}";
    }
}
