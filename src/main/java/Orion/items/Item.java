package Orion.items;

import Burst.Engine.Source.Core.Actor.Actor;
import Burst.Engine.Source.Core.Assets.AssetManager;
import Burst.Engine.Source.Core.Assets.Graphics.Sprite;
import Burst.Engine.Source.Core.Assets.Graphics.Texture;
import Orion.res.AssetConfig;
/**
 * @author Oliver Schuetz
 */
public class Item extends Actor{
    public Item() {
        super();
        this.icon = AssetManager.getAssetFromType(AssetConfig.Files.Images.Icons.ITEM, Sprite.class);

    }

    public void init()
    {
        super.init();
        this.name = "new Item";
    }

    @Override
    public void imgui()
    {
        super.imgui(this);
    }
}
