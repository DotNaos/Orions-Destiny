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
        float r = ((HEX >> 24) & 0xFF) / 255f;
        float g = ((HEX >> 16) & 0xFF) / 255f;
        float b = ((HEX >> 8) & 0xFF) / 255f;
        float a = ((HEX) & 0xFF) / 255f;
        return new Vector4f(r, g, b, a);
    }

    /**
     * @param seed The value of a color channel from which to generate a new color value.
     * @param range The maximum value of a color channel. Starts at 0.
     * @return a new color value that is unique to the given value, but deterministic.
     */
    public static float generateUniqueColorValue(float seed, int range) {
        float colorValue = (seed * 0.618033988749895f) % 1;

        // map value in the given range
        colorValue = colorValue * range;

        return colorValue;
    }
}
