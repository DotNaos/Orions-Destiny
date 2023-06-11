package Burst.Engine.Source.Core.Actor;

import Burst.Engine.Source.Core.Assets.AssetManager;
import Burst.Engine.Source.Core.Assets.Graphics.Texture;
import Orion.res.AssetConfig;

/**
 * @author Oliver Schütz
 * <p>
 * An Actor that can be controlled.
 */
public abstract class Pawn extends Actor {
  public Pawn() {
    super();
    this.icon = AssetManager.getAssetFromType(AssetConfig.Files.Images.Icons.PAWN, Texture.class);
  }
}
