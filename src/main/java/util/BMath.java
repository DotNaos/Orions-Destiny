package util;

import org.joml.Vector2f;

public class BMath {


    /**
     * Rotates a vector around an origin point.
     * @param vec The vector to rotate.
     * @param angleDeg The angle to rotate by in degrees.
     * @param origin The origin point to rotate around.
     */
    public static void rotate(Vector2f vec, float angleDeg, Vector2f origin) {

        float x = vec.x - origin.x;
        float y = vec.y - origin.y;

        float angleRad = (float) Math.toRadians(angleDeg);
        float cos = (float) Math.cos(angleRad);
        float sin = (float) Math.sin(angleRad);

        float xPrime = (x * cos) - (y * sin);
        float yPrime = (x * sin) + (y * cos);

        xPrime += origin.x;
        yPrime += origin.y;

        vec.x = xPrime;
        vec.y = yPrime;
    }


    /**
     * @param x First float value
     * @param y Second float value
     * @param epsilon The maximum difference between the two values
     * @return True if the difference between the two values is less than epsilon
     */
    public static boolean compare(float x, float y, float epsilon) {
        return Math.abs(x - y) <= epsilon * Math.max(1.0f, Math.max(Math.abs(x), Math.abs(y)));
    }

    public static boolean compare(Vector2f a, Vector2f b, float epsilon) {
        return compare(a.x, b.x, epsilon) && compare(a.y, b.y, epsilon);
    }
    public static boolean compare(float x, float y) {
        return Math.abs(x - y) <= Float.MIN_VALUE * Math.max(1.0f, Math.max(Math.abs(x), Math.abs(y)));
    }
    public static boolean compare(Vector2f a, Vector2f b) {
        return compare(a.x, b.x) && compare(a.y, b.y);
    }

}
