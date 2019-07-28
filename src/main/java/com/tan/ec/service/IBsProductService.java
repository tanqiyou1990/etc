package com.tan.ec.service;

import com.tan.ec.entity.BsProduct;
import com.baomidou.mybatisplus.service.IService;
import com.tan.ec.vo.CreateProductVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author tan
 * @since 2019-05-14
 */
public interface IBsProductService extends IService<BsProduct> {

    /**
     * 根据VO创建产品
     * @param createProductVo
     * @return
     */
    Integer createByVo(CreateProductVo createProductVo);

    /**
     * 根据VO更新产品
     * @param createProductVo
     * @return
     */
    Integer updateByVo(CreateProductVo createProductVo);

    /**
     * 获取最新的产品信息
     * @return
     */
    BsProduct getLatest();
}
