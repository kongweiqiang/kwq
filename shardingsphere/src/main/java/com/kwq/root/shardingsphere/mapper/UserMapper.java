package com.kwq.root.shardingsphere.mapper;

import com.kwq.root.shardingsphere.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @auther : kongweiqiang
 * @DATE : 2020/8/13
 * @DESC :
 */
@Mapper
public interface UserMapper {

    int add(User user);

    List<User> find(User user);

    int delete(User user);

    int update(@Param("record") User user);

    Long count(User user);

}
