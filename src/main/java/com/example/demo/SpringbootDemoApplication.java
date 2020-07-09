package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @author Administrator
 * 继承SpringBootServletInitializer可以在将项目打包为war的时候（需要依赖tomcat）配置并使用外部tomcat，不使用外部tomcat可以不用继承，
 * spring-boot打包成的jar自带tomcat，直接使用java -jar xxx.jar就可以启动
 * spring boot只会扫描启动类当前包和以下的包,所以自己编写的类或者组件最好在该目录下，或者需要在启动类上添加注解@ComponentScan(basePackages = { "XXXXXX" })解决
 */
@SpringBootApplication
public class SpringbootDemoApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootDemoApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(SpringbootDemoApplication.class);
	}
}
