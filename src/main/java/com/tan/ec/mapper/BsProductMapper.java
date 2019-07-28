package com.tan.ec.mapper;

import com.tan.ec.entity.BsProduct;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author tan
 * @since 2019-05-14
 */
public interface BsProductMapper extends BaseMapper<BsProduct> {

    BsProduct getLatest();
}
