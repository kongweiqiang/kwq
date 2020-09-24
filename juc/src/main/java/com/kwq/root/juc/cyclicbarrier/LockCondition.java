package com.kwq.root.juc.cyclicbarrier;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @auther : kongweiqiang
 * @DATE : 2020/9/18
 * @DESC :
 */
public class LockCondition {

    public static void main(String[] args){
        char [] a1="123456".toCharArray();
        char [] a2="abcdef".toCharArray();
        /**
         * ReentrantLock与Synchronize区别:
         *  1.实现不同:ReentrantLock通过AQS实现的自旋互斥可重入锁,Synchronize是基于JVM实现
         *  2.ReentrantLock可中断
         *  3.更灵活:提供Condition控制,可绑定多个条件,不像Synchronize要么唤醒一个,要么全部唤醒
         *  4.都是互斥,但ReentrantLock可公平/非公平
         */
        Lock lock = new ReentrantLock();
        Condition c1 = lock.newCondition();//Condition本质是队列
        Condition c2 = lock.newCondition();//新创建一个队列,condition队列间独立

        new Thread(()->{
            lock.lock();
            try {
                for (char c : a1) {
                    System.out.println(c);
                    c2.signal();//叫醒c2队列中的一个线程
                    c1.await();//当前线程进入c1的队列
                }
                c2.signal();//叫醒c2队列中的一个线程
            }catch (Exception e){

            }finally {
                lock.unlock();
            }
        },"1").start();

        new Thread(()->{
            lock.lock();
            try {
                for (char c : a1) {
                    System.out.println(c);
                    c1.signal();
                    c2.await();
                }
                c1.signal();
            }catch (Exception e){

            }finally {
                lock.unlock();
            }
        },"2").start();
    }

}
