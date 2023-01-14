package com.learning.notebook.tips.basic.proxy.staticproxy;


import com.learning.notebook.basic.proxy.UserDao;
import com.learning.notebook.basic.proxy.UserDaoImpl;

public class Test {

    public static void main(String[] args) {
        UserDao target = new UserDaoImpl();
        UserDaoProxy proxy = new UserDaoProxy(target);
        proxy.save();
    }

}
