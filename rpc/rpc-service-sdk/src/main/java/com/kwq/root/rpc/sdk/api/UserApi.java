package com.kwq.root.rpc.sdk.api;


import com.kwq.root.rpc.sdk.domain.User;

/**
 * 用户操作接口
 */
public interface UserApi {
    /**
     * 根据用户ID获取用户详情
     * @param id
     * @return
     */
    User queryById(Long id);

}
