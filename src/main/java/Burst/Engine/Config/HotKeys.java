package Burst.Engine.Config;

import Burst.Engine.Source.Core.Component;
import Burst.Engine.Source.Core.Util.Shortcut;

import static org.lwjgl.glfw.GLFW.*;

/**
 * @author Oliver Schuetz
 * The HotKeys class represents the configuration of hotkeys used in the application.
 * It provides key codes for various controls such as editor controls, player controls, and debug controls.
 * The class follows the singleton pattern to ensure that only one instance of HotKeys exists.
 */
public class HotKeys extends Component {
  private static final HotKeys instance = new HotKeys();

  /**
   * Returns the instance of HotKeys.
   *
   * @return the HotKeys instance
   */
  public static HotKeys get() {
    if (instance == null) {
      return new HotKeys();
    }
    return instance;
  }

  // Editor Controls
  public int EditorMoveCamera = GLFW_MOUSE_BUTTON_MIDDLE;
  public Shortcut Shortcut_EditorMoveCamera = new Shortcut(GLFW_KEY_LEFT_SHIFT, GLFW_MOUSE_BUTTON_LEFT);

  // Switches between the different editor modes
  public int EditorToggleSelect = GLFW_KEY_W;
  public int EditorToggleMove = GLFW_KEY_M;
  public int EditorToggleScale = GLFW_KEY_E;
  public int EditorToggleRotate = GLFW_KEY_R;
  public int EditorToggleMultiSelect = GLFW_KEY_T;

  // Editor Camera Controls
  public int EditorDelete = GLFW_KEY_DELETE;
  public int EditorMoveZToBack = GLFW_KEY_J;
  public int EditorMoveZToFront = GLFW_KEY_K;

  public int EditorMoveUp = GLFW_KEY_UP;
  public int EditorMoveDown = GLFW_KEY_DOWN;
  public int EditorMoveLeft = GLFW_KEY_LEFT;
  public int EditorMoveRight = GLFW_KEY_RIGHT;


  // Modifier Keys, change the behavior of the editor controls
  public int Modifier_EditorSlow = GLFW_KEY_LEFT_SHIFT;
  public int Modifier_EditorFast = GLFW_KEY_LEFT_CONTROL;
  public int Modifier_EditorNoSnap = GLFW_KEY_LEFT_CONTROL;
  public int Modifier_EditorDuplicate = GLFW_KEY_LEFT_ALT;

  // Editor Shortcuts
  public Shortcut Shortcut_EditorDuplicate = new Shortcut(GLFW_KEY_LEFT_CONTROL, GLFW_KEY_D);
  public Shortcut Shortcut_EditorUndo = new Shortcut(GLFW_KEY_LEFT_CONTROL, GLFW_KEY_Z);
  public Shortcut Shortcut_EditorRedo = new Shortcut(GLFW_KEY_LEFT_CONTROL, GLFW_KEY_Y);
  public Shortcut Shortcut_EditorCopy = new Shortcut(GLFW_KEY_LEFT_CONTROL, GLFW_KEY_C);
  public Shortcut Shortcut_EditorCut = new Shortcut(GLFW_KEY_LEFT_CONTROL, GLFW_KEY_X);
  public Shortcut Shortcut_EditorPaste = new Shortcut(GLFW_KEY_LEFT_CONTROL, GLFW_KEY_V);

  // Player Controls
  public int PlayerMoveUp = GLFW_KEY_W;
  public int PlayerMoveDown = GLFW_KEY_S;
  public int PlayerMoveLeft = GLFW_KEY_A;
  public int PlayerMoveRight = GLFW_KEY_D;
  public int PlayerJump = GLFW_KEY_SPACE;
  public int PlayerSprint = GLFW_KEY_LEFT_CONTROL;
  public int PlayerCrouch = GLFW_KEY_LEFT_SHIFT;


  // TODO: Implement these
  // Debug Controls
  public int DebugToggle = GLFW_KEY_F1;
  public int DebugToggleWireframe = GLFW_KEY_F2;
  public int DebugTogglePhysics = GLFW_KEY_F3;
  public int DebugToggleHitboxes = GLFW_KEY_F4;
  public int DebugToggleGrid = GLFW_KEY_F5;
  public int DebugToggleConsole = GLFW_KEY_F6;
  public int DebugToggleProfiler = GLFW_KEY_F7;
  public int DebugToggleEditor = GLFW_KEY_F8;
  public int DebugGotoNextLevel = GLFW_KEY_F9;
  public int DebugGotoPreviousLevel = GLFW_KEY_F10;


}
