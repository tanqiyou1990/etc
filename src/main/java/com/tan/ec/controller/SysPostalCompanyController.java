package com.tan.ec.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.tan.ec.entity.SysPostalCompany;
import com.tan.ec.service.ISysPostalCompanyService;
import com.tan.ec.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/admin/postalCompany")
public class SysPostalCompanyController extends BaseController {

    @Autowired
    private ISysPostalCompanyService sysPostalCompanyService;

    /**
     * 根据ID获取订单
     * @return
     */
    @GetMapping("/getList")
    public R<List> getById(){
        EntityWrapper<SysPostalCompany> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("del_flag","0");
        return new R<>(sysPostalCompanyService.selectList(entityWrapper));
    }

}

