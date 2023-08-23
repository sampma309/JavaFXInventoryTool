package com.example.sampc482pa;

import java.util.concurrent.atomic.AtomicInteger;


/**
 * A class that keeps track of the next available part and product ID numbers to ensure there
 * are no duplicates
 *
 * @author Michael Samp
 */
public abstract class IDCounters {

    final private static AtomicInteger nextAvailablePartID = new AtomicInteger(100);
    final private static AtomicInteger nextAvailableProductID = new AtomicInteger(1000);

    /**
     * Returns the next available part ID
     *
     * @return The part ID
     */
    public static int getNextAvailablePartID() {
        return nextAvailablePartID.getAndIncrement();
    }

    /**
     * Returns the next available product ID
     *
     * @return The product ID
     */
    public static int getNextAvailableProductID() {
        return nextAvailableProductID.getAndIncrement();
    }
}
