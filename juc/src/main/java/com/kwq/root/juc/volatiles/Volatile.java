package com.kwq.root.juc.volatiles;

import java.util.concurrent.CountDownLatch;

/**
 * @auther : kongweiqiang
 * @DATE : 2020/10/27
 * @DESC : volatile 可见性(非运算操作才行,赋值可以,像++的不行)和有序性(内存屏障)
 *          synchronized 可见性 有序性(线程间的有序性:串行化)
 *          synchronized内部和外部的代码可以并行执行
 */
public class Volatile {

    private static int x=0,y=0;
    private static int a=0,b=0;
    public static void main(String[] args) throws Exception {
        for(long i =0 ; i< Long.MAX_VALUE;i++){
            x=0;
            y=0;
            a=0;
            b=0;
            CountDownLatch latch = new CountDownLatch(2);
            Thread one = new Thread(()->{
                a = 1;//后
                x = b;//先
                latch.countDown();
            },"a");
            Thread two = new Thread(()->{
                b = 1;//后
                y = a;//先
                latch.countDown();
            },"b");
            one.start();
            two.start();
            latch.await();
            String result = "第" + i + "次( X = " + x + ";Y=" + y + ";a=" + a + ";b=" + b + ")";
            if(x == 0 && y == 0){
                System.out.println(result);
            }
        }
    }

}
