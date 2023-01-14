package com.learning.notebook.tips.common.aspect;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j
@Aspect
@Component
public class LogAspect {

    @Pointcut("@annotation(com.learning.notebook.common.annotation.RequestLog)")
    public void recordLog() {}

    @Before("recordLog()")
    public void doBefore(JoinPoint joinPoint) {
        // 接收到请求，记录请求内容
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        Optional.ofNullable((ServletRequestAttributes) requestAttributes).map(ServletRequestAttributes::getRequest)
            .ifPresent(request -> {
                // 记录下请求内容
                log.info("[Request Info] URL : " + request.getRequestURI());
                log.info("[Request Info] HTTP_METHOD : " + request.getMethod());
                log.info("[Request Info] IP : " + request.getRemoteAddr());
                log.info("[Request Info] CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
                log.info("[Request Info] ARGS : " + Arrays.toString(joinPoint.getArgs()));
                log.info("[Request Info] SESSION : " + request.getSession(true));
            });

    }

    @AfterReturning(returning = "result", pointcut = "recordLog()")
    public void doAfterReturning(Object result) {
        // 处理完请求，返回内容
        log.info("[Response Info] RESPONSE : $result");
    }
}
