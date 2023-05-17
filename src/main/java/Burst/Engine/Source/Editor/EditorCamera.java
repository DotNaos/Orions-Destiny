package Burst.Engine.Source.Editor;

import Burst.Engine.Source.Core.Graphics.Input.KeyListener;
import Burst.Engine.Source.Core.Graphics.Input.MouseListener;
import Burst.Engine.Source.Core.UI.Viewport;
import org.joml.Vector3f;

import static org.lwjgl.glfw.GLFW.*;

public class EditorCamera {

    private float dragDebounce = 0.032f;
    private Viewport viewport;
    private Vector3f clickOrigin;
    private boolean reset = false;
    private float lerpTime = 0.0f;
    private float dragSensitivity = 30.0f;
    private float scrollSensitivity = 0.1f;

    public EditorCamera(Viewport viewport) {
        this.viewport = viewport;
        this.clickOrigin = new Vector3f();
    }

    public void update(float dt) {

        if (MouseListener.mouseButtonDown(GLFW_MOUSE_BUTTON_MIDDLE) && dragDebounce > 0) {
            this.clickOrigin = new Vector3f(MouseListener.getWorldX(), MouseListener.getWorldY(), 0);
            dragDebounce -= dt;
            return;
        } else if (MouseListener.mouseButtonDown(GLFW_MOUSE_BUTTON_MIDDLE)) {
            Vector3f mousePos = new Vector3f(MouseListener.getWorldX(), MouseListener.getWorldY(), 0);
            Vector3f delta = new Vector3f(mousePos).sub(this.clickOrigin);

            viewport.position.sub(delta.mul(dt).mul(dragSensitivity));
            this.clickOrigin.lerp(mousePos, dt);
        }

        if (dragDebounce <= 0.0f && !MouseListener.mouseButtonDown(GLFW_MOUSE_BUTTON_MIDDLE)) {
            dragDebounce = 0.1f;
        }

        if (MouseListener.getScrollY() != 0.0f) {
            float addValue = (float) Math.pow(Math.abs(MouseListener.getScrollY() * scrollSensitivity), 1 / viewport.getZoom());
            addValue *= -Math.signum(MouseListener.getScrollY());

            addValue = (float) Math.floor(addValue * 10) / 10;
            viewport.addZoom(addValue);
        }

        if (KeyListener.isKeyPressed(GLFW_KEY_0)) {
            reset = true;
        }

        if (reset) {
            final float standardZoom = 10.0f;
            final Vector3f standardViewportPos = new Vector3f();

            viewport.position.lerp(new Vector3f(), lerpTime);
            viewport.setZoom(this.viewport.getZoom() + ((standardZoom - viewport.getZoom()) * lerpTime));
            this.lerpTime += 0.1f * dt;
            if (Math.abs(viewport.position.x) <= 10.0f && Math.abs(viewport.position.y) <= 10.0f && Math.abs(viewport.getZoom() - 10.0f) <= 0.01f) {
                this.lerpTime = 0.0f;
                viewport.position.set(standardViewportPos);
                this.viewport.setZoom(standardZoom);
                reset = false;
            }
        }
    }
}
