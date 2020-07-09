package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jiangqingdong
 * @version v1.0
 * @Description
 * @DATE 2020/7/9 0009 11:02
 * @Copyright Copyright © 2019 深圳花儿绽放网络科技股份有限公司. All rights reserved.
 */
@RestController
public class TestController {

    @RequestMapping("/test")
    public String testHelloWorld() {
        System.out.println("阿斯蒂芬");
        return "test,hello";
    }
}
