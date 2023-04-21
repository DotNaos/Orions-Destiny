package editor;

import Burst.GameObject;
import Burst.MouseListener;
import Burst.Window;
import imgui.ImGui;
import imgui.flag.ImGuiFocusedFlags;
import org.joml.Vector2f;
import org.joml.Vector3f;
import renderer.DebugDraw;
import renderer.PickingTexture;
import scenes.LevelEditorScene;
import scenes.Scene;

import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LEFT;

public class PropertiesWindow {
    private GameObject activeGameObject = null;
    private PickingTexture pickingTexture;

    public PropertiesWindow(PickingTexture pickingTexture) {
        this.pickingTexture = pickingTexture;
    }

    public void update(float dt, Scene currentScene)
    {
        if (MouseListener.mouseButtonDown(GLFW_MOUSE_BUTTON_LEFT)) {
            int x = (int)MouseListener.getScreenX();
            int y = (int)MouseListener.getScreenY();

            int gameObjectID = pickingTexture.readPixel(x, y);
            activeGameObject = currentScene.getGameObject(gameObjectID);

            // Mouse postion debug
            // DebugDraw.addBox2D(new Vector2f(MouseListener.getOrthoX(), MouseListener.getOrthoY()), new Vector2f(10, 10), 0, new Vector3f(1, 0, 0), 20);

        }
    }

    public void imgui() {
        if (activeGameObject != null) {
            ImGui.begin("Properties");
            activeGameObject.imgui();

            // Draws a box around the selected object
            DebugDraw.addBox2D(new Vector2f(activeGameObject.transform.position).add(new Vector2f(activeGameObject.transform.scale).mul(0.5f)), new Vector2f(activeGameObject.transform.scale).add(new Vector2f(1)), activeGameObject.transform.rotation, new Vector3f(1, 0, 0), 1);

            ImGui.end();

        }
    }

}
