package com.kwq.root.rpc.annotion;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface RpcService {
    //服务暴露的接口的类对象
    Class<?> exposeInterface();
}
