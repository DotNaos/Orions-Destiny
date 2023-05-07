package Burst.Engine.Source.Core.Graphics.Input.Components;

import Burst.Engine.Source.Core.Graphics.Input.MouseListener;
import Burst.Engine.Source.Core.Game.Game;
import Burst.Engine.Source.Editor.NonPickable;
import Burst.Engine.Source.Core.Graphics.Render.PickingTexture;
import Burst.Engine.Source.Core.Graphics.Debug.DebugDraw;
import Burst.Engine.Source.Core.Graphics.Render.SpriteRenderer;
import Burst.Engine.Source.Core.Graphics.Input.KeyListener;
import Burst.Engine.Source.Editor.Panel.PropertiesPanel;
import Burst.Engine.Source.Core.Actor.Actor;
import Burst.Engine.Source.Core.UI.Window;
import Burst.Engine.Source.Core.Component;
import Burst.Engine.Source.Core.Game.Animation.StateMachine;
import org.joml.Vector2f;
import org.joml.Vector2i;
import org.joml.Vector4f;
import Burst.Engine.Config.Constants.GridLines_Config;

import java.util.HashSet;
import java.util.Set;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LEFT;

public class MouseControls extends Component {
    Actor holdinactorbject = null;
    private float debounceTime = 0.2f;
    private float debounce = debounceTime;

    private boolean boxSelectSet = false;
    private Vector2f boxSelectStart = new Vector2f();
    private Vector2f boxSelectEnd = new Vector2f();

    public void pickupObject(Actor actor) {
        if (this.holdinactorbject != null) {
            this.holdinactorbject.destroy();
        }
        this.holdinactorbject = actor;
        this.holdinactorbject.getComponent(SpriteRenderer.class).setColor(new Vector4f(0.8f, 0.8f, 0.8f, 0.5f));
        this.holdinactorbject.addComponent(new NonPickable());
        Window.getScene().getGame().addActor(actor);
    }

    public void place() {
        Actor newObj = holdinactorbject.copy();
        if (newObj.getComponent(StateMachine.class) != null) {
            newObj.getComponent(StateMachine.class).refreshTextures();
        }
        newObj.getComponent(SpriteRenderer.class).setColor(new Vector4f(1, 1, 1, 1));
        newObj.removeComponent(NonPickable.class);
        Window.getScene().getGame().addActor(newObj);
    }

    @Override
    public void updateEditor(float dt) {
        debounce -= dt;
        PropertiesPanel propertiesPanel =  Window.getScene().getPanel(PropertiesPanel.class);
        PickingTexture pickingTexture = propertiesPanel.getPickingTexture();
        Game game = Window.getScene().getGame();
        if (holdinactorbject != null) {
            float x = MouseListener.getWorldX();
            float y = MouseListener.getWorldY();
            holdinactorbject.transform.position.x = ((int)Math.floor(x / GridLines_Config.GRID_WIDTH) * GridLines_Config.GRID_WIDTH) + GridLines_Config.GRID_WIDTH / 2.0f;
            holdinactorbject.transform.position.y = ((int)Math.floor(y / GridLines_Config.GRID_HEIGHT) * GridLines_Config.GRID_HEIGHT) + GridLines_Config.GRID_HEIGHT / 2.0f;

            if (MouseListener.mouseButtonDown(GLFW_MOUSE_BUTTON_LEFT)) {
                float halfWidth = GridLines_Config.GRID_WIDTH / 2.0f;
                float halfHeight = GridLines_Config.GRID_HEIGHT / 2.0f;
                if (MouseListener.isDragging() &&
                    !blockInSquare(holdinactorbject.transform.position.x - halfWidth,
                            holdinactorbject.transform.position.y - halfHeight)) {
                    place();
                } else if (!MouseListener.isDragging() && debounce < 0) {
                    place();
                    debounce = debounceTime;
                }
            }

            if (KeyListener.isKeyPressed(GLFW_KEY_ESCAPE)) {
                holdinactorbject.destroy();
                holdinactorbject = null;
            }
        } else if (!MouseListener.isDragging() && MouseListener.mouseButtonDown(GLFW_MOUSE_BUTTON_LEFT) && debounce < 0) {
            int x = (int)MouseListener.getScreenX();
            int y = (int)MouseListener.getScreenY();
            int gameObjectId = pickingTexture.readPixel(x, y);

            Actor pickedObj = game.getActor(gameObjectId);
            if (pickedObj != null && pickedObj.getComponent(NonPickable.class) == null) {
                propertiesPanel.setActiveGameObject(pickedObj);
            } else if (pickedObj == null && !MouseListener.isDragging()) {
                propertiesPanel.clearSelected();
            }
            this.debounce = 0.2f;
        } else if (MouseListener.isDragging() && MouseListener.mouseButtonDown(GLFW_MOUSE_BUTTON_LEFT)) {
            if (!boxSelectSet) {
                propertiesPanel.clearSelected();
                boxSelectStart = MouseListener.getScreen();
//                System.out.println("Box Select Start: " + boxSelectStart);
                boxSelectSet = true;
            }
            boxSelectEnd = MouseListener.getScreen();
            Vector2f boxSelectStartWorld = MouseListener.screenToWorld(boxSelectStart);
            Vector2f boxSelectEndWorld = MouseListener.screenToWorld(boxSelectEnd);
            Vector2f halfSize =
                    (new Vector2f(boxSelectEndWorld).sub(boxSelectStartWorld)).mul(0.5f);
            DebugDraw.addBox2D(
                    (new Vector2f(boxSelectStartWorld)).add(halfSize),
                    new Vector2f(halfSize).mul(2.0f),
                    0.0f);
        } else if (boxSelectSet) {
            boxSelectSet = false;
            int screenStartX = (int)boxSelectStart.x;
            int screenStartY = (int)boxSelectStart.y;
            int screenEndX = (int)boxSelectEnd.x;
            int screenEndY = (int)boxSelectEnd.y;
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

            float[] gameObjectIds = pickingTexture.readPixels(
                    new Vector2i(screenStartX, screenStartY),
                    new Vector2i(screenEndX, screenEndY)
            );
            Set<Integer> uniqueGameObjectIds = new HashSet<>();
            for (float objId : gameObjectIds) {
                uniqueGameObjectIds.add((int)objId);
            }

            for (Integer gameObjectId : uniqueGameObjectIds) {
                Actor pickedObj = game.getActor(gameObjectId);
                if (pickedObj != null && pickedObj.getComponent(NonPickable.class) == null) {
                    propertiesPanel.addActiveGameObject(pickedObj);
                }
            }
        }
    }

    private boolean blockInSquare(float x, float y) {
        PropertiesPanel propertiesPanel = Window.getScene().getPanel(PropertiesPanel.class);
        Vector2f start = new Vector2f(x, y);
        Vector2f end = new Vector2f(start).add(new Vector2f(GridLines_Config.GRID_WIDTH, GridLines_Config.GRID_HEIGHT));
        Vector2f startScreenf = MouseListener.worldToScreen(start);
        Vector2f endScreenf = MouseListener.worldToScreen(end);
        Vector2i startScreen = new Vector2i((int)startScreenf.x + 2, (int)startScreenf.y + 2);
        Vector2i endScreen = new Vector2i((int)endScreenf.x - 2, (int)endScreenf.y - 2);
        float[] gameObjectIds = propertiesPanel.getPickingTexture().readPixels(startScreen, endScreen);

        for (int i = 0; i < gameObjectIds.length; i++) {
            if (gameObjectIds[i] >= 0) {
                Actor pickedObj = Window.getScene().getGame().getActor((int)gameObjectIds[i]);
                if (pickedObj.getComponent(NonPickable.class) == null) {
                    return true;
                }
            }
        }

        return false;
    }
}
