package Burst.Engine.Source.Core.Graphics.Render.Components;

import Burst.Engine.Source.Core.Component;
import Burst.Engine.Source.Core.Graphics.Sprite.SpriteRenderer;

public class FontRenderer extends Component {

    @Override
    public void start() {
        if (gameObject.getComponent(SpriteRenderer.class) != null) {
            System.out.println("Found Font Renderer!");
        }
    }

    @Override
    public void update(float dt) {

    }
}
