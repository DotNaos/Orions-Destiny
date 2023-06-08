package Orion.res;

import Burst.Engine.Source.Core.Assets.Graphics.SpriteSheetConfig;
import Burst.Engine.Source.Core.Assets.Graphics.SpriteSheetUsage;

import java.util.List;

public class AssetConfig {

  //!=========================================================================
  //!========================  Asset Directories  ============================
  //!=========================================================================

  public static final String DIR_OF_ASSETS = "Assets/";

  // Images
  public static final String DIR_OF_IMAGES = "Assets/images/";

    public static final String DIR_OF_SPRITESHEETS = DIR_OF_IMAGES + "spritesheets/";
    public static final String DIR_OF_ENEMIES = DIR_OF_SPRITESHEETS + "Enemy/";

    // Textures
    public static final String DIR_OF_TEXTURES = DIR_OF_IMAGES + "textures/";
    public static final String DIR_OF_ICONS = DIR_OF_TEXTURES + "icons/";


    public static final String DIR_OF_DEBUG_TEXTURES = DIR_OF_IMAGES + "debug/";

    // Backgrounds
    public static final String DIR_OF_BACKGROUNDS = DIR_OF_IMAGES + "backgrounds/";


  // Audio
  public static final String DIR_OF_SOUNDS = "Assets/sounds/";

  // Maps
  public static final String DIR_OF_MAPS = "Assets/maps/";



  //!===================================================================
  //!========================  Asset Files  ============================
  //!===================================================================

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
      public static final String GIZMOS = DIR_OF_SPRITESHEETS + "gizmos.png";

    // Enemies
      public static final String ENEMY_1 = DIR_OF_SPRITESHEETS + DIR_OF_ENEMIES + "enemy_1.png";

    // UI
      public static final String BUTTONS = DIR_OF_SPRITESHEETS + "buttons.png";

    // Level Blocks
      public static final String BLOCKS = DIR_OF_SPRITESHEETS + "blocks.png";


    //!========================================================================
    //!========================  SpriteSheet Configs  =========================
    //!========================================================================
    public static final List<SpriteSheetConfig> SPRITESHEETS_CONFIG = List.of(
            new SpriteSheetConfig(GIZMOS, SpriteSheetUsage.UI, 24, 48, 0),
            new SpriteSheetConfig(BUTTONS, SpriteSheetUsage.UI, 16, 16, 0),
            new SpriteSheetConfig(BLOCKS, SpriteSheetUsage.BLOCK, 16, 16, 0, new int[]{16, 16, 4, 13, 10, 9, 7, 9, 14, 17, 18, 5, 21, 8, 3, 0, 7, 20, 15, 29, 32, 30, 32, 16, 16, 8, 5, 0, 0, 15, 15, 15})
    );
}
