package Orion.playercharacters;

import Burst.Engine.Source.Core.Assets.AssetManager;
import Burst.Engine.Source.Core.Assets.Graphics.SpriteSheet;
import Burst.Engine.Source.Core.Render.SpriteRenderer;
import Orion.res.AssetConfig;

import java.util.Timer;

public class Apex extends PlayerCharacter {
  @Override
    public void init() {
        super.init();

    // Stealth shadow ninja
    this.description = """ 
                A stealthy shadow ninja that can control the shadows. \s
                Slashing his enemies empowered with dark magic. \s
                Sometimes casting a shadow clone to confuse his enemies. \s
            """;
    this.name = "Apex";
    this.HP = 0;
    this.DEF = 0;
    this.ATK = 0;
    this.SPD = 0;
    this.STAMINA = 0;
    this.LVL = 0;
    this.EXP = 0;

    // Spritesheets
    this.idleSprites = AssetManager.getAssetFromType(AssetConfig.Files.Images.SpriteSheets.APEX_IDLE, SpriteSheet.class);
    this.walkSprites = AssetManager.getAssetFromType(AssetConfig.Files.Images.SpriteSheets.APEX_WALK, SpriteSheet.class);
    this.runSprites = AssetManager.getAssetFromType(AssetConfig.Files.Images.SpriteSheets.APEX_RUN, SpriteSheet.class);
    this.jumpSprites = AssetManager.getAssetFromType(AssetConfig.Files.Images.SpriteSheets.APEX_JUMP, SpriteSheet.class);
    this.attackSprites =  AssetManager.getAssetFromType(AssetConfig.Files.Images.SpriteSheets.APEX_ATTACK, SpriteSheet.class);
    getComponent(SpriteRenderer.class).setTexture(this.idleSprites.getTexture());
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
    super.imgui();
  }
}
