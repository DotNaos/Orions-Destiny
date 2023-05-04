package Burst.Engine.Source.Editor.Gizmo;

import Burst.Engine.Source.Editor.Panel.PropertiesPanel;
import Burst.Engine.Source.Core.Graphics.Input.MouseListener;
import Burst.Engine.Source.Core.Graphics.Sprite.Sprite;

public class TranslateGizmo extends Gizmo {

    public TranslateGizmo(Sprite arrowSprite, PropertiesPanel propertiesPanel) {
        super(arrowSprite, propertiesPanel);
    }

    @Override
    public void editorUpdate(float dt) {
        if (activeGameObject != null) {
            if (xAxisActive && !yAxisActive) {
                activeGameObject.transform.position.x -= MouseListener.getWorldDx();
            } else if (yAxisActive) {
                activeGameObject.transform.position.y -= MouseListener.getWorldDy();
            }
        }

        super.editorUpdate(dt);
    }
}
