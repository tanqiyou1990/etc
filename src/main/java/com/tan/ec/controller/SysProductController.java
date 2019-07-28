package com.tan.ec.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.tan.ec.entity.BsProduct;
import com.tan.ec.service.IBsProductService;
import com.tan.ec.utils.R;
import com.tan.ec.vo.CreateProductVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
@RequestMapping("/admin/pro")
public class SysProductController extends BaseController {


    @Autowired
    private IBsProductService bsProductService;

    /**
     * 创建产品
     * @param createProductVo
     * @param result
     * @return
     */
    @PostMapping("/create")
    public R<String> create(@RequestBody @Valid CreateProductVo createProductVo, BindingResult result){

        //校验参数
        if (result.hasErrors()){
            //返回错误校验信息
            return new R<>(null, false, result.getAllErrors().get(0).getDefaultMessage());
        }

        Integer ret = bsProductService.createByVo(createProductVo);

        if(ret>0){
            return new R<>("");
        }else {
            return new R<>(null,false,"新建商品失败");
        }
    }

    /**
     * 更新产品
     * @param createProductVo
     * @param result
     * @return
     */
    @PostMapping("/update")
    public R<String> update(@RequestBody @Valid CreateProductVo createProductVo, BindingResult result){

        //校验参数
        if (result.hasErrors()){
            //返回错误校验信息
            return new R<>(null, false, result.getAllErrors().get(0).getDefaultMessage());
        }

        Integer ret = bsProductService.updateByVo(createProductVo);

        if(ret>0){
            return new R<>("");
        }else {
            return new R<>(null,false,"更新商品失败");
        }
    }

    /**
     * 根据ID获取产品
     * @param pid
     * @return
     */
    @GetMapping("/get")
    public R<BsProduct> getById(@RequestParam("pid") String pid){
        return new R<>(bsProductService.selectById(pid));
    }

    /**
     * 根据ID删除产品
     * @param pid
     * @return
     */
    @GetMapping("/deleteById")
    public R<String> deleteById(@RequestParam("pid") String pid){

        BsProduct p = bsProductService.selectById(pid);
        p.setDelFlag("1");
        if(bsProductService.updateById(p)){
            return new R<>("");
        }else {
            return new R<>(null,false,"删除失败");
        }
    }

    /**
     * 根据ID发布产品
     * @param pid
     * @return
     */
    @GetMapping("/publishById")
    public R<String> publishById(@RequestParam("pid") String pid){

        BsProduct p = bsProductService.selectById(pid);
        p.setPublishFlag("1");
        if(bsProductService.updateById(p)){
            return new R<>("");
        }else {
            return new R<>(null,false,"发布失败");
        }
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
        String searchKey = param.getString("searchKey");

        if(!StringUtils.isEmpty(param.getString("pageNo"))){
            pageNo = param.getInteger("pageNo");
        }
        if(!StringUtils.isEmpty(param.getString("pageSize"))){
            pageSize = param.getInteger("pageSize");
        }

        EntityWrapper<BsProduct> entityWrapper = new EntityWrapper<>();
        if(!StringUtils.isEmpty(searchKey)){
            entityWrapper.where("title like '%{0}%'or desc like '%{0}%'",searchKey);
        }
        entityWrapper.andNew("del_flag = '0'");

        Page<BsProduct> page = new Page(pageNo,pageSize,"create_time");
        page.setAsc(false);

        page = bsProductService.selectPage(page,entityWrapper);

        return new R<>(page);

    }

}

