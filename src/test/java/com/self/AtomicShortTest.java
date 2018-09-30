package com.self;

/**
 * Created by shaojieyue
 * Created time 2018-09-30 11:33
 */
public class AtomicShortTest {
    public static void main(String[] args) {
        AtomicShort atomicShort = new AtomicShort();
        for (int i = 0; i < 1000000; i++) {
            atomicShort.incrementAndGet();
            if (i%1000 == 0) {
                System.out.println(i);
                System.out.println(Short.MAX_VALUE);
            }
        }
        System.out.println(Short.MAX_VALUE);
        System.out.println(atomicShort.get());
    }
}
