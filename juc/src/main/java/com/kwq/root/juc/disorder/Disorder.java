package com.kwq.root.juc.disorder;

/**
 * @auther : kongweiqiang
 * @DATE : 2020/9/18
 * @DESC : 指令重排:无法保证有序性
 */
public class Disorder {

    private static int x =0,y=0,a =0,b=0;

    public static void main(String[] args) throws Exception{
        int i=0;
        for(;;){
            i++;
            x =0;y=0;
            a =0;b=0;
            Thread one = new Thread(()->{
                a = 1;//1
                x = b;
            });
            Thread two = new Thread(()->{
                b = 1;//1
                y = a;
            });
            one.start();
            two.start();
            one.join();
            two.join();
            String result = "第" + i + "次 ("  + x + "," + y + ")";
            /* 会存在x==y==0么?何时出现?
             * 当发生指令重排(单线程指令重排不影响最终一致性,系统为提高效率,存在指令重排),a = 1 和b = 1 均在线程内被排到了后面
             *
             */
            if(x==0 && y==0){
                System.err.println(result);
                break;
            }
        }
    }
}
