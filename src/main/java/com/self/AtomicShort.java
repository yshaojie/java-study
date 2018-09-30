package com.self;

import sun.misc.Unsafe;

import java.io.Serializable;
import java.lang.reflect.Field;

/**
 * Created by shaojieyue
 * Created time 2018-09-30 11:08
 */
public class AtomicShort extends Number implements Serializable {
    private static Unsafe unsafe;
    private volatile short value;
    private static long valueOffset;
    
    static {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            unsafe = (Unsafe)field.get(null);
        } catch (Exception e) {
            throw new Error(e);
        }

        try {
            valueOffset = unsafe.objectFieldOffset(AtomicShort.class.getDeclaredField("value"));
        } catch (NoSuchFieldException e) {
            throw new Error(e);
        }
    }
    
    public AtomicShort(short initialValue) {
        value = initialValue;
    }

    /**
     * Creates a new AtomicInteger with initial value {@code 0}.
     */
    public AtomicShort() {
    }

    public final short incrementAndGet() {
        for (; ; ) {
            final short currentVal = unsafe.getShort(this, valueOffset);
            if (unsafe.compareAndSwapInt(this,valueOffset,currentVal,currentVal+1)) {
                return currentVal;
            }
        }
    }

    public final short getAndSet() {
        return 0;
    }
    
    public short get() {
        return value;
    }
    /**
     * Returns the value of the specified number as an {@code int},
     * which may involve rounding or truncation.
     *
     * @return the numeric value represented by this object after conversion
     * to type {@code int}.
     */
    public int intValue() {
        return get();
    }

    /**
     * Returns the value of the specified number as a {@code long},
     * which may involve rounding or truncation.
     *
     * @return the numeric value represented by this object after conversion
     * to type {@code long}.
     */
    public long longValue() {
        return get();
    }

    /**
     * Returns the value of the specified number as a {@code float},
     * which may involve rounding.
     *
     * @return the numeric value represented by this object after conversion
     * to type {@code float}.
     */
    public float floatValue() {
        return get();
    }

    /**
     * Returns the value of the specified number as a {@code double},
     * which may involve rounding.
     *
     * @return the numeric value represented by this object after conversion
     * to type {@code double}.
     */
    public double doubleValue() {
        return get();
    }
}
