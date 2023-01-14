package com.learning.notebook.tips.db.mapper;

import com.learning.notebook.rocketmq.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * UserDAO继承基类
 */
@Mapper
@Repository
public interface UserMapper extends BaseMapper<User, Long> {
    int reduceMoney(@Param("userId") Long userId, @Param("money") Long money);
}