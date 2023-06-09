package Burst.Engine.Source.Core.Input;

import Burst.Engine.Source.Core.Render.Debug.DebugDraw;
import Burst.Engine.Source.Core.UI.ImGui.DebugPanel;
import Burst.Engine.Source.Core.UI.Viewport;
import Burst.Engine.Source.Core.UI.Window;
import Burst.Engine.Source.Core.Util.DebugMessage;
import imgui.ImGui;
import imgui.ImVec2;
import imgui.flag.ImGuiMouseButton;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector2f;
import org.joml.Vector4f;

import javax.swing.*;
import java.util.Arrays;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public class MouseListener {
    private static MouseListener instance;
    private double scrollX, scrollY;
    private double xPos, yPos, lastY, lastX, worldX, worldY, lastWorldX, lastWorldY;
    private boolean mouseButtonPressed[] = new boolean[9];
    private boolean isDragging;

    private int mouseButtonDown = 0;

    private Vector2f gameViewportPos = new Vector2f();
    private Vector2f gameViewportSize = new Vector2f();

    private MouseListener() {
        this.scrollX = 0.0;
        this.scrollY = 0.0;
        this.xPos = 0.0;
        this.yPos = 0.0;
        this.lastX = 0.0;
        this.lastY = 0.0;
    }

    public static void endFrame() {
        get().scrollY = 0.0;
        get().scrollX = 0.0;
    }

    public static void clear() {
        get().scrollX = 0.0;
        get().scrollY = 0.0;
        get().xPos = 0.0;
        get().yPos = 0.0;
        get().lastX = 0.0;
        get().lastY = 0.0;
        get().mouseButtonDown = 0;
        get().isDragging = false;
        Arrays.fill(get().mouseButtonPressed, false);
    }

    public static MouseListener get() {
        if (MouseListener.instance == null) {
            MouseListener.instance = new MouseListener();
        }

        return MouseListener.instance;
    }

    public static void mousePosCallback(long window, double xpos, double ypos) {
        Window.getScene().mousePositionCallback(window, xpos, ypos);


        if (get().mouseButtonDown > 0) {
            // TODO: REMOVE
            System.out.println("Dragging");
            get().isDragging = true;
        }



        get().lastX = get().xPos;
        get().lastY = get().yPos;
        get().lastWorldX = get().worldX;
        get().lastWorldY = get().worldY;
        get().xPos = xpos;
        get().yPos = ypos;

    }

    public static void mouseButtonCallback(long window, int button, int action, int mods) {
        if (action == GLFW_PRESS) {
            get().mouseButtonDown++;
            if (button < get().mouseButtonPressed.length) {
                get().mouseButtonPressed[button] = true;
            }
        } else if (action == GLFW_RELEASE) {
            get().mouseButtonDown--;

            if (button < get().mouseButtonPressed.length) {
                get().mouseButtonPressed[button] = false;
                get().isDragging = false;
            }
        }
    }

    public static void mouseScrollCallback(long window, double xOffset, double yOffset) {
        get().scrollX = xOffset;
        get().scrollY = yOffset;
    }

    public static float getX() {
        return (float) get().xPos;
    }

    public static float getY() {
        return (float) get().yPos;
    }

    public static float getWorldDx() {
        return (float) (get().lastWorldX - get().worldX);
    }

    public static float getWorldDy() {
        return (float) (get().lastWorldY - get().worldY);
    }

    public static float getScrollX() {
        return (float) get().scrollX;
    }

    public static float getScrollY() {
        return (float) get().scrollY;
    }

    public static boolean isDragging() {
        return get().isDragging;
    }

    public static boolean mouseButtonDown(int button) {
        if (button < get().mouseButtonPressed.length) {
            return get().mouseButtonPressed[button];
        } else {
            return false;
        }
    }

    public static float getWorldX() {
        return getWorld().x;
    }

    public static float getWorldY() {
        return getWorld().y;
    }

    public static Vector2f getWorld() {
        float currentX = getX() - get().gameViewportPos.x;
        currentX = (2.0f * (currentX / get().gameViewportSize.x)) - 1.0f;
        float currentY = (getY() - get().gameViewportPos.y);
        currentY = (2.0f * (1.0f - (currentY / get().gameViewportSize.y))) - 1;

        Viewport viewport = Window.getScene().getViewport();
        Vector4f tmp = new Vector4f(currentX, currentY, 0, 1);
        Matrix4f inverseView = new Matrix4f(viewport.getInverseView());
        Matrix4f inverseProjection = new Matrix4f(viewport.getInverseProjection());
        tmp.mul(inverseView.mul(inverseProjection));

        return new Vector2f(tmp.x, tmp.y);
    }

    public static Vector2f screenToWorld(Vector2f screenCoords) {
        float currentX = screenCoords.x - get().gameViewportPos.x;
        currentX = (2.0f * (currentX / get().gameViewportSize.x)) - 1.0f;
        float currentY = (screenCoords.y - get().gameViewportPos.y - 24);
        currentY = (2.0f * (1.0f - (currentY / get().gameViewportSize.y))) - 1.0f;

        Viewport viewport = Window.getScene().getViewport();
        Vector4f tmp = new Vector4f(currentX, currentY, 0, 1);
        Matrix4f inverseView = new Matrix4f(viewport.getInverseView());
        Matrix4f inverseProjection = new Matrix4f(viewport.getInverseProjection());
        tmp.mul(inverseView.mul(inverseProjection));

        return new Vector2f(tmp.x, tmp.y);
    }

    public static Vector2f viewToWorld(Vector2f viewCoords) {
        // first convert to screen coords
        Vector2f screenCoords = new Vector2f(viewToScreen(viewCoords));

        // then convert to world coords
        return new Vector2f(screenToWorld(screenCoords));
    }


    public static Vector2f worldToScreen(Vector2f worldCoords) {
        Viewport viewport = Window.getScene().getViewport();
        Vector4f ndcSpacePos = new Vector4f(worldCoords.x, worldCoords.y, 0, 1);
        Matrix4f view = new Matrix4f(viewport.getViewMatrix());
        Matrix4f projection = new Matrix4f(viewport.getProjectionMatrix());
        ndcSpacePos.mul(projection.mul(view));
        Vector2f windowSpace = new Vector2f(ndcSpacePos.x, ndcSpacePos.y).mul(1.0f / ndcSpacePos.w);
        windowSpace.add(new Vector2f(1.0f, 1.0f)).mul(0.5f);
        windowSpace.mul(new Vector2f(Window.getWidth(), Window.getHeight()));

        return windowSpace;
    }

    public static float getScreenX() {
        return getScreen().x;
    }

    public static float getScreenY() {
        return getScreen().y;
    }

    public static Vector2f getScreen() {
        ImVec2 mousePos = new ImVec2();
        ImGui.getMousePos(mousePos);

        // Invert Y
//       mousePos.y = Window.getHeight() - mousePos.y;

        return new Vector2f(mousePos.x, mousePos.y);
    }

    public static float getViewX() {
        return getView().x;
    }

    public static float getViewY() {
        return getView().y;
    }

    /**
     * @return the position of the mouse in the viewport in pixels
     */
    public static Vector2f getView() {
        float currentX = getX() - get().gameViewportPos.x;
        currentX = (currentX / get().gameViewportSize.x) * Window.getWidth();
        float currentY = (getY() - get().gameViewportPos.y);
        currentY = (1.0f - (currentY / get().gameViewportSize.y)) * Window.getHeight();
        // Invert Y
        currentY = 1080.0f - currentY;
        return new Vector2f(currentX, currentY);
    }


    public static Vector2f viewToScreen(Vector2f viewCoords) {
        float currentX = viewCoords.x;
        float currentY = viewCoords.y;


        // calculate the percentage position in the viewport
        currentX = currentX / Window.getWidth();
        currentY = currentY / Window.getHeight();


        // calculate the position in the viewport
        currentX = (currentX * get().gameViewportSize.x);
        currentY = (currentY * get().gameViewportSize.y);

        // offset by the viewport position
        currentX = currentX + get().gameViewportPos.x;
        currentY = (currentY + get().gameViewportPos.y) + 23;

        return new Vector2f(currentX, currentY);
    }


    // get GameViewportSize
    public static Vector2f getGameViewportSize() {
        return get().gameViewportSize;
    }

    public static void setGameViewportSize(Vector2f gameViewportSize) {
        get().gameViewportSize.set(gameViewportSize);
    }

    public static Vector2f getGameViewportPos() {
        return new Vector2f(get().gameViewportPos);
    }

    public static void setGameViewportPos(Vector2f gameViewportPos) {
        get().gameViewportPos.set(gameViewportPos);
    }
}
