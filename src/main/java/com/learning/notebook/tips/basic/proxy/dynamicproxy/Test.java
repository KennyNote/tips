package com.learning.notebook.tips.basic.proxy.dynamicproxy;

import com.learning.notebook.tips.basic.proxy.UserDao;
import com.learning.notebook.tips.basic.proxy.UserDaoImpl;

public class Test {

    public static void main(String[] args) {
        UserDao target = new UserDaoImpl();
        UserDao proxy = (UserDao) new ProxyFactory(target).getProxyInstance();
        proxy.save();
    }

}

