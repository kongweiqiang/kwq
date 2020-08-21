package com.kwq.root.shardingsphere.service.impl;

import com.kwq.root.shardingsphere.mapper.UserMapper;
import com.kwq.root.shardingsphere.model.User;
import com.kwq.root.shardingsphere.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @auther : kongweiqiang
 * @DATE : 2020/8/13
 * @DESC :
 */
@Log4j2
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public int add(User user) {
        return userMapper.add(user);
    }

    @Override
    public List<User> find(User user) {
        return userMapper.find(user);
    }

    @Override
    public int delete(User user) {
        return userMapper.delete(user);
    }

    @Override
    public int update(User user) {
        return userMapper.update(user);
    }

    @Override
    public Long count(User user) {
        return userMapper.count(user);
    }

}
