package com.kwq.root.juc.countdownlanch;

import java.util.concurrent.CountDownLatch;

/**
 * @auther : kongweiqiang
 * @DATE : 2020/10/30
 * @DESC :
 */
public class CountDownLanchPlusPlus {

    private static long n = 0L;

    public static void main(String[] args) throws Exception {
        int sum = 10000;
        Thread [] threads = new Thread[100];
        CountDownLatch countDownLatch = new CountDownLatch(threads.length);
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(()->{
                for (int j = 0; j < sum; j++) {
                    //synchronized (CountDownLanchPlusPlus.class) {
                        n++;
                    //}
                }
                countDownLatch.countDown();
            });
        }

        for (Thread thread : threads) {
            thread.start();
        }
        countDownLatch.await();
        System.out.println(n);
    }
}
