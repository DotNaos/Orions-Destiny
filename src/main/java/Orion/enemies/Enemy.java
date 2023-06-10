package Orion.enemies;

import Burst.Engine.Source.Core.Actor.Pawn;
import Burst.Engine.Source.Core.Assets.Graphics.SpriteSheet;

public class Enemy extends Pawn {
  protected SpriteSheet idleSprites;
  protected SpriteSheet walkSprites;
  protected SpriteSheet runSprites;
  protected SpriteSheet jumpSprites;
  public Enemy() {
      super();
      this.name = "new Enemy";

  }

  @Override
  public void imgui()
  {
    super.imgui(this);
  }
}
