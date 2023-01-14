package com.learning.notebook.tips.mvc;

public class UserContext {

    private final ThreadLocal<UserInfoDTO> threadLocal;

    private UserContext() {
        this.threadLocal = new ThreadLocal<>();
    }

    /**
     * 创建实例
     */
    public static UserContext getInstance() {
        return SingletonContext.sInstance;
    }

    /**
     * 静态内部类单例模式
     * 单例初使化
     */
    private static class SingletonContext {

        private static final UserContext sInstance = new UserContext();
    }

    /**
     * 用户上下文中放入信息
     */
    public void setContext(UserInfoDTO userInfoDTO) {
        threadLocal.set(userInfoDTO);
    }

    /**
     * 获取上下文中的信息
     */
    public UserInfoDTO getContext() {
        return threadLocal.get();
    }

    /**
     * 获取上下文中的用户名
     */
    public String getUsername() {
        return getContext().getUserName();
    }

    /**
     * 获取上下文中的用户id
     */
    public Long getUserId() {
        return getContext().getUserId();
    }

    /**
     * 清空上下文
     */
    public void clear() {
        threadLocal.remove();
    }


}
