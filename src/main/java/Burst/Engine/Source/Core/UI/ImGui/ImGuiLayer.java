package Burst.Engine.Source.Core.UI.ImGui;

import Burst.Engine.Config.Config;
import Burst.Engine.Config.Config.Fonts;
import Burst.Engine.Config.Config.ImGuiStyle;
import Burst.Engine.Source.Core.Input.KeyListener;
import Burst.Engine.Source.Core.Input.MouseListener;
import Burst.Engine.Source.Core.Scene.Scene;
import Burst.Engine.Source.Core.UI.Window;
import Burst.Engine.Source.Editor.Panel.ViewportPanel;
import imgui.*;
import imgui.callback.ImStrConsumer;
import imgui.callback.ImStrSupplier;
import imgui.flag.ImGuiConfigFlags;
import imgui.flag.ImGuiStyleVar;
import imgui.flag.ImGuiWindowFlags;
import imgui.gl3.ImGuiImplGl3;
import imgui.glfw.ImGuiImplGlfw;
import imgui.type.ImBoolean;

import java.io.File;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER;
import static org.lwjgl.opengl.GL30.glBindFramebuffer;
/**
 * @author Spair Official ImGUi Java Binding
 */
public class ImGuiLayer {

  // LWJGL3 renderer (SHOULD be initialized)
  private final ImGuiImplGl3 imGuiGl3 = new ImGuiImplGl3();
  private final ImGuiImplGlfw imGuiGlfw = new ImGuiImplGlfw();

  private long glfwWindow;


  public ImGuiLayer(long glfwWindow) {
    this.glfwWindow = glfwWindow;
  }


  // Initialize Dear ImGui.
  public void initImGui() {
    // IMPORTANT!!
    // This line is critical for Dear ImGui to work.
    ImGui.createContext();

    // ------------------------------------------------------------
    // Initialize ImGuiIO config
    final ImGuiIO io = ImGui.getIO();

    io.setIniFilename("imgui.ini"); // We don't want to save .ini file
    io.addConfigFlags(ImGuiConfigFlags.DockingEnable);
    io.addConfigFlags(ImGuiConfigFlags.ViewportsEnable);
    io.setBackendPlatformName("imgui_java_impl_glfw");

    // ------------------------------------------------------------
    // GLFW callbacks to handle user input

    glfwSetKeyCallback(glfwWindow, (w, key, scancode, action, mods) -> {
      if (action == GLFW_PRESS) {
        io.setKeysDown(key, true);
      } else if (action == GLFW_RELEASE) {
        io.setKeysDown(key, false);
      }

      io.setKeyCtrl(io.getKeysDown(GLFW_KEY_LEFT_CONTROL) || io.getKeysDown(GLFW_KEY_RIGHT_CONTROL));
      io.setKeyShift(io.getKeysDown(GLFW_KEY_LEFT_SHIFT) || io.getKeysDown(GLFW_KEY_RIGHT_SHIFT));
      io.setKeyAlt(io.getKeysDown(GLFW_KEY_LEFT_ALT) || io.getKeysDown(GLFW_KEY_RIGHT_ALT));
      io.setKeySuper(io.getKeysDown(GLFW_KEY_LEFT_SUPER) || io.getKeysDown(GLFW_KEY_RIGHT_SUPER));

      if (!io.getWantCaptureKeyboard()) {
        KeyListener.keyCallback(w, key, scancode, action, mods);
      }

      Window.getScene().keyCallback(w, key, scancode, action, mods);
    });

    glfwSetCharCallback(glfwWindow, (w, c) -> {
      if (c != GLFW_KEY_DELETE) {
        io.addInputCharacter(c);
      }
      Window.getScene().charCallback(w, c);
    });

    glfwSetMouseButtonCallback(glfwWindow, (w, button, action, mods) -> {
      final boolean[] mouseDown = new boolean[5];

      mouseDown[0] = button == GLFW_MOUSE_BUTTON_1 && action != GLFW_RELEASE;
      mouseDown[1] = button == GLFW_MOUSE_BUTTON_2 && action != GLFW_RELEASE;
      mouseDown[2] = button == GLFW_MOUSE_BUTTON_3 && action != GLFW_RELEASE;
      mouseDown[3] = button == GLFW_MOUSE_BUTTON_4 && action != GLFW_RELEASE;
      mouseDown[4] = button == GLFW_MOUSE_BUTTON_5 && action != GLFW_RELEASE;

      io.setMouseDown(mouseDown);

      if (!io.getWantCaptureMouse() && mouseDown[1]) {
        ImGui.setWindowFocus(null);
      }

      Window.getScene().mouseButtonCallback(w, button, action, mods);
    });

    glfwSetScrollCallback(glfwWindow, (w, xOffset, yOffset) -> {
      io.setMouseWheelH(io.getMouseWheelH() + (float) xOffset);
      io.setMouseWheel(io.getMouseWheel() + (float) yOffset);
      if (Window.getScene().getPanel(ViewportPanel.class) != null) {
        if (!io.getWantCaptureMouse() || Window.getScene().getPanel(ViewportPanel.class).getWantCaptureMouse()) {
          MouseListener.mouseScrollCallback(w, xOffset, yOffset);
        } else {
          MouseListener.clear();
        }
      }
      Window.getScene().mouseScrollCallback(w, xOffset, yOffset);
    });

    io.setSetClipboardTextFn(new ImStrConsumer() {
      @Override
      public void accept(final String s) {
        glfwSetClipboardString(glfwWindow, s);
      }
    });

    io.setGetClipboardTextFn(new ImStrSupplier() {
      @Override
      public String get() {
        final String clipboardString = glfwGetClipboardString(glfwWindow);
        if (clipboardString != null) {
          return clipboardString;
        } else {
          return "";
        }
      }
    });

    // ------------------------------------------------------------
    // Fonts configuration
    // Read: https://raw.githubusercontent.com/ocornut/imgui/master/docs/FONTS.txt


    if (new File(Fonts.UI).isFile()) {
      final ImFontAtlas fontAtlas = io.getFonts();
      final ImFontConfig fontConfig = new ImFontConfig(); // Natively allocated object, should be explicitly destroyed

      // Glyphs could be added per-font as well as per config used globally like here
      fontConfig.setGlyphRanges(fontAtlas.getGlyphRangesDefault());

      // Fonts merge example
      fontConfig.setPixelSnapH(true);
      fontAtlas.addFontFromFileTTF(Fonts.UI, 18, fontConfig);
      fontConfig.destroy(); // After all fonts were added we don't need this config more
    } else if (new File("C:/Windows/Fonts/seactoreui.ttf").isFile()) {
      final ImFontAtlas fontAtlas = io.getFonts();
      final ImFontConfig fontConfig = new ImFontConfig(); // Natively allocated object, should be explicitly destroyed

      // Glyphs could be added per-font as well as per config used globally like here
      fontConfig.setGlyphRanges(fontAtlas.getGlyphRangesDefault());

      // Fonts merge example
      fontConfig.setPixelSnapH(true);
      fontAtlas.addFontFromFileTTF("C:/Windows/Fonts/seactoreui.ttf", 32, fontConfig);
      fontConfig.destroy(); // After all fonts were added we don't need this config more
    } else if (new File("C:/Windows/Fonts/Cour.ttf").isFile()) {
      // Fallback font

      final ImFontAtlas fontAtlas = io.getFonts();
      final ImFontConfig fontConfig = new ImFontConfig(); // Natively allocated object, should be explicitly destroyed

      // Glyphs could be added per-font as well as per config used globally like here
      fontConfig.setGlyphRanges(fontAtlas.getGlyphRangesDefault());

      // Fonts merge example
      fontConfig.setPixelSnapH(true);
      fontAtlas.addFontFromFileTTF("C:/Windows/Fonts/Cour.ttf", 32, fontConfig);
      fontConfig.destroy(); // After all fonts were added we don't need this config more
    }


    // Method initializes LWJGL3 renderer.
    // This method SHOULD be called after you've initialized your ImGui configuration (fonts and so on).
    // ImGui context should be created as well.
    imGuiGlfw.init(glfwWindow, false);
    imGuiGl3.init("#version 330 core");
  }


  /**
   * IMPORTANT!! ImGui code MUST be called between ImGui.newFrame()/ImGui.render() methods
   */
  public void update(float dt, Scene currentScene) {
    startFrame();
    Config.get(ImGuiStyle.class).style();
    setupDockspace();
    currentScene.imgui();

//        ImGui.showDemoWindow();


    DebugPanel.imgui();
    endFrame();
  }

  private void startFrame() {
    imGuiGlfw.newFrame();
    ImGui.newFrame();
  }

  private void endFrame() {
    glBindFramebuffer(GL_FRAMEBUFFER, 0);
    glViewport(0, 0, Window.getWidth(), Window.getHeight());
    glClearColor(0, 0, 0, 1);
    glClear(GL_COLOR_BUFFER_BIT);

    // After Dear ImGui prepared a draw data, we use it in the LWJGL3 renderer.
    // At that moment ImGui will be rendered to the current OpenGL context.
    // Flip because imgui has flipped y coordinates
    ImGui.render();
    imGuiGl3.renderDrawData(ImGui.getDrawData());

    long backupWindowPtr = glfwGetCurrentContext();
    ImGui.updatePlatformWindows();
    ImGui.renderPlatformWindowsDefault();
    glfwMakeContextCurrent(backupWindowPtr);
  }

  // If you want a clean room after yourself - do it by yourself
  private void destroyImGui() {
    imGuiGl3.dispose();
    ImGui.destroyContext();
  }

  private void setupDockspace() {
    int windowFlags = ImGuiWindowFlags.MenuBar | ImGuiWindowFlags.NoDocking;

    ImGuiViewport mainViewport = ImGui.getMainViewport();
    ImGui.setNextWindowPos(mainViewport.getWorkPosX(), mainViewport.getWorkPosY());
    ImGui.setNextWindowSize(mainViewport.getWorkSizeX(), mainViewport.getWorkSizeY());
    ImGui.setNextWindowViewport(mainViewport.getID());
    ImGui.setNextWindowPos(0.0f, 0.0f);
    ImGui.setNextWindowSize(Window.getWidth(), Window.getHeight());
    ImGui.pushStyleVar(ImGuiStyleVar.WindowRounding, 0.0f);
    ImGui.pushStyleVar(ImGuiStyleVar.WindowBorderSize, 0.0f);

    windowFlags |= ImGuiWindowFlags.NoTitleBar | ImGuiWindowFlags.NoCollapse | ImGuiWindowFlags.NoResize | ImGuiWindowFlags.NoMove | ImGuiWindowFlags.NoBringToFrontOnFocus | ImGuiWindowFlags.NoNavFocus;

    ImGui.begin("Dockspace Demo", new ImBoolean(true), windowFlags);
    ImGui.popStyleVar(2);

    // Dockspace
    ImGui.dockSpace(ImGui.getID("Dockspace"));

    ImGui.end();
  }

}
