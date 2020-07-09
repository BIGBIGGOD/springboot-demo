package com.example.demo.config;

import org.springframework.context.annotation.Bean;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @author jiangqingdong
 * @version v1.0
 * @Description Swagger2配置
 * 访问地址:http://localhost:8080/swagger-ui.html
 * 常用参数解析:
 * - @Api()用于类；
 * 表示标识这个类是swagger的资源
 * - @ApiOperation()用于方法；
 * 表示一个http请求的操作
 * - @ApiParam()用于方法，参数，字段说明；
 * 表示对参数的添加元数据（说明或是否必填等）
 * - @ApiModel()用于类
 * 表示对类进行说明，用于参数用实体类接收
 * - @ApiModelProperty()用于方法，字段
 * 表示对model属性的说明或者数据操作更改
 * - @ApiIgnore()用于类，方法，方法参数
 * 表示这个方法或者类被忽略
 * - @ApiImplicitParam() 用于方法
 * 表示单独的请求参数
 * - @ApiImplicitParams() 用于方法，包含多个 @ApiImplicitParam
 * @DATE 2020/7/9 0009 19:38
 * @Copyright Copyright © 2019 深圳花儿绽放网络科技股份有限公司. All rights reserved.
 */
public class Swagger2Config {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                //你需要生成文档所在的包
                .apis(RequestHandlerSelectors.basePackage("com.example.demo.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                //文档标题
                .title("springboot利用swagger构建api文档")
                //描述
                .description("简单优雅的restfun风格，http://blog.csdn.net/saytime")
                .termsOfServiceUrl("http://blog.csdn.net/saytime")
                .version("1.0")
                .build();
    }

}
