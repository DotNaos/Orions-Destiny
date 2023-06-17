package Orion.playercharacters;

import Burst.Engine.Source.Core.Assets.AssetManager;
import Burst.Engine.Source.Core.Assets.Graphics.SpriteSheet;
import Burst.Engine.Source.Game.Animation.AnimationState;
import Burst.Engine.Source.Game.Animation.StateMachine;
import Orion.res.AssetConfig;

/**
 * @author Oliver Schuetz
 */
public class Apex extends PlayerCharacter {
  public Apex() {
    super();
    icon = AssetManager.getAssetFromType(AssetConfig.Files.Images.SpriteSheets.APEX, SpriteSheet.class).getSprite(0);
  }
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
      this.sprites = AssetManager.getAssetFromType(AssetConfig.Files.Images.SpriteSheets.APEX, SpriteSheet.class);

      StateMachine stateMachine = getComponent(StateMachine.class);
      stateMachine.addState(
              new AnimationState("idle", sprites)
                      .setRow(1)
                      .setFrameCount(9)
                      .setLooping()
      );
      stateMachine.addState(
              new AnimationState("run", sprites)
                      .setRow(2)
                      .setFrameCount(6)
                      .setLooping()
      );
      stateMachine.addState(
              new AnimationState("jump", sprites)
                      .setRow(1)
                      .setFrameCount(9)
                      .setLooping()
      );
      stateMachine.addState(
              new AnimationState("attack", sprites)
                      .setRow(3)
                      .setFrameCount(12)
      );
      stateMachine.addState(
              new AnimationState("damage", sprites)
                      .setRow(4)
                      .setFrameCount(5)
      );
      stateMachine.addState(
              new AnimationState("death", sprites)
                      .setRow(5)
                      .setFrameCount(23)
      );

    }

  @Override
  public void imgui() {
    super.imgui();
  }
}
