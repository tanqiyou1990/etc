package com.tan.ec.controller;


import com.alibaba.druid.sql.visitor.functions.Ascii;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.tan.ec.entity.BsProduct;
import com.tan.ec.service.IBsProductService;
import com.tan.ec.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author tan
 * @since 2019-05-14
 */
@RestController
@Slf4j
@RequestMapping("/pro")
public class BsProductController extends BaseController {


    @Autowired
    private IBsProductService bsProductService;

    /**
     * 用户加载商品列表
     * @param param
     * @return
     */
    @PostMapping("/pageList")
    public R<Page> pageList(@RequestBody JSONObject param){
        Integer pageSize = 10;
        Integer pageNum = 1;
        if(param.get("pageSize")!=null){
            pageSize = param.getInteger("pageSize");
        }

        if(param.get("pageNum")!=null){
            pageNum = param.getInteger("pageNum");
        }

        EntityWrapper<BsProduct> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("del_flag","0");
        entityWrapper.eq("publish_flag","1");

        Page page = new Page(pageNum,pageSize,"create_time");
        page.setAsc(false);

        return new R<>(bsProductService.selectPage(page,entityWrapper));
    }

    /**
     * 根据ID查询详情
     * @param pid
     * @return
     */
    @GetMapping("/getbyId")
    public R<BsProduct> getById(@RequestParam("pid") String pid){
        return new R<>(bsProductService.selectById(pid));
    }


    /**
     * 获取最新的产品信息
     * @return
     */
    @GetMapping("/getLatest")
    public R<BsProduct> getLatest(){
        return new R<>(bsProductService.getLatest());
    }
}

