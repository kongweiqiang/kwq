package com.kwq.root.juc.escape;

/**
 * @auther : kongweiqiang
 * @DATE : 2020/10/29
 * @DESC :
 */
public class ThisEscape {

    private int num = 8;//invoke和astore方法可能会由于cpu调度重排,导致输出初始值(半初始化对象)为0

    public ThisEscape(){
        new Thread(()-> System.out.println(this.num)).start();
    }

    public static void main(String[] args) throws Exception {
        new ThisEscape();
        System.in.read();
    }
}
