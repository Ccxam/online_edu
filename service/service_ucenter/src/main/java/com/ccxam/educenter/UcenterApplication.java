package com.ccxam.educenter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.admin.SpringApplicationAdminMXBeanRegistrar;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@ComponentScan(basePackages ={"com.ccxam"})
@EnableSwagger2
@EnableDiscoveryClient
@MapperScan("com.ccxam.educenter.mapper")
public class UcenterApplication  {
    public static void main(String[] args) {
        SpringApplication.run(UcenterApplication.class,args);
    }
}
