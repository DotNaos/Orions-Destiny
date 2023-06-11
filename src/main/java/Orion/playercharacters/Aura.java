package Orion.playercharacters;

import Burst.Engine.Source.Core.Assets.AssetManager;
import Burst.Engine.Source.Core.Assets.Graphics.Sprite;
import Burst.Engine.Source.Core.Assets.Graphics.SpriteSheet;
import Burst.Engine.Source.Core.Render.SpriteRenderer;
import Orion.res.AssetConfig;

public class Aura extends PlayerCharacter {
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

        if(getComponent(SpriteRenderer.class) == null) {
      addComponent(new SpriteRenderer(this));
    }
    getComponent(SpriteRenderer.class).setTexture(this.idleSprites.getTexture());

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
