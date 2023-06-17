package Orion.playercharacters;

import Burst.Engine.Source.Core.Assets.AssetManager;
import Burst.Engine.Source.Core.Assets.Graphics.Sprite;
import Burst.Engine.Source.Core.Assets.Graphics.SpriteSheet;
import Burst.Engine.Source.Core.Render.SpriteRenderer;
import Burst.Engine.Source.Game.Animation.AnimationState;
import Burst.Engine.Source.Game.Animation.StateMachine;
import Orion.res.AssetConfig;

import java.util.Timer;
/**
 * @author Oliver Schuetz
 */
public class Aura extends PlayerCharacter {

  public Aura() {
    super();
    this.icon = AssetManager.getAssetFromType(AssetConfig.Files.Images.SpriteSheets.AURA, SpriteSheet.class).getSprite(2,1);
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


    this.sprites = AssetManager.getAssetFromType(AssetConfig.Files.Images.SpriteSheets.AURA, SpriteSheet.class);


    StateMachine stateMachine = getComponent(StateMachine.class);
    stateMachine.addState(
            new AnimationState("idle", sprites)
                    .setRow(1)
                    .setFrameCount(4)
                    .setLooping()
    );
    stateMachine.addState(
            new AnimationState("run", sprites)
                    .setRow(1)
                    .setFrameCount(4)
                    .setLooping()
    );
    stateMachine.addState(
            new AnimationState("jump", sprites)
                    .setRow(1)
                    .setFrameCount(4)
                    .setLooping()
    );
    stateMachine.addState(
            new AnimationState("attack", sprites)
                    .setRow(3)
                    .setFrameCount(9)
                    .setFrameDuration(0.1f)

    );
    stateMachine.addState(
            new AnimationState("damage", sprites)
                    .setRow(7)
                    .setFrameCount(10)
    );
    stateMachine.addState(
            new AnimationState("death", sprites)
                    .setRow(8)
                    .setFrameCount(10)
    );
    stateMachine.addState(
            new AnimationState("dead", sprites)
                    .setRow(9)
                    .setFrameCount(4)
    );

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
