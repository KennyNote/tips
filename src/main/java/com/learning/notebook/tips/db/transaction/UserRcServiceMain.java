package com.learning.notebook.tips.db.transaction;

import com.learning.notebook.tips.rocketmq.entity.User;
import com.learning.notebook.tips.db.mapper.UserMapper;
import java.time.LocalDateTime;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserRcServiceMain {

    @Resource
    private UserRcServiceA userRcServiceA;

    @Resource
    private UserRcServiceB userRcServiceB;

    @Resource
    private UserMapper userMapper;


    /**
     * 不同类的方法互相调用:
     * 【事务Main（public）】调【事务userRcServiceA】和【事务userRcServiceB】：UserRcServiceA和UserRcServiceB的注解事务方法不会生效，会加入到Main的注解事务中。
     * 【非事务Main（public）】调【事务userRcServiceA】和【事务userRcServiceB】：UserRcServiceA和UserRcServiceB的注解事务方法各自生效。
     * 内部的方法互相调用：
     * 1.【事务SelfA】调【事务SelfB】：SelfB会加入到SelfA的注解事务中。
     * 2.【事务SelfA】调【非事务SelfB】：SelfB会加入到SelfA的注解事务中。
     * 3.【非事务SelfA】调【事务SelfB】：SelfB注解事务方法不会生效。
     * 4.【非事务SelfA】调【非事务SelfB】：无事务
     */

    @Transactional(propagation = Propagation.REQUIRED)
    public void insertWithTransactional4AB(){
        userRcServiceA.insert();
        userRcServiceB.insert();
    }

    public void insertWithoutTransactional4AB(){
        userRcServiceA.insert();
        userRcServiceB.insert();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void insertWithTransactional4SelfA(){
        String now = String.valueOf(LocalDateTime.now());
        User user = new User();
        user.setName(now);
        user.setPassword(now);
        userMapper.insert(user);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void insertWithTransactional4SelfB(){
        String now = String.valueOf(LocalDateTime.now());
        User user = new User();
        user.setName(now);
        user.setPassword(now);
        userMapper.insert(user);
    }

    public void insertWithoutTransactional4SelfA(){
        String now = String.valueOf(LocalDateTime.now());
        User user = new User();
        user.setName(now);
        user.setPassword(now);
        userMapper.insert(user);
    }

    public void insertWithoutTransactional4SelfB(){
        String now = String.valueOf(LocalDateTime.now());
        User user = new User();
        user.setName(now);
        user.setPassword(now);
        userMapper.insert(user);
    }
}
