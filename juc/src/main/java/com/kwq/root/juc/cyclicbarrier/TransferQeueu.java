package com.kwq.root.juc.cyclicbarrier;

import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TransferQueue;

/**
 * @auther : kongweiqiang
 * @DATE : 2020/9/18
 * @DESC :
 */
public class TransferQeueu {

    public static void main(String[] args){
        char [] a1="123456".toCharArray();
        char [] a2="abcdef".toCharArray();

        /**
         * TransferQueue容器的容量为0,一旦容器中放了东西(transfer),就会阻塞,必须等待东西被拿走才返回(take),才会被唤醒
         * transfer和take谁先谁后无所谓,transfer是生产者,take是消费者
         */
        TransferQueue<Character> queue = new LinkedTransferQueue();//阻塞式同步容器

        /**
         * t1线程操作t2线程的东西,t2线程操作t1的东西
         */
        new Thread(()->{
            try {
                for (char c : a1) {
                    System.out.println(queue.take());
                    queue.transfer(c);//阻塞方法
                }
            }catch (Exception e){

            }
        },"1").start();

        new Thread(()->{
            try {
                for (char c : a1) {
                    queue.transfer(c);
                    System.out.println(queue.take());
                }
            }catch (Exception e){

            }
        },"2").start();
    }

}
