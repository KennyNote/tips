package com.learning.notebook.tips.mvc.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AuthFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {
        log.info("[Filter Info] AuthFilter.init()");
    }

    @Override
    public void destroy() {
        log.info("[Filter Info] AuthFilter.destroy()");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
        throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String requestURI = request.getRequestURI();
        log.info("[Filter Info] AuthFilter.doFilter() start");

        // url 过滤
        if (requestURI.endsWith("admin")) {
            log.info("[Filter Info] AuthFilter.doFilter() url is baned");
            return;
        }
        chain.doFilter(servletRequest, servletResponse);
        log.info("[Filter Info] AuthFilter.doFilter() end");
    }
}
