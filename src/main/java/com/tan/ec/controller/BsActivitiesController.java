package com.tan.ec.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.tan.ec.entity.BsActivities;
import com.tan.ec.service.IBsActivitiesService;
import com.tan.ec.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author tan
 * @since 2019-06-09
 */
@RestController
@Slf4j
@RequestMapping("/active")
public class BsActivitiesController extends BaseController {

    @Autowired
    private IBsActivitiesService bsActivitiesService;


    /**
     * 获取列表
     * @param param
     * @return
     */
    @PostMapping("/pageList")
    public R<Page> pageList(@RequestBody JSONObject param){
        Integer pageNo = 1;
        Integer pageSize = 10;

        if(!org.apache.commons.lang3.StringUtils.isEmpty(param.getString("pageNo"))){
            pageNo = param.getInteger("pageNo");
        }
        if(!org.apache.commons.lang3.StringUtils.isEmpty(param.getString("pageSize"))){
            pageSize = param.getInteger("pageSize");
        }

        EntityWrapper<BsActivities> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("del_flag","0");
        entityWrapper.eq("publish_flag","1");

        Page page = new Page(pageNo,pageSize,"create_time");
        page.setAsc(false);
        page = bsActivitiesService.selectPage(page,entityWrapper);
        return new R<>(page);
    }

    /**
     * 根据ID获取数据
     * @param id
     * @return
     */
    @GetMapping("/getById")
    public R<Object> getById(@RequestParam String id){
        return new R<>(bsActivitiesService.selectById(id));
    }

    /**
     * 获取最新的产品信息
     * @return
     */
    @GetMapping("/getLatest")
    public R<Object> getLatest(){
        return new R<>(bsActivitiesService.getLatest());
    }

}

