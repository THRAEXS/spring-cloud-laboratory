package com.cnpc.config.controller;

import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;

/**
 * @Author 鬼王
 * @Date 2018/11/23 15:47
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

    //public StringHttpMessageConverter stringHttpMessageConverter() {
    //    StringHttpMessageConverter shm = new StringHttpMessageConverter();
    //    shm.setSupportedMediaTypes(MediaType.asMediaTypes(MediaType.TEXT_HTML_VALUE));
    //    SpringResourceTemplateResolver s = new SpringResourceTemplateResolver();
    //    s.setCharacterEncoding();
    ////}

    //@Bean
    //public SpringResourceTemplateResolver springResourceTemplateResolver(ThymeleafProperties properties) {
    //    SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
    //    resolver.setPrefix(properties.getPrefix());
    //    resolver.setSuffix(properties.getSuffix());
    //    resolver.setTemplateMode(properties.getMode());
    //    resolver.setCharacterEncoding("utf-8");
    //    return resolver;
    //}


    //@Override
    //public void addResourceHandlers(ResourceHandlerRegistry registry) {
    //    //super.addResourceHandlers(registry);
    //    registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static");
    //}
}
