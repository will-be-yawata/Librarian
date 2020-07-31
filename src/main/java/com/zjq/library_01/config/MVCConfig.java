package com.zjq.library_01.config;

import com.zjq.library_01.interceptor.AdminLoginInterceptor;
import com.zjq.library_01.interceptor.LibrarianLoginInterceptor;
import com.zjq.library_01.interceptor.StudentLoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MVCConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AdminLoginInterceptor())
                .addPathPatterns("/adm/**");
        registry.addInterceptor(new LibrarianLoginInterceptor())
                .addPathPatterns("/lib/**");
        registry.addInterceptor(new StudentLoginInterceptor())
                .addPathPatterns("/stu/**");
    }
}
