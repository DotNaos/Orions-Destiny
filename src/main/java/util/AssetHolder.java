package util;

import components.Spritesheet;

public class AssetHolder {

    // Shaders
        public static final String SHADERS = "Assets/shaders/";

    // Images
        public static final String SPRITESHEETS = "Assets/images/spritesheets/";
        public static final String TEXTURES = "Assets/images/textures/";

        public static final String DEBUGTEXTURES = "Assets/images/debug/";

        // Backgrounds
        public static final String BACKGROUNDS = "Assets/images/backgrounds/";

        // UI
        public static final String UI = "Assets/images/ui/";

    // Fonts
        public static final String FONTS = "Assets/fonts/";

    // Audio
        public static final String SOUNDS = "Assets/sounds/";

    // Maps
        public static final String MAPS = "Assets/maps/";

    //============================================================
    //========================  Asset Names  =====================
    //============================================================

    public static final String SHADER_DEFAULT = SHADERS + "default.glsl";
    // Fragment shader
    public static final String SHADER_PICKING = SHADERS + "pickingShader.glsl";
    // Vertex shader
    public static final String SHADER_DEBUG = SHADERS + "debugLine2D.glsl";



    //===================================================================

    public static final String PLAYER =  SPRITESHEETS + "spritesheet.png";
    public static final String BIG_PLAYER =  SPRITESHEETS + "bigSpritesheet.png";
    public static final String TURTLE =  SPRITESHEETS + "turtle.png";

    public static final String BLOCKS =  SPRITESHEETS +  "blocks.png";

    public static final String ITEMS =  SPRITESHEETS + "items.png";

    public static final String GIZMOS =  SPRITESHEETS +  "gizmos.png";

    public static final String PIPES =  SPRITESHEETS +  "pipes.png";

    //===================================================================

    public static final String FONT_INTER = FONTS + "inter.ttf";


    //===================================================================
    //========================  Asset Spritesheets  =====================
    //===================================================================
    public static final Spritesheet PLAYER_SPRITESHEET = new Spritesheet(AssetManager.getTexture(PLAYER), 16, 16, 26, 0);
    public static final Spritesheet BLOCKS_SPRITESHEET = new Spritesheet(AssetManager.getTexture(AssetHolder.BLOCKS), 32, 32, 81, 0);
    public static final Spritesheet TURTLE_SPRITESHEET = new Spritesheet(AssetManager.getTexture(AssetHolder.TURTLE), 16, 24, 4, 0);
    public static final Spritesheet BIG_PLAYER_SPRITESHEET = new Spritesheet(AssetManager.getTexture(AssetHolder.BIG_PLAYER), 16, 32, 42, 0);
    public static final Spritesheet PIPES_SPRITESHEET = new Spritesheet(AssetManager.getTexture(AssetHolder.PIPES), 32, 32, 4, 0);
    public static final Spritesheet ITEMS_SPRITESHEET = new Spritesheet(AssetManager.getTexture(AssetHolder.ITEMS), 16, 16, 43, 0);
    public static final Spritesheet GIZMOS_SPRITESHEET = new Spritesheet(AssetManager.getTexture(AssetHolder.GIZMOS), 24, 48, 3, 0);

}
