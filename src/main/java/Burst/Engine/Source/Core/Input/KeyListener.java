package Burst.Engine.Source.Core.Input;

import imgui.ImGui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

/**
 * @author GamesWithGabe
 * The KeyListener class handles input events related to keyboard keys.
 * It provides methods to track the state of keys being pressed and released.
 * Uses the singleton pattern.
 */
public class KeyListener {
  private static KeyListener instance;
  private boolean keyPressed[] = new boolean[350];
  private boolean keyBeginPress[] = new boolean[350];

  /**
   * Private constructor to prevent direct instantiation of KeyListener.
   * Use the get() method to obtain an instance of KeyListener.
   */
  private KeyListener() {

  }

  /**
   * Resets the key begin press state for all keys to false.
   * This method is typically called at the end of a frame to prepare for the next frame.
   */
  public static void endFrame() {
    Arrays.fill(get().keyBeginPress, false);
  }

  /**
   * Returns the instance of KeyListener.
   * If an instance does not exist, a new one is created.
   *
   * @return the instance of KeyListener
   */
  public static KeyListener get() {
    if (KeyListener.instance == null) {
      KeyListener.instance = new KeyListener();
    }

    return KeyListener.instance;
  }

  /**
   * Callback function for handling key events.
   * Updates the state of the specified key based on the provided action.
   *
   * @param window   the window that received the event
   * @param key      the keyboard key that was pressed or released
   * @param scancode the system-specific scancode of the key
   * @param action   the action performed on the key (GLFW_PRESS or GLFW_RELEASE)
   * @param mods     the bitfield describing the modifier keys held down
   */
  public static void keyCallback(long window, int key, int scancode, int action, int mods) {
    if (key < 0 || key > 350) return;

    if (action == GLFW_PRESS) {
      get().keyPressed[key] = true;
      get().keyBeginPress[key] = true;
    } else if (action == GLFW_RELEASE) {
      get().keyPressed[key] = false;
      get().keyBeginPress[key] = false;
    }
  }

  /**
   * Callback function for handling character input events.
   * This method is not implemented and serves as a placeholder for future implementation.
   *
   * @param window    the window that received the event
   * @param codepoint the Unicode code point of the character
   */
  public static void charCallback(long window, int codepoint) {
    //TODO: Implement char callback
  }

  /**
   * Checks if the specified key is currently being pressed.
   *
   * @param keyCode the key code of the keyboard key
   * @return true if the key is currently pressed, false otherwise
   */
  public static boolean isKeyPressed(int keyCode) {
    return get().keyPressed[keyCode];
  }

  public static List<Integer> getPressedKeys() {
    List<Integer> pressedKeys = new ArrayList<>();
    for (int i = 0; i < get().keyPressed.length; i++) {
      if (get().keyPressed[i]) {
        pressedKeys.add(i);
      }
    }

    return pressedKeys;
  }

  /**
   * Checks if the specified key is currently being pressed.
   *
   * @param keyCode the key code of the keyboard key
   * @return true if the key is currently pressed, false otherwise
   */
  public static boolean keyBeginPress(int keyCode) {
    return get().keyBeginPress[keyCode];
  }
}
