package com.tan.ec.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.tan.ec.entity.BsOrder;
import com.tan.ec.entity.BsProduct;
import com.tan.ec.service.IBsOrderService;
import com.tan.ec.utils.R;
import com.tan.ec.vo.CreateOrderVo;
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
@RequestMapping("/admin/order")
public class SysOrderController extends BaseController {

    @Autowired
    private IBsOrderService bsOrderService;

    /**
     * 创建订单
     * @param createOrderVo
     * @param result
     * @return
     */
    @PostMapping("/create")
    public R<String> create(@RequestBody @Valid CreateOrderVo createOrderVo, BindingResult result){

        //校验参数
        if (result.hasErrors()){
            //返回错误校验信息
            return new R<>(null, false, result.getAllErrors().get(0).getDefaultMessage());
        }

        Integer ret = bsOrderService.createByVo(createOrderVo);

        if(ret>0){
            return new R<>("");
        }else {
            return new R<>(null,false,"新建订单失败");
        }
    }

    /**
     * 更新产品
     * @param createOrderVo
     * @param result
     * @return
     */
    @PostMapping("/update")
    public R<String> update(@RequestBody @Valid CreateOrderVo createOrderVo, BindingResult result){

        //校验参数
        if (result.hasErrors()){
            //返回错误校验信息
            return new R<>(null, false, result.getAllErrors().get(0).getDefaultMessage());
        }

        Integer ret = bsOrderService.updateByVo(createOrderVo);

        if(ret>0){
            return new R<>("");
        }else {
            return new R<>(null,false,"更新订单失败");
        }
    }

    /**
     * 根据ID获取订单
     * @param oid
     * @return
     */
    @GetMapping("/get")
    public R<BsOrder> getById(@RequestParam("oid") String oid){
        return new R<>(bsOrderService.selectById(oid));
    }

    /**
     * 根据ID删除产品
     * @param oid
     * @return
     */
    @GetMapping("/deleteById")
    public R<String> deleteById(@RequestParam("oid") String oid){

        BsOrder o = bsOrderService.selectById(oid);
        o.setDelFlag("1");
        if(bsOrderService.updateById(o)){
            return new R<>("");
        }else {
            return new R<>(null,false,"删除失败");
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
        String orderType = param.getString("orderType");

        if(!StringUtils.isEmpty(param.getString("pageNo"))){
            pageNo = param.getInteger("pageNo");
        }
        if(!StringUtils.isEmpty(param.getString("pageSize"))){
            pageSize = param.getInteger("pageSize");
        }

        Page page = new Page(pageNo,pageSize,"o.create_time");
        page.setAsc(false);
        if(orderType.equals("1")){
            page = bsOrderService.selectOrderPostal(page);
        }
        if(orderType.equals("2")){
            page = bsOrderService.selectOrderActive(page);
        }
        return new R<>(page);

    }

}

