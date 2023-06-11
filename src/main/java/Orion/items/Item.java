package Orion.items;

import Burst.Engine.Source.Core.Actor.Actor;
import Burst.Engine.Source.Core.Assets.AssetManager;
import Burst.Engine.Source.Core.Assets.Graphics.Texture;
import Orion.res.AssetConfig;

public class Item extends Actor{
    public Item() {
        super();

        this.name = "new Item";
        this.icon = AssetManager.getAssetFromType(AssetConfig.Files.Images.Icons.ITEM,Texture.class);

    }
    @Override
    public void imgui()
    {
        super.imgui(this);
    }
}
