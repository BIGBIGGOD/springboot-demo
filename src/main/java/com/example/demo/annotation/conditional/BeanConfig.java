package com.example.demo.annotation.conditional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

/**
 * @author jiangqingdong
 * @version v1.0
 * @Description Bean配置类，创建两个bean
 * @DATE 2020/7/9 0009 16:48
 * @Copyright Copyright © 2019 深圳花儿绽放网络科技股份有限公司. All rights reserved.
 */
@Configuration
public class BeanConfig {

    /**
     * 只有一个类时，大括号可以省略,当有多个类也就是多个参数的时候直接添加，参数之间用逗号隔开即可
     * 如果WindowsCondition的实现方法返回true，则注入这个bean
     * @return res
     */
    @Conditional({WindowsCondition.class})
    @Bean(name = "bill")
    public Person person1() {
        return new Person("Bill Gates", 62);
    }
    @Conditional({LinuxCondition.class})
    @Bean("linus")
    public Person person2() {
        return new Person("Linus", 48);
    }
}
