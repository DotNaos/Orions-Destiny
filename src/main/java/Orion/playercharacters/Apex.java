package Orion.playercharacters;

import Burst.Engine.Source.Core.Assets.AssetManager;
import Burst.Engine.Source.Core.Assets.Graphics.SpriteSheet;
import Orion.res.AssetConfig;

public class Apex extends PlayerCharacter {
  public Apex() {
    super();
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
  }

  @Override
  public void imgui() {
    super.imgui();
  }
}
