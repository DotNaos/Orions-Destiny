package Orion.playercharacters;

import Burst.Engine.Source.Core.Assets.AssetManager;
import Burst.Engine.Source.Core.Assets.Graphics.SpriteSheet;
import Burst.Engine.Source.Game.Animation.AnimationState;
import Burst.Engine.Source.Game.Animation.StateMachine;
import Orion.res.AssetConfig;

/**
 * @author Oliver Schuetz
 */
public class Solaris extends PlayerCharacter {
  public Solaris() {
    super();
    this.icon = AssetManager.getAssetFromType(AssetConfig.Files.Images.SpriteSheets.SOLARIS, SpriteSheet.class).getSprite(0);
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

    this.idleSprites = AssetManager.getAssetFromType(AssetConfig.Files.Images.SpriteSheets.SOLARIS, SpriteSheet.class);
    this.attackSprites = AssetManager.getAssetFromType(AssetConfig.Files.Images.SpriteSheets.SOLARIS_ATTACK, SpriteSheet.class);

    StateMachine stateMachine = getComponent(StateMachine.class);
    stateMachine.addState(
            new AnimationState("idle", idleSprites)
                    .setRow(1)
                    .setFrameCount(6)
                    .setLooping()
    );
    stateMachine.addState(
            new AnimationState("run", idleSprites)
                    .setRow(1)
                    .setFrameCount(6)
                    .setLooping()
    );
    stateMachine.addState(
            new AnimationState("jump", idleSprites)
                    .setRow(1)
                    .setFrameCount(6)
                    .setLooping()
    );
    stateMachine.addState(
            new AnimationState("attack", attackSprites)
                    .setRow(1)
                    .setFrameCount(4)
    );
  }

  @Override
  public void imgui() {
    super.imgui(this);
  }
}
