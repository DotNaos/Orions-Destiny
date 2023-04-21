package editor;

import Burst.GameObject;
import Burst.MouseListener;
import imgui.ImGui;
import renderer.PickingTexture;
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
            ImGui.end();
        }
    }

}
