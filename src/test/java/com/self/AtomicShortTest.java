package com.self;

/**
 * Created by shaojieyue
 * Created time 2018-09-30 11:33
 */
public class AtomicShortTest {
    public static void main(String[] args) {
        AtomicShort atomicShort = new AtomicShort();
        for (int i = 0; i < Short.MAX_VALUE*2+3; i++) {
            final short i1 = atomicShort.incrementAndGet();
        }
    }
}
