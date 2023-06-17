package Burst.Engine.Source.Game.Animation;

import Burst.Engine.Source.Core.Assets.Graphics.Sprite;
import Burst.Engine.Source.Core.Assets.Graphics.SpriteSheet;
import Burst.Engine.Source.Core.Util.DebugMessage;

public class AnimationState {
  private String name = "NEW ANIMATION STATE";
  private SpriteSheet sprites;
  private Sprite currentSprite;
  private int currentFrame = 1;
  private int row  = 1;
  private boolean isLooping = false;

  float time = 0;

  public StateMachine stateMachine;

  /**
   * Frames per second
   */
  private float frameDuration = 0.15f;
  private int frameCount = 1;

  public AnimationState(String name, SpriteSheet sprites) {
    this.name = name;
    this.sprites = sprites;
  }

  public AnimationState setFrameCount(int frameCount) {
    this.frameCount = frameCount;
    return this;
  }

  public AnimationState setRow(int row) {
      this.row = row;
      return this;
  }

  public AnimationState setLooping() {
      this.isLooping = true;
      return this;
  }

  public AnimationState setFrameDuration(float frameDuration) {
      this.frameDuration = frameDuration;
      return this;
  }

  public void update(float dt) {
    if (this.sprites == null) {
      return;
    }
    showFrame();
    nextFrame(dt);
  }

  private void showFrame()
  {
    this.currentSprite = this.sprites.getSprite(this.row, this.currentFrame);
    if (this.currentSprite == null) {
      this.currentSprite = this.sprites.getSprite(1, 1);
      DebugMessage.error("To many frames in animation state: " + this.name);
    }
  }

  private void nextFrame(float dt)
  {
    if (this.frameDuration == 0) {
      return;
    }
    if (this.currentFrame >= this.frameCount) {
      reset();
      if (!this.isLooping) {
        stateMachine.toEntry();
        return;
      }
    }

    time += dt;
//    System.out.println(dt);
    if (time < this.frameDuration) {
      return;
    }

    this.currentFrame++;
    time = 0;
  }

  public String getName() {
    return name;
  }

  public Sprite getFrame() {
    return this.currentSprite;
  }

  public Sprite getFrame(int frame) {
    return this.sprites.getSprite(this.row, frame);
  }

  public void reset() {
    this.time = 0;
    this.currentFrame = 1;
    this.currentSprite = this.sprites.getSprite(this.row, this.currentFrame);
  }



}
