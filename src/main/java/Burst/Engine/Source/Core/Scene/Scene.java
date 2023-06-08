package Burst.Engine.Source.Core.Scene;

import Burst.Engine.Source.Game.Game;
import Burst.Engine.Source.Core.Input.MouseListener;
import Burst.Engine.Source.Core.Scene.Initializer.*;
import Burst.Engine.Source.Core.UI.ImGui.ImGuiPanel;
import Burst.Engine.Source.Core.UI.Viewport;
import Burst.Engine.Source.Core.Util.DebugMessage;
import Burst.Engine.Source.Editor.Editor;
import Burst.Engine.Source.Editor.Panel.ViewportPanel;

import java.util.ArrayList;
import java.util.List;

public class Scene {
    private SceneInitializer sceneInitializer;
    private List<ImGuiPanel> panels = new ArrayList<>();
    private boolean isPaused = false;
    private SceneType openScene;
    private Editor editor;
//    private Game game;
    private Viewport viewport;

    public Scene() {

    }

    public void init(SceneType sceneType) {
        this.viewport = new Viewport();
        this.openScene = sceneType;
        switch (sceneType) {
            case GAME, EDITOR -> {
//                this.game = new Game(this);
//                this.sceneInitializer = new GameInitializer(this);
//                this.game.init();

                this.editor = new Editor(this);
                this.sceneInitializer = new EditorInitializer(this);
                this.editor.init();
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
            case GAME, EDITOR -> {
                if (!isPaused) {
                    this.editor.update(dt);
                }
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
                this.editor.imgui();
            }
        }
    }

    public void render() {
        switch (openScene) {
            case GAME, EDITOR -> {
                this.editor.render();
            }
        }
    }

    //!====================================================================================================
    //! All Mouse and Keyboard callbacks
    //!====================================================================================================

    public void keyCallback(long window, int key, int scancode, int action, int mods) {
    }

    public void charCallback(long window, int codepoint) {
    }

    public void mouseButtonCallback(long window, int button, int action, int mods) {
        ViewportPanel viewportPanel = getPanel(ViewportPanel.class);
        if (viewportPanel == null) return;
        if (!viewportPanel.getWantCaptureMouse()) return;
        MouseListener.mouseButtonCallback(window, button, action, mods);
    }


    public void mousePositionCallback(long window, double xpos, double ypos) {
        ViewportPanel viewportPanel = getPanel(ViewportPanel.class);
        if (viewportPanel == null) return;
        if (viewportPanel.getWantCaptureMouse()) return;
        MouseListener.clear();
    }

    public void scrollCallback(long window, double xOffset, double yOffset) {
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

    public Game getEditor() {
        return this.editor;
    }


    public void addPanel(ImGuiPanel panel) {
        DebugMessage.info("Adding panel: " + panel.getClass().getSimpleName());
        this.panels.add(panel);
    }

    public boolean isPaused() {
        return isPaused;
    }

    public boolean isRunning() {
        return !isPaused;
    }

    public void destroy() {
    }

    public Viewport getViewport() {
        return this.viewport;
    }
}
