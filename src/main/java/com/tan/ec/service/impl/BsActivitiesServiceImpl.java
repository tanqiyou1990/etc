package com.tan.ec.service.impl;

import com.tan.ec.entity.BsActivities;
import com.tan.ec.mapper.BsActivitiesMapper;
import com.tan.ec.service.IBsActivitiesService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author tan
 * @since 2019-06-09
 */
@Service
public class BsActivitiesServiceImpl extends ServiceImpl<BsActivitiesMapper, BsActivities> implements IBsActivitiesService {

    @Override
    public BsActivities getLatest() {
        return baseMapper.getLatest();
    }
}
