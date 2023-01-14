package com.learning.notebook.tips.mvc.interceptor;

import com.learning.notebook.common.annotation.RequestAuth;
import com.learning.notebook.mvc.UserContext;
import com.learning.notebook.mvc.UserInfoDTO;
import java.lang.reflect.Method;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        log.info("[Interceptor Info] AuthInterceptor.preHandle");
        if (handler instanceof HandlerMethod handlerMethod) {
            Method method = handlerMethod.getMethod();
            if (method.isAnnotationPresent(RequestAuth.class)) {
                RequestAuth annotation = method.getAnnotation(RequestAuth.class);
                if (annotation.required()) {
                    String token = Optional.ofNullable(request.getHeader("token"))
                        .orElseThrow(() -> new RuntimeException("无token，请重新登录"));
                    UserInfoDTO userInfoDTO = UserInfoDTO.builder().userId(Long.parseLong(token)).build();
                    UserContext.getInstance().setContext(userInfoDTO);
                    return StringUtils.hasLength(token);
                }
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
        ModelAndView modelAndView) {
        UserContext.getInstance().clear();
        log.info("[Interceptor Info] AuthInterceptor.postHandle");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        log.info("[Interceptor Info] AuthInterceptor.afterCompletion");
    }
}
