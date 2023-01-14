package com.learning.notebook.tips.db.transaction;

import com.learning.notebook.tips.rocketmq.entity.User;
import com.learning.notebook.tips.db.mapper.UserMapper;
import jakarta.annotation.Resource;
import java.time.LocalDateTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserRcServiceA {

    @Resource
    private UserMapper userMapper;

    @Transactional(propagation = Propagation.REQUIRED)
    public int insert(){
        String now = String.valueOf(LocalDateTime.now());
        User user = new User();
        user.setName(now);
        user.setPassword(now);
        return userMapper.insert(user);
    }
}
