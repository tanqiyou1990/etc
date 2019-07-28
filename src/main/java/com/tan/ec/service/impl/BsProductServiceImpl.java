package com.tan.ec.service.impl;

import com.tan.ec.entity.BsProduct;
import com.tan.ec.mapper.BsProductMapper;
import com.tan.ec.service.IBsProductService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.tan.ec.utils.IdUtil;
import com.tan.ec.vo.CreateProductVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author tan
 * @since 2019-05-14
 */
@Service
public class BsProductServiceImpl extends ServiceImpl<BsProductMapper, BsProduct> implements IBsProductService {

    @Override
    public Integer createByVo(CreateProductVo createProductVo) {
        BsProduct product = new BsProduct();
        BeanUtils.copyProperties(createProductVo,product);
        product.setId(IdUtil.generateUuid());
        product.setPublishFlag(createProductVo.getPublishFlag() ? "1":"0");
        return baseMapper.insert(product);
    }

    @Override
    public Integer updateByVo(CreateProductVo createProductVo) {
        BsProduct product = new BsProduct();
        BeanUtils.copyProperties(createProductVo,product);
        product.setPublishFlag(createProductVo.getPublishFlag() ? "1":"0");
        return baseMapper.updateById(product);
    }

    @Override
    public BsProduct getLatest() {
        return baseMapper.getLatest();
    }
}
