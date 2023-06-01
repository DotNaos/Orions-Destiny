package Orion.res;

import Burst.Engine.Source.Core.Assets.AssetManager;
import Burst.Engine.Source.Core.Assets.Graphics.SpriteSheetUsage;
import Burst.Engine.Source.Core.Assets.Graphics.Spritesheet;
import Burst.Engine.Source.Core.Assets.Graphics.Texture;

import java.util.List;

public class AssetHolder {

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
    // public static final Spritesheet GIZMOS_SPRITESHEET = new Spritesheet(AssetManager.getAssetFromType(GIZMOS, Texture.class), SpriteSheetUsage.UI, 24, 48, 0);

  // Enemies
    public static final String ENEMY_1 = SPRITESHEETS + ENEMIES + "enemy_1.png";

  // UI
    public static final String BUTTONS = SPRITESHEETS + "buttons.png";
    // public static final Spritesheet BUTTONS_SPRITESHEET = new Spritesheet(AssetManager.getAssetFromType(BUTTONS, Texture.class), SpriteSheetUsage.UI,  16, 16, 0);


  // Level Blocks
    public static final String BLOCKS = SPRITESHEETS + "blocks.png";
    // public static final Spritesheet BLOCKS_SPRITESHEET = new Spritesheet(AssetManager.getAssetFromType(BLOCKS, Texture.class), SpriteSheetUsage.BLOCK, 16, 16, 0, new int[]{16, 16, 4, 13, 10, 9, 7, 9, 14, 17, 18, 5, 21, 8, 3, 0, 7, 20, 15, 29, 32, 30, 32, 16, 16, 8, 5, 0, 0, 15, 15, 15});




  //============================================================
  //                  List of all Spritesheets
  //============================================================
    // public static final List<Spritesheet> SPRITESHEETS_LIST = List.of(BUTTONS_SPRITESHEET, BLOCKS_SPRITESHEET, GIZMOS_SPRITESHEET);
}
