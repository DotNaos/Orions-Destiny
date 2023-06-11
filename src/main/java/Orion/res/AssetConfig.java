package Orion.res;

import Burst.Engine.Source.Core.Assets.Graphics.SpriteSheetConfig;
import Burst.Engine.Source.Core.Assets.Graphics.SpriteSheetUsage;

import java.util.List;

public class AssetConfig {

  //!=========================================================================
  //!========================  Asset Directories  ============================
  //!=========================================================================
  public static class Directories {
    public static final String ASSETS = "Assets/";
    public static final String ROOT = "Assets/";
    public static final String FONTS = ASSETS + "fonts/";
    public static final String IMAGES = ASSETS + "images/";
    public static final String SPRITESHEETS = IMAGES + "spritesheets/";
    public static final String UI = SPRITESHEETS + "ui/";

    public static final String ENEMIES = SPRITESHEETS + "enemy/";
    public static final String ITEMS = SPRITESHEETS + "item/";
    public static final String ENVIRONMENT = SPRITESHEETS + "environment/";
    public static final String TILES = ENVIRONMENT + "tiles/";
    public static final String TEXTURES = IMAGES + "textures/";
    public static final String ICONS = TEXTURES + "icons/";
    public static final String DEBUG_TEXTURES = IMAGES + "debug/";

    public static final String ABILITIES = SPRITESHEETS + "ability/";
    public static final String PLAYERS = SPRITESHEETS + "player/";


    // Backgrounds
    public static final String BACKGROUNDS = IMAGES + "background/";

    // Audio
    public static final String SOUNDS = "Assets/sounds/";
  }

  //!===================================================================
  //!========================  Asset Files  ============================
  //!===================================================================
  public static class Files {
    public static class Fonts {
      public static final String FONT_INTER = Directories.FONTS + "Inter.ttf";
      public static final String FONT_PEABERRY = Directories.FONTS + "Peaberry.ttf";
    }

    public static class Images {
      public static class Icons {
        public static final String ACTOR = Directories.ICONS + "actor.png";
        public static final String PAWN = Directories.ICONS + "pawn.png";
        public static final String ENEMY = Directories.ICONS + "enemy.png";

        public static final String ITEM = Directories.ICONS + "item.png";
        public static final String BLOCK = Directories.ICONS + "block.png";
        public static final String PLAYER = Directories.ICONS + "player.png";
        public static final String CAMERA = Directories.ICONS + "camera.png";
      }

      public static class Debug {
        public static final String TEXTURE_DEBUG = Directories.DEBUG_TEXTURES + "debug1.png";
        public static final String TEXTURE_DEBUG2 = Directories.DEBUG_TEXTURES + "debug2.png";
      }

      public static class SpriteSheets{
        //* ========================= UI ==================================
        public static final String GIZMOS = Directories.UI + "gizmos.png";
        public static final String BUTTONS = Directories.UI + "buttons.png";

        //* ======================= Blocks ================================
        public static final String BLOCKS = Directories.TILES + "blocks.png";


        //* ======================= Player ================================


        //* ======================= Enemies ===============================

        //* ======================= Items =================================
        // Maps
        public static final String MAPS = "Assets/maps/";


        public static final String AURA = Directories.PLAYERS + "Aura/" + "aura.png";
        //*----------------------------------------------------------------------------------------\\
        //*________________________________________________________________________________________\\


        public static final String APEX = Directories.PLAYERS + "apex.png";
        //*----------------------------------------------------------------------------------------\\
        //! ONLY ONE SPRITESHEET FOR APEX
        //*________________________________________________________________________________________\\


        public static final String GENESIS = Directories.PLAYERS + "idle.png";
        //*----------------------------------------------------------------------------------------\\
        public static final String GENESIS_RUN = Directories.PLAYERS + "run.png";
        public static final String GENESIS_JUMP = Directories.PLAYERS + "jump.png";
        public static final String GENESIS_FALL = Directories.PLAYERS + "fall.png";
        public static final String GENESIS_ATTACK = Directories.PLAYERS + "attack_1.png";

        //*________________________________________________________________________________________\\


        public static final String HELIX = Directories.PLAYERS + "helix.png";
        //*----------------------------------------------------------------------------------------\\
        //*________________________________________________________________________________________\\

        public static final String SOLARIS = Directories.PLAYERS + "solaris.png";
        //*----------------------------------------------------------------------------------------\\
        //*________________________________________________________________________________________\\
      }

    }


  }

  //!========================================================================
  //!========================  SpriteSheet Configs  =========================
  //!========================================================================
  public static final List<SpriteSheetConfig> SPRITESHEETS_CONFIG = List.of(
          new SpriteSheetConfig(Files.Images.SpriteSheets.GIZMOS, SpriteSheetUsage.UI, 24, 48, 0),
          new SpriteSheetConfig(Files.Images.SpriteSheets.BUTTONS, SpriteSheetUsage.UI, 16, 16, 0),
          new SpriteSheetConfig(Files.Images.SpriteSheets.BLOCKS, SpriteSheetUsage.BLOCK, 16, 16, 0,
                  new int[]{16, 16, 4, 13, 10, 9, 7, 9, 14, 17, 18, 5, 21, 8, 3, 0, 7, 20, 15, 29, 32, 30, 32, 16, 16, 8, 5, 0, 0, 15, 15, 15}));


}
