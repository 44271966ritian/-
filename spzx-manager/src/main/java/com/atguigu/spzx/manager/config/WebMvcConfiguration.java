package com.atguigu.spzx.manager.config;


import com.atguigu.spzx.manager.interceptor.LoginAuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.atguigu.spzx.manager.properties.UserProperties;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Component
public class WebMvcConfiguration implements WebMvcConfigurer {


    @Autowired
    private LoginAuthInterceptor loginAuthInterceptor;

    @Autowired
    private UserProperties userPropertieser;

    //跨域注册
    @Override
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/**")      // 添加路径规则
                .allowCredentials(true)               // 是否允许在跨域的情况下传递Cookie
                .allowedOriginPatterns("*")           // 允许请求来源的域规则
                .allowedMethods("*")
                .allowedHeaders("*") ;                // 允许所有的请求头
    }

    //拦截器注册
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginAuthInterceptor)
               /* .excludePathPatterns("/admin/system/index/login",
                        "/admin/system/index/generateValidateCode")//哪些路径不拦截*/
                .excludePathPatterns(userPropertieser.getNoAuthUrls())
                .addPathPatterns("/**");
    }
}
