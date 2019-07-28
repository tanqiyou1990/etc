package com.tan.ec.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.tan.ec.entity.BsActivities;
import com.tan.ec.service.IBsActivitiesService;
import com.tan.ec.utils.IdUtil;
import com.tan.ec.utils.R;
import com.tan.ec.vo.CreateActivitiesVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
@RequestMapping("/admin/active")
public class SysActivitiesController extends BaseController {

    @Autowired
    private IBsActivitiesService bsActivitiesService;


    /**
     * 创建一个活动
     * @param createActivitiesVo
     * @param result
     * @return
     */
    @PostMapping("/create")
    public R<Object> createActive(@RequestBody @Valid CreateActivitiesVo createActivitiesVo, BindingResult result){

        //校验参数
        if (result.hasErrors()){
            //返回错误校验信息
            return new R<>(null, false, result.getAllErrors().get(0).getDefaultMessage());
        }
        BsActivities activities = new BsActivities();
        BeanUtils.copyProperties(createActivitiesVo,activities);
        activities.setId(IdUtil.generateUuid());

        bsActivitiesService.insert(activities);

        return new R<>(activities);

    }


    /**
     * 更新一个活动
     * @param createActivitiesVo
     * @param result
     * @return
     */
    @PostMapping("/update")
    public R<Object> updateActive(@RequestBody @Valid CreateActivitiesVo createActivitiesVo, BindingResult result){

        //校验参数
        if (result.hasErrors()){
            //返回错误校验信息
            return new R<>(null, false, result.getAllErrors().get(0).getDefaultMessage());
        }
        if(StringUtils.isEmpty(createActivitiesVo.getId())){
            return new R<>(null,false,"活动ID不能为空!");
        }

        BsActivities activities = new BsActivities();
        BeanUtils.copyProperties(createActivitiesVo,activities);
        bsActivitiesService.updateById(activities);

        return new R<>(activities);

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
     * 根据Id 删除
     * @param id
     * @return
     */
    @GetMapping("/deleteById")
    public R<Object> deleteById(@RequestParam String id){
        BsActivities activities = bsActivitiesService.selectById(id);
        activities.setDelFlag("1");
        return new R<>(bsActivitiesService.updateById(activities));
    }

    /**
     * 获取列表
     * @param param
     * @return
     */
    @PostMapping("/page")
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

        Page page = new Page(pageNo,pageSize,"create_time");
        page.setAsc(false);
        page = bsActivitiesService.selectPage(page,entityWrapper);
        return new R<>(page);
    }


    /**
     * 发布活动
     * @param id
     * @return
     */
    @GetMapping("/publishById")
    public R<Object> publish(@RequestParam String id){
        BsActivities activities = bsActivitiesService.selectById(id);
        activities.setPublishFlag("1");
        return new R<>(bsActivitiesService.updateById(activities));
    }


}

