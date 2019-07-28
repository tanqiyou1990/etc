package com.tan.ec.service;

import com.tan.ec.entity.BsActivities;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author tan
 * @since 2019-06-09
 */
public interface IBsActivitiesService extends IService<BsActivities> {

    /**
     * 获取最新一期活动详情
     * @return
     */
    BsActivities getLatest();
}
