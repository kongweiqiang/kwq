package com.kwq.root.rpc.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * @auther : kongweiqiang
 * @DATE : 2020/11/5
 * @DESC :
 */
@Slf4j
public class ClientHandler extends ChannelInboundHandlerAdapter {

    private Object result;

    public Object getResult() {
        return result;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        result = msg;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("client error : {}",cause.getCause());
    }
}
