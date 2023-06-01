package Orion.items;

import Burst.Engine.Source.Core.Actor.Actor;
import Burst.Engine.Source.Core.Assets.AssetManager;
import Burst.Engine.Source.Core.Assets.Graphics.Texture;
import Orion.res.AssetConfig;

public class Item extends Actor{
    public static final transient Texture icon = AssetManager.getAssetFromType(AssetConfig.ICON_ITEM,Texture.class).flippedTexture();
    public Item() {
        super();
    }
}
