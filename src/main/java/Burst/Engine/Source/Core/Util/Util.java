package Burst.Engine.Source.Core.Util;

import org.joml.Vector4f;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class Util {
    private static final AtomicLong idCounter = new AtomicLong();
    private static final AtomicLong tempIDCounter = new AtomicLong();


    public static long generateUniqueID() {
        return idCounter.incrementAndGet();
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

    public static float generateRandomColorValue(int range) {
        float colorValue = (float) Math.random();

        // map value in the given range
        colorValue *= range;

        return colorValue;
    }
    public static float generateRandomColorValue() {
        return generateRandomColorValue(1);
    }

    public static Vector4f generateRandomColor(int range) {
        float r = generateRandomColorValue(range);
        float g = generateRandomColorValue(range);
        float b = generateRandomColorValue(range);
        float a = 1.0f;
        return new Vector4f(r, g, b, a);
    }

    public static Vector4f generateRandomColor() {
        return generateRandomColor(1);
    }

    public static Object copy(Object value) {

        if (value == null) {
            return null;
        }
        // Check if the value is a primitive type
        if (value.getClass().isPrimitive()) {
            return value;
        }

        try {
            Class<?> clazz = value.getClass();

            Constructor<?> constructor = clazz.getConstructor();

            Object copy = constructor.newInstance();


            List<Field> fields = new ArrayList<>();

            // Also add all superclass fields from all superclasses
            Class<?> superClass = clazz.getSuperclass();
            while (superClass.getSuperclass() != null)
            {
                fields.addAll(List.of(superClass.getDeclaredFields()));
                superClass = superClass.getSuperclass();
            }

            fields.addAll(List.of(clazz.getDeclaredFields()));

            for (Field field : fields) {
                if (Modifier.isStatic(field.getModifiers()) || Modifier.isFinal(field.getModifiers())) {
                    continue;
                }
                field.setAccessible(true);
                Object fieldValue = field.get(value);
                field.set(copy, fieldValue);
            }


            return copy;
        } catch (Exception e) {
            return value;
        }
    }

  public static long generateTempID() {
    return idCounter.incrementAndGet();
  }
}
