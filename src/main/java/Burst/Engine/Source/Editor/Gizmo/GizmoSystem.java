package Burst.Engine.Source.Editor.Gizmo;

import Burst.Engine.Source.Core.Graphics.Input.KeyListener;
import Burst.Engine.Source.Core.UI.Window;
import Burst.Engine.Source.Core.Graphics.Sprite.Spritesheet;
import Burst.Engine.Source.Core.Component;
import Burst.Engine.Source.Editor.Panel.PropertiesPanel;
import Burst.Engine.Source.Editor.Panel.ViewportPanel;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_E;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_R;

public class GizmoSystem extends Component {
    private Spritesheet gizmos;
    private int usingGizmo = 0;

    public GizmoSystem(Spritesheet gizmoSprites) {
        gizmos = gizmoSprites;
    }

    @Override
    public void start() {
        gameObject.addComponent(new TranslateGizmo(gizmos.getSprite(1),
                Window.getScene().getSceneInitializer().getPanel(PropertiesPanel.class)));
        gameObject.addComponent(new ScaleGizmo(gizmos.getSprite(2),
                Window.getScene().getSceneInitializer().getPanel(PropertiesPanel.class)));
    }

    @Override
    public void editorUpdate(float dt) {
        if (usingGizmo == 0) {
            gameObject.getComponent(TranslateGizmo.class).setUsing();
            gameObject.getComponent(ScaleGizmo.class).setNotUsing();
        } else if (usingGizmo == 1) {
            gameObject.getComponent(TranslateGizmo.class).setNotUsing();
            gameObject.getComponent(ScaleGizmo.class).setUsing();
        }

        if (KeyListener.isKeyPressed(GLFW_KEY_E)) {
            usingGizmo = 0;
        } else if (KeyListener.isKeyPressed(GLFW_KEY_R)) {
            usingGizmo = 1;
        }
    }
}
