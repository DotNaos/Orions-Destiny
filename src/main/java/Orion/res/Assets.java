package Orion.res;

import Burst.Engine.Config.ProjectConfig;

import Burst.Engine.Source.Core.Assets.Graphics.Shader;
import Burst.Engine.Source.Core.Assets.Graphics.Spritesheet;
import Burst.Engine.Source.Core.Assets.AssetManager;
import Burst.Engine.Source.Core.Assets.Graphics.Texture;

import java.util.List;

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
    // SpriteSheets
    public static final String PLAYER =  SPRITESHEETS + "spritesheet.png";
    public static final String BIG_PLAYER =  SPRITESHEETS + "bigSpritesheet.png";
    public static final String TURTLE =  SPRITESHEETS + "turtle.png";

    public static final String BLOCKS =  SPRITESHEETS +  "blocks.png";

    public static final String ITEMS =  SPRITESHEETS + "items.png";

    public static final String GIZMOS =  SPRITESHEETS +  "gizmos.png";

    public static final String PIPES =  SPRITESHEETS +  "pipes.png";

    //===================================================================
    // Fonts
    public static final String FONT_INTER = FONTS + "inter.ttf";





    //===================================================================
    //========================  Asset Spritesheets  =====================
    //===================================================================
    public static final Spritesheet PLAYER_SPRITESHEET = new Spritesheet(AssetManager.getAssetFromType(PLAYER, Texture.class), 16, 16, 0);
    public static final Spritesheet BLOCKS_SPRITESHEET = new Spritesheet(AssetManager.getAssetFromType(BLOCKS, Texture.class), 16, 16, 0,
            new int[] {
                    16,
                    16,
                    4,
                    13,
                    10,
                    9,
                    7,
                    9, 14, 17, 18, 5, 21, 8, 3, 0, 7, 20,
                    15, 29, 32, 30, 32, 16, 16, 8, 5, 0, 0, 15, 15, 15
            }
            );
    public static final Spritesheet TURTLE_SPRITESHEET = new Spritesheet(AssetManager.getAssetFromType(TURTLE, Texture.class), 16, 24, 0);
    public static final Spritesheet BIG_PLAYER_SPRITESHEET = new Spritesheet(AssetManager.getAssetFromType(BIG_PLAYER, Texture.class), 16, 32, 0);
    public static final Spritesheet PIPES_SPRITESHEET = new Spritesheet(AssetManager.getAssetFromType(PIPES, Texture.class), 32, 32, 0);
    public static final Spritesheet ITEMS_SPRITESHEET = new Spritesheet(AssetManager.getAssetFromType(ITEMS, Texture.class), 16, 16, 0);
    public static final Spritesheet GIZMOS_SPRITESHEET = new Spritesheet(AssetManager.getAssetFromType(GIZMOS, Texture.class), 24, 48, 0);

    public static final List<Spritesheet> SPRITESHEETS_LIST = List.of(PLAYER_SPRITESHEET, BLOCKS_SPRITESHEET, TURTLE_SPRITESHEET, BIG_PLAYER_SPRITESHEET, PIPES_SPRITESHEET, ITEMS_SPRITESHEET, GIZMOS_SPRITESHEET);


}
