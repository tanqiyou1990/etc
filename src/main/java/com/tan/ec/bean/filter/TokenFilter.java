package com.tan.ec.bean.filter;

import com.alibaba.fastjson.JSONObject;
import com.tan.ec.constants.CommonConstant;
import com.tan.ec.constants.SecurityConstant;
import com.tan.ec.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * token调用API过滤器
 */
@Order(-1)
@WebFilter(filterName = "tokenFilter",urlPatterns = "*")
@Slf4j
public class TokenFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        //请求url
        String requestURI = request.getRequestURI();

        if (requestURI.startsWith("/admin/")){
            //放行,忽略路径
            filterChain.doFilter(request, response);
            return;
        }

        String token = request.getHeader(CommonConstant.REQ_HEADER);
        log.debug("传入的token:{}",token);
        log.debug("配置的token:{}",SecurityConstant.API_TOKEN);
        if (token == null || !token.equals(SecurityConstant.API_TOKEN)){
            R<String> result = new R<>();
            result.setCode(401);
            result.setSuccess(false);
            result.setMsg("无效的token");
            response.setStatus(401);
            response.setCharacterEncoding(CommonConstant.UTF8);
            response.setContentType(CommonConstant.CONTENT_TYPE);
            PrintWriter printWriter = response.getWriter();
            printWriter.print(JSONObject.toJSONString(result));
            return;
        }
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
