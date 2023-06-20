package Orion.res;

import Burst.Engine.Source.Core.Assets.Graphics.SpriteSheetConfig;
import Burst.Engine.Source.Core.Assets.Graphics.SpriteSheetUsage;

import java.util.List;

/**
 * @author Oliver Schuetz
 */
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
    public static final String BACKGROUNDS = TEXTURES + "background/";

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
        public static final String FOLDER = Directories.ICONS + "folder.png";
      }

      public static class Debug {
        public static final String TEXTURE_DEBUG = Directories.DEBUG_TEXTURES + "debug1.png";
        public static final String TEXTURE_DEBUG2 = Directories.DEBUG_TEXTURES + "debug2.png";
      }

      public static class SpriteSheets {
        //* ========================= UI ==================================
        public static final String GIZMOS = Directories.UI + "gizmos.png";
        public static final String BUTTONS = Directories.UI + "buttons.png";
        public static final String ICONS = Directories.UI + "icons.png";
        public static final String SELECT = Directories.UI + "select.png";
        public static final String SELECT_ROUND = Directories.UI + "select_round.png";
        public static final String FLAT_BUTTONS = Directories.UI + "flat_buttons.png";
        public static final String FLAT_BUTTONS_EDITOR = Directories.UI + "flat_buttons_editor.png";
        public static final String FLAT_BUTTONS_GAME = Directories.UI + "flat_buttons_game.png";
        public static final String FLAT_BUTTONS_SETTINGS = Directories.UI + "flat_buttons_settings.png";
        public static final String FLAT_BUTTONS_ROUND = Directories.UI + "flat_buttons_round.png";

        public static final String UI = Directories.UI + "ui.png";


        //* ======================= Blocks ================================
        public static final String BLOCKS = Directories.TILES + "blocks.png";


        //* ======================= Player ================================


        //* ======================= Enemies ===============================

        //* ======================= Items =================================
        // Maps
        public static final String MAPS = "Assets/maps/";


        public static final String AURA = Directories.AURA + "aura.png";
        //*----------------------------------------------------------------------------------------\\

        //*________________________________________________________________________________________\\


        public static final String APEX = Directories.APEX + "apex.png";
        //*----------------------------------------------------------------------------------------\\

        //*________________________________________________________________________________________\\


        public static final String GENESIS_IDLE = Directories.GENESIS + "genesis.png";
        //*----------------------------------------------------------------------------------------\\
        public static final String GENESIS_RUN = Directories.GENESIS + "run.png";
        public static final String GENESIS_JUMP = Directories.GENESIS + "jump.png";
        public static final String GENESIS_FALL = Directories.GENESIS + "genesis.png";
        public static final String GENESIS_ATTACK = Directories.GENESIS + "attack_1.png";
        public static final String GENESIS_ATTACK2 = Directories.GENESIS + "attack_2.png";


        //*________________________________________________________________________________________\\


        public static final String HELIX_IDLE = Directories.HELIX + "helix.png";
        //*----------------------------------------------------------------------------------------\\
        public static final String HELIX_RUN = Directories.HELIX + "run.png";
        public static final String HELIX_JUMP = Directories.HELIX + "jump.png";
        public static final String HELIX_FALL = Directories.HELIX + "helix.png";
        public static final String HELIX_ATTACK = Directories.HELIX + "attack.png";

        //*________________________________________________________________________________________\\

        public static final String SOLARIS = Directories.SOLARIS + "solaris.png";
        //*----------------------------------------------------------------------------------------\\
        public static final String SOLARIS_ATTACK = Directories.SOLARIS + "breath.png";
        public static final String SOLARIS_ATTACK2 = Directories.SOLARIS + "burn.png";

        //*________________________________________________________________________________________\\
      }

      public static class Backgrounds {
        public static final String MOUNTAINS = Directories.BACKGROUNDS + "background_1.png";
        public static final String DESERT = Directories.BACKGROUNDS + "background_2.png";
        public static final String FOREST = Directories.BACKGROUNDS + "background_3.png";
        public static final String SWAMP = Directories.BACKGROUNDS + "background_4.png";
      }
    }


  }

  //!========================================================================
  //!========================  SpriteSheet Configs  =========================
  //!========================================================================
  public static final List<SpriteSheetConfig> SPRITESHEETS_CONFIG = List.of(
          // Gizmos
          new SpriteSheetConfig(Files.Images.SpriteSheets.GIZMOS, SpriteSheetUsage.UI, 24, 48, 0),

          // UI
          // Reset Buttons ...
          new SpriteSheetConfig(Files.Images.SpriteSheets.BUTTONS, SpriteSheetUsage.UI, 16, 16, 0),
          // Flat Buttons ...
          new SpriteSheetConfig(Files.Images.SpriteSheets.FLAT_BUTTONS, SpriteSheetUsage.UI, 96, 32, 0),
          new SpriteSheetConfig(Files.Images.SpriteSheets.FLAT_BUTTONS_EDITOR, SpriteSheetUsage.UI, 96, 32, 0),
          new SpriteSheetConfig(Files.Images.SpriteSheets.FLAT_BUTTONS_GAME, SpriteSheetUsage.UI, 96, 32, 0),
            new SpriteSheetConfig(Files.Images.SpriteSheets.FLAT_BUTTONS_SETTINGS, SpriteSheetUsage.UI, 96, 32, 0),
          // Flat Buttons Round ...
          new SpriteSheetConfig(Files.Images.SpriteSheets.FLAT_BUTTONS_ROUND, SpriteSheetUsage.UI, 96, 32, 0),

          // Select ...
          new SpriteSheetConfig(Files.Images.SpriteSheets.SELECT, SpriteSheetUsage.UI, 32, 32, 0),
          // Select Round ...
          new SpriteSheetConfig(Files.Images.SpriteSheets.SELECT_ROUND, SpriteSheetUsage.UI, 32, 32, 0),

          // Icons
          new SpriteSheetConfig(Files.Images.SpriteSheets.ICONS, SpriteSheetUsage.UI, 16, 16, 16),



          // Blocks
          new SpriteSheetConfig(Files.Images.SpriteSheets.BLOCKS, SpriteSheetUsage.BLOCK, 16, 16, 0,
                  new int[]{16, 16, 4, 13, 10, 9, 7, 9, 14, 17, 18, 5, 21, 8, 3, 20, 7, 20, 14, 27, 30, 28, 30, 17, 17, 8, 5, 0, 0, 14, 14, 14}),



          // Aura
          new SpriteSheetConfig(Files.Images.SpriteSheets.AURA, SpriteSheetUsage.PLAYER, 100, 100, 0),
          // Apex
          new SpriteSheetConfig(Files.Images.SpriteSheets.APEX, SpriteSheetUsage.PLAYER, 80, 80, 0),
          // Helix
          new SpriteSheetConfig(Files.Images.SpriteSheets.HELIX_IDLE, SpriteSheetUsage.PLAYER, 96, 96, 0),
          new SpriteSheetConfig(Files.Images.SpriteSheets.HELIX_RUN, SpriteSheetUsage.PLAYER, 96, 96, 0),
          // Genesis
          new SpriteSheetConfig(Files.Images.SpriteSheets.GENESIS_IDLE, SpriteSheetUsage.PLAYER, 231, 190, 0),
          new SpriteSheetConfig(Files.Images.SpriteSheets.GENESIS_RUN, SpriteSheetUsage.PLAYER, 231, 190, 0),
          // Solaris
          new SpriteSheetConfig(Files.Images.SpriteSheets.SOLARIS, SpriteSheetUsage.PLAYER, 55, 67, 0));
}
