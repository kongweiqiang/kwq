package com.kwq.root.rpc.server;

import com.kwq.root.rpc.handler.ServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import lombok.extern.slf4j.Slf4j;

/**
 * @auther : kongweiqiang
 * @DATE : 2020/11/5
 * @DESC :
 */
@Slf4j
public class RpcNettyServer {
    private int port;

    public RpcNettyServer(int port) {
        this.port = port;
    }

    /**
     * 启动RPC框架方法入口
     */
    public void startOn(){
        //两个线程组
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();
        try{
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(boss,worker).channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            //通过socket获取管道
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            //解决粘包拆包
                            pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE,0,4,0,4));
                            pipeline.addLast(new LengthFieldPrepender(4));
                            //对内容进行编码解码:JDK
                            pipeline.addLast("encoder",new ObjectEncoder());
                            //                                                  大小              对应的类的解析器(null:不需要解析)
                            pipeline.addLast("decoder",new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.cacheDisabled(null)));
                            //定义需要处理自己逻辑的Handler
                            pipeline.addLast(new ServerHandler());
                        }
                    }).option(ChannelOption.SO_BACKLOG,128)//默认1024
                      .childOption(ChannelOption.SO_KEEPALIVE,true);//指定长连接
            //绑定监听端口
            ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
            log.info("Netty RPC Server start on ... ... listen on port {}",port);
            //优雅释放资源
            channelFuture.channel().closeFuture().sync();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //优雅关闭
            worker.shutdownGracefully();
            boss.shutdownGracefully();
        }
    }
}
