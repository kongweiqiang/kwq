package com.kwq.root.rpc.client;

import com.kwq.root.rpc.protocol.ProtocolBoby;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * @auther : kongweiqiang
 * @DATE : 2020/11/4
 * @DESC :
 */
public class RpcClient {

    public static <T> T getRemoteProxy(Class<T> interfaceClass, InetSocketAddress adderss){
        return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class<?>[]{interfaceClass}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                //远程连接到服务端 BIO
                Socket socket = new Socket();
                socket.connect(adderss);
                ObjectOutputStream serializer = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream deSerializer = new ObjectInputStream(socket.getInputStream());
                ProtocolBoby protocol = new ProtocolBoby();
                protocol.setInterfaceName(interfaceClass.getName());
                protocol.setMethodName(method.getName());
                protocol.setParameterTypes(method.getParameterTypes());
                protocol.setParameterValus(args);
                //把调用远程接口中需要参数进行封装成一个协议数据包传递给服务端
                serializer.writeObject(protocol);
                //获取服务端调用的返回结果
                return deSerializer.readObject();
            }
        });
    }
}
