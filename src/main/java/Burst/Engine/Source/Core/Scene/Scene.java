package Burst.Engine.Source.Core.Scene;

import Burst.Engine.Source.Core.Camera;
import Burst.Engine.Source.Core.Graphics.Render.ViewportRenderer;
import Burst.Engine.Source.Core.UI.ImGuiPanel;

import java.util.ArrayList;
import java.util.List;

public class Scene {
    protected SceneInitializer sceneInitializer;
    protected List<ImGuiPanel> panels = new ArrayList<>();
    protected Camera camera;
    protected final ViewportRenderer viewportRenderer;

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
}
