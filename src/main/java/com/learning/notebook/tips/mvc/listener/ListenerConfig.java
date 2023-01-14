package com.learning.notebook.tips.mvc.listener;

import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;

public class ListenerConfig {

    @Bean
    public ServletListenerRegistrationBean<CommonServletContextListener> commonServletContextListener() {
        return new ServletListenerRegistrationBean<>(new CommonServletContextListener());
    }

    @Bean
    public ServletListenerRegistrationBean<CommonServletRequestListener> commonServletRequestListener() {
        return new ServletListenerRegistrationBean<>(new CommonServletRequestListener());
    }

    @Bean
    public ServletListenerRegistrationBean<CommonHttpSessionListener> commonHttpSessionListener() {
        return new ServletListenerRegistrationBean<>(new CommonHttpSessionListener());
    }
}
