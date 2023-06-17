package Orion.playercharacters;

import Burst.Engine.Source.Core.Assets.AssetManager;
import Burst.Engine.Source.Core.Assets.Graphics.Sprite;
import Burst.Engine.Source.Core.Assets.Graphics.SpriteSheet;
import Burst.Engine.Source.Core.Render.SpriteRenderer;
import Orion.res.AssetConfig;

import java.util.Timer;
/**
 * @author Oliver Schuetz
 */
public class Aura extends PlayerCharacter {

  public Aura() {
    super();
    this.icon = AssetManager.getAssetFromType(AssetConfig.Files.Images.SpriteSheets.AURA_IDLE, SpriteSheet.class).getSprite(2,1);
  }

  @Override
  public void init() {
    super.init();
    this.name = "Aura";
    // Energie und Gravitation
    this.description = """
            An ancient golem older than the world itself. \s
            He is made of pure energy and can control gravity. \s
            Throwing shards at his enemies empowered with all sorts of energies. \s
            """;

    this.HP = 0;
    this.DEF = 0;
    this.ATK = 0;
    this.SPD = 0;
    this.STAMINA = 0;
    this.LVL = 0;
    this.EXP = 0;


    this.idleSprites = AssetManager.getAssetFromType(AssetConfig.Files.Images.SpriteSheets.AURA_IDLE, SpriteSheet.class);
    this.walkSprites = AssetManager.getAssetFromType(AssetConfig.Files.Images.SpriteSheets.AURA_WALK, SpriteSheet.class);
    this.runSprites = AssetManager.getAssetFromType(AssetConfig.Files.Images.SpriteSheets.AURA_RUN, SpriteSheet.class);
    this.jumpSprites = AssetManager.getAssetFromType(AssetConfig.Files.Images.SpriteSheets.AURA_JUMP, SpriteSheet.class);
    this.attackSprites =  AssetManager.getAssetFromType(AssetConfig.Files.Images.SpriteSheets.AURA_ATTACK, SpriteSheet.class);

    getComponent(SpriteRenderer.class).setSprite(this.idleSprites.getSprite(spriteIndex));

    Timer timer = new Timer();
    timer.scheduleAtFixedRate(new java.util.TimerTask() {
      @Override
      public void run() {
        if (spriteIndex < 4) {
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

    @Override
    public void updateEditor(float dt) {
        super.updateEditor(dt);
    }
}
