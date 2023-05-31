package Burst.Engine.Source.Core.Actor;

import Burst.Engine.Source.Core.Assets.AssetManager;
import Burst.Engine.Source.Core.Assets.Graphics.Sprite;

public abstract class Pawn extends Actor {
    public static Sprite icon = AssetManager.getAssetFromType("Icon_Pawn" ,Sprite.class);
    public Pawn()
    {
        super();
    }

}
