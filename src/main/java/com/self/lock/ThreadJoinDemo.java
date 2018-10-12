package com.self.lock;

/**
 *
 * thread.Join把指定的线程加入到当前线程，可以将两个交替执行的线程合并为顺序执行的线程。
 * 比如在线程B中调用了线程A的Join()方法，直到线程A执行完毕后，才会继续执行线程B。
 * Created by shaojieyue
 * Created time 2018-10-12 14:01
 */
public class ThreadJoinDemo {
    public static void main(String[] args) {
        final Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName()+" start...");
            }
        },"thread1");

        final Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    thread1.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName()+" start...");
            }
        },"thread2");

        final Thread thread3 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    thread2.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName()+" start...");
            }
        },"thread3");


        thread1.start();
        thread2.start();
        thread3.start();
    }
}
