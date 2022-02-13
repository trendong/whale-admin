package com.whale.admin.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebAutoConfiguration implements WebMvcConfigurer {

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.addPathPrefix("/*", clazz ->
                clazz.isAnnotationPresent(RestController.class)
                        && clazz.getPackage().getName().startsWith("com.whale.admin.web")); // 仅仅匹配 controller 包
        WebMvcConfigurer.super.configurePathMatch(configurer);
    }

}
