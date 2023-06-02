package Burst.Engine.Source.Core.Actor;

import Burst.Engine.Source.Core.Assets.AssetManager;
import Burst.Engine.Source.Core.Assets.Graphics.Texture;
import Orion.res.AssetConfig;

public abstract class Pawn extends Actor {
    public static Texture icon = AssetManager.getAssetFromType(AssetConfig.ICON_PAWN,Texture.class);
    public Pawn()
    {
        super();
    }

}
