package Burst.Engine.Config;

import static Burst.Engine.Config.ProjectConfig.EnginePath;

public class Shader_Config {
    public static final String PATH = EnginePath + "Shaders/";

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
