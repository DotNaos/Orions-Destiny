package Orion.playercharacters;

import Burst.Engine.Source.Core.Assets.AssetManager;
import Burst.Engine.Source.Core.Assets.Graphics.SpriteSheet;
import Orion.res.AssetConfig;

public class Genesis extends PlayerCharacter {

  @Override
  public void init()
  {
    this.name = "Genesis";
    // this.sprite = new Sprite("assets/textures/characters/genesis.png");
    this.description = """
            An old whise man that lived for centuries. He controls dark magic. \s
            But don't be fooled by his age, he is still a strong fighter. \s
            """;
    this.HP = 0;
    this.DEF = 0;
    this.ATK = 0;
    this.SPD = 0;
    this.STAMINA = 0;
    this.LVL = 0;
    this.EXP = 0;

    this.idleSprites = AssetManager.getAssetFromType(AssetConfig.Files.Images.SpriteSheets.GENESIS_IDLE, SpriteSheet.class);
    this.walkSprites = AssetManager.getAssetFromType(AssetConfig.Files.Images.SpriteSheets.GENESIS_WALK, SpriteSheet.class);
    this.runSprites = AssetManager.getAssetFromType(AssetConfig.Files.Images.SpriteSheets.GENESIS_RUN, SpriteSheet.class);
    this.jumpSprites = AssetManager.getAssetFromType(AssetConfig.Files.Images.SpriteSheets.GENESIS_JUMP, SpriteSheet.class);
    this.attackSprites =  AssetManager.getAssetFromType(AssetConfig.Files.Images.SpriteSheets.GENESIS_ATTACK, SpriteSheet.class);
  }
  @Override
  public void imgui() {
    super.imgui();
  }
}
