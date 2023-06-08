package Burst.Engine.Source.Editor.Gizmo;

import Burst.Engine.Source.Core.Actor.Actor;
import Burst.Engine.Source.Core.Actor.ActorComponent;
import Burst.Engine.Source.Core.Assets.Graphics.SpriteSheet;
import Burst.Engine.Source.Core.Input.KeyListener;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_E;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_R;

public class GizmoSystem extends ActorComponent {
    private SpriteSheet gizmos;
    private int usingGizmo = 0;


    public GizmoSystem(SpriteSheet gizmoSprites, Actor actor) {
        super(actor);
        gizmos = gizmoSprites;
    }

    @Override
    public void start() {
        if (this.actor == null) return;
        actor.addComponent(new TranslateGizmo(gizmos.getSprite(1)));
        actor.addComponent(new ScaleGizmo(gizmos.getSprite(2)));
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
