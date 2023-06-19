package Burst.Engine.Config;

import Burst.Engine.Source.Core.Component;
import Burst.Engine.Source.Core.Util.Shortcut;
import imgui.ImGui;
import imgui.flag.ImGuiCol;
import imgui.flag.ImGuiDir;
import org.joml.Vector2f;
import org.joml.Vector4f;

import java.util.ArrayList;
import java.util.List;

import static Burst.Engine.Source.Core.Util.Util.hexToVec4f;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_F10;

public class Config {
  private static Config configInstance;

  private List<Component> configs;

  private Config() {
      configs = new ArrayList<>();
      configs.add(new HotKeys());
      configs.add(new ImGuiStyle());
  }

  public static Config get()
  {
    if (configInstance == null) {
      configInstance = new Config();
    }
    return configInstance;
  }

  public static <T extends Component> T get(Class<T> componentClass) throws ClassCastException {
    for (Component c : get().configs) {
      if (componentClass.isAssignableFrom(c.getClass())) {
        try {
          return componentClass.cast(c);
        } catch (ClassCastException e) {
          e.printStackTrace();
          assert false : "Error: Casting component.";
        }
      }
    }
    return null;
  }


  public class Colors {

    /**
     * The RGBA value for the active actor color.
     */
    public static final Vector4f ACTIVE_ACTOR = new Vector4f(0.5f, 0.5f, 0.5f, 1);

    /**
     * The RGBA value for the transparent color.
     */
    public static Vector4f TRANSPARENT = new Vector4f(0, 0, 0, 0);

    /**
     * The RGBA value for the white color.
     */
    public static Vector4f WHITE = new Vector4f(1, 1, 1, 1);

    /**
     * The RGBA value for the black color.
     */
    public static Vector4f BLACK = new Vector4f(0, 0, 0, 1);

    /**
     * The RGBA value for the red color.
     */
    public static Vector4f RED = new Vector4f(1, 0, 0, 1);

    /**
     * The RGBA value for the green color.
     */
    public static Vector4f GREEN = new Vector4f(0, 1, 0, 1);

    /**
     * The RGBA value for the blue color.
     */
    public static Vector4f BLUE = new Vector4f(0, 0, 1, 1);

    /**
     * The RGBA value for the yellow color.
     */
    public static Vector4f YELLOW = new Vector4f(1, 1, 0, 1);

    /**
     * The RGBA value for the magenta color.
     */
    public static Vector4f MAGENTA = new Vector4f(1, 0, 1, 1);

    /**
     * The RGBA value for the cyan color.
     */
    public static Vector4f CYAN = new Vector4f(0, 1, 1, 1);

    /**
     * The RGBA value for the gray color.
     */
    public static Vector4f GRAY = new Vector4f(0.5f, 0.5f, 0.5f, 1);

    /**
     * The RGBA value for the light gray color.
     */
    public static Vector4f LIGHT_GRAY = new Vector4f(0.75f, 0.75f, 0.75f, 1);

    /**
     * The RGBA value for the dark gray color.
     */
    public static Vector4f DARK_GRAY = new Vector4f(0.25f, 0.25f, 0.25f, 1);

    /**
     * The RGBA value for the orange color.
     */
    public static Vector4f ORANGE = new Vector4f(1, 0.5f, 0, 1);

    /**
     * The RGBA value for the brown color.
     */
    public static Vector4f BROWN = new Vector4f(0.6f, 0.3f, 0.1f, 1);

    /**
     * The RGBA value for the pink color.
     */
    public static Vector4f PINK = new Vector4f(1, 0.68f, 0.68f, 1);

    /**
     * An array of predefined colors.
     */
    public static Vector4f[] COLORS = new Vector4f[]{TRANSPARENT, WHITE, BLACK, RED, GREEN, BLUE

            , YELLOW, MAGENTA, CYAN, GRAY, LIGHT_GRAY, DARK_GRAY, ORANGE, BROWN, PINK};

    /**
     * Returns a random color from the predefined color array {@code COLORS}.
     *
     * @return a random color as a {@code Vector4f} object
     */
    public static Vector4f getRandomColor() {
      return COLORS[(int) (Math.random() * COLORS.length)];
    }

    /**
     * Returns a random color from the specified color array.
     *
     * @param colors the array of colors to choose from
     * @return a random color as a {@code Vector4f} object
     */
    public static Vector4f getRandomColor(Vector4f[] colors) {
      return colors[(int) (Math.random() * colors.length)];
    }

    /**
     * Returns a random color from the specified color array, excluding a specified color.
     *
     * @param colors  the array of colors to choose from
     * @param exclude the color to exclude from the selection
     * @return a random color as a {@code Vector4f} object
     */
    public static Vector4f getRandomColor(Vector4f[] colors, Vector4f exclude) {
      Vector4f color = exclude;
      while (color == exclude) {
        color = colors[(int) (Math.random() * colors.length)];
      }
      return color;
    }

    /**
     * Returns a random color from the predefined color array {@code COLORS},
     * excluding a specified color.
     *
     * @param exclude the color to exclude from the selection
     * @return a random color as a {@code Vector4f} object
     */
    public static Vector4f getRandomColor(Vector4f exclude) {
      Vector4f color = exclude;
      while (color == exclude) {
        color = COLORS[(int) (Math.random() * COLORS.length)];
      }
      return color;
    }

    /**
     * Returns a random color from the predefined color array {@code COLORS},
     * excluding two specified colors.
     *
     * @param exclude1 the first color to exclude from the selection
     * @param exclude2 the second color to exclude from the selection
     * @return a random color as a {@code Vector4f} object
     */
    public static Vector4f getRandomColor(Vector4f exclude1, Vector4f exclude2) {
      Vector4f color = exclude1;
      while (color == exclude1 || color == exclude2) {
        color = COLORS[(int) (Math.random() * COLORS.length)];
      }
      return color;
    }

    /**
     * Returns a random color from the predefined color array {@code COLORS},
     * excluding three specified colors.
     *
     * @param exclude1 the first color to exclude from the selection
     * @param exclude2 the second color to exclude from the selection
     * @param exclude3 the third color to exclude from the selection
     * @return a random color as a {@code Vector4f} object
     */
    public static Vector4f getRandomColor(Vector4f exclude1, Vector4f exclude2, Vector4f exclude3) {
      Vector4f color = exclude1;
      while (color == exclude1 || color == exclude2 || color == exclude3) {
        color = COLORS[(int) (Math.random() * COLORS.length)];
      }
      return color;
    }
  }

  public class Fonts {

    /**
     * The base path for font assets.
     */
    public static final String PATH = "Assets/fonts/";

    /**
     * The path for the UI font.
     */
    public static final String UI = PATH + "Inter.ttf";

    /**
     * The path for the game font.
     */
    public static final String GAME = PATH + "Peaberry.ttf";
  }

  public class Gridlines {
    public static float SIZE = 1f;
    public static Vector4f COLOR = new Vector4f(0.2f, 0.2f, 0.2f, 0.0f);
  }

  public class HotKeys extends Component{

    /**
     * Returns the instance of HotKeys.
     *
     * @return the HotKeys instance
     */
//    public static HotKeys get() {
//        if (instance == null) {
//            instance = new HotKeys();
//        }
//        return instance;
//    }

    // Editor Controls
    public  int EditorMoveCamera = GLFW_MOUSE_BUTTON_MIDDLE;
    public Shortcut Shortcut_EditorMoveCamera = new Shortcut(GLFW_KEY_LEFT_SHIFT, GLFW_MOUSE_BUTTON_LEFT);

    // Switches between the different editor modes
    public  int EditorToggleSelect = GLFW_KEY_W;
    public  int EditorToggleMove = GLFW_KEY_M;
    public  int EditorToggleScale = GLFW_KEY_E;
    public  int EditorToggleRotate = GLFW_KEY_R;
    public  int EditorToggleMultiSelect = GLFW_KEY_T;

    // Edit or Camera Controls
    public  int EditorDelete = GLFW_KEY_DELETE;
    public  int EditorMoveZToBack = GLFW_KEY_J;
    public  int EditorMoveZToFront = GLFW_KEY_K;

    public  int EditorMoveUp = GLFW_KEY_UP;
    public  int EditorMoveDown = GLFW_KEY_DOWN;
    public  int EditorMoveLeft = GLFW_KEY_LEFT;
    public  int EditorMoveRight = GLFW_KEY_RIGHT;


    // Modifier Keys, change the behavior of the editor controls
    public  int Modifier_EditorSlow = GLFW_KEY_LEFT_SHIFT;
    public  int Modifier_EditorFast = GLFW_KEY_LEFT_CONTROL;
    public  int Modifier_EditorNoSnap = GLFW_KEY_LEFT_CONTROL;
    public  int Modifier_EditorDuplicate = GLFW_KEY_LEFT_ALT;

    // Editor Shortcuts
    public  Shortcut Shortcut_EditorDuplicate = new Shortcut(GLFW_KEY_LEFT_CONTROL, GLFW_KEY_D);
    public  Shortcut Shortcut_EditorUndo = new Shortcut(GLFW_KEY_LEFT_CONTROL, GLFW_KEY_Z);
    public  Shortcut Shortcut_EditorRedo = new Shortcut(GLFW_KEY_LEFT_CONTROL, GLFW_KEY_Y);
    public  Shortcut Shortcut_EditorCopy = new Shortcut(GLFW_KEY_LEFT_CONTROL, GLFW_KEY_C);
    public  Shortcut Shortcut_EditorCut = new Shortcut(GLFW_KEY_LEFT_CONTROL, GLFW_KEY_X);
    public  Shortcut Shortcut_EditorPaste = new Shortcut(GLFW_KEY_LEFT_CONTROL, GLFW_KEY_V);

    // Player Controls
    public  int PlayerMoveUp = GLFW_KEY_W;
    public  int PlayerMoveDown = GLFW_KEY_S;
    public  int PlayerMoveLeft = GLFW_KEY_A;
    public  int PlayerMoveRight = GLFW_KEY_D;
    public  int PlayerJump = GLFW_KEY_SPACE;
    public  int PlayerSprint = GLFW_KEY_LEFT_CONTROL;
    public  int PlayerCrouch = GLFW_KEY_LEFT_SHIFT;


    // TODO: Implement these
    // Debug Controls
    public  int DebugToggle = GLFW_KEY_F1;
    public  int DebugToggleWireframe = GLFW_KEY_F2;
    public  int DebugTogglePhysics = GLFW_KEY_F3;
    public  int DebugToggleHitboxes = GLFW_KEY_F4;
    public  int DebugToggleGrid = GLFW_KEY_F5;
    public  int DebugToggleConsole = GLFW_KEY_F6;
    public  int DebugToggleProfiler = GLFW_KEY_F7;
    public  int DebugToggleEditor = GLFW_KEY_F8;
    public  int DebugGotoNextLevel = GLFW_KEY_F9;
    public  int DebugGotoPreviousLevel = GLFW_KEY_F10;


  }

  public class ImGuiStyle extends Component {
    private transient boolean active = true;
    private boolean dark = true;
    //!=========================================================================
    //!~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //!------------------------------[Style Vars]-------------------------------
    //!~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //!=========================================================================
    private float frameRounding = 2.5f;
    private float childBorderSize = 1.0f;
    private float alpha = 1.0f;
    private boolean antiAliasedFill = true;
    private boolean antiAliasedLines = true;
    private boolean antiAliasedLinesUseTex = true;
    private Vector2f buttonTextAlign = new Vector2f(0.5f, 0.5f);
    private Vector2f cellPadding = new Vector2f(4.0f, 4.0f);
    private float childRounding = 0.0f;
    private float circleSegmentMaxError = 1.5f;
    private int colorButtonPosition = 0;
    private float columnsMinSpacing = 6.0f;
    private float curveTessellationTol = 1.25f;
    private Vector2f displaySafeAreaPadding = new Vector2f(3.0f, 3.0f);
    private Vector2f displayWindowPadding = new Vector2f(4.0f, 4.0f);
    private float frameBorderSize = 1.0f;
    private Vector2f framePadding = new Vector2f(4.0f, 4.0f);
    private float grabMinSize = 10.0f;
    private float grabRounding = 0.0f;
    private float indentSpacing = 21.0f;
    private Vector2f itemInnerSpacing = new Vector2f(4.0f, 4.0f);
    private Vector2f itemSpacing = new Vector2f(2);
    private float logSliderDeadzone = 4.0f;
    private float mouseCursorScale = 1.0f;
    private float popupBorderSize = 1.0f;
    private float popupRounding = 0.0f;
    private float scrollbarRounding = 0.0f;
    private float scrollbarSize = 16.0f;
    private Vector2f selectableTextAlign = new Vector2f(0.0f, 0.0f);
    private float tabBorderSize = 0.0f;
    private float tabMinWidthForCloseButton = 0.0f;
    private float tabRounding = 5.0f;
    private Vector2f touchExtraPadding = new Vector2f(0.0f, 0.0f);
    private float windowBorderSize = 1.0f;
    private int windowMenuButtonPosition = ImGuiDir.None;
    private Vector2f windowMinSize = new Vector2f(32.0f, 32.0f);
    private Vector2f windowPadding = new Vector2f(8.0f, 8.0f);
    private float windowRounding = 0.0f;
    private Vector2f windowTitleAlign = new Vector2f(0.0f, 0.5f);

    //!=========================================================================
    //!~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //!-------------------------------[Colors]----------------------------------
    //!~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //!=========================================================================

    private Vector4f Text = hexToVec4f(0xffffffff);
    private Vector4f TextDisabled = hexToVec4f(0x000001ff);
    /**
     * Background of normal windows
     */
    private Vector4f WindowBg = hexToVec4f(0x151515ff);
    /**
     * Background of child windows
     */
    private Vector4f ChildBg = hexToVec4f(0x151515ff);
    /**
     * Background of popups, menus, tooltips windows
     */
    private Vector4f PopupBg = hexToVec4f(0x202020C8);
    private Vector4f Border = hexToVec4f(0x00000000);
    private Vector4f BorderShadow = hexToVec4f(0x00000000);
    /**
     * Background of checkbox, radio button, plot, slider, text input
     */
    private Vector4f FrameBg = hexToVec4f(0x0f0f0fff);
    private Vector4f FrameBgHovered = hexToVec4f(0x0f0f0fff);
    private Vector4f FrameBgActive = hexToVec4f(0x0064FFff);
    private Vector4f TitleBg = hexToVec4f(0x151515ff);
    private Vector4f TitleBgActive = hexToVec4f(0x151515ff);
    private Vector4f TitleBgCollapsed = hexToVec4f(0x151515ff);
    private Vector4f MenuBarBg = hexToVec4f(0x242424ff);
    private Vector4f ScrollbarBg = hexToVec4f(0x00000eff);
    private Vector4f ScrollbarGrab = hexToVec4f(0x575757ff);
    private Vector4f ScrollbarGrabHovered = hexToVec4f((0x575757ff + (0x111111ff * 2)));
    private Vector4f ScrollbarGrabActive = hexToVec4f((0x575757ff + (0x111111ff * 2)));
    private Vector4f CheckMark = hexToVec4f(0xffffffff);
    private Vector4f SliderGrab = hexToVec4f(0x000013ff);
    private Vector4f SliderGrabActive = hexToVec4f(0x000014ff);
    private Vector4f Button = hexToVec4f(0x00000000);
    private Vector4f ButtonHovered = hexToVec4f(0x0064FF64);
    private Vector4f ButtonActive = hexToVec4f(0x000017ff);
    /**
     * Header* colors are used for CollapsingHeader, TreeNode, Selectable, MenuItem
     */
    private Vector4f Header = hexToVec4f(0x2f2f2fff);
    private Vector4f HeaderHovered = hexToVec4f(0x2f2f2fff);
    private Vector4f HeaderActive = hexToVec4f(0x2f2f2fff);
    private Vector4f Separator = hexToVec4f(0x151515ff);
    private Vector4f SeparatorHovered = hexToVec4f(0x404040ff);
    private Vector4f SeparatorActive = hexToVec4f(0x404040ff);
    private Vector4f ResizeGrip = hexToVec4f(0x00001eff);
    private Vector4f ResizeGripHovered = hexToVec4f(0x00001fff);
    private Vector4f ResizeGripActive = hexToVec4f(0x000020ff);
    private Vector4f Tab = hexToVec4f(0x00000000);
    private Vector4f TabHovered = hexToVec4f(0x242424ff);
    private Vector4f TabActive = hexToVec4f(0x242424ff);
    private Vector4f TabUnfocused = hexToVec4f(0x242424ff);
    private Vector4f TabUnfocusedActive = hexToVec4f(0x242424ff);
    /**
     * Preview overlay color when about to docking something
     */
    private Vector4f DockingPreview = hexToVec4f(0xFDFDFF76);
    /**
     * Background color for empty node (e.g. CentralNode with no window docked Vector4fo it)
     */
    private Vector4f DockingEmptyBg = hexToVec4f(0x00000000);
    private Vector4f PlotLines = hexToVec4f(0x000028ff);
    private Vector4f PlotLinesHovered = hexToVec4f(0x000029ff);
    private Vector4f PlotHistogram = hexToVec4f(0x00002aff);
    private Vector4f PlotHistogramHovered = hexToVec4f(0x00002bff);
    /**
     * Table header background
     */
    private Vector4f TableHeaderBg = hexToVec4f(0x00002cff);
    /**
     * Table outer and header borders (prefer using Alpha= hexToVec4f(1.0 here)
     */
    private Vector4f TableBorderStrong = hexToVec4f(0xFFFFFF1E);
    /**
     * Table inner borders (prefer using Alpha= hexToVec4f(1.0 here)
     */
    private Vector4f TableBorderLight = hexToVec4f(0xFFFFFF1E);
    /**
     * Table row background (even rows)
     */
    private Vector4f TableRowBg = hexToVec4f(0x00002fff);
    /**
     * Table row background (odd rows)
     */
    private Vector4f TableRowBgAlt = hexToVec4f(0x000030ff);
    private Vector4f TextSelectedBg = hexToVec4f(0x000031ff);
    private Vector4f DragDropTarget = hexToVec4f(0x000032ff);
    /**
     * Gamepad/keyboard: current highlighted item
     */
    private Vector4f NavHighlight = hexToVec4f(0x000033ff);
    /**
     * Highlight window when using CTRL+TAB
     */
    private Vector4f NavWindowingHighlight = hexToVec4f(0x000034ff);
    /**
     * Darken/colorize entire screen behind the CTRL+TAB window list, when active
     */
    private Vector4f NavWindowingDimBg = hexToVec4f(0x000035ff);
    /**
     * Darken/colorize entire screen behind a modal window, when one is active
     */
    private Vector4f ModalWindowDimBg = hexToVec4f(0x000036ff);


    //!=========================================================================
    //!~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //!-------------------------------------------------------------------------
    //!~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //!=========================================================================


    public void style() {
      if (!active) return;

      // Style
      imgui.ImGuiStyle style = ImGui.getStyle();
      style.setFrameRounding(this.frameRounding);
      style.setChildBorderSize(this.childBorderSize);
      style.setAlpha(this.alpha);
      style.setAntiAliasedFill(this.antiAliasedFill);
      style.setAntiAliasedLines(this.antiAliasedLines);
      style.setAntiAliasedLinesUseTex(this.antiAliasedLinesUseTex);
      style.setButtonTextAlign(this.buttonTextAlign.x, this.buttonTextAlign.y);
      style.setCellPadding(this.cellPadding.x, this.cellPadding.y);
      style.setChildBorderSize(this.childBorderSize);
      style.setChildRounding(this.childRounding);
//    style.setCircleSegmentMaxError(this.circleSegmentMaxError);
      style.setColorButtonPosition(this.colorButtonPosition);
      style.setColumnsMinSpacing(this.columnsMinSpacing);
      style.setCurveTessellationTol(this.curveTessellationTol);
      style.setDisplaySafeAreaPadding(this.displaySafeAreaPadding.x, this.displaySafeAreaPadding.y);
      style.setDisplayWindowPadding(this.displayWindowPadding.x, this.displayWindowPadding.y);
      style.setFrameBorderSize(this.frameBorderSize);
      style.setFramePadding(this.framePadding.x, this.framePadding.y);
      style.setGrabMinSize(this.grabMinSize);
      style.setGrabRounding(this.grabRounding);
      style.setIndentSpacing(this.indentSpacing);
      style.setItemInnerSpacing(this.itemInnerSpacing.x, this.itemInnerSpacing.y);
      style.setItemSpacing(this.itemSpacing.x, this.itemSpacing.y);
      style.setLogSliderDeadzone(this.logSliderDeadzone);
      style.setMouseCursorScale(this.mouseCursorScale);
      style.setPopupBorderSize(this.popupBorderSize);
      style.setPopupRounding(this.popupRounding);
      style.setScrollbarRounding(this.scrollbarRounding);
      style.setScrollbarSize(this.scrollbarSize);
      style.setSelectableTextAlign(this.selectableTextAlign.x, this.selectableTextAlign.y);
      style.setTabBorderSize(this.tabBorderSize);
      style.setTabMinWidthForCloseButton(this.tabMinWidthForCloseButton);
      style.setTabRounding(this.tabRounding);
      style.setTouchExtraPadding(this.touchExtraPadding.x, this.touchExtraPadding.y);
      style.setWindowBorderSize(this.windowBorderSize);
      style.setWindowMenuButtonPosition(this.windowMenuButtonPosition);
      style.setWindowMinSize(this.windowMinSize.x, this.windowMinSize.y);
      style.setWindowPadding(this.windowPadding.x, this.windowPadding.y);
      style.setWindowRounding(this.windowRounding);
      style.setWindowTitleAlign(this.windowTitleAlign.x, this.windowTitleAlign.y);

      // Colors
      style.setColor(ImGuiCol.Text, this.Text.x, this.Text.y, this.Text.z, this.Text.w);

      style.setColor(ImGuiCol.TextDisabled, this.TextDisabled.x, this.TextDisabled.y, this.TextDisabled.z, this.TextDisabled.w);
      style.setColor(ImGuiCol.WindowBg, this.WindowBg.x, this.WindowBg.y, this.WindowBg.z, this.WindowBg.w);
      style.setColor(ImGuiCol.ChildBg, this.ChildBg.x, this.ChildBg.y, this.ChildBg.z, this.ChildBg.w);
      style.setColor(ImGuiCol.PopupBg, this.PopupBg.x, this.PopupBg.y, this.PopupBg.z, this.PopupBg.w);
      style.setColor(ImGuiCol.Border, this.Border.x, this.Border.y, this.Border.z, this.Border.w);
      style.setColor(ImGuiCol.BorderShadow, this.BorderShadow.x, this.BorderShadow.y, this.BorderShadow.z, this.BorderShadow.w);
      style.setColor(ImGuiCol.FrameBg, this.FrameBg.x, this.FrameBg.y, this.FrameBg.z, this.FrameBg.w);
      style.setColor(ImGuiCol.FrameBgHovered, this.FrameBgHovered.x, this.FrameBgHovered.y, this.FrameBgHovered.z, this.FrameBgHovered.w);
      style.setColor(ImGuiCol.FrameBgActive, this.FrameBgActive.x, this.FrameBgActive.y, this.FrameBgActive.z, this.FrameBgActive.w);
      style.setColor(ImGuiCol.TitleBg, this.TitleBg.x, this.TitleBg.y, this.TitleBg.z, this.TitleBg.w);
      style.setColor(ImGuiCol.TitleBgActive, this.TitleBgActive.x, this.TitleBgActive.y, this.TitleBgActive.z, this.TitleBgActive.w);
      style.setColor(ImGuiCol.TitleBgCollapsed, this.TitleBgCollapsed.x, this.TitleBgCollapsed.y, this.TitleBgCollapsed.z, this.TitleBgCollapsed.w);
      style.setColor(ImGuiCol.MenuBarBg, this.MenuBarBg.x, this.MenuBarBg.y, this.MenuBarBg.z, this.MenuBarBg.w);
      style.setColor(ImGuiCol.ScrollbarBg, this.ScrollbarBg.x, this.ScrollbarBg.y, this.ScrollbarBg.z, this.ScrollbarBg.w);
      style.setColor(ImGuiCol.ScrollbarGrab, this.ScrollbarGrab.x, this.ScrollbarGrab.y, this.ScrollbarGrab.z, this.ScrollbarGrab.w);
      style.setColor(ImGuiCol.ScrollbarGrabHovered, this.ScrollbarGrabHovered.x, this.ScrollbarGrabHovered.y, this.ScrollbarGrabHovered.z, this.ScrollbarGrabHovered.w);
      style.setColor(ImGuiCol.ScrollbarGrabActive, this.ScrollbarGrabActive.x, this.ScrollbarGrabActive.y, this.ScrollbarGrabActive.z, this.ScrollbarGrabActive.w);
      style.setColor(ImGuiCol.CheckMark, this.CheckMark.x, this.CheckMark.y, this.CheckMark.z, this.CheckMark.w);
      style.setColor(ImGuiCol.SliderGrab, this.SliderGrab.x, this.SliderGrab.y, this.SliderGrab.z, this.SliderGrab.w);
      style.setColor(ImGuiCol.SliderGrabActive, this.SliderGrabActive.x, this.SliderGrabActive.y, this.SliderGrabActive.z, this.SliderGrabActive.w);
      style.setColor(ImGuiCol.Button, this.Button.x, this.Button.y, this.Button.z, this.Button.w);
      style.setColor(ImGuiCol.ButtonHovered, this.ButtonHovered.x, this.ButtonHovered.y, this.ButtonHovered.z, this.ButtonHovered.w);
      style.setColor(ImGuiCol.ButtonActive, this.ButtonActive.x, this.ButtonActive.y, this.ButtonActive.z, this.ButtonActive.w);
      style.setColor(ImGuiCol.Header, this.Header.x, this.Header.y, this.Header.z, this.Header.w);
      style.setColor(ImGuiCol.HeaderHovered, this.HeaderHovered.x, this.HeaderHovered.y, this.HeaderHovered.z, this.HeaderHovered.w);
      style.setColor(ImGuiCol.HeaderActive, this.HeaderActive.x, this.HeaderActive.y, this.HeaderActive.z, this.HeaderActive.w);
      style.setColor(ImGuiCol.Separator, this.Separator.x, this.Separator.y, this.Separator.z, this.Separator.w);
      style.setColor(ImGuiCol.SeparatorHovered, this.SeparatorHovered.x, this.SeparatorHovered.y, this.SeparatorHovered.z, this.SeparatorHovered.w);
      style.setColor(ImGuiCol.SeparatorActive, this.SeparatorActive.x, this.SeparatorActive.y, this.SeparatorActive.z, this.SeparatorActive.w);
      style.setColor(ImGuiCol.ResizeGrip, this.ResizeGrip.x, this.ResizeGrip.y, this.ResizeGrip.z, this.ResizeGrip.w);
      style.setColor(ImGuiCol.ResizeGripHovered, this.ResizeGripHovered.x, this.ResizeGripHovered.y, this.ResizeGripHovered.z, this.ResizeGripHovered.w);
      style.setColor(ImGuiCol.ResizeGripActive, this.ResizeGripActive.x, this.ResizeGripActive.y, this.ResizeGripActive.z, this.ResizeGripActive.w);
      style.setColor(ImGuiCol.Tab, this.Tab.x, this.Tab.y, this.Tab.z, this.Tab.w);
      style.setColor(ImGuiCol.TabHovered, this.TabHovered.x, this.TabHovered.y, this.TabHovered.z, this.TabHovered.w);
      style.setColor(ImGuiCol.TabActive, this.TabActive.x, this.TabActive.y, this.TabActive.z, this.TabActive.w);
      style.setColor(ImGuiCol.TabUnfocused, this.TabUnfocused.x, this.TabUnfocused.y, this.TabUnfocused.z, this.TabUnfocused.w);
      style.setColor(ImGuiCol.TabUnfocusedActive, this.TabUnfocusedActive.x, this.TabUnfocusedActive.y, this.TabUnfocusedActive.z, this.TabUnfocusedActive.w);
      style.setColor(ImGuiCol.DockingPreview, this.DockingPreview.x, this.DockingPreview.y, this.DockingPreview.z, this.DockingPreview.w);
      style.setColor(ImGuiCol.DockingEmptyBg, this.DockingEmptyBg.x, this.DockingEmptyBg.y, this.DockingEmptyBg.z, this.DockingEmptyBg.w);
      style.setColor(ImGuiCol.PlotLines, this.PlotLines.x, this.PlotLines.y, this.PlotLines.z, this.PlotLines.w);
      style.setColor(ImGuiCol.PlotLinesHovered, this.PlotLinesHovered.x, this.PlotLinesHovered.y, this.PlotLinesHovered.z, this.PlotLinesHovered.w);
      style.setColor(ImGuiCol.PlotHistogram, this.PlotHistogram.x, this.PlotHistogram.y, this.PlotHistogram.z, this.PlotHistogram.w);
      style.setColor(ImGuiCol.PlotHistogramHovered, this.PlotHistogramHovered.x, this.PlotHistogramHovered.y, this.PlotHistogramHovered.z, this.PlotHistogramHovered.w);
      style.setColor(ImGuiCol.TableHeaderBg, this.TableHeaderBg.x, this.TableHeaderBg.y, this.TableHeaderBg.z, this.TableHeaderBg.w);
      style.setColor(ImGuiCol.TableBorderStrong, this.TableBorderStrong.x, this.TableBorderStrong.y, this.TableBorderStrong.z, this.TableBorderStrong.w);
      style.setColor(ImGuiCol.TableBorderLight, this.TableBorderLight.x, this.TableBorderLight.y, this.TableBorderLight.z, this.TableBorderLight.w);
      style.setColor(ImGuiCol.TableRowBg, this.TableRowBg.x, this.TableRowBg.y, this.TableRowBg.z, this.TableRowBg.w);
      style.setColor(ImGuiCol.TableRowBgAlt, this.TableRowBgAlt.x, this.TableRowBgAlt.y, this.TableRowBgAlt.z, this.TableRowBgAlt.w);
      style.setColor(ImGuiCol.TextSelectedBg, this.TextSelectedBg.x, this.TextSelectedBg.y, this.TextSelectedBg.z, this.TextSelectedBg.w);
      style.setColor(ImGuiCol.DragDropTarget, this.DragDropTarget.x, this.DragDropTarget.y, this.DragDropTarget.z, this.DragDropTarget.w);
      style.setColor(ImGuiCol.NavHighlight, this.NavHighlight.x, this.NavHighlight.y, this.NavHighlight.z, this.NavHighlight.w);
      style.setColor(ImGuiCol.NavWindowingHighlight, this.NavWindowingHighlight.x, this.NavWindowingHighlight.y, this.NavWindowingHighlight.z, this.NavWindowingHighlight.w);
      style.setColor(ImGuiCol.NavWindowingDimBg, this.NavWindowingDimBg.x, this.NavWindowingDimBg.y, this.NavWindowingDimBg.z, this.NavWindowingDimBg.w);
      style.setColor(ImGuiCol.ModalWindowDimBg, this.ModalWindowDimBg.x, this.ModalWindowDimBg.y, this.ModalWindowDimBg.z, this.ModalWindowDimBg.w);

    }

    public Vector2f getWindowPadding() {
      return new Vector2f(this.windowPadding);
    }
  }

  public class Project {
    public static String EnginePath = "src/main/java/Burst/Engine/";

  }

  public class Shader {
    public static final String PATH = "Assets/Shaders/";

    /**
     * Default Shader - Contains Fragment and Vertex Shader
     */
    public static final String SHADER_DEFAULT = PATH + "default.glsl";

    /**
     * Picking Shader - Used to pick Pixel on Screen
     */
    public static final String SHADER_PICKING = PATH + "pickingShader.glsl";

    /**
     * DebugDraw Shader - Used to draw Debug Shapes
     */
    public static final String SHADER_DEBUG = PATH + "debugLine2D.glsl";

  }
}
