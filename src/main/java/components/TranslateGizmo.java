package components;


import Burst.MouseListener;
import editor.PropertiesWindow;

public class TranslateGizmo extends Gizmo{


    public TranslateGizmo(Sprite arrowSprite, PropertiesWindow propertiesWindow) {
        super(arrowSprite, propertiesWindow);
    }

    @Override
    public void update(float dt) {
        if (this.activeGameObject != null) {
            if (xAxisActive && !yAxisActive)
            {
                this.activeGameObject.transform.position.x -= MouseListener.getWorldDx();
            } else if (yAxisActive)
            {
                this.activeGameObject.transform.position.y -= MouseListener.getWorldDy();
            }
        }
        super.update(dt);
    }
}
