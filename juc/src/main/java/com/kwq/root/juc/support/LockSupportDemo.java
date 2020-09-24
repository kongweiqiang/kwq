package com.kwq.root.juc.support;

import java.util.concurrent.locks.LockSupport;

/**
 * @auther : kongweiqiang
 * @DATE : 2020/9/18
 * @DESC :
 */
public class LockSupportDemo {

    private static Thread t1,t2 = null;

    public static void main(String[] args) {
        char [] a1="123456".toCharArray();
        char [] a2="abcdef".toCharArray();
        t1=new Thread(()->{
            for(char c: a1){
                System.out.println(c);
                LockSupport.unpark(t2);//唤醒,park和unpark谁先谁后没关系
                LockSupport.park();//阻塞当前线程
            }
        },"t1");

        t2=new Thread(()->{
            for(char c: a2){
                LockSupport.park();//阻塞当前线程
                System.out.println(c);
                LockSupport.unpark(t1);//唤醒
            }
        },"t2");
        t1.start();
        t2.start();
    }
}
