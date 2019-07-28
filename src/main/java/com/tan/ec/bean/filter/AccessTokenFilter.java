package com.tan.ec.bean.filter;


import com.tan.ec.constants.CommonConstant;
import com.tan.ec.utils.MutableHttpServletRequest;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 忽略路径的access_token过滤
 */
public class AccessTokenFilter implements Filter {


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //去掉access_token
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        if (!"/user/removeToken".equals(request.getRequestURI())){
            MutableHttpServletRequest.removeHeader(request, CommonConstant.REQ_HEADER);
        }
        filterChain.doFilter(request, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
