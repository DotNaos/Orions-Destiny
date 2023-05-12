package Burst.Engine.Source.Editor.Gizmo;

import Burst.Engine.Source.Core.Actor.Actor;
import Burst.Engine.Source.Core.Assets.Graphics.Spritesheet;
import Burst.Engine.Source.Core.Component;
import Burst.Engine.Source.Core.Graphics.Input.KeyListener;
import Burst.Engine.Source.Core.UI.Window;
import Burst.Engine.Source.Editor.Panel.PropertiesPanel;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_E;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_R;

public class GizmoSystem extends Component {
    private Spritesheet gizmos;
    private int usingGizmo = 0;


    public GizmoSystem(Spritesheet gizmoSprites, Actor actor) {
        super(actor);
        gizmos = gizmoSprites;
    }

    @Override
    public void start() {
        if (this.actor == null) return;
        actor.addComponent(new TranslateGizmo(gizmos.getSprite(1), Window.getScene().getPanel(PropertiesPanel.class)));
        actor.addComponent(new ScaleGizmo(gizmos.getSprite(2), Window.getScene().getPanel(PropertiesPanel.class)));
    }

    @Override
    public void updateEditor(float dt) {
        if (this.actor == null) return;
        if (usingGizmo == 0) {
            actor.getComponent(TranslateGizmo.class).setUsing();
            actor.getComponent(ScaleGizmo.class).setNotUsing();
        } else if (usingGizmo == 1) {
            actor.getComponent(TranslateGizmo.class).setNotUsing();
            actor.getComponent(ScaleGizmo.class).setUsing();
        }

        if (KeyListener.isKeyPressed(GLFW_KEY_E)) {
            usingGizmo = 0;
        } else if (KeyListener.isKeyPressed(GLFW_KEY_R)) {
            usingGizmo = 1;
        }
    }
}
