package Burst.Engine.Source.Editor.Gizmo;

import Burst.Engine.Source.Editor.Panel.PropertiesPanel;
import Burst.Engine.Source.Core.Graphics.Input.MouseListener;
import Burst.Engine.Source.Core.Graphics.Sprite.Sprite;

public class ScaleGizmo extends Gizmo {
    public ScaleGizmo(Sprite scaleSprite, PropertiesPanel propertiesPanel) {
        super(scaleSprite, propertiesPanel);
    }

    @Override
    public void editorUpdate(float dt) {
        if (activeGameObject != null) {
            if (xAxisActive && !yAxisActive) {
                activeGameObject.transform.scale.x -= MouseListener.getWorldDx();
            } else if (yAxisActive) {
                activeGameObject.transform.scale.y -= MouseListener.getWorldDy();
            }
        }

        super.editorUpdate(dt);
    }
}
