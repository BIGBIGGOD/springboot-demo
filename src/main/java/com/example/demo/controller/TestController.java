package com.example.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @author jiangqingdong
 * @version v1.0
 * @Description
 * @DATE 2020/7/9 0009 11:02
 * @Copyright Copyright © 2019 深圳花儿绽放网络科技股份有限公司. All rights reserved.
 */
@RestController
@RequestMapping("/test")
@Api(value = "测试", description = "测试controller")
public class TestController {

    @RequestMapping("/test1")
    @ApiOperation(value = "测试接口", notes = "测试一号接口")
    public String testHelloWorld(@ApiParam(value = "用户名", type = "String") String name) {
        System.out.println("阿斯蒂芬" + name);
        return "test,hello";
    }
}
