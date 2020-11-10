package com.kwq.root.rpc.server;

import com.kwq.root.rpc.protocol.ProtocolBoby;
import lombok.extern.log4j.Log4j2;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @auther : kongweiqiang
 * @DATE : 2020/11/4
 * @DESC :
 */
@Log4j2
public class RpcServer {

   private ThreadPoolExecutor pool = new ThreadPoolExecutor(
           8,
           20,
           200,
           TimeUnit.SECONDS,
           new ArrayBlockingQueue<>(10));

   private Map<String,Object> services = new ConcurrentHashMap<>(32);
    /**
     * 发布服务
     * @param interfaceClass 发布的接口
     * @param instance 发布的对象实例
     */
    public void registryService(Class<?> interfaceClass, Object instance){
        services.put(interfaceClass.getName(),instance);
    }

    //启动服务
    public void startOn(int port){
        try{
            //创建服务端
            ServerSocket serverSocket = new ServerSocket();
            //绑定端口
            serverSocket.bind(new InetSocketAddress(port));
            log.info("RPC Service start ... ...");
            //等待客户端连接
            for(;;){
                //池化 重用线程
                pool.execute(new Server(serverSocket.accept()));
            }
        }catch (Exception e){
            log.error("service start fail, status is off");
        }
    }

    // 内部服务线程:用于处理客户端请求的线程类
    class Server implements Runnable {

        private final Socket client;

        public Server(Socket client){
            this.client = client;
        }

        @Override
        public void run() {
            try {
                //获取对象反序列化流(从网络中获取服务端序列化的数据)
                ObjectInputStream in = new ObjectInputStream(client.getInputStream());
                //获取对象序列化流(把数据写入网络)
                ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
                //从网络中获取客户端传递的数据表
                ProtocolBoby protocol = (ProtocolBoby)in.readObject();
                //直接获取协议中需要调用的具体实例
                Object instance = services.get(protocol.getInterfaceName());
                if(instance == null) throw new RuntimeException("intance is not exist!");
                //通过获取的实例对象创建一个方法对象
                Method method = instance.getClass().getDeclaredMethod(protocol.getMethodName());
                //反射调用方法
                Object result = method.invoke(instance, protocol.getParameterValus(), protocol.getParameterTypes());
                //把结果进行序列化写入到网络中
                out.writeObject(result);
            }catch (Exception e){
                e.printStackTrace();
                log.error("client error : {}",e.getMessage());
            }
        }
    }
}
