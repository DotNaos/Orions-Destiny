package components;

import Burst.MouseListener;
import editor.PropertiesWindow;

public class ScaleGizmo extends Gizmo{

    public ScaleGizmo(Sprite scaleSprite, PropertiesWindow propertiesWindow) {
        super(scaleSprite, propertiesWindow);

    }

    @Override
    public void update(float dt) {
        if (this.activeGameObject != null) {
            if (xAxisActive && !yAxisActive)
            {
                this.activeGameObject.transform.scale.x -= MouseListener.getWorldDx();
            } else if (yAxisActive)
            {
                this.activeGameObject.transform.scale.y -= MouseListener.getWorldDy();
            }
        }
        super.update(dt);
    }
}
