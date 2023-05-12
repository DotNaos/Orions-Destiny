package Burst.Engine.Source.Editor;

import Burst.Engine.Source.Core.Graphics.Input.KeyListener;
import Burst.Engine.Source.Core.Graphics.Input.MouseListener;
import Burst.Engine.Source.Core.UI.Viewport;
import org.joml.Vector2f;

import static org.lwjgl.glfw.GLFW.*;

public class EditorCamera {

    private float dragDebounce = 0.032f;
    private Viewport viewport;
    private Vector2f clickOrigin;
    private boolean reset = false;
    private float lerpTime = 0.0f;
    private float dragSensitivity = 30.0f;
    private float scrollSensitivity = 0.1f;

    public EditorCamera(Viewport viewport) {
        this.viewport = viewport;
        this.clickOrigin = new Vector2f();
    }

    public void update(float dt) {
        if (MouseListener.mouseButtonDown(GLFW_MOUSE_BUTTON_MIDDLE) && dragDebounce > 0) {
            this.clickOrigin = new Vector2f(MouseListener.getWorldX(), MouseListener.getWorldY());
            dragDebounce -= dt;
            return;
        } else if (MouseListener.mouseButtonDown(GLFW_MOUSE_BUTTON_MIDDLE)) {
            System.out.println("test");
            Vector2f mousePos = new Vector2f(MouseListener.getWorldX(), MouseListener.getWorldY());
            Vector2f delta = new Vector2f(mousePos).sub(this.clickOrigin);
            viewport.position.sub(delta.mul(dt).mul(dragSensitivity));
            this.clickOrigin.lerp(mousePos, dt);
        }

        if (dragDebounce <= 0.0f && !MouseListener.mouseButtonDown(GLFW_MOUSE_BUTTON_MIDDLE)) {
            dragDebounce = 0.1f;
        }

        if (MouseListener.getScrollY() != 0.0f) {
            float addValue = (float) Math.pow(Math.abs(MouseListener.getScrollY() * scrollSensitivity), 1 / viewport.getZoom());
            addValue *= -Math.signum(MouseListener.getScrollY());
            viewport.addZoom(addValue);
        }

        if (KeyListener.isKeyPressed(GLFW_KEY_KP_DECIMAL)) {
            reset = true;
        }

        if (reset) {
            viewport.position.lerp(new Vector2f(), lerpTime);
            viewport.setZoom(this.viewport.getZoom() + ((1.0f - viewport.getZoom()) * lerpTime));
            this.lerpTime += 0.1f * dt;
            if (Math.abs(viewport.position.x) <= 5.0f && Math.abs(viewport.position.y) <= 5.0f) {
                this.lerpTime = 0.0f;
                viewport.position.set(0f, 0f);
                this.viewport.setZoom(1.0f);
                reset = false;
            }
        }
    }
}
