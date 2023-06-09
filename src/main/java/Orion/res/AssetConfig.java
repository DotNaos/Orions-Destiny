package Orion.res;

import Burst.Engine.Source.Core.Assets.Graphics.SpriteSheetConfig;
import Burst.Engine.Source.Core.Assets.Graphics.SpriteSheetUsage;

import java.util.List;

public class AssetConfig {

  //!=========================================================================
  //!========================  Asset Directories  ============================
  //!=========================================================================

  public static final String DIR_OF_ASSETS = "Assets/";

  // Fonts
  public static final String DIR_OF_FONTS = DIR_OF_ASSETS + "fonts/";

  // Images
  public static final String DIR_OF_IMAGES = DIR_OF_ASSETS + "images/";

    public static final String DIR_OF_SPRITESHEETS = DIR_OF_IMAGES + "spritesheets/";
    public static final String DIR_OF_ENEMIES = DIR_OF_SPRITESHEETS + "enemy/";
    public static final String DIR_OF_UI = DIR_OF_SPRITESHEETS + "ui/";

    public static final String DIR_OF_ENVIRONMENT = DIR_OF_SPRITESHEETS + "environment/";
    public static final String DIR_OF_TILES = DIR_OF_ENVIRONMENT + "tiles/";

    // Textures
    public static final String DIR_OF_TEXTURES = DIR_OF_IMAGES + "textures/";
    public static final String DIR_OF_ICONS = DIR_OF_TEXTURES + "icons/";


    public static final String DIR_OF_DEBUG_TEXTURES = DIR_OF_IMAGES + "debug/";

    // Backgrounds
    public static final String DIR_OF_BACKGROUNDS = DIR_OF_IMAGES + "background/";


  // Audio
  public static final String DIR_OF_SOUNDS = "Assets/sounds/";

  // Maps
  public static final String DIR_OF_MAPS = "Assets/maps/";



  //!===================================================================
  //!========================  Asset Files  ============================
  //!===================================================================


  //? ======================== Fonts - UI ==============================
  public static final String FONT_INTER = DIR_OF_FONTS + "Inter.ttf";

  //? ======================== Fonts - Game ==============================

  public static final String FONT_PEABERRY = DIR_OF_FONTS + "Peaberry.ttf";

  //? ======================== Images - Icons ==========================
    public static final String ICON_ACTOR = DIR_OF_ICONS + "actor.png";
    public static final String ICON_PAWN = DIR_OF_ICONS + "pawn.png";
    public static final String ICON_ENEMY = DIR_OF_ICONS + "enemy.png";
    public static final String ICON_ITEM = DIR_OF_ICONS + "item.png";
    public static final String ICON_BLOCK = DIR_OF_ICONS + "block.png";
    public static final String ICON_PLAYER = DIR_OF_ICONS + "player.png";
    public static final String ICON_CAMERA = DIR_OF_ICONS + "camera.png";


  //? =================== Images - SpriteSheets ==========================

    // Editor
      public static final String GIZMOS = DIR_OF_UI + "gizmos.png";

    // Enemies
      public static final String ENEMY_1 = DIR_OF_SPRITESHEETS + DIR_OF_ENEMIES + "enemy_1.png";

    // UI

      public static final String BUTTONS = DIR_OF_UI + "buttons.png";

    // Level Blocks
      public static final String BLOCKS = DIR_OF_TILES + "blocks.png";


    //!========================================================================
    //!========================  SpriteSheet Configs  =========================
    //!========================================================================
    public static final List<SpriteSheetConfig> SPRITESHEETS_CONFIG = List.of(
            new SpriteSheetConfig(GIZMOS, SpriteSheetUsage.UI, 24, 48, 0),
            new SpriteSheetConfig(BUTTONS, SpriteSheetUsage.UI, 16, 16, 0),
            new SpriteSheetConfig(BLOCKS, SpriteSheetUsage.BLOCK, 16, 16, 0, new int[]{16, 16, 4, 13, 10, 9, 7, 9, 14, 17, 18, 5, 21, 8, 3, 0, 7, 20, 15, 29, 32, 30, 32, 16, 16, 8, 5, 0, 0, 15, 15, 15})
    );


}
