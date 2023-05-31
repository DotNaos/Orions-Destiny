package Burst.Engine.Source.Editor.Components;

import Burst.Engine.Config.Constants.GridLines_Config;
import Burst.Engine.Source.Core.Actor.Actor;
import Burst.Engine.Source.Core.Assets.AssetManager;
import Burst.Engine.Source.Core.Assets.Graphics.Spritesheet;
import Burst.Engine.Source.Core.Component;
import Burst.Engine.Source.Game.Animation.StateMachine;
import Burst.Engine.Source.Game.Game;
import Burst.Engine.Source.Core.Render.Debug.DebugDraw;
import Burst.Engine.Source.Core.Input.KeyListener;
import Burst.Engine.Source.Core.Input.MouseListener;
import Burst.Engine.Source.Core.Render.PickingTexture;
import Burst.Engine.Source.Core.Render.SpriteRenderer;
import Burst.Engine.Source.Core.UI.Window;
import Burst.Engine.Source.Editor.Gizmo.GizmoSystem;
import Burst.Engine.Source.Editor.NonPickable;
import Burst.Engine.Source.Editor.Panel.PropertiesPanel;
import Orion.res.AssetHolder;
import org.joml.Vector3f;
import org.joml.Vector2i;
import org.joml.Vector4f;

import java.util.HashSet;
import java.util.Set;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LEFT;

public class MouseControls extends Component {
    Actor holdingActor = null;
    Spritesheet gizmos = AssetManager.getAssetFromType(AssetHolder.GIZMOS, Spritesheet.class);
    private float debounceTime = 0.2f;
    private float debounce = debounceTime;
    private boolean boxSelectSet = false;
    private Vector3f boxSelectStart = new Vector3f();
    private Vector3f boxSelectEnd = new Vector3f();
    // Gizmos
    private GizmoSystem gizmoSystem;

    public MouseControls() {
        super();
        this.gizmoSystem = new GizmoSystem(gizmos, null);
    }

    public void pickupObject(Actor actor) {
        if (this.holdingActor != null) {
            this.holdingActor.destroy();
        }
        this.holdingActor = actor;
        this.holdingActor.getComponent(SpriteRenderer.class).setColor(new Vector4f(0.8f, 0.8f, 0.8f, 0.5f));
        this.holdingActor.addComponent(new NonPickable());
        this.gizmoSystem = new GizmoSystem(gizmos, this.holdingActor);

        Window.getScene().getGame().addActor(actor);
    }

    public void place() {
        Actor newObj = holdingActor.copy();
        if (newObj.getComponent(StateMachine.class) != null) {
            newObj.getComponent(StateMachine.class).refreshTextures();
        }
        newObj.getComponent(SpriteRenderer.class).setColor(new Vector4f(1, 1, 1, 1));
        newObj.removeComponent(NonPickable.class);
        Window.getScene().getGame().addActor(newObj);
    }


    public void update(float dt) {
        this.gizmoSystem.update(dt);
        debounce -= dt;
        PropertiesPanel propertiesPanel = Window.getScene().getPanel(PropertiesPanel.class);
        PickingTexture pickingTexture = propertiesPanel.getPickingTexture();
        Game game = Window.getScene().getGame();
        if (holdingActor != null) {
            float x = MouseListener.getWorldX();
            float y = MouseListener.getWorldY();
            holdingActor.transform.position.x = ((int) Math.floor(x / GridLines_Config.SIZE) * GridLines_Config.SIZE) + GridLines_Config.SIZE / 2.0f;
            holdingActor.transform.position.y = ((int) Math.floor(y / GridLines_Config.SIZE) * GridLines_Config.SIZE) + GridLines_Config.SIZE / 2.0f;

            if (MouseListener.mouseButtonDown(GLFW_MOUSE_BUTTON_LEFT)) {
                float halfWidth = GridLines_Config.SIZE / 2.0f;
                float halfHeight = GridLines_Config.SIZE / 2.0f;
                if (MouseListener.isDragging() &&
                        !blockInSquare(holdingActor.transform.position.x - halfWidth,
                                holdingActor.transform.position.y - halfHeight, 0)) {
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
        } else if (!MouseListener.isDragging() && MouseListener.mouseButtonDown(GLFW_MOUSE_BUTTON_LEFT) && debounce < 0) {
            int x = (int) MouseListener.getScreenX();
            int y = (int) MouseListener.getScreenY();
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
            Vector3f boxSelectStartWorld = MouseListener.screenToWorld(boxSelectStart);
            Vector3f boxSelectEndWorld = MouseListener.screenToWorld(boxSelectEnd);
            Vector3f halfSize =
                    (new Vector3f(boxSelectEndWorld).sub(boxSelectStartWorld)).mul(0.5f);
            DebugDraw.addBox2D(
                    (new Vector3f(boxSelectStartWorld)).add(halfSize),
                    new Vector3f(halfSize).mul(2.0f),
                    0.0f);
        } else if (boxSelectSet) {
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

            float[] gameObjectIds = pickingTexture.readPixels(
                    new Vector2i(screenStartX, screenStartY),
                    new Vector2i(screenEndX, screenEndY)
            );
            Set<Integer> uniqueGameObjectIds = new HashSet<>();
            for (float objId : gameObjectIds) {
                uniqueGameObjectIds.add((int) objId);
            }

            for (Integer gameObjectId : uniqueGameObjectIds) {
                Actor pickedObj = game.getActor(gameObjectId);
                if (pickedObj != null && pickedObj.getComponent(NonPickable.class) == null) {
                    propertiesPanel.addActiveGameObject(pickedObj);
                }
            }
        }
    }

    private boolean blockInSquare(float x, float y, float z) {
        PropertiesPanel propertiesPanel = Window.getScene().getPanel(PropertiesPanel.class);
        Vector3f start = new Vector3f(x, y, z);
        Vector3f end = new Vector3f(start).add(new Vector3f(GridLines_Config.SIZE, GridLines_Config.SIZE, 0));
        Vector3f startScreenf = MouseListener.worldToScreen(start);
        Vector3f endScreenf = MouseListener.worldToScreen(end);
        Vector2i startScreen = new Vector2i((int) startScreenf.x + 2, (int) startScreenf.y + 2);
        Vector2i endScreen = new Vector2i((int) endScreenf.x - 2, (int) endScreenf.y - 2);
        float[] gameObjectIds = propertiesPanel.getPickingTexture().readPixels(startScreen, endScreen);

        for (int i = 0; i < gameObjectIds.length; i++) {
            if (gameObjectIds[i] >= 0) {
                Actor pickedObj = Window.getScene().getGame().getActor((int) gameObjectIds[i]);
                if (pickedObj.getComponent(NonPickable.class) == null) {
                    return true;
                }
            }
        }

        return false;
    }

}
