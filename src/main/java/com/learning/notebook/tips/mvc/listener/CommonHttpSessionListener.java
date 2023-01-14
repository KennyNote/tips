package com.learning.notebook.tips.mvc.listener;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CommonHttpSessionListener implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        log.info("[Session Info] 会话创建");
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        log.info("[Session Info] 会话销毁");
    }
}
