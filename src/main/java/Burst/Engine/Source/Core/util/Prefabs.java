package Burst.Engine.Source.Core.util;

import Burst.Engine.Source.Core.Actor.Actor;
import Burst.Engine.Source.Core.Assets.Graphics.Sprite;
import Burst.Engine.Source.Core.Graphics.Render.SpriteRenderer;
import Burst.Engine.Source.Core.UI.Window;

public class Prefabs {

    public static Actor generateSpriteObject(Sprite sprite, float sizeX, float sizeY) {
        
        Actor block = Window.getScene().getGame().spawnActor("Generated_Num: " + Window.getScene().getGame().getActors().size());
        block.transform.scale.x = sizeX;
        block.transform.scale.y = sizeY;
        SpriteRenderer renderer = new SpriteRenderer();
        renderer.setSprite(sprite);
        block.addComponent(renderer);

        return block;
    }

    public static long generateUniqueID() {
        return System.nanoTime();
    }
}
