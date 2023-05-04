package Orion.res;

import Burst.Engine.Source.Core.Graphics.Sprite.Spritesheet;
import Burst.Engine.Source.Core.util.AssetManager;

public class Assets {



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
    public static final Spritesheet PLAYER_SPRITESHEET = new Spritesheet(AssetManager.getTexture(PLAYER), 16, 16, 0);
    public static final Spritesheet BLOCKS_SPRITESHEET = new Spritesheet(AssetManager.getTexture(BLOCKS), 16, 16, 0);
    public static final Spritesheet TURTLE_SPRITESHEET = new Spritesheet(AssetManager.getTexture(TURTLE), 16, 24, 0);
    public static final Spritesheet BIG_PLAYER_SPRITESHEET = new Spritesheet(AssetManager.getTexture(BIG_PLAYER), 16, 32, 0);
    public static final Spritesheet PIPES_SPRITESHEET = new Spritesheet(AssetManager.getTexture(PIPES), 32, 32, 0);
    public static final Spritesheet ITEMS_SPRITESHEET = new Spritesheet(AssetManager.getTexture(ITEMS), 16, 16, 0);
    public static final Spritesheet GIZMOS_SPRITESHEET = new Spritesheet(AssetManager.getTexture(GIZMOS), 24, 48, 0);

}
