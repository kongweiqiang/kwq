package com.kwq.root.provider;

import com.kwq.root.rpc.server.RpcNettyServer;

/**
 * @auther : kongweiqiang
 * @DATE : 2020/11/9
 * @DESC :
 */
public class Provider {

    private static int port = 10086;

    public static void main(String[] args) {
        RpcNettyServer rpcNettyServer = new RpcNettyServer(port);
        rpcNettyServer.startOn();
    }
}
