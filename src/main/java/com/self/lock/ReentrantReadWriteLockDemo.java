package com.self.lock;


import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by shaojieyue
 * Created time 2018-10-04 17:14
 */
public class ReentrantReadWriteLockDemo {

    public static void main(String[] args) {
        final Cache cache = new Cache();
        for (int i = 0; i < 3; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (; ; ) {
                        final int count = cache.getCount();
                        System.out.println(Thread.currentThread().getName()+ " get count="+count);
                        try {
                            TimeUnit.SECONDS.sleep(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }, "consumer-" + i).start();
        }

        for (int i = 0; i < 3; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (; ; ) {
                        cache.setCount(ThreadLocalRandom.current().nextInt(10));
                        try {
                            TimeUnit.MILLISECONDS.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }, "product-" + i).start();
        }
    }

    static class Cache {
        private int count;
        ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

        public void setCount(int count) {
            readWriteLock.writeLock().lock();
            System.out.println(Thread.currentThread().getName()+" lock and set count.");
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                this.count = count;
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                readWriteLock.writeLock().unlock();
            }
        }

        public int getCount() {
            readWriteLock.readLock().lock();
            int count = 0;
            try {
                count = this.count;
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                readWriteLock.readLock().unlock();
            }

            return count;
        }
    }

}
