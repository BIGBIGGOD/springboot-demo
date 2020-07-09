package com.example.demo.annotation.conditional;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author jiangqingdong
 * @version v1.0
 * @Description 普通实体类
 * @DATE 2020/7/9 0009 16:47
 * @Copyright Copyright © 2019 深圳花儿绽放网络科技股份有限公司. All rights reserved.
 */
@Data
@AllArgsConstructor
public class Person {
    private String name;
    private Integer age;
}
