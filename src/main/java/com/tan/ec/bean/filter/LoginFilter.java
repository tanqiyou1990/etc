package com.tan.ec.bean.filter;

import com.alibaba.fastjson.JSONObject;
import com.tan.ec.constants.CommonConstant;
import com.tan.ec.utils.R;
import com.tan.ec.constants.ResultCode;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

/**
 * 登录过滤器
 */
@Slf4j
public class LoginFilter implements Filter {

    private Set<String> excludesPattern = new HashSet<>();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession(false);

        //请求url
        String requestURI = request.getRequestURI();

        log.debug("请求路径：{}",requestURI);

        if (excludesPattern.contains(requestURI)){
            //放行,忽略路径
            filterChain.doFilter(request, response);
            return;
        }

        if(!requestURI.startsWith("/admin/")){
            //放行,忽略路径
            filterChain.doFilter(request, response);
            return;
        }

        if (session == null) {
            R<String> result = new R<>();
            result.setCode(401);
            result.setSuccess(false);
            result.setMsg(ResultCode.UNAUTHORIZED.getMessage());
            response.setStatus(401);
            response.setCharacterEncoding(CommonConstant.UTF8);
            response.setContentType(CommonConstant.CONTENT_TYPE);
            PrintWriter printWriter = response.getWriter();
            printWriter.print(JSONObject.toJSONString(result));
            return;
        }
        //放行
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Enumeration<String> initParameterNames = filterConfig.getInitParameterNames();
        while (initParameterNames.hasMoreElements()){
            excludesPattern.add(initParameterNames.nextElement());
        }
    }
}

