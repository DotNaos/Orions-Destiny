package Burst.Engine.Source.Core.Scene;

import Burst.Engine.Source.Core.Input.MouseListener;
import Burst.Engine.Source.Core.Scene.Initializer.*;
import Burst.Engine.Source.Core.UI.ImGui.ImGuiPanel;
import Burst.Engine.Source.Core.UI.Viewport;
import Burst.Engine.Source.Core.Util.DebugMessage;
import Burst.Engine.Source.Editor.Panel.ViewportPanel;
import Burst.Engine.Source.Game.Game;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Oliver Schuetz
 * The Scene class represents a scene in the game or editor.
 * It manages the initialization, updating, rendering, and user input callbacks for the scene.
 */
public class Scene {
  private SceneInitializer sceneInitializer;
  private List<ImGuiPanel> panels = new ArrayList<>();
  private SceneType openScene;

  private Game game;

  private Viewport viewport;

  public Scene() {

  }

  public void init(SceneType sceneType) {
    this.viewport = new Viewport();
    this.openScene = sceneType;

    switch (sceneType) {
      case GAME -> {
        this.game = new Game(this);
        this.sceneInitializer = new GameInitializer(this);
        this.game.init();
      }
      case EDITOR -> {
        this.game = new Game(this);
        this.sceneInitializer = new EditorInitializer(this);
        this.game.init();
      }

      case SETTINGS_MENU -> {
        this.sceneInitializer = new SettingsMenuInitializer(this);
      }

      default -> {
        this.sceneInitializer = new StartMenuInitializer(this);
      }
    }

    this.sceneInitializer.init();

  }


  public void update(float dt) {
    switch (openScene) {
      case GAME -> {
        this.game.update(dt);
      }
      case EDITOR -> {
        this.game.updateEditor(dt);
      }
    }
  }

  public <T extends ImGuiPanel> T getPanel(Class<T> type) {
    for (ImGuiPanel panel : panels) {
      if (panel.getClass() == type) {
        return (T) panel;
      }
    }
    return null;
  }

  public void imgui() {
    for (ImGuiPanel panel : panels) {
      panel.imgui();
    }
    this.sceneInitializer.imgui();

    switch (openScene) {
      case GAME, EDITOR -> {
        this.game.imgui();
      }
    }
  }

  public void render() {
    switch (openScene) {
      case GAME, EDITOR -> {
        this.game.render();
      }
    }
  }


  //!====================================================================================================
  //! All getters and setters
  //!====================================================================================================

  public SceneInitializer getSceneInitializer() {
    return this.sceneInitializer;
  }

  public SceneType getOpenScene() {
    return this.openScene;
  }

  public Game getGame() {
    return this.game;
  }


  public void addPanel(ImGuiPanel panel) {
    DebugMessage.info("Adding panel: " + panel.getClass().getSimpleName());
    this.panels.add(panel);
  }

  public void destroy() {
  }

  public Viewport getViewport() {
    return this.viewport;
  }

  public ViewportPanel getViewportPanel() {
    return this.getPanel(ViewportPanel.class);
  }

  //!====================================================================================================
  //! All Mouse and Keyboard callbacks
  //!====================================================================================================

  public void mousePositionCallback(long window, double xpos, double ypos) {
    ViewportPanel viewportPanel = getViewportPanel();
    if (viewportPanel == null) return;
    if (viewportPanel.getWantCaptureMouse()) return;
    MouseListener.clear();
  }

  public void mouseButtonCallback(long window, int button, int action, int mods) {
    ViewportPanel viewportPanel = getViewportPanel();
    if (viewportPanel == null) return;
    if (!viewportPanel.getWantCaptureMouse()) return;
    MouseListener.mouseButtonCallback(window, button, action, mods);
  }

  public void mouseScrollCallback(long w, double xOffset, double yOffset) {
    // TODO: Implement mouse scroll callback
  }

  public void keyCallback(long w, int key, int scancode, int action, int mods) {
    // TODO: Implement key callback
  }

  public void charCallback(long w, int c) {
    // TODO: Implement char callback
  }

}
