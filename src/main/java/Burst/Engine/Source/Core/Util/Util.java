package Burst.Engine.Source.Core.Util;

import org.joml.Vector4f;

import java.util.concurrent.atomic.AtomicLong;

public class Util {
    private static final AtomicLong counter = new AtomicLong();


    public static long generateUniqueID() {
        return counter.incrementAndGet();
    }

    public static long generateHashID(String filepath) {
        return filepath.hashCode();
    }

    public static Vector4f hexToVec4f(int HEX)
    {
        float r = ((HEX >> 16) & 0xFF) / 255f;
        float g = ((HEX >> 8) & 0xFF) / 255f;
        float b = ((HEX) & 0xFF) / 255f;
        float a = ((HEX >> 24) & 0xFF) / 255f;
        return new Vector4f(r, g, b, a);
    }
}
