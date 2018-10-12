package com.self.thread;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * 验证线程各个状态
 * Created by shaojieyue
 * Created time 2018-10-12 11:53
 */
public class ThreadStateDemo {
    final static Object lock = new Object();
    final static ReentrantLock reentrantLock = new ReentrantLock();
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(1);
                    synchronized (lock) {
                        Thread.sleep(3000);
                        System.out.println(Thread.currentThread().getState());
                        lock.wait();
                    }

                    reentrantLock.lock();
                    reentrantLock.unlock();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.stop();
        System.out.println("NEW="+thread.getState());
        thread.start();
        System.out.println("RUNNABLE="+thread.getState());
        synchronized (lock) {
            TimeUnit.SECONDS.sleep(2);
            System.out.println("BLOCKED="+thread.getState());
        }

        TimeUnit.SECONDS.sleep(1);
        System.out.println("TIMED_WAITING="+thread.getState());
        synchronized (lock) {
            TimeUnit.SECONDS.sleep(2);
            System.out.println("WAITING="+thread.getState());
            lock.notifyAll();
            reentrantLock.lock();
        }

        TimeUnit.SECONDS.sleep(1);
        System.out.println("WAITING="+thread.getState());
        reentrantLock.unlock();
        thread.join();
        System.out.println("TERMINATED="+thread.getState());
    }
}
