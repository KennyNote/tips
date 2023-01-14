package com.learning.notebook.tips.mvc.filter;

import jakarta.servlet.Filter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<Filter> authFilterRegistrationBean() {
        FilterRegistrationBean<Filter> authFilterRegistrationBean = new FilterRegistrationBean<>();
        authFilterRegistrationBean.setFilter(new AuthFilter());
        authFilterRegistrationBean.addUrlPatterns("/*");
        authFilterRegistrationBean.setOrder(1);
        return authFilterRegistrationBean;
    }

}
