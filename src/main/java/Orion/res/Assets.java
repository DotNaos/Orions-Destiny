package Orion.res;

<<<<<<< HEAD
import Burst.Engine.Source.Core.Assets.AssetManager;
import Burst.Engine.Source.Core.Assets.Graphics.SpriteSheetUsage;
import Burst.Engine.Source.Core.Assets.Graphics.Spritesheet;
import Burst.Engine.Source.Core.Assets.Graphics.Texture;
import Orion.abilities.UltimateAbility.LightAbilityOne;
=======
import java.util.HashMap;
import java.util.Map;
>>>>>>> main

import Burst.Engine.Source.Core.Assets.Asset;

public class Assets {
  private String dir = AssetConfig.DIR_OF_ASSETS;
  private String fileType;
  private Class<? extends Asset> assetType = Asset.class;

<<<<<<< HEAD
    // Images
    public static final String SPRITESHEETS = "Assets/images/spritesheets/";
    public static final String ABILITIES = "Abilities/";
    public static final String TEXTURES = "Assets/images/textures/";

    public static final String DEBUG_TEXTURES = "Assets/images/debug/";

    // Backgrounds
    public static final String BACKGROUNDS = "Assets/images/backgrounds/";

    // UI
    public static final String UI = "Assets/images/ui/";

    // Audio
    public static final String SOUNDS = "Assets/sounds/";

    // Maps
    public static final String MAPS = "Assets/maps/";


    //============================================================
    //========================  Asset Names  =====================
    //============================================================


    //===================================================================
    //========================  Asset Spritesheets  =====================
    //===================================================================

    // Editor
    public static final String GIZMOS = SPRITESHEETS + "gizmos.png";
    public static final Spritesheet GIZMOS_SPRITESHEET = new Spritesheet(AssetManager.getAssetFromType(GIZMOS, Texture.class), SpriteSheetUsage.UI, 24, 48, 0);

    // UI
    public static final String BUTTONS = SPRITESHEETS + "buttons.png";
    public static final Spritesheet BUTTONS_SPRITESHEET = new Spritesheet(AssetManager.getAssetFromType(BUTTONS, Texture.class), SpriteSheetUsage.UI, 16, 16, 0);


    // Level Blocks
    public static final String BLOCKS = SPRITESHEETS + "blocks.png";
    public static final Spritesheet BLOCKS_SPRITESHEET = new Spritesheet(AssetManager.getAssetFromType(BLOCKS, Texture.class), SpriteSheetUsage.BLOCK, 16, 16, 0, new int[]{16, 16, 4, 13, 10, 9, 7, 9, 14, 17, 18, 5, 21, 8, 3, 0, 7, 20, 15, 29, 32, 30, 32, 16, 16, 8, 5, 0, 0, 15, 15, 15});


    //============================================================
    //                  List of all Spritesheets
    //============================================================
    //Ability
    //Poison Ability
    public static final String poisonAbility = SPRITESHEETS + ABILITIES + "Poison_FX/PoisonClaw.png";
    public static final Spritesheet poisonAbilitySpritesheet = new Spritesheet(AssetManager.getAssetFromType(poisonAbility, Texture.class), SpriteSheetUsage.ANIMATION, 96, 96, 0);

    public static final List<Spritesheet> SPRITESHEETS_LIST = List.of(BUTTONS_SPRITESHEET, BLOCKS_SPRITESHEET, GIZMOS_SPRITESHEET, poisonAbilitySpritesheet);
    //LightAbility
    public static final String lightAbility = SPRITESHEETS + ABILITIES + "Light_FX/HolyExplosion";
    public static final Spritesheet lightAbilitySpritesheet = new Spritesheet(AssetManager.getAssetFromType(lightAbility, Texture.class), SpriteSheetUsage.ANIMATION, 96,96,0);
    //IceAbility1
    public static final String iceAbilityStart = SPRITESHEETS+ABILITIES+ "Ice_FX/IceFXStart1";
    public static final Spritesheet iceAbilityStartSpritesheet = new Spritesheet(AssetManager.getAssetFromType(iceAbilityStart, Texture.class), SpriteSheetUsage.ANIMATION, 144, 32, 0);
    public static final String iceAbilityHit = SPRITESHEETS+ABILITIES+"Ice_FX/IceFXHit1";
    public static final Spritesheet iceAbilityHitSpritesheet = new Spritesheet(AssetManager.getAssetFromType(iceAbilityHit, Texture.class), SpriteSheetUsage.ANIMATION, 384,32,0);
    //Ice Ability2
    public static final String iceAbilityStart2 = SPRITESHEETS+ABILITIES+"Ice_FX/Ice_FX2Start";
    public static final Spritesheet iceAbility2StartSpritesheet = new Spritesheet(AssetManager.getAssetFromType(iceAbilityStart2, Texture.class), SpriteSheetUsage.ANIMATION, 288, 32, 0);
    public static final String iceAbilityActive  = SPRITESHEETS+ABILITIES+"Ice_FX/Ice_FX2Active";
    public static final Spritesheet iceAbility2ActiveSpritesheet = new Spritesheet(AssetManager.getAssetFromType(iceAbilityActive, Texture.class), SpriteSheetUsage.ANIMATION,256,32,0);
    public static final String iceAbilityEnding2 = SPRITESHEETS+ABILITIES+"Ice_FX/Ice_FX2Ending";
    public static final Spritesheet iceAbility2EndingSpritesheet = new Spritesheet(AssetManager.getAssetFromType(iceAbilityEnding2, Texture.class), SpriteSheetUsage.ANIMATION,576,32,0);
    //Fire Ability
    public static final String fireAbility = SPRITESHEETS+ABILITIES+"Fire_FX/ActualFireball";
    public static final Spritesheet fireAbilitySpritesheet = new Spritesheet(AssetManager.getAssetFromType(fireAbility, Texture.class),SpriteSheetUsage.ANIMATION,64,64,0);
    //Water Ability
    public static final String waterAbility = SPRITESHEETS+ABILITIES+"Ice_FX/WaterAbility";
    public static final Spritesheet waterAbilitySpritesheet = new Spritesheet(AssetManager.getAssetFromType(waterAbility, Texture.class),SpriteSheetUsage.ANIMATION,96,96,0);
    //Plant Ability
    public static final String plantAbility = SPRITESHEETS+ABILITIES+"Poison_FX/PlantAbility";
    public static final Spritesheet plantAbolitySpritesheet = new Spritesheet(AssetManager.getAssetFromType(plantAbility,Texture.class), SpriteSheetUsage.ANIMATION,96,96,0);
=======
  private Map<String, Asset> assets = new HashMap<>();

    public String getDir() {
    return dir;
  }
  public String getFileType() {
    return fileType;
  }

  public Map<String, Asset> getAssets() {
    return assets;
  }

  public String getName() {
    // The name of the ClassType T
    return assetType.getSimpleName();
  }

  public Class<? extends Asset> getAssetType() {
    return assetType;
  }
>>>>>>> main

  public Assets(Class<? extends Asset> assetType, String dir, String fileType)
  {
    this.assetType = assetType;
    this.dir = dir;
    this.fileType = fileType;
  }
}
