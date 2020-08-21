package com.kwq.root.shardingsphere.service;

import com.kwq.root.shardingsphere.model.User;

import java.util.List;

/**
 * @auther : kongweiqiang
 * @DATE : 2020/8/13
 * @DESC :
 */
public interface UserService {

    int add(User user);

    List<User> find(User user);

    int delete(User user);

    int update(User user);

    Long count(User user);

}
