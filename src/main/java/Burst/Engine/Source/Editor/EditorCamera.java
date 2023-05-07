package Burst.Engine.Source.Editor;

import Burst.Engine.Source.Core.Viewport;
import Burst.Engine.Source.Core.Graphics.Input.KeyListener;
import Burst.Engine.Source.Core.Graphics.Input.MouseListener;
import Burst.Engine.Source.Core.Component;
import org.joml.Vector2f;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_KP_DECIMAL;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_MIDDLE;

public class EditorCamera extends Component {

    private float dragDebounce = 0.032f;
    private Viewport levelEditorViewport;
    private Vector2f clickOrigin;
    private boolean reset = false;
    private float lerpTime = 0.0f;
    private float dragSensitivity = 30.0f;
    private float scrollSensitivity = 0.1f;

    public EditorCamera(Viewport levelEditorViewport) {
        this.levelEditorViewport = levelEditorViewport;
        this.clickOrigin = new Vector2f();
    }

    @Override
    public void updateEditor(float dt) {
        if (MouseListener.mouseButtonDown(GLFW_MOUSE_BUTTON_MIDDLE) && dragDebounce > 0) {
            this.clickOrigin = new Vector2f(MouseListener.getWorldX(), MouseListener.getWorldY());
            dragDebounce -= dt;
            return;
        } else if (MouseListener.mouseButtonDown(GLFW_MOUSE_BUTTON_MIDDLE)) {
            Vector2f mousePos = new Vector2f(MouseListener.getWorldX(), MouseListener.getWorldY());
            Vector2f delta = new Vector2f(mousePos).sub(this.clickOrigin);
            levelEditorViewport.position.sub(delta.mul(dt).mul(dragSensitivity));
            this.clickOrigin.lerp(mousePos, dt);
        }

        if (dragDebounce <= 0.0f && !MouseListener.mouseButtonDown(GLFW_MOUSE_BUTTON_MIDDLE)) {
            dragDebounce = 0.1f;
        }

        if (MouseListener.getScrollY() != 0.0f) {
            float addValue = (float)Math.pow(Math.abs(MouseListener.getScrollY() * scrollSensitivity),
                    1 / levelEditorViewport.getZoom());
            addValue *= -Math.signum(MouseListener.getScrollY());
            levelEditorViewport.addZoom(addValue);
        }

        if (KeyListener.isKeyPressed(GLFW_KEY_KP_DECIMAL)) {
            reset = true;
        }

        if (reset) {
            levelEditorViewport.position.lerp(new Vector2f(), lerpTime);
            levelEditorViewport.setZoom(this.levelEditorViewport.getZoom() +
                    ((1.0f - levelEditorViewport.getZoom()) * lerpTime));
            this.lerpTime += 0.1f * dt;
            if (Math.abs(levelEditorViewport.position.x) <= 5.0f &&
                    Math.abs(levelEditorViewport.position.y) <= 5.0f) {
                this.lerpTime = 0.0f;
                levelEditorViewport.position.set(0f, 0f);
                this.levelEditorViewport.setZoom(1.0f);
                reset = false;
            }
        }
    }
}
