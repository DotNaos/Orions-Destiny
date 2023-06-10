package Burst.Engine.Source.Editor.Gizmo;

import Burst.Engine.Source.Core.Assets.Graphics.Sprite;
import Burst.Engine.Source.Core.Input.MouseListener;

public class TranslateGizmo extends Gizmo {

    public TranslateGizmo(Sprite arrowSprite) {
        super(arrowSprite);
    }

    @Override
    public void updateEditor(float dt) {
        super.updateEditor(dt);
        if (activeActor != null) {
            if (xAxisActive && !yAxisActive) {
                activeActor.getTransform().getPosition().x -= MouseListener.getWorldDx();
            } else if (yAxisActive) {
                activeActor.getTransform().getPosition().y -= MouseListener.getWorldDy();
            }
        }
    }
}
