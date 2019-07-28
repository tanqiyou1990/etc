package com.tan.ec.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.tan.ec.constants.CommonConstant;
import com.tan.ec.entity.SysUser;
import com.tan.ec.service.ISysUserService;
import com.tan.ec.utils.R;
import com.tan.ec.vo.UserLoginVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author tan
 * @since 2019-04-23
 */
@RestController
@Slf4j
@RequestMapping("/admin/sysUser")
public class SysUserController extends BaseController {


    @Autowired
    private ISysUserService sysUserService;


    /**
     * 后台登陆
     * @param userLoginVo
     * @param result
     * @return
     */
    @PostMapping("/login")
    public R<SysUser> login(@RequestBody @Valid UserLoginVo userLoginVo, BindingResult result){

        //校验参数
        if (result.hasErrors()){
            //返回错误校验信息
            return new R<>(null, false, result.getAllErrors().get(0).getDefaultMessage());
        }

        EntityWrapper<SysUser> sysUserEntityWrapper = new EntityWrapper<>();
        sysUserEntityWrapper.eq("user_id",userLoginVo.getUserId());
        sysUserEntityWrapper.eq("password",userLoginVo.getUserPwd());
        sysUserEntityWrapper.eq("del_flag","0");

        SysUser u = sysUserService.selectOne(sysUserEntityWrapper);

        if(u != null){
            //登录成功
            HttpSession session = request.getSession();
            session.setAttribute(CommonConstant.SESSION_LOGIN_NAME, u.getUserId());
            return new R<>(u);
        }else {
            return new R<>(null,false,"用户或密码错误!");
        }
    }

    /**
     * 更新系统用户
     * @param u
     * @return
     */
    @PostMapping("/updateInfo")
    public R<SysUser> updateInfo(@RequestBody SysUser u){

        sysUserService.updateById(u);

        return new R<>(u);

    }

    /**
     * 根据id查找用户资料
     * @return
     */
    @GetMapping("/info")
    public R<SysUser> info(){
        String userId = (String) request.getSession().getAttribute(CommonConstant.SESSION_LOGIN_NAME);
        EntityWrapper<SysUser> sysUserEntityWrapper = new EntityWrapper<>();
        sysUserEntityWrapper.eq("user_id",userId);
        sysUserEntityWrapper.eq("del_flag","0");
        SysUser u = sysUserService.selectOne(sysUserEntityWrapper);
        return new R<>(u);
    }

    /**
     * 注销用户
     * @param userId
     * @return
     */
    @GetMapping("/logout")
    public R<Boolean> logout(@RequestParam("userId") String userId){

        HttpSession session = request.getSession();
        session.removeAttribute(CommonConstant.SESSION_LOGIN_NAME);

        return new R<>(true);

    }


}

