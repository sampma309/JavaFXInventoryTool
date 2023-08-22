package com.example.sampc482pa;

import java.util.concurrent.atomic.AtomicInteger;

public class IDCounters {

    final private static AtomicInteger nextAvailablePartID = new AtomicInteger(100);
    final private static AtomicInteger nextAvailableProductID = new AtomicInteger(1000);

    public static int getNextAvailablePartID() {
        return nextAvailablePartID.getAndIncrement();
    }

    public static int getNextAvailableProductID() {
        return nextAvailableProductID.getAndIncrement();
    }
}
