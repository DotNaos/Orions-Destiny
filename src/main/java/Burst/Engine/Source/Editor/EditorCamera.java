package Burst.Engine.Source.Editor;

import Burst.Engine.Config.HotKeys;
import Burst.Engine.Source.Core.Component;
import Burst.Engine.Source.Core.Input.KeyListener;
import Burst.Engine.Source.Core.Input.MouseListener;
import Burst.Engine.Source.Core.UI.Viewport;
import org.joml.Vector2f;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_0;

/**
 * @author Oliver Schuetz
 */
public class EditorCamera extends Component {

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

  public void updateEditor(float dt) {
    super.updateEditor(dt);

    // If the user grabs in the viewport, move the viewport
    // And change the cursor to a hand
    if (isMovingCamera() && dragDebounce > 0) {
      this.clickOrigin = new Vector2f(MouseListener.getWorldX(), MouseListener.getWorldY());
      dragDebounce -= dt;
      return;
    } else if (isMovingCamera()) {
      Vector2f mousePos = new Vector2f(MouseListener.getWorldX(), MouseListener.getWorldY());
      Vector2f delta = new Vector2f(mousePos).sub(this.clickOrigin);


      viewport.position.sub(delta.mul(dt).mul(dragSensitivity));
      this.clickOrigin.lerp(mousePos, dt);
    }

    if (dragDebounce <= 0.0f && !isMovingCamera()) {
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
      final Vector2f standardViewportPos = new Vector2f();

      viewport.position.lerp(new Vector2f(), lerpTime);
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

  private boolean isMovingCamera() {
    boolean moveCamera = false;

    // Move the camera if the user is holding the move camera hotkey
    if (MouseListener.mouseButtonDown(HotKeys.get().EditorMoveCamera)) {
      moveCamera = true;
    }
    // Or if the user is holding the move camera hotkey and the move camera modifier
    // TODO: FIX HOTKEYS
//        if (KeyListener.isKeyPressed(GLFW_KEY_LEFT_SHIFT) && MouseListener.mouseButtonDown(GLFW_MOUSE_BUTTON_LEFT)) {
//            moveCamera = true;
//        }

    return moveCamera;
  }
}
