package com.learning.notebook.tips.basic.proxy.staticproxy;

import com.learning.notebook.basic.proxy.UserDao;

/**
 * 1.可以做到在不修改目标对象的功能前提下,对目标功能扩展。
 * 2.但是因为代理对象`UserDaoProxy`需要与目标对象`UserDaoImpl`实现一样的接口，所以会有很多代理类，类太多。
 * 3.同时，一旦接口增加方法，目标对象与代理对象都要维护。
 */
public class UserDaoProxy implements UserDao {

    //接收保存目标对象
    private UserDao target;

    public UserDaoProxy(UserDao target) {
        this.target = target;
    }

    @Override
    public void save() {
        System.out.println("开始事务...");
        target.save();//执行目标对象的方法
        System.out.println("提交事务...");
    }

}

