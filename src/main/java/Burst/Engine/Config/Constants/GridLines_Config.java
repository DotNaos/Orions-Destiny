package Burst.Engine.Config.Constants;

import org.joml.Vector4f;

/**
 * @author Oliver Schütz
 * The {@code GridLines_Config} class contains constants related to grid line configuration.
 * <p>
 * The {@link GridLines_Config#SIZE} and {@link GridLines_Config#COLOR} constants are used to configure the grid lines.
 * These fields specify the width and height of the grid lines, respectively, in floating-point units.
 * </p>
 * <p>
 * This class is intended to be used as a utility class and thus does not provide a public constructor.
 * </p>
 *
 *
 * @version 1.0
 * @since 2023-05-08
 */
public class GridLines_Config {
  public static float SIZE = 1f;
  public static Vector4f COLOR = new Vector4f(0.2f, 0.2f, 0.2f, 0.0f);
}
