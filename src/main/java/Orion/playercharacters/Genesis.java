package Orion.playercharacters;

import Burst.Engine.Source.Core.Assets.AssetManager;
import Burst.Engine.Source.Core.Assets.Graphics.SpriteSheet;
import Burst.Engine.Source.Core.Render.SpriteRenderer;
import Burst.Engine.Source.Game.Animation.AnimationState;
import Burst.Engine.Source.Game.Animation.StateMachine;
import Orion.res.AssetConfig;

import java.util.Timer;
/**
 * @author Oliver Schuetz
 */
public class Genesis extends PlayerCharacter {
  public Genesis() {
    super();
    this.icon = AssetManager.getAssetFromType(AssetConfig.Files.Images.SpriteSheets.GENESIS_IDLE, SpriteSheet.class).getSprite(0);
  }
  @Override
  public void init()
  {
    super.init();
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
    this.runSprites = AssetManager.getAssetFromType(AssetConfig.Files.Images.SpriteSheets.GENESIS_RUN, SpriteSheet.class);
    this.jumpSprites = AssetManager.getAssetFromType(AssetConfig.Files.Images.SpriteSheets.GENESIS_JUMP, SpriteSheet.class);
    this.attackSprites =  AssetManager.getAssetFromType(AssetConfig.Files.Images.SpriteSheets.GENESIS_ATTACK, SpriteSheet.class);

    StateMachine stateMachine = getComponent(StateMachine.class);
    stateMachine.addState(
            new AnimationState("idle", idleSprites)
                    .setRow(1)
                    .setFrameCount(6)
                    .setLooping()
    );
    stateMachine.addState(
            new AnimationState("run", runSprites)
                    .setRow(1)
                    .setFrameCount(8)
                    .setLooping()
    );
    stateMachine.addState(
            new AnimationState("jump", jumpSprites)
                    .setRow(1)
                    .setFrameCount(2)
                    .setLooping()
    );
    stateMachine.addState(
            new AnimationState("attack", attackSprites)
                    .setRow(1)
                    .setFrameCount(8)
    );
  }
  @Override
  public void imgui() {
    super.imgui();
  }
}
