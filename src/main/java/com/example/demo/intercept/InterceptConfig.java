package com.example.demo.intercept;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

/**
 * @author jiangqingdong
 * @version v1.0
 * @Description 注册拦截器
 * @DATE 2020/7/9 0009 12:17
 * @Copyright Copyright © 2019 深圳花儿绽放网络科技股份有限公司. All rights reserved.
 */
@Configuration
public class InterceptConfig {

    @Autowired
    private TestInterceptor testInterceptor;

    /**
     * 注册拦截器 * @param registry
     */
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(testInterceptor).addPathPatterns("/**").excludePathPatterns("/index.html", "/");
    }
}
