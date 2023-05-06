package Burst.Engine.Source.Core.Scene;

import Burst.Engine.Source.Core.Camera;
import Burst.Engine.Source.Core.Graphics.Render.ViewportRenderer;
import Burst.Engine.Source.Core.UI.ImGuiPanel;
import Burst.Engine.Source.Editor.Panel.ViewportPanel;
import Burst.Engine.Source.Runtime.Game;

import java.util.ArrayList;
import java.util.List;

public class Scene {
    private SceneInitializer sceneInitializer;
    private List<ImGuiPanel> panels = new ArrayList<>();
    private Camera camera;
    private final ViewportRenderer viewportRenderer;
    private boolean isPaused = false;
    private Scenes openScene = Scenes.NONE;
    private Editor editor;
    private Game game;

    public Scene(SceneInitializer sceneInitializer) {
        this.camera = new Camera();
        this.viewportRenderer = new ViewportRenderer();
        this.sceneInitializer = sceneInitializer;
    }
    public void init(){}

    public void destroy() {
    }

    public void render() {
        this.viewportRenderer.render();
    }

    public void update(float dt) {
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
    public Camera getCamera() {
        return this.camera;
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

    public <T extends ImGuiPanel> void addPanel(T panel) {
        this.panels.add(panel);
    }

    public boolean isPaused() {
        return isPaused;
    }

    public boolean isRunning() {
        return !isPaused;
    }
}
