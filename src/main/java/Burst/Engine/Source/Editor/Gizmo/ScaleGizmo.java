package Burst.Engine.Source.Editor.Gizmo;

import Burst.Engine.Source.Core.Assets.Graphics.Sprite;
import Burst.Engine.Source.Core.Input.MouseListener;


public class ScaleGizmo extends Gizmo {
    public ScaleGizmo(Sprite scaleSprite) {
        super(scaleSprite);
    }

    @Override
    public void update(float dt) {
        super.update(dt);
        if (activeActor != null) {
            if (xAxisActive && !yAxisActive) {
                activeActor.getTransform().size.x -= MouseListener.getWorldDx();
            } else if (yAxisActive) {
                activeActor.getTransform().size.y -= MouseListener.getWorldDy();
            }
        }
    }
}
