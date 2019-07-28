package com.tan.ec.service;

import com.baomidou.mybatisplus.service.IService;
import com.tan.ec.entity.SysUser;

/**
 * <p>
 *  快递鸟
 * </p>
 *
 * @author tan
 * @since 2019-05-14
 */
public interface IKdniaoService {

    /**
     * Json方式 查询订单物流轨迹
     * @param expCode
     * @param expNo
     * @return
     * @throws Exception
     */
    String getOrderTracesByJson(String expCode, String expNo) throws Exception;

}
