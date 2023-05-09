package Burst.Engine.Source.Core.Scene;

import Burst.Engine.Source.Core.Game.Editor;
import Burst.Engine.Source.Core.Scene.Initializer.*;
import Burst.Engine.Source.Core.UI.Viewport;
import Burst.Engine.Source.Core.Graphics.Render.ViewportRenderer;
import Burst.Engine.Source.Core.UI.ImGuiPanel;
import Burst.Engine.Source.Core.util.DebugMessage;
import Burst.Engine.Source.Core.Game.Game;

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
                this.game = new Game(this);
                this.sceneInitializer = new GameInitializer(this);
                this.game.init();
            }

            case EDITOR -> {
                this.editor = new Editor(this);
                this.sceneInitializer = new EditorInitializer(this);
                this.editor.init();
            }

            case MENU -> {
                this.sceneInitializer = new MenuInitializer(this);
            }

            case START_MENU -> {
                this.sceneInitializer = new StartMenuInitializer(this);
            }

            case SETTINGS_MENU -> {
                this.sceneInitializer = new SettingsMenuInitializer(this);
            }

            default -> {
                this.sceneInitializer = new SceneInitializer(this);
            }
        }

        this.sceneInitializer.init();
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
        this.sceneInitializer.imgui();

        if (this.game != null && this.openScene == SceneType.GAME) {
            this.game.imgui();
        }

        if (this.editor != null && this.openScene == SceneType.EDITOR) {
            this.editor.imgui();
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
    public Viewport getViewport() {
        return this.viewport;
    }
    public SceneInitializer getSceneInitializer() {
        return this.sceneInitializer;
    }

    public SceneType getOpenScene() {
        return this.openScene;
    }

    public ViewportRenderer getViewportRenderer() {
        return this.viewportRenderer;
    }

    public Game getGame() {
        if (this.game == null) {
            return this.editor;
        }
        return this.game;
    }
    public Editor getEditor() {
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
}
