package Burst.Engine.Source.Editor.Components;

import Burst.Engine.Config.Config.Gridlines;
import Burst.Engine.Source.Core.Actor.Actor;
import Burst.Engine.Source.Core.Component;
import Burst.Engine.Source.Core.Input.KeyListener;
import Burst.Engine.Source.Core.Input.MouseListener;
import Burst.Engine.Source.Core.Render.Debug.DebugDraw;
import Burst.Engine.Source.Core.Render.PickingTexture;
import Burst.Engine.Source.Core.UI.Window;
import Burst.Engine.Source.Editor.Panel.PropertiesPanel;
import Burst.Engine.Source.Game.Game;
import org.joml.Vector2f;
import org.joml.Vector2i;

import java.util.HashSet;
import java.util.Set;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LEFT;

/**
 * @author Oliver Schuetz
 */
public class MouseControls extends Component {
  private transient Actor holdingActor = null;
  private float debounceTime = 0.2f;
  private transient float debounce = debounceTime;
  private transient boolean boxSelectSet = false;
  private Vector2f boxSelectStart = new Vector2f();
  private Vector2f boxSelectEnd = new Vector2f();

  public MouseControls() {
    super();
  }

  public void pickupObject(Actor actor) {
    if (this.holdingActor != null) {
      this.holdingActor.destroy();
    }
    this.holdingActor = actor;
    this.holdingActor.setPickable(false);

    Window.getScene().getGame().addActor(actor);
  }

  public void place() {
    Actor copyActor = holdingActor.copy();
    copyActor.setPickable(true);
    Window.getScene().getGame().addActor(copyActor);
  }


  public void updateEditor(float dt) {
    super.updateEditor(dt);
    debounce -= dt;
    handleInput();
  }

  private void handleInput() {
    PropertiesPanel propertiesPanel = Window.getScene().getPanel(PropertiesPanel.class);
    PickingTexture pickingTexture = propertiesPanel.getPickingTexture();
    Game game = Window.getScene().getGame();

    // * Placing objects on release
    if (placingActor(propertiesPanel, pickingTexture, game)) ;

      // * Clicking on objects
    else if (clickOnActor(propertiesPanel, pickingTexture, game)) ;

      // * Box select
    else if (boxSelectActors(propertiesPanel, pickingTexture, game)) ;

  }

  private boolean blockInSquare(float x, float y) {
    PropertiesPanel propertiesPanel = Window.getScene().getPanel(PropertiesPanel.class);
    Vector2f start = new Vector2f(x, y);
    Vector2f end = new Vector2f(start).add(new Vector2f(Gridlines.SIZE, Gridlines.SIZE));
    Vector2f startScreenf = MouseListener.worldToScreen(start);
    Vector2f endScreenf = MouseListener.worldToScreen(end);
    Vector2i startScreen = new Vector2i((int) startScreenf.x + 2, (int) startScreenf.y + 2);
    Vector2i endScreen = new Vector2i((int) endScreenf.x - 2, (int) endScreenf.y - 2);
    float[] gameObjectIds = propertiesPanel.getPickingTexture().getPickingActorBuffer(startScreen, endScreen);

    for (int i = 0; i < gameObjectIds.length; i++) {
      if (gameObjectIds[i] >= 0) {
        Actor pickedObj = Window.getScene().getGame().getActor((int) gameObjectIds[i]);
        if (pickedObj.isPickable()) {
          return true;
        }
      }
    }

    return false;
  }

  private boolean placingActor(PropertiesPanel propertiesPanel, PickingTexture pickingTexture, Game game) {
    if (holdingActor != null) {
      float x = MouseListener.getWorldX();
      float y = MouseListener.getWorldY();

      holdingActor.getTransform().getPosition().x = ((int) Math.floor(x / Gridlines.SIZE) * Gridlines.SIZE) + Gridlines.SIZE / 2.0f;
      holdingActor.getTransform().getPosition().y = ((int) Math.floor(y / Gridlines.SIZE) * Gridlines.SIZE) + Gridlines.SIZE / 2.0f;

      if (MouseListener.mouseButtonDown(GLFW_MOUSE_BUTTON_LEFT)) {
        float halfWidth = Gridlines.SIZE / 2.0f;
        float halfHeight = Gridlines.SIZE / 2.0f;
        if (MouseListener.isDragging() && !blockInSquare(holdingActor.getTransform().getPosition().x - halfWidth, holdingActor.getTransform().getPosition().y - halfHeight)) {
          place();
        } else if (!MouseListener.isDragging() && debounce < 0) {
          place();
          debounce = debounceTime;
        }
      }

      if (KeyListener.isKeyPressed(GLFW_KEY_ESCAPE)) {
        holdingActor.destroy();
        holdingActor = null;
      }

      return true;
    }
    return false;
  }

  private boolean clickOnActor(PropertiesPanel propertiesPanel, PickingTexture pickingTexture, Game game) {
    if (!MouseListener.isDragging() && MouseListener.mouseButtonDown(GLFW_MOUSE_BUTTON_LEFT) && debounce < 0) {
      int x = (int) MouseListener.getViewX();
      int y = (int) MouseListener.getViewY();
      int actorID = pickingTexture.readPixel(x, y);

      Actor pickedActor = game.getActor(actorID);
      if (pickedActor != null && pickedActor.isPickable()) {
        propertiesPanel.setActiveActor(pickedActor);

      } else if (pickedActor == null && !MouseListener.isDragging()) {
        propertiesPanel.clearSelected();
      }
      this.debounce = 0.2f;
      return true;
    }
    return false;
  }

  private boolean boxSelectActors(PropertiesPanel propertiesPanel, PickingTexture pickingTexture, Game game) {
    // * Box select start
    if (MouseListener.isDragging() && MouseListener.mouseButtonDown(GLFW_MOUSE_BUTTON_LEFT)) {
      if (!boxSelectSet) {
        propertiesPanel.clearSelected();
        boxSelectStart = MouseListener.getView();
        boxSelectSet = true;
      }
      boxSelectEnd = MouseListener.getView();
      Vector2f boxSelectStartWorld = MouseListener.viewToWorld(boxSelectStart);
      Vector2f boxSelectEndWorld = MouseListener.viewToWorld(boxSelectEnd);

      Vector2f halfSize = (new Vector2f(boxSelectEndWorld).sub(boxSelectStartWorld)).mul(0.5f);
      DebugDraw.addBox((new Vector2f(boxSelectStartWorld)).add(halfSize), new Vector2f(halfSize).mul(2.0f), 0.0f);

      return true;
    }

    // * Box select end
    else if (boxSelectSet) {
      boxSelectSet = false;
      int screenStartX = (int) boxSelectStart.x;
      int screenStartY = (int) boxSelectStart.y;
      int screenEndX = (int) boxSelectEnd.x;
      int screenEndY = (int) boxSelectEnd.y;
      boxSelectStart.zero();
      boxSelectEnd.zero();

      if (screenEndX < screenStartX) {
        int tmp = screenStartX;
        screenStartX = screenEndX;
        screenEndX = tmp;
      }
      if (screenEndY < screenStartY) {
        int tmp = screenStartY;
        screenStartY = screenEndY;
        screenEndY = tmp;
      }

      float[] gameObjectIds = pickingTexture.getPickingActorBuffer(new Vector2i(screenStartX, screenStartY), new Vector2i(screenEndX, screenEndY));

      Set<Integer> uniqueGameObjectIds = new HashSet<>();
      for (float objId : gameObjectIds) {
        uniqueGameObjectIds.add((int) objId);
      }

      for (Integer gameObjectId : uniqueGameObjectIds) {
        Actor pickedObj = game.getActor(gameObjectId);
        if (pickedObj != null && pickedObj.isPickable()) {
          propertiesPanel.addActiveActor(pickedObj);
        }
      }

      return true;
    }
    return false;
  }


}
