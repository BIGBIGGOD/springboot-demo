package com.example.demo.annotation.conditional;

import java.util.Optional;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * @author jiangqingdong
 * @version v1.0
 * @Description 重写@Conditional注解中的matches方法，通过该方法中的实现判断是否满足条件并返回Boolean结果，从而决定是否向容器中注册bean
 * @DATE 2020/7/9 0009 16:52
 * @Copyright Copyright © 2019 深圳花儿绽放网络科技股份有限公司. All rights reserved.
 */
public class LinuxCondition implements Condition {

    /**
     * @param conditionContext:判断条件能使用的上下文环境
     * @param annotatedTypeMetadata:注解所在位置的注释信息
     */
    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        Environment environment = conditionContext.getEnvironment();
        String property = environment.getProperty("os.name");
        String value = Optional.ofNullable(property).orElse("");
        return value.contains("Linux");
    }
}
