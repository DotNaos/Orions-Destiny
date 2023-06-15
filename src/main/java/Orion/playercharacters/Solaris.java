package Orion.playercharacters;

import Burst.Engine.Source.Core.Assets.AssetManager;
import Burst.Engine.Source.Core.Assets.Graphics.SpriteSheet;
import Orion.res.AssetConfig;

/**
 * @author Oliver Schuetz
 */
public class Solaris extends PlayerCharacter {
  public Solaris() {
    super();
    this.icon = AssetManager.getAssetFromType(AssetConfig.Files.Images.SpriteSheets.SOLARIS_IDLE, SpriteSheet.class).getSprite(0);
  }
  @Override
  public void init() {
    this.name = "Solaris";
    // Hellbeast
    this.description = """
            A beast from the depths of hell, Solaris is a powerful and fearsome creature.\s
            He can shoot fireballs from his mouth, and expel flames from his body.\s
            """;
    this.name = "Solaris";
    this.HP = 0;
    this.DEF = 0;
    this.ATK = 0;
    this.SPD = 0;
    this.STAMINA = 0;
    this.LVL = 0;
    this.EXP = 0;

    this.idleSprites = AssetManager.getAssetFromType(AssetConfig.Files.Images.SpriteSheets.SOLARIS_IDLE, SpriteSheet.class);
    this.walkSprites = AssetManager.getAssetFromType(AssetConfig.Files.Images.SpriteSheets.SOLARIS_WALK, SpriteSheet.class);
    this.runSprites = AssetManager.getAssetFromType(AssetConfig.Files.Images.SpriteSheets.SOLARIS_RUN, SpriteSheet.class);
    this.jumpSprites = AssetManager.getAssetFromType(AssetConfig.Files.Images.SpriteSheets.SOLARIS_JUMP, SpriteSheet.class);
    this.attackSprites = AssetManager.getAssetFromType(AssetConfig.Files.Images.SpriteSheets.SOLARIS_ATTACK, SpriteSheet.class);
  }

  @Override
  public void imgui() {
    super.imgui(this);
  }
}
