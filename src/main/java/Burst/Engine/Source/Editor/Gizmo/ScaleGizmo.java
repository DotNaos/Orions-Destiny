package Burst.Engine.Source.Editor.Gizmo;

import Burst.Engine.Source.Core.Assets.Graphics.Sprite;
import Burst.Engine.Source.Core.Input.MouseListener;


public class ScaleGizmo extends Gizmo {
    public ScaleGizmo(Sprite scaleSprite) {
        super(scaleSprite);
    }

    @Override
    public void updateEditor(float dt) {
        if (activeActor != null) {
            if (xAxisActive && !yAxisActive) {
                activeActor.transform.size.x -= MouseListener.getWorldDx();
            } else if (yAxisActive) {
                activeActor.transform.size.y -= MouseListener.getWorldDy();
            }
        }

        super.updateEditor(dt);
    }
}
