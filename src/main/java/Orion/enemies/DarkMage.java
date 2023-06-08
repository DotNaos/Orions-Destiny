package Orion.enemies;

import Burst.Engine.Source.Core.Assets.AssetManager;
import Burst.Engine.Source.Core.Assets.Graphics.Sprite;
import Burst.Engine.Source.Core.Render.SpriteRenderer;

public class DarkMage extends  EnemyCharacter{

    public DarkMage(){
        super("Evil Wizard");
        this.HP = 200;
        this.ATK = 10;
        this.DEF = 10;
        this.SPD = 10;
        this.LVL = 1;

        SpriteRenderer spr = this.getComponent(SpriteRenderer.class);
        spr.setSprite(AssetManager.getAssetFromType("Assets/images/spritesheets/Enemy/Evil Wizard/Idle.png", Sprite.class));


    }

}
