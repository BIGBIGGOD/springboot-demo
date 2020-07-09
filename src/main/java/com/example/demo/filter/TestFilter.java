package com.example.demo.filter;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.FilterConfig;

/**
 * @author jiangqingdong
 * @version v1.0
 * @Description
 * @DATE 2020/7/9 0009 12:19
 * @Copyright Copyright © 2019 深圳花儿绽放网络科技股份有限公司. All rights reserved.
 */
public class TestFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("TestFilter的doFilter方法");
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
    }
}
