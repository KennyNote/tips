package com.learning.notebook.tips.basic.proxy.dynamicproxy;

import java.lang.reflect.Proxy;

/**
 * 1.代理对象自身不需要实现接口
 * 2.代理对象的生成,是利用JDK的API,动态的在内存中构建代理对象(需要我们指定创建代理对象/目标对象实现的接口的类型)
 * 3.动态代理也叫做:JDK代理,接口代理
 */
public class ProxyFactory {

    private final Object target;

    public ProxyFactory(Object target) {
        this.target = target;
    }

    public Object getProxyInstance() {
        /**
         * 接收的三个参数依次为:
         * ClassLoader loader : 指定当前目标对象使用类加载器,获取加载器的方法是固定的
         * Class<?>[] interfaces : 目标对象实现的接口的类型,使用泛型方式确认类型
         * InvocationHandler h : 事件处理,执行目标对象的方法时,会触发事件处理器的方法,会把当前执行目标对象的方法作为参数传入
         */
        return Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                (proxy, method, args) -> method.invoke(target, args)
        );
    }

}


