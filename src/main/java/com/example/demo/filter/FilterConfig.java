package com.example.demo.filter;

import javax.servlet.Filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.filter.DelegatingFilterProxy;

/**
 * @author jiangqingdong
 * @version v1.0
 * @Description springboot中过滤器的使用
 * @DATE 2020/7/9 0009 12:21
 * @Copyright Copyright © 2019 深圳花儿绽放网络科技股份有限公司. All rights reserved.
 */
@Configuration
public class FilterConfig {

//    /**
//     * 注册一个filter
//     * @return
//     */
//    @Bean
//    public FilterRegistrationBean registryTestFilter() {
//        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
//        filterRegistrationBean.setFilter(new TestFilter());
//        filterRegistrationBean.addUrlPatterns("/*");
//        return filterRegistrationBean;
//    }

    @Bean
    public Filter testFilter() {
        return new TestFilter();
    }

    /**
     * 注册一个filter
     * @return
     */
    @Bean
    public FilterRegistrationBean<DelegatingFilterProxy> registryTestFilter() {
        FilterRegistrationBean<DelegatingFilterProxy> registration = new FilterRegistrationBean<>();
        registration.setFilter(new DelegatingFilterProxy("testFilter"));
        registration.addUrlPatterns("/*");
        registration.setName("testFilter");
        registration.setOrder(Ordered.LOWEST_PRECEDENCE);
        return registration;
    }
}
