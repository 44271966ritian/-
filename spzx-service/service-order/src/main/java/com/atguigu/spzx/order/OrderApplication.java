package com.atguigu.spzx.order;

import com.atguigu.spzx.common.ano.EnableUserTokenFeignInterceptor;
import com.atguigu.spzx.common.ano.EnableUserWebMvcConfiguration;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.context.request.RequestContextListener;

@EnableUserWebMvcConfiguration
@EnableFeignClients(basePackages ={"com.atguigu.spzx"})
@EnableUserTokenFeignInterceptor
@SpringBootApplication

public class OrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class , args) ;
    }
//    @Bean
//    public MessageConverter jacksonMessageConvertor(){
//
//        return new Jackson2JsonMessageConverter();
//    }



}