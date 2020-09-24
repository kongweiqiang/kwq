package com.kwq.root.juc.open;

import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @auther : kongweiqiang
 * @DATE : 2020/9/23
 * @DESC :
 */
public class EpollMain {

    public void initServier(){
        try{
            /* I/O多路复用器 NioEventLoopGroup池内部存在一个<NioEventLoop>数据结构,NioEventLoop调用register方法,轮询/加权轮询chooser,next会在组中挑选一个线程执行
             * EventExecutorChooser::chooser会选择工作线程池中的一个NioEventLoop处理事件,NioEventLoop存在selector,会调用select(){for(;;)}
             * work线程池最好跟cpu核数相等
             * 接收客户端所有链接,boss分发到work池中,runAllTasks()
             * selector.select(),processSelectedKeys,runAllTasks()
             * socket绑定一个channel,交给NioEventLoop处理
             */
            NioEventLoopGroup boss = new NioEventLoopGroup(1);
            NioEventLoopGroup work = new NioEventLoopGroup(2);
            NioServerSocketChannel channel = new NioServerSocketChannel();
            work.register(channel);



        }catch (Exception e){

        }
    }
}
