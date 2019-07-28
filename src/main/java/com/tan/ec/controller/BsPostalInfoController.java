package com.tan.ec.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.tan.ec.entity.BsOrder;
import com.tan.ec.entity.BsPostalList;
import com.tan.ec.entity.SysPostalCompany;
import com.tan.ec.service.IBsOrderService;
import com.tan.ec.service.IBsPostalListService;
import com.tan.ec.service.ISysPostalCompanyService;
import com.tan.ec.utils.KdniaoUtil;
import com.tan.ec.utils.R;
import com.tan.ec.vo.CreateOrderVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
@RequestMapping("/bs/postall")
public class BsPostalInfoController extends BaseController {

    @Autowired
    private IBsPostalListService bsPostalListService;

    @Autowired
    private ISysPostalCompanyService postalCompanyService;

    @Autowired
    private IBsOrderService bsOrderService;


    /**
     * 保存快递信息
     * @param postalList
     * @return
     */
    @PostMapping("/savePostInfo")
    public R<String> create(@RequestBody BsPostalList postalList){

        EntityWrapper<BsOrder> orderEntityWrapper = new EntityWrapper<>();
        orderEntityWrapper.eq("batch_no",postalList.getBatchNo());
        BsOrder order = bsOrderService.selectOne(orderEntityWrapper);

        order.setPostalId(postalList.getId());
        order.setSendFlag("1");

        bsOrderService.updateById(order);

        bsPostalListService.updateById(postalList);

        return new R<>("保存成功");

    }

    /**
     * 查询订单快递
     * @param pid
     * @return
     */
    @GetMapping("/getPostInfo/{pid}")
    public R<JSONObject> getPostInfo(@PathVariable("pid") String pid){
        BsPostalList p = bsPostalListService.selectById(pid);
        SysPostalCompany postalCompany = postalCompanyService.selectById(p.getPostalCompanyId());
        KdniaoUtil kdniaoUtil = new KdniaoUtil();
        try {
            String result =  kdniaoUtil.getOrderTracesByJson(postalCompany.getCode(),p.getExpressNo());
            return new R<>(JSONObject.parseObject(result));
        } catch (Exception e) {
            e.printStackTrace();
            return new R<>(null,false,"查询出现错误!");
        }

    }
}

