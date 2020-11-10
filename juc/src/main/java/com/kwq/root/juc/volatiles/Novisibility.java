package com.kwq.root.juc.volatiles;

/**
 * @auther : kongweiqiang
 * @DATE : 2020/10/29
 * @DESC :
 */
public class Novisibility {

    private static boolean ready = false; //要加volatile
    private static int number;

    private static class ReaderThread extends Thread{
        @Override
        public void run() {
            while(!ready){
                Thread.yield();
            }
            System.out.println(number);
        }
    }

    public static void main(String[] args) throws Exception{
        Thread t = new ReaderThread();
        t.start();
        number = 42;
        ready = true;//由于重排可能导致这行在t.start()前执行,导致number=0
        t.join();
    }

}
