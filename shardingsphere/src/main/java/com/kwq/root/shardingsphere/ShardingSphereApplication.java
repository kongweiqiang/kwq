package com.kwq.root.shardingsphere;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @auther : kongweiqiang
 * @DATE : 2020/8/13
 * @DESC :
 */
@ComponentScan("com.kwq.root")
@MapperScan(basePackages = "com.kwq.root.shardingsphere.mapper")
@EnableAspectJAutoProxy(exposeProxy = true)
@SpringBootApplication
public class ShardingSphereApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShardingSphereApplication.class, args);
    }

}
