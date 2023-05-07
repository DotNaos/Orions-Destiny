package Burst.Engine.Source.Core.Scene;

import Burst.Engine.Source.Core.Viewport;
import Burst.Engine.Source.Core.Graphics.Render.ViewportRenderer;
import Burst.Engine.Source.Core.UI.ImGuiPanel;
import Burst.Engine.Source.Runtime.Game;

import java.util.ArrayList;
import java.util.List;

public class Scene {
    private SceneInitializer sceneInitializer;
    private List<ImGuiPanel> panels = new ArrayList<>();
    private Viewport viewport;
    private final ViewportRenderer viewportRenderer;
    private boolean isPaused = false;
    private SceneType openScene = SceneType.NONE;
    private Editor editor;
    private Game game;

    public Scene(SceneType sceneType) {
        this.viewport = new Viewport();
        this.viewportRenderer = new ViewportRenderer();
        this.openScene = sceneType;
        switch (openScene)
        {
            case GAME -> {
                this.game = new Game();
                this.sceneInitializer = new GameInitializer();
            }

            case EDITOR -> {
                this.editor = new Editor();
                this.sceneInitializer = new EditorInitializer();
            }

            case MENU -> {
                this.sceneInitializer = new MenuInitializer();
            }

            case START_MENU -> {
                this.sceneInitializer = new StartMenuInitializer();
            }

            case SETTINGS_MENU -> {
                this.sceneInitializer = new SettingsMenuInitializer();
            }

            default -> {
                this.sceneInitializer = new SceneInitializer();
            }
        }
    }




    public void update(float dt) {
        switch (openScene) {
            case GAME -> {
                if (!isPaused) {
                    this.viewportRenderer.render();
                    this.game.update(dt);
                }
            }
            case EDITOR -> {
                this.viewportRenderer.render();
                this.editor.update(dt);
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
    }

    public void render() {
        this.viewportRenderer.render();
    }

    //====================================================================================================
    // All Mouse and Keyboard callbacks
    //====================================================================================================

    public void keyCallback( long window, int key, int scancode, int action, int mods){}
    public void charCallback ( long window, int codepoint){}
    public void mouseButtonCallback( long window, int button, int action, int mods){}
    public void scrollCallback( long window, double xOffset, double yOffset){}
    public void mousePositionCallback(long window, double xpos, double ypos){}



    //====================================================================================================
    // All getters and setters
    //====================================================================================================
    public Viewport getCamera() {
        return this.viewport;
    }
    public SceneInitializer getSceneInitializer() {
        return this.sceneInitializer;
    }

    public ViewportRenderer getViewportRenderer() {
        return this.viewportRenderer;
    }

    public Game getGame() {
        return this.game;
    }
    public Editor getEditor() {
        return this.editor;
    }

    public <T extends ImGuiPanel> void addPanel(T panel) {
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
}
