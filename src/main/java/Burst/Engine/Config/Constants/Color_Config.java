package Burst.Engine.Config.Constants;

import org.joml.Vector4f;

/**
 * @author Oliver Schuetz
 * <p>
 * The {@code Color_Config} class provides constants and utility methods for working with colors.
 * It includes predefined colors as {@code Vector4f} objects and methods to retrieve random colors.
 * </p>
 */
public class Color_Config {

  /**
   * The RGBA value for the active actor color.
   */
  public static final Vector4f ACTIVE_ACTOR = new Vector4f(0.5f, 0.5f, 0.5f, 1);

  /**
   * The RGBA value for the transparent color.
   */
  public static Vector4f TRANSPARENT = new Vector4f(0, 0, 0, 0);

  /**
   * The RGBA value for the white color.
   */
  public static Vector4f WHITE = new Vector4f(1, 1, 1, 1);

  /**
   * The RGBA value for the black color.
   */
  public static Vector4f BLACK = new Vector4f(0, 0, 0, 1);

  /**
   * The RGBA value for the red color.
   */
  public static Vector4f RED = new Vector4f(1, 0, 0, 1);

  /**
   * The RGBA value for the green color.
   */
  public static Vector4f GREEN = new Vector4f(0, 1, 0, 1);

  /**
   * The RGBA value for the blue color.
   */
  public static Vector4f BLUE = new Vector4f(0, 0, 1, 1);

  /**
   * The RGBA value for the yellow color.
   */
  public static Vector4f YELLOW = new Vector4f(1, 1, 0, 1);

  /**
   * The RGBA value for the magenta color.
   */
  public static Vector4f MAGENTA = new Vector4f(1, 0, 1, 1);

  /**
   * The RGBA value for the cyan color.
   */
  public static Vector4f CYAN = new Vector4f(0, 1, 1, 1);

  /**
   * The RGBA value for the gray color.
   */
  public static Vector4f GRAY = new Vector4f(0.5f, 0.5f, 0.5f, 1);

  /**
   * The RGBA value for the light gray color.
   */
  public static Vector4f LIGHT_GRAY = new Vector4f(0.75f, 0.75f, 0.75f, 1);

  /**
   * The RGBA value for the dark gray color.
   */
  public static Vector4f DARK_GRAY = new Vector4f(0.25f, 0.25f, 0.25f, 1);

  /**
   * The RGBA value for the orange color.
   */
  public static Vector4f ORANGE = new Vector4f(1, 0.5f, 0, 1);

  /**
   * The RGBA value for the brown color.
   */
  public static Vector4f BROWN = new Vector4f(0.6f, 0.3f, 0.1f, 1);

  /**
   * The RGBA value for the pink color.
   */
  public static Vector4f PINK = new Vector4f(1, 0.68f, 0.68f, 1);

  /**
   * An array of predefined colors.
   */
  public static Vector4f[] COLORS = new Vector4f[]{TRANSPARENT, WHITE, BLACK, RED, GREEN, BLUE

          , YELLOW, MAGENTA, CYAN, GRAY, LIGHT_GRAY, DARK_GRAY, ORANGE, BROWN, PINK};

  /**
   * Returns a random color from the predefined color array {@code COLORS}.
   *
   * @return a random color as a {@code Vector4f} object
   */
  public static Vector4f getRandomColor() {
    return COLORS[(int) (Math.random() * COLORS.length)];
  }

  /**
   * Returns a random color from the specified color array.
   *
   * @param colors the array of colors to choose from
   * @return a random color as a {@code Vector4f} object
   */
  public static Vector4f getRandomColor(Vector4f[] colors) {
    return colors[(int) (Math.random() * colors.length)];
  }

  /**
   * Returns a random color from the specified color array, excluding a specified color.
   *
   * @param colors  the array of colors to choose from
   * @param exclude the color to exclude from the selection
   * @return a random color as a {@code Vector4f} object
   */
  public static Vector4f getRandomColor(Vector4f[] colors, Vector4f exclude) {
    Vector4f color = exclude;
    while (color == exclude) {
      color = colors[(int) (Math.random() * colors.length)];
    }
    return color;
  }

  /**
   * Returns a random color from the predefined color array {@code COLORS},
   * excluding a specified color.
   *
   * @param exclude the color to exclude from the selection
   * @return a random color as a {@code Vector4f} object
   */
  public static Vector4f getRandomColor(Vector4f exclude) {
    Vector4f color = exclude;
    while (color == exclude) {
      color = COLORS[(int) (Math.random() * COLORS.length)];
    }
    return color;
  }

  /**
   * Returns a random color from the predefined color array {@code COLORS},
   * excluding two specified colors.
   *
   * @param exclude1 the first color to exclude from the selection
   * @param exclude2 the second color to exclude from the selection
   * @return a random color as a {@code Vector4f} object
   */
  public static Vector4f getRandomColor(Vector4f exclude1, Vector4f exclude2) {
    Vector4f color = exclude1;
    while (color == exclude1 || color == exclude2) {
      color = COLORS[(int) (Math.random() * COLORS.length)];
    }
    return color;
  }

  /**
   * Returns a random color from the predefined color array {@code COLORS},
   * excluding three specified colors.
   *
   * @param exclude1 the first color to exclude from the selection
   * @param exclude2 the second color to exclude from the selection
   * @param exclude3 the third color to exclude from the selection
   * @return a random color as a {@code Vector4f} object
   */
  public static Vector4f getRandomColor(Vector4f exclude1, Vector4f exclude2, Vector4f exclude3) {
    Vector4f color = exclude1;
    while (color == exclude1 || color == exclude2 || color == exclude3) {
      color = COLORS[(int) (Math.random() * COLORS.length)];
    }
    return color;
  }
}