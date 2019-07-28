package com.tan.ec.bean.filter;

import com.tan.ec.utils.MutableHttpServletRequest;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Order(1)
@WebFilter(filterName = "accessFilter",urlPatterns = "/*")
public class AccessFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        MutableHttpServletRequest mutableRequest = new MutableHttpServletRequest(request);

        filterChain.doFilter(mutableRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
