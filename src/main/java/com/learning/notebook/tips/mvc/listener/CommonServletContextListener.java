package com.learning.notebook.tips.mvc.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CommonServletContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        log.info("[Context Info] 上下文初始化");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        log.info("[Context Info] 上下文已销毁");
    }
}
