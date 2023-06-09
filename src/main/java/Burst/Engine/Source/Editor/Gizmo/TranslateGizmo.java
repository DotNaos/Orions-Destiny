package Burst.Engine.Source.Editor.Gizmo;

import Burst.Engine.Source.Core.Assets.Graphics.Sprite;
import Burst.Engine.Source.Core.Input.MouseListener;
import Burst.Engine.Source.Editor.Panel.PropertiesPanel;

public class TranslateGizmo extends Gizmo {

    public TranslateGizmo(Sprite arrowSprite) {
        super(arrowSprite);
    }

    @Override
    public void update(float dt) {
        super.update(dt);
        if (activeActor != null) {
            if (xAxisActive && !yAxisActive) {
                activeActor.getTransform().position.x -= MouseListener.getWorldDx();
            } else if (yAxisActive) {
                activeActor.getTransform().position.y -= MouseListener.getWorldDy();
            }
        }
    }
}
