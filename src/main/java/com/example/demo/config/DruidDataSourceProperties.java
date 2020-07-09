package com.example.demo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * @author jiangqingdong
 * @version v1.0
 * @Description 利用spring-boot-configuration-processor包可以更好的获取配置文件中的参数
 * @DATE 2020/7/9 0009 17:53
 * @Copyright Copyright © 2019 深圳花儿绽放网络科技股份有限公司. All rights reserved.
 */
@ConfigurationProperties(prefix = "spring.datasource.druid")
@Data
public class DruidDataSourceProperties {

    private String username;

    private String password;

    private String jdbcUrl;

    private String driverClassName;

    private Integer initialSize;

    private Integer maxActive;

    private Integer minIdle;

    private long maxWait;

    private boolean poolPreparedStatements;

}
