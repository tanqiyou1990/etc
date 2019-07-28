package com.tan.ec.mapper;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.tan.ec.entity.BsOrder;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author tan
 * @since 2019-05-14
 */
public interface BsOrderMapper extends BaseMapper<BsOrder> {

    List<HashMap> selectOrderPostal(Pagination page);

    List<HashMap> selectOrderActive(Pagination page);

    Integer payflag(@Param("oid") String oid);
}
