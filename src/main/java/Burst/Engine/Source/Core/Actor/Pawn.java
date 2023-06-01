package Burst.Engine.Source.Core.Actor;

import Burst.Engine.Source.Core.Assets.AssetManager;
import Burst.Engine.Source.Core.Assets.Graphics.Sprite;
import Burst.Engine.Source.Core.Assets.Graphics.Texture;
import Orion.res.AssetHolder;

public abstract class Pawn extends Actor {
    public static Texture icon = AssetManager.getAssetFromType(AssetHolder.ICON_PAWN,Texture.class);
    public Pawn()
    {
        super();
    }

}
