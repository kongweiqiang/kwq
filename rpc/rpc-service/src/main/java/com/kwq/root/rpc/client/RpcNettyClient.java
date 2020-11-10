package com.kwq.root.rpc.client;

import com.kwq.root.rpc.handler.ClientHandler;
import com.kwq.root.rpc.protocol.ProtocolBoby;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;

/**
 * @auther : kongweiqiang
 * @DATE : 2020/11/5
 * @DESC : RPC框架提供的客户端
 */
public class RpcNettyClient {

    public static <T> T getRemoteProxy(Class<T> interfaceClass, InetSocketAddress adderss){
        //直接使用JDK动态代理俩创建接口对应的代理实例对象
        return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class<?>[]{interfaceClass}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                //创建线程组:此处是client端,只需要发送请求,不需要处理请求,所以不需要线程组
                EventLoopGroup group = new NioEventLoopGroup();
                //定义返回结果处理器对象
                ClientHandler rpcClientHander = new ClientHandler();
                try{
                    //创建客户端通道
                    Bootstrap bootstrap = new Bootstrap();
                    bootstrap.group(group).channel(NioSocketChannel.class)
                            .option(ChannelOption.TCP_NODELAY,true)
                            .handler(new ChannelInitializer<NioSocketChannel>() {
                                @Override
                                protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                                    ChannelPipeline pipeline = nioSocketChannel.pipeline();
                                    //解决粘包拆包问题
                                    pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE,0,4,0,4));
                                    pipeline.addLast(new LengthFieldPrepender(4));
                                    //对内容进行编码解码:JDK
                                    pipeline.addLast("encoder",new ObjectEncoder());
                                    //                                                  大小              对应的类的解析器(null:不需要解析)
                                    pipeline.addLast("decoder",new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.cacheDisabled(null)));
                                    //定义需要处理自己逻辑的Handler
                                    pipeline.addLast(rpcClientHander);
                                }
                            });
                    ChannelFuture future = bootstrap.connect(adderss).sync();
                    //封装客户端发送的参数
                    ProtocolBoby protocol = new ProtocolBoby();
                    protocol.setInterfaceName(interfaceClass.getName());
                    protocol.setParameterValus(args);
                    protocol.setMethodName(method.getName());
                    protocol.setParameterTypes(method.getParameterTypes());
                    //发送数据到服务端
                    future.channel().writeAndFlush(protocol).sync();
                    //优雅释放资源
                    future.channel().closeFuture().sync();
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    //优雅关停
                    group.shutdownGracefully();
                }
                //返回调用结果
                return rpcClientHander.getResult();
            }
        });
    }
}
