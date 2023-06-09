package Burst.Engine.Source.Core.Input;

import Burst.Engine.Source.Core.UI.Viewport;
import Burst.Engine.Source.Core.UI.Window;
import imgui.ImGui;
import imgui.ImVec2;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector4f;
import org.joml.Vector4fc;

import java.util.Arrays;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

/**
 * @author Oliver Schuetz
 * The MouseListener class handles mouse input events and provides methods to access mouse-related information.
 */
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

  /**
   * Clears the mouse state, resetting all values to their default state.
   */
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

  /**
   * Retrieves the MouseListener instance.
   *
   * @return The MouseListener instance.
   */
  public static MouseListener get() {
    if (MouseListener.instance == null) {
      MouseListener.instance = new MouseListener();
    }

    return MouseListener.instance;
  }

  /**
   * Updates the mouse position when the mouse moves.
   *
   * @param window The window ID.
   * @param xpos   The new x-coordinate of the mouse position.
   * @param ypos   The new y-coordinate of the mouse position.
   */
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

  /**
   * Updates the mouse position when the mouse moves.
   *
   * @param window The window ID.
   */
  public static void mouseButtonCallback(long window, int button, int action, int mods) {
    if (action == GLFW_PRESS) {
      if (get().mouseButtonDown == -1) {
        get().mouseButtonDown = 0;
      }
      get().mouseButtonDown++;
      if (button < get().mouseButtonPressed.length) {
        get().mouseButtonPressed[button] = true;
      }
    } else if (action == GLFW_RELEASE) {
      if (get().mouseButtonDown != 0) {
        get().mouseButtonDown--;
      }


      if (button < get().mouseButtonPressed.length) {
        get().mouseButtonPressed[button] = false;
        get().isDragging = false;
      }
    }
  }

  /**
   * Updates the mouse position when the mouse moves.
   *
   * @param window The window ID.
   */
  public static void mouseScrollCallback(long window, double xOffset, double yOffset) {
    get().scrollX = xOffset;
    get().scrollY = yOffset;
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


  public static float getScreenX() {
    return getScreen().x;
  }

  public static float getScreenY() {
    return getScreen().y;
  }

  /**
   * @return the position of the mouse in the viewport in pixels
   */
  public static Vector2f getScreen() {
    ImVec2 mousePos = new ImVec2();
    ImGui.getMousePos(mousePos);

    // Invert Y
    mousePos.y = Window.getHeight() - mousePos.y;

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
    return screenToView(getScreen());
  }

  /**
   * @return The current mouse position in world coordinates.
   */
  public static float getWorldX() {
    return getWorld().x;
  }

  /**
   * @return The current mouse position in world coordinates.
   */
  public static float getWorldY() {
    return getWorld().y;
  }

  /**
   * @return The current mouse position in world coordinates.
   */
  public static Vector2f getWorld() {
    return new Vector2f(screenToWorld(new Vector2f(getScreen())));
  }


  /**
   * View coordinates are the pixel position in the viewport.
   * @param screenCoords The screen coordinates to convert to view coordinates.
   * @return converted view coordinates
   */
  private static Vector2f screenToView(Vector2f screenCoords) {
    Vector2f screenCoordsCopy = new Vector2f(screenCoords);

    screenCoordsCopy.y = Window.getHeight() - screenCoordsCopy.y;

    // offset by the viewport position
    float currentX = screenCoordsCopy.x - get().gameViewportPos.x;
    float currentY = screenCoordsCopy.y - get().gameViewportPos.y - 24;

    // calculate the percentage position in the viewport
    currentX /= get().gameViewportSize.x;
    currentY /= get().gameViewportSize.y;

    currentX *= Window.getWidth();
    currentY *= Window.getHeight();

    currentY = Window.getHeight() - currentY;

    return new Vector2f(currentX, currentY);
  }

  /**
   * @param screenCoords The screen coordinates to convert to world coordinates.
   * @return The current mouse position in world coordinates.
   */
  public static Vector2f screenToWorld(Vector2f screenCoords) {
    Vector2f screenCoordsCopy = screenToView(new Vector2f(screenCoords));

    screenCoordsCopy.y = Window.getHeight() - screenCoordsCopy.y;

    float currentX = screenCoordsCopy.x / Window.getWidth() * 2 - 1;
    float currentY = screenCoordsCopy.y / Window.getHeight() * 2 - 1;


    Viewport viewport = Window.getScene().getViewport();
    Vector4f tmp = new Vector4f(currentX, currentY, 0, 1);
    Matrix4f inverseView = new Matrix4f(viewport.getInverseView());
    Matrix4f inverseProjection = new Matrix4f(viewport.getInverseProjection());
    tmp.mul(inverseView.mul(inverseProjection));

    return new Vector2f(tmp.x, tmp.y);
  }

  /**
   * @param viewCoords The view coordinates to convert to screen coordinates.
   * @return The current mouse position in screen coordinates.
   */
  public static Vector2f viewToScreen(Vector2f viewCoords) {
    Vector2f viewCoordsCopy = new Vector2f(viewCoords);

    // offset by the viewport position
    float currentX = viewCoordsCopy.x;
    float currentY = Window.getHeight() - viewCoordsCopy.y;

    // calculate the percentage position in the viewport
    currentX /= Window.getWidth();
    currentY /= Window.getHeight();

    // calculate the position in the viewport
    currentX *= get().gameViewportSize.x;
    currentY *= get().gameViewportSize.y;

    // offset by the viewport position
    currentX += get().gameViewportPos.x;
    currentY += get().gameViewportPos.y + 24;

    currentY = Window.getHeight() - currentY;


    return new Vector2f(currentX, currentY);
  }

  /**
   * @param worldCoords The world coordinates to convert to screen coordinates.
   * @return The current mouse position in screen coordinates.
   */
  public static Vector2f worldToScreen(Vector2f worldCoords) {

    Vector2f windowSpace = worldToView(worldCoords);

    // View to Screen
    windowSpace = viewToScreen(windowSpace);

    return windowSpace;
  }

  public static Vector2f worldToView(Vector2f worldCoords) {
    Vector2f worldCoordsCopy = new Vector2f(worldCoords);

    // World to View
    Viewport viewport = Window.getScene().getViewport();
    Vector4f ndcSpacePos = new Vector4f(worldCoordsCopy.x, worldCoordsCopy.y, 0, 1);
    Matrix4f view = new Matrix4f(viewport.getViewMatrix());
    Matrix4f projection = new Matrix4f(viewport.getProjectionMatrix());
    ndcSpacePos.mul(projection.mul(view));
    Vector2f windowSpace = new Vector2f(ndcSpacePos.x, ndcSpacePos.y).mul(1.0f / ndcSpacePos.w);
    windowSpace.add(new Vector2f(1.0f, 1.0f)).mul(0.5f);
    windowSpace.mul(new Vector2f(Window.getWidth(), Window.getHeight()));
    windowSpace.y = Window.getHeight() - windowSpace.y;

    return windowSpace;
  }

  /**
   * View coordinates are related to the viewport.
   *
   * @param viewCoords The view coordinates to convert to screen coordinates.
   * @return The current mouse position in screen coordinates.
   */
  public static Vector2f viewToWorld(Vector2f viewCoords) {
    // first convert to screen coords
    Vector2f screenCoords = viewToScreen(viewCoords);

    // then convert to world coords
    return screenToWorld(screenCoords);
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
