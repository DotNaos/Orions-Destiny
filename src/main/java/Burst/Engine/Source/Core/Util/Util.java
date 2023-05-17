package Burst.Engine.Source.Core.Util;

import java.util.concurrent.atomic.AtomicLong;

public class Util {
    private static final AtomicLong counter = new AtomicLong();


    public static long generateUniqueID() {
        return counter.incrementAndGet();
    }

    public static long generateHashID(String filepath) {
        return filepath.hashCode();
    }
}
