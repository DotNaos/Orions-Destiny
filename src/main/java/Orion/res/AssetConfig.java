package Orion.res;

import Burst.Engine.Source.Core.Assets.Graphics.SpriteSheetConfig;
import Burst.Engine.Source.Core.Assets.Graphics.SpriteSheetUsage;

import java.util.List;

public class AssetConfig {

  public static final String ASSETS = "Assets/";

  // Images
  public static final String IMAGES = "Assets/images/";

    public static final String SPRITESHEETS = IMAGES + "spritesheets/";
    public static final String ENEMIES = SPRITESHEETS + "Enemy/";

    // Textures
    public static final String TEXTURES = IMAGES + "textures/";
    public static final String ICONS = TEXTURES + "icons/";
    public static final String ICON_ACTOR = ICONS + "actor.png";
    public static final String ICON_PAWN = ICONS + "pawn.png";
    public static final String ICON_ENEMY = ICONS + "enemy.png";
    public static final String ICON_ITEM = ICONS + "item.png";
    public static final String ICON_BLOCK = ICONS + "block.png";
    public static final String ICON_PLAYER = ICONS + "player.png";
    


    public static final String DEBUG_TEXTURES = IMAGES + "debug/";

    // Backgrounds
    public static final String BACKGROUNDS = IMAGES + "backgrounds/";


  // Audio
  public static final String SOUNDS = "Assets/sounds/";

  // Maps
  public static final String MAPS = "Assets/maps/";


  // // ============================================================
  // // ========================  Asset Names  =====================
  // // ============================================================


  //===================================================================
  //========================  Class Icons  ============================
  //===================================================================


  //===================================================================
  //========================  Asset Spritesheets  =====================
  //===================================================================

  // Editor
    public static final String GIZMOS = SPRITESHEETS + "gizmos.png";

  // Enemies
    public static final String ENEMY_1 = SPRITESHEETS + ENEMIES + "enemy_1.png";

  // UI
    public static final String BUTTONS = SPRITESHEETS + "buttons.png";

  // Level Blocks
    public static final String BLOCKS = SPRITESHEETS + "blocks.png";


    //===================================================================
    //========================  Asset Configs  ==========================
    //===================================================================
    public static final List<SpriteSheetConfig> SPRITESHEETS_CONFIG = List.of(
            new SpriteSheetConfig(GIZMOS, SpriteSheetUsage.UI, 24, 48, 0),
            new SpriteSheetConfig(BUTTONS, SpriteSheetUsage.UI, 16, 16, 0),
            new SpriteSheetConfig(BLOCKS, SpriteSheetUsage.BLOCK, 16, 16, 0, new int[]{16, 16, 4, 13, 10, 9, 7, 9, 14, 17, 18, 5, 21, 8, 3, 0, 7, 20, 15, 29, 32, 30, 32, 16, 16, 8, 5, 0, 0, 15, 15, 15})
            );
}
