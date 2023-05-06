package Burst.Engine.Source.Editor.Gizmo;

import Burst.Engine.Source.Editor.Panel.PropertiesPanel;
import Burst.Engine.Source.Core.Graphics.Input.MouseListener;
import Burst.Engine.Source.Core.Assets.Graphics.Sprite;

public class TranslateGizmo extends Gizmo {

    public TranslateGizmo(Sprite arrowSprite, PropertiesPanel propertiesPanel) {
        super(arrowSprite, propertiesPanel);
    }

    @Override
    public void updateEditor(float dt) {
        if (activeActor != null) {
            if (xAxisActive && !yAxisActive) {
                activeActor.transform.position.x -= MouseListener.getWorldDx();
            } else if (yAxisActive) {
                activeActor.transform.position.y -= MouseListener.getWorldDy();
            }
        }

        super.updateEditor(dt);
    }
}
