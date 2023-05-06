package Burst.Engine.Source.Core.Graphics.Render.Components;

import Burst.Engine.Source.Core.Component;
import Burst.Engine.Source.Core.Graphics.Render.SpriteRenderer;

public class FontRenderer extends Component {

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
