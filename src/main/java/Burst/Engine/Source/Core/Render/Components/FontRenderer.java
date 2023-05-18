package Burst.Engine.Source.Core.Render.Components;

import Burst.Engine.Source.Core.Actor.Actor;
import Burst.Engine.Source.Core.Actor.ActorComponent;
import Burst.Engine.Source.Core.Component;
import Burst.Engine.Source.Core.Render.SpriteRenderer;

public class FontRenderer extends ActorComponent {

    public FontRenderer(Actor actor) {
        super(actor);
    }

    @Override
    public void start() {
        if (actor.getComponent(SpriteRenderer.class) != null) {
            System.out.println("Found Font Renderer!");
        }
    }

    @Override
    public void update(float dt) {

    }
}
