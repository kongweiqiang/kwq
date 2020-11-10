package com.kwq.root.rpc.handler;

import com.kwq.root.rpc.annotion.RpcService;
import com.kwq.root.rpc.protocol.ProtocolBoby;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @auther : kongweiqiang
 * @DATE : 2020/11/5
 * @DESC : 服务端主要业务处理逻辑
 */
public class ServerHandler extends ChannelInboundHandlerAdapter {

    //类对象容器
    public List<Class<?>> classes = new CopyOnWriteArrayList<>();
    //发布服务集合
    public Map<String,Object> servers = new ConcurrentHashMap<>(32);

    public static final String NULL_STRING = "";

    public ServerHandler(){
        //扫描
        doScan(NULL_STRING);
        //注册服务
        doRegistryService();
    }

    /**
     * 注册服务
     */
    private void doRegistryService() {
        if(classes.size() == 0) return;
        try{
            for (Class<?> clazz : classes) {
                if(clazz.isAnnotationPresent(RpcService.class)){
                    RpcService rpcServer = clazz.getAnnotation(RpcService.class);
                    Class<?> clazzInterface = rpcServer.exposeInterface();
                    this.servers.put(clazzInterface.getName(),clazz.newInstance());
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 执行注解扫描找到对应注解类
     * @param resource 扫描资源位置
     */
    private void doScan(String resource) {
        URL url = this.getClass().getClassLoader().getResource(resource);
        File dir = new File(url.getFile());
        if (resource!=null && !resource.equals("") && resource.contains(".")) {
            resource = resource.replaceAll("\\.","/");
        }
        File[] files = dir.listFiles();
        for (File file : files) {
            if(file.isDirectory()){
                doScan(resource + file.getName() + ".");
            } else {
                String fileName = file.getName();
                if(fileName.endsWith(".class")){
                    //类路径
                    String classPath = resource + fileName;
                    //获取类全限定名
                    classPath = classPath.replaceAll("/","\\.").replaceAll("\\.class","");
                    //转换为类
                    try {
                        Class<?> clazz = Class.forName(classPath);
                        classes.add(clazz);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx,Object msg) throws Exception {
        Object result = null;
        ProtocolBoby protocol = (ProtocolBoby) msg;
        if(this.servers.containsKey(protocol.getInterfaceName())){
            Object instance = servers.get(protocol.getInterfaceName());
            Method method = instance.getClass().getDeclaredMethod(protocol.getMethodName(), protocol.getParameterTypes());
            result = method.invoke(instance, protocol.getParameterValus());
        }else{
            throw new RuntimeException("service is not exist!");
        }
        //发送数据给客户端
        ctx.writeAndFlush(result);
        //关闭资源
        ctx.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
