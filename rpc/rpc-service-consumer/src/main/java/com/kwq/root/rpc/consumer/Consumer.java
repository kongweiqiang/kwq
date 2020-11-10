package com.kwq.root.rpc.consumer;

import com.kwq.root.rpc.client.RpcNettyClient;
import com.kwq.root.rpc.sdk.api.UserApi;
import com.kwq.root.rpc.sdk.domain.User;

import java.net.InetSocketAddress;

/**
 * Hello world!
 *
 */
public class Consumer {
    public static void main( String[] args ) {
        InetSocketAddress address = new InetSocketAddress("127.0.0.1", 10086);
        // 需要调用API中方法
        //UserApi userApi = RpcClient.getRemoteProxy(UserApi.class, address);
        UserApi userApi = RpcNettyClient.getRemoteProxy(UserApi.class, address);
        User user = userApi.queryById(1001L);
        System.out.println(user.getGender() + ","+ user.getName());
    }
}
