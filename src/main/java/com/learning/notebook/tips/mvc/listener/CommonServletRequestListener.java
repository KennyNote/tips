package com.learning.notebook.tips.mvc.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CommonServletRequestListener implements ServletRequestListener {

    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        ServletRequest servletRequest = sre.getServletRequest();
        if (servletRequest instanceof HttpServletRequest request) {
            log.info("[Request Info] 有请求接入服务器");
        }
    }

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        ServletContext servletContext = sre.getServletContext();
        log.info("[Request Info] 请求完成进行销毁");
    }
}
