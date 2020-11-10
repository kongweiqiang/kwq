package com.kwq.root.juc.atomic;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @auther : kongweiqiang
 * @DATE : 2020/10/30
 * @DESC : Atomic可以解决CAS的比较替换的原子问题 LOCK 缓存行/总线
 */
public class AtomicInger_T {

    AtomicInteger count = new AtomicInteger(0);


}
