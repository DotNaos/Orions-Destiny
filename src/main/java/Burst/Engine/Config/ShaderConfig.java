package Burst.Engine.Config;

import static Burst.Engine.Config.ProjectConfig.EnginePath;

public class ShaderConfig {
    public static String SHADER_PATH = EnginePath + "Shaders/";

    /**
     * Default Shader - Contains Fragment and Vertex Shader
     */
    public static final String SHADER_DEFAULT = SHADER_PATH + "default.glsl";

    /**
     * Picking Shader - Used to pick Pixel on Screen
     */
    public static final String SHADER_PICKING = SHADER_PATH + "pickingShader.glsl";

    /**
     * DebugDraw Shader - Used to draw Debug Shapes
     */
    public static final String SHADER_DEBUG = SHADER_PATH + "debugLine2D.glsl";

}
