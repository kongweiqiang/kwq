package com.kwq.root.juc.support;

/**
 * @auther : kongweiqiang
 * @DATE : 2020/9/18
 * @DESC :
 */
public class Exchanger {

    public static void main(String[] args) {
        char [] a1="123456".toCharArray();
        char [] a2="abcdef".toCharArray();
        java.util.concurrent.Exchanger exchanger = new java.util.concurrent.Exchanger();

        new Thread(()->{
            try{
                for(char c: a1){
                    System.out.println("数字线程"+exchanger.exchange(c));
                }
            }catch (Exception e){

            }
        },"t1").start();

        new Thread(()->{
            try{
                for(char c: a2){
                    System.out.println("字符线程"+exchanger.exchange(c));
                }
            }catch (Exception e){

            }
        },"t2").start();
    }
}
