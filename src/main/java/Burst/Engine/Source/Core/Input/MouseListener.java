package Burst.Engine.Source.Core.Input;

import Burst.Engine.Source.Core.UI.Viewport;
import Burst.Engine.Source.Core.UI.Window;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;

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


    private Vector3f gameViewportPos = new Vector3f();
    private Vector3f gameViewportSize = new Vector3f();

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

    public static Vector3f getWorld() {
        float currentX = getX() - get().gameViewportPos.x;
        currentX = (2.0f * (currentX / get().gameViewportSize.x)) - 1.0f;
        float currentY = (getY() - get().gameViewportPos.y);
        currentY = (2.0f * (1.0f - (currentY / get().gameViewportSize.y))) - 1;

        Viewport viewport = Window.getScene().getViewport();
        Vector4f tmp = new Vector4f(currentX, currentY, 0, 1);
        Matrix4f inverseView = new Matrix4f(viewport.getInverseView());
        Matrix4f inverseProjection = new Matrix4f(viewport.getInverseProjection());
        tmp.mul(inverseView.mul(inverseProjection));

        return new Vector3f(tmp.x, tmp.y, 0);
    }

    public static Vector3f screenToWorld(Vector3f screenCoords) {
        Vector3f normalizedScreenCords = new Vector3f(
                screenCoords.x / Window.getWidth(),
                screenCoords.y / Window.getHeight(), 0
        );
        normalizedScreenCords.mul(2.0f).sub(new Vector3f(1.0f, 1.0f, 0));
        Viewport viewport = Window.getScene().getViewport();
        Vector4f tmp = new Vector4f(normalizedScreenCords.x, normalizedScreenCords.y,
                0, 1);
        Matrix4f inverseView = new Matrix4f(viewport.getInverseView());
        Matrix4f inverseProjection = new Matrix4f(viewport.getInverseProjection());
        tmp.mul(inverseView.mul(inverseProjection));

        return new Vector3f(tmp.x, tmp.y, 0);
    }

    public static Vector3f worldToScreen(Vector3f worldCoords) {
        Viewport viewport = Window.getScene().getViewport();
        Vector4f ndcSpacePos = new Vector4f(worldCoords.x, worldCoords.y, 0, 1);
        Matrix4f view = new Matrix4f(viewport.getViewMatrix());
        Matrix4f projection = new Matrix4f(viewport.getProjectionMatrix());
        ndcSpacePos.mul(projection.mul(view));
        Vector3f windowSpace = new Vector3f(ndcSpacePos.x, ndcSpacePos.y, 0).mul(1.0f / ndcSpacePos.w);
        windowSpace.add(new Vector3f(1.0f, 1.0f, 0)).mul(0.5f);
        windowSpace.mul(new Vector3f(Window.getWidth(), Window.getHeight(), 0));

        return windowSpace;
    }

    public static float getScreenX() {
        return getScreen().x;
    }

    public static float getScreenY() {
        return getScreen().y;
    }

    public static Vector3f getScreen() {
        float currentX = getX() - get().gameViewportPos.x;
        currentX = (currentX / get().gameViewportSize.x) * Window.getWidth();
        float currentY = (getY() - get().gameViewportPos.y);
        currentY = (1.0f - (currentY / get().gameViewportSize.y)) * Window.getHeight();


        return new Vector3f(currentX, currentY, 0);
    }

    // get GameViewportSize
    public static Vector3f getGameViewportSize() {
        return get().gameViewportSize;
    }

    public static void setGameViewportSize(Vector3f gameViewportSize) {
        get().gameViewportSize.set(gameViewportSize);
    }

    public static Vector3f getGameViewportPos() {
        return new Vector3f(get().gameViewportPos);
    }

    public static void setGameViewportPos(Vector3f gameViewportPos) {
        get().gameViewportPos.set(gameViewportPos);
    }
}
