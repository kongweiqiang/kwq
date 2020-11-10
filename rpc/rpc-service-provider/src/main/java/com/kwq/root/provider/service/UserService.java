package com.kwq.root.provider.service;


import com.kwq.root.rpc.annotion.RpcService;
import com.kwq.root.rpc.sdk.api.UserApi;
import com.kwq.root.rpc.sdk.domain.User;

import java.util.ArrayList;
import java.util.List;

/**
 * 具体SDK中接口实现类
 */
@RpcService(exposeInterface = UserApi.class)
public class UserService implements UserApi {

    private static final List<User> USER_LIST = new ArrayList<>();

    static {
        USER_LIST.add(new User(1001L,"张三","男"));
        USER_LIST.add(new User(1002L,"李四","女"));
        USER_LIST.add(new User(1003L,"王五","男"));
    }

    @Override
    public User queryById(Long id) {
        //此处模仿数据源
        for (User user : USER_LIST) {
            if (user.getId().equals(id)) {
                return user;
            }
        }
        return new User(1L,"k","1");
    }
}
