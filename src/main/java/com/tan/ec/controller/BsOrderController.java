package com.tan.ec.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.tan.ec.entity.BsActivePerson;
import com.tan.ec.entity.BsOrder;
import com.tan.ec.entity.BsPostalList;
import com.tan.ec.service.IBsActivePersonService;
import com.tan.ec.service.IBsOrderService;
import com.tan.ec.service.IBsPostalListService;
import com.tan.ec.utils.R;
import com.tan.ec.vo.CreateActiveOrderVo;
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
@RequestMapping("/bs/order")
public class BsOrderController extends BaseController {

    @Autowired
    private IBsOrderService bsOrderService;

    @Autowired
    private IBsPostalListService bsPostalListService;

    @Autowired
    private IBsActivePersonService bsActivePersonService;


    /**
     * 创建销售订单
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
     * 取消订单
     * @param oid
     */
    @GetMapping("/cancelOrder/{oid}")
    public void cancelOrder(@PathVariable("oid") String oid){
        EntityWrapper<BsOrder> orderEntityWrapper = new EntityWrapper<>();
        orderEntityWrapper.eq("batch_no",oid);
        BsOrder o = bsOrderService.selectOne(orderEntityWrapper);
        o.setDelFlag("1");

        if("1".equals(o.getOrderType())){
            EntityWrapper<BsPostalList> entityWrapper = new EntityWrapper<>();
            entityWrapper.eq("batch_no",oid);
            BsPostalList p = bsPostalListService.selectOne(entityWrapper);
            p.setDelFlag("1");
            bsPostalListService.updateById(p);
            bsOrderService.updateById(o);
        }

//        if("2".equals(o.getOrderType())){
//            EntityWrapper<BsActivePerson> entityWrapper = new EntityWrapper<>();
//            entityWrapper.eq("batch_no",oid);
//            BsActivePerson a = bsActivePersonService.selectOne(entityWrapper);
//            a.setDelFlag("1");
//            bsActivePersonService.updateById(a);
//        }

    }


    /**
     * 更新支付成功标识
     * @param oid
     * @return
     */
    @GetMapping("/payflag/{oid}")
    public R<String> payflag(@PathVariable("oid") String oid){

        if(StringUtils.isEmpty(oid)){
            return new R<>(null,false,"参数有误");
        }
        bsOrderService.payflag(oid);

        return new R<>("更新成功");
    }

    /**
     * 根据OpenId获取订单列表
     * @param openId
     * @return
     */
    @GetMapping("/getOrder/{openId}")
    public R<List> getOrder(@PathVariable("openId") String openId){

        EntityWrapper<BsOrder> orderEntityWrapper = new EntityWrapper<>();
        orderEntityWrapper.eq("open_id",openId);
        orderEntityWrapper.eq("del_flag","0");

        return new R<>(bsOrderService.selectList(orderEntityWrapper));

    }

    /**
     * 根据ID获取订单详情
     * @param id
     * @return
     */
    @GetMapping("/getById")
    public R<Object> getById(@RequestParam String id){
        return new R<>(bsOrderService.selectById(id));
    }
}

