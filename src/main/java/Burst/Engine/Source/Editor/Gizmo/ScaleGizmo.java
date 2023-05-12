package Burst.Engine.Source.Editor.Gizmo;

import Burst.Engine.Source.Editor.Panel.PropertiesPanel;
import Burst.Engine.Source.Core.Graphics.Input.MouseListener;
import Burst.Engine.Source.Core.Assets.Graphics.Sprite;

public class ScaleGizmo extends Gizmo {
    public ScaleGizmo(Sprite scaleSprite, PropertiesPanel propertiesPanel) {
        super(scaleSprite, propertiesPanel);
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
