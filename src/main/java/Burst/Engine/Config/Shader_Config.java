package Burst.Engine.Config;

/**
 * @Author Oliver Schütz
 * <p>
 * The Shader_Config class provides constants for shader paths used in the application.
 * <p>
 * All paths are relative to the "Assets/Shaders/" directory.
 */
public class Shader_Config {
  public static final String PATH = "Assets/Shaders/";

  /**
   * Default Shader - Contains Fragment and Vertex Shader
   */
  public static final String SHADER_DEFAULT = PATH + "default.glsl";

  /**
   * Picking Shader - Used to pick Pixel on Screen
   */
  public static final String SHADER_PICKING = PATH + "pickingShader.glsl";

  /**
   * DebugDraw Shader - Used to draw Debug Shapes
   */
  public static final String SHADER_DEBUG = PATH + "debugLine2D.glsl";

}
