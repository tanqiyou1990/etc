package com.tan.ec.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.tan.ec.entity.BsOrder;
import com.tan.ec.entity.BsPostalList;
import com.tan.ec.service.IBsOrderService;
import com.tan.ec.service.IBsPostalListService;
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
@RequestMapping("/admin/postall")
public class SysPostallInfoController extends BaseController {

    @Autowired
    private IBsPostalListService bsPostalListService;



    /**
     * 根据ID获取订单
     * @return
     */
    @GetMapping("/get/{batchNo}")
    public R<BsPostalList> getById(@PathVariable("batchNo") String batchNo){
        EntityWrapper<BsPostalList> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("batch_no",batchNo);
        entityWrapper.eq("del_flag","0");
        return new R<>(bsPostalListService.selectOne(entityWrapper));
    }


}

