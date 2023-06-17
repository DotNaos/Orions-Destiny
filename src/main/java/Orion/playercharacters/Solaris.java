package Orion.playercharacters;

import Burst.Engine.Source.Core.Assets.AssetManager;
import Burst.Engine.Source.Core.Assets.Graphics.SpriteSheet;
import Burst.Engine.Source.Core.Render.SpriteRenderer;
import Orion.res.AssetConfig;

import java.util.Timer;

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
    super.init();
    this.name = "Solaris";
    // Hellbeast
    this.description = """
            A beast from the depths of hell, Solaris is a powerful and fearsome creature.\s
            He can shoot fireballs from his mouth, and expel flames from his body.\s
            """;
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

    getComponent(SpriteRenderer.class).setSprite(this.idleSprites.getSprite(spriteIndex));

    Timer timer = new Timer();
    timer.scheduleAtFixedRate(new java.util.TimerTask() {
      @Override
      public void run() {
        if (spriteIndex < 6) {
          spriteIndex++;
        } else {
          spriteIndex = 0;
        }

        getComponent(SpriteRenderer.class).setSprite(idleSprites.getSprite(1, spriteIndex));
      }
    }, 0, 100);
  }

  @Override
  public void imgui() {
    super.imgui(this);
  }
}
