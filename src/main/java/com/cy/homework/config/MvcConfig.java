package com.cy.homework.config;

import com.cy.homework.common.JwtInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    /**上传图片地址*/
    @Value("${file.upload.pic.path}")
    private String picPath;

    /**上传文件地址*/
    @Value("${file.upload.file.path}")
    private String filePath;

    @Bean
    public JwtInterceptor jwtInterceptor(){return new JwtInterceptor();}

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor())
                .excludePathPatterns("/student/login")
                .excludePathPatterns("/teacher/login")
                .excludePathPatterns("/admin/login")
                .excludePathPatterns("/static/image/**");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/image/**").addResourceLocations("file:/" + picPath);
        registry.addResourceHandler("/static/file/**").addResourceLocations("file:/" + filePath);
    }

}
