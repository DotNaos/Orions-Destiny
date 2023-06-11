package Orion.blocks;

import Burst.Engine.Source.Core.Actor.Actor;
import Burst.Engine.Source.Core.Actor.ActorComponent;
import Burst.Engine.Source.Core.Actor.PlayerController;
import Burst.Engine.Source.Core.Assets.AssetManager;
import Burst.Engine.Source.Core.Assets.Graphics.Sprite;
import Burst.Engine.Source.Core.Assets.Graphics.SpriteSheet;
import Burst.Engine.Source.Core.Assets.Graphics.Texture;
import Burst.Engine.Source.Core.Component;
import Burst.Engine.Source.Core.Render.SpriteRenderer;
import Orion.res.AssetConfig;
import org.jbox2d.dynamics.contacts.Contact;
import org.joml.Vector3f;

public class Block extends Actor {
    private int row = 0;
    private transient int prevRow = 0;
    private int col = 0;
    private transient int prevCol = 0;
    public Block() {
        super();
        this.name = "new Block";
        this.icon = AssetManager.getAssetFromType(AssetConfig.Files.Images.Icons.BLOCK,Texture.class);
    }

    @Override
    public void init()
    {
        super.init();

        getComponent(SpriteRenderer.class).setSprite(AssetManager.getAssetFromType(AssetConfig.Files.Images.SpriteSheets.BLOCKS, SpriteSheet.class).getSprite(row,col));
    }

    @Override
    public void updateEditor(float dt)
    {
        super.updateEditor(dt);
        if(row != prevRow || col != prevCol)
        {
            getComponent(SpriteRenderer.class).setSprite(AssetManager.getAssetFromType(AssetConfig.Files.Images.SpriteSheets.BLOCKS, SpriteSheet.class).getSprite(row,col));
            prevRow = row;
            prevCol = col;
        }
    }

    @Override
    public void imgui()
    {
        super.imgui(this);
    }

}
