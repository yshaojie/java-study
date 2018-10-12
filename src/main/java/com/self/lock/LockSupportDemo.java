package com.self.lock;

import java.util.concurrent.locks.LockSupport;

/**
 * Created by shaojieyue
 * Created time 2018-10-09 19:41
 */
public class LockSupportDemo {
    public static void main(String[] args) throws InterruptedException {
        final Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                LockSupport.park();
                System.out.println(Thread.currentThread().getName()+" start...");
            }
        },"thread1");

        final Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                LockSupport.park();
                System.out.println(Thread.currentThread().getName()+" start...");
                LockSupport.unpark(thread1);
            }
        },"thread2");

        final Thread thread3 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName()+" start...");
                LockSupport.unpark(thread2);
            }
        },"thread3");


        thread1.start();
        thread2.start();
        thread3.start();

        LockSupport.unpark(thread3);
    }
}
