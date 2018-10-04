package com.self.lock;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by shaojieyue
 * Created time 2018-10-04 16:33
 */
public class ReenterLockDemo {
    ReentrantLock reentrantLock = new ReentrantLock();
    Condition consumerCondition = reentrantLock.newCondition();
    Condition productCondition = reentrantLock.newCondition();
    private int items = 0;

    public static void main(String[] args) {
        new ReenterLockDemo().start();
    }

    public void start() {
        for (int i = 0; i < 3; i++) {
            new Thread(new Consumer(),"consumer-"+i).start();
        }

        for (int i = 0; i < 3; i++) {
            new Thread(new Producter(),"product-"+i).start();
        }
    }

    class Consumer implements Runnable {

        /**
         * When an object implementing interface <code>Runnable</code> is used
         * to create a thread, starting the thread causes the object's
         * <code>run</code> method to be called in that separately executing
         * thread.
         * <p>
         * The general contract of the method <code>run</code> is that it may
         * take any action whatsoever.
         *
         * @see Thread#run()
         */
        public void run() {
            for (; ; ) {
                reentrantLock.lock();
                try {
                    if (items == 0) {//没有可消费的元素,则等待着
                        try {
                            System.out.println(Thread.currentThread().getName() + " wait a item ");
                            consumerCondition.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else if (items > 0) {
                        items --;
//                        System.out.println(Thread.currentThread().getName() + " consume a item " + count);

                    }
                    //消费一个则通知某一个成生产者进行生成
                    productCondition.signalAll();
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    reentrantLock.unlock();
                }
                try {
                    TimeUnit.MILLISECONDS.sleep(ThreadLocalRandom.current().nextInt(500));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class Producter implements Runnable {

        /**
         * When an object implementing interface <code>Runnable</code> is used
         * to create a thread, starting the thread causes the object's
         * <code>run</code> method to be called in that separately executing
         * thread.
         * <p>
         * The general contract of the method <code>run</code> is that it may
         * take any action whatsoever.
         *
         * @see Thread#run()
         */
        public void run() {
            for (; ; ) {
                reentrantLock.lock();
                try {
                    if (items >= 3) { //元素已满,则等待唤醒,然后开始生产
                        try {
                            productCondition.await();
                            System.out.println(Thread.currentThread().getName() + " wait product a item ");
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        items++;
//                        System.out.println(Thread.currentThread().getName() + " product a item ");
                        consumerCondition.signal();//通知消费者去消费
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    reentrantLock.unlock();
                }

                try {
                    TimeUnit.MILLISECONDS.sleep(ThreadLocalRandom.current().nextInt(500));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
