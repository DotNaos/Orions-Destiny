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
    public static final String AURA = PLAYERS + "Aura/";
    public static final String APEX = PLAYERS + "Apex/";
    public static final String GENESIS = PLAYERS + "Genesis/";
    public static final String HELIX = PLAYERS + "Helix/";
    public static final String SOLARIS = PLAYERS + "Solaris/";


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

      public static class SpriteSheets {
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


        public static final String AURA_IDLE = Directories.AURA + "aura.png";
        //*----------------------------------------------------------------------------------------\\

        public static final String AURA_WALK = Directories.AURA + "aura.png";
        public static final String AURA_RUN = Directories.AURA + "aura.png";
        public static final String AURA_JUMP = Directories.AURA + "aura.png";
        public static final String AURA_FALL = Directories.AURA + "aura.png";
        public static final String AURA_ATTACK = Directories.AURA + "aura.png";
        //*________________________________________________________________________________________\\


        public static final String APEX_IDLE = Directories.APEX + "apex.png";
        //*----------------------------------------------------------------------------------------\\
        public static final String APEX_WALK = Directories.APEX + "apex.png";
        public static final String APEX_RUN = Directories.APEX + "apex.png";
        public static final String APEX_JUMP = Directories.APEX + "apex.png";
        public static final String APEX_FALL = Directories.APEX + "apex.png";
        public static final String APEX_ATTACK = Directories.APEX + "apex.png";

        //*________________________________________________________________________________________\\


        public static final String GENESIS_IDLE = Directories.GENESIS + "genesis.png";
        //*----------------------------------------------------------------------------------------\\
        public static final String GENESIS_WALK = Directories.GENESIS + "genesis.png";
        public static final String GENESIS_RUN = Directories.GENESIS + "genesis.png";
        public static final String GENESIS_JUMP = Directories.GENESIS + "genesis.png";
        public static final String GENESIS_FALL = Directories.GENESIS + "genesis.png";
        public static final String GENESIS_ATTACK = Directories.GENESIS + "genesis.png";
        public static final String GENESIS_ATTACK2 = Directories.GENESIS + "genesis.png";


        //*________________________________________________________________________________________\\


        public static final String HELIX_IDLE = Directories.HELIX + "helix.png";
        //*----------------------------------------------------------------------------------------\\
        public static final String HELIX_WALK = Directories.HELIX + "helix.png";
        public static final String HELIX_RUN = Directories.HELIX + "helix.png";
        public static final String HELIX_JUMP = Directories.HELIX + "helix.png";
        public static final String HELIX_FALL = Directories.HELIX + "helix.png";
        public static final String HELIX_ATTACK = Directories.HELIX + "helix.png";

        //*________________________________________________________________________________________\\

        public static final String SOLARIS_IDLE = Directories.SOLARIS + "solaris.png";
        //*----------------------------------------------------------------------------------------\\
        public static final String SOLARIS_WALK = Directories.SOLARIS + "solaris.png";
        public static final String SOLARIS_RUN = Directories.SOLARIS + "solaris.png";
        public static final String SOLARIS_JUMP = Directories.SOLARIS + "solaris.png";
        public static final String SOLARIS_FALL = Directories.SOLARIS + "solaris.png";
        public static final String SOLARIS_ATTACK = Directories.SOLARIS + "solaris.png";

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
                  new int[]{16, 16, 4, 13, 10, 9, 7, 9, 14, 17, 18, 5, 21, 8, 3, 0, 7, 20, 15, 29, 32, 30, 32, 16, 16, 8, 5, 0, 0, 15, 15, 15}),
          new SpriteSheetConfig(Files.Images.SpriteSheets.AURA_IDLE, SpriteSheetUsage.PLAYER, 100, 100, 0),
          new SpriteSheetConfig(Files.Images.SpriteSheets.APEX_IDLE, SpriteSheetUsage.PLAYER, 80, 80, 0),
          new SpriteSheetConfig(Files.Images.SpriteSheets.HELIX_IDLE, SpriteSheetUsage.PLAYER, 96, 96, 0),
          new SpriteSheetConfig(Files.Images.SpriteSheets.GENESIS_IDLE, SpriteSheetUsage.PLAYER, 231, 190, 0),
          new SpriteSheetConfig(Files.Images.SpriteSheets.SOLARIS_IDLE, SpriteSheetUsage.PLAYER, 55, 67, 0)
  );


}
