package com.kwq.root.juc.synchronize;

import com.kwq.root.juc.model.JavaObjectHead;
import org.openjdk.jol.info.ClassLayout;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @auther : kongweiqiang
 * @DATE : 2020/8/31
 * @DESC : Synchronized 可重入 父子类 独占锁 悲观锁 原子性 可见性 多个线程进入串行临界区
 */
public class Synchronize {

    private AtomicInteger i = new AtomicInteger(0);

    private JavaObjectHead head = new JavaObjectHead();

    public static void main(String[] args){
        /*//打印
        Synchronize sync = new Synchronize();
        sync.print("start");
        sync.print("end");
        sync.sleep(100L);*/

        final Object o = new Object();
        char [] a1="123456".toCharArray();
        char [] a2="abcdef".toCharArray();
        new Thread(()->{
            synchronized (o){
                for(char c: a1){
                    System.out.println(c);
                    try {
                        o.notify();//随机叫醒锁队列中的任一线程
                        o.wait();//让出锁入列
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    o.notify();//必须 否则无法停止程序
                }
            }
        },"1").start();

        new Thread(()->{
            synchronized (o){
                for(char c: a2){
                    System.out.println(c);
                    try {
                        //使用顺序必须是先notify()再wait(),否则一直阻塞自己,无法唤醒队列中其他线程
                        o.notify();//随机叫醒锁队列中的任一线程,notifyAll()是唤醒该锁的队列里所有的等待线程
                        o.wait();//让出锁并入列阻塞
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    o.notify();//必须 否则无法停止程序
                }
            }
        },"2").start();
    }

    public void print(String message){
        System.out.println(message);
        System.out.println(ClassLayout.parseInstance(head).toPrintable());
    }


    public synchronized void TestA(String message){
        print(message);
    }

    public synchronized void TestB(String message){
        print(message);
    }

    public void sleep(Long time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
