package Burst.Engine.Config.Constants;

import org.joml.Vector4f;

public class Color_Config {
    public static Vector4f TRANSPARENT = new Vector4f(0, 0, 0, 0);
    public static Vector4f WHITE = new Vector4f(1, 1, 1, 1);
    public static Vector4f BLACK = new Vector4f(0, 0, 0, 1);
    public static Vector4f RED = new Vector4f(1, 0, 0, 1);
    public static Vector4f GREEN = new Vector4f(0, 1, 0, 1);
    public static Vector4f BLUE = new Vector4f(0, 0, 1, 1);
    public static Vector4f YELLOW = new Vector4f(1, 1, 0, 1);
    public static Vector4f MAGENTA = new Vector4f(1, 0, 1, 1);
    public static Vector4f CYAN = new Vector4f(0, 1, 1, 1);
    public static Vector4f GRAY = new Vector4f(0.5f, 0.5f, 0.5f, 1);
    public static Vector4f LIGHT_GRAY = new Vector4f(0.75f, 0.75f, 0.75f, 1);
    public static Vector4f DARK_GRAY = new Vector4f(0.25f, 0.25f, 0.25f, 1);
    public static Vector4f ORANGE = new Vector4f(1, 0.5f, 0, 1);
    public static Vector4f BROWN = new Vector4f(0.6f, 0.3f, 0.1f, 1);
    public static Vector4f PINK = new Vector4f(1, 0.68f, 0.68f, 1);

    public static Vector4f[] COLORS = new Vector4f[]{
            TRANSPARENT,
            WHITE,
            BLACK,
            RED,
            GREEN,
            BLUE,
            YELLOW,
            MAGENTA,
            CYAN,
            GRAY,
            LIGHT_GRAY,
            DARK_GRAY,
            ORANGE,
            BROWN,
            PINK
    };

    public static Vector4f getRandomColor() {
        return COLORS[(int) (Math.random() * COLORS.length)];
    }

    public static Vector4f getRandomColor(Vector4f[] colors) {
        return colors[(int) (Math.random() * colors.length)];
    }

    public static Vector4f getRandomColor(Vector4f[] colors, Vector4f exclude) {
        Vector4f color = exclude;
        while (color == exclude) {
            color = colors[(int) (Math.random() * colors.length)];
        }
        return color;
    }

    public static Vector4f getRandomColor(Vector4f exclude) {
        Vector4f color = exclude;
        while (color == exclude) {
            color = COLORS[(int) (Math.random() * COLORS.length)];
        }
        return color;
    }

    public static Vector4f getRandomColor(Vector4f exclude1, Vector4f exclude2) {
        Vector4f color = exclude1;
        while (color == exclude1 || color == exclude2) {
            color = COLORS[(int) (Math.random() * COLORS.length)];
        }
        return color;
    }

    public static Vector4f getRandomColor(Vector4f exclude1, Vector4f exclude2, Vector4f exclude3) {
        Vector4f color = exclude1;
        while (color == exclude1 || color == exclude2 || color == exclude3) {
            color = COLORS[(int) (Math.random() * COLORS.length)];
        }
        return color;
    }
}
