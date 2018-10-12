package com.self.lock;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * Created by shaojieyue
 * Created time 2018-10-10 14:49
 */
public class CyclicBarrierDemo {
    public static void main(String[] args) {
        final CyclicBarrier cyclicBarrier = new CyclicBarrier(3);

        for (int i = 0; i < 6; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        cyclicBarrier.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName()+" started");
                }
            },"thread-"+i).start();

            if (i == 3) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
