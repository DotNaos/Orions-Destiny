package Orion.res;

import Burst.Engine.Source.Core.Assets.AssetManager;
import Burst.Engine.Source.Core.Assets.Graphics.SpriteSheetUsage;
import Burst.Engine.Source.Core.Assets.Graphics.Spritesheet;
import Burst.Engine.Source.Core.Assets.Graphics.Texture;
import Orion.abilities.UltimateAbility.LightAbilityOne;

import java.util.List;

public class Assets {

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
    public static final String IceAbilityHit = SPRITESHEETS+ABILITIES+"Ice_FX/IceFXHit1";
    public static final Spritesheet IceAbilityHitSpritesheet = new Spritesheet(AssetManager.getAssetFromType(IceAbilityHit, Texture.class), SpriteSheetUsage.ANIMATION, 384,32,0);
}
