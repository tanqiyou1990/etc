package com.tan.ec.mapper;

import com.tan.ec.entity.BsActivities;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author tan
 * @since 2019-06-09
 */
public interface BsActivitiesMapper extends BaseMapper<BsActivities> {

    BsActivities getLatest();
}
