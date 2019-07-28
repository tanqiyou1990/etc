package com.tan.ec.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.tan.ec.entity.BsOrder;
import com.baomidou.mybatisplus.service.IService;
import com.tan.ec.vo.CreateActiveOrderVo;
import com.tan.ec.vo.CreateOrderVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author tan
 * @since 2019-05-14
 */
public interface IBsOrderService extends IService<BsOrder> {

    /**
     * 根据VO创建订单
     * @param createOrderVo
     * @return
     */
    Integer createByVo(CreateOrderVo createOrderVo);


    /**
     * 根据VO创建活动订单
     * @param createActiveOrderVo
     * @return
     */
    Integer createByVo(CreateActiveOrderVo createActiveOrderVo);

    /**
     * 根据VO更新订单
     * @param createOrderVo
     * @return
     */
    Integer updateByVo(CreateOrderVo createOrderVo);

    /**
     * 分页查询订单及收货人信息
     * @param page
     * @return
     */
    Page selectOrderPostal(Page page);

    /**
     * 分页活动订单
     * @param page
     * @return
     */
    Page selectOrderActive(Page page);

    /**
     * 支付标识
     * @param oid
     * @return
     */
    Integer payflag(String oid);
}
