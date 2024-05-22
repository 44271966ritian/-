package com.atguigu.spzx.user;


import com.atguigu.spzx.common.ano.EnableUserWebMvcConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@EnableUserWebMvcConfiguration
@SpringBootApplication
@ComponentScan(basePackages = {"com.atguigu.spzx"})
public class UserApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class,args);
    }

}
