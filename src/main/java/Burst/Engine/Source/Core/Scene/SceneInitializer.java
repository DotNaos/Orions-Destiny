package Burst.Engine.Source.Core.Scene;

import Burst.Engine.Source.Core.Graphics.Sprite.SpriteRenderer;
import Burst.Engine.Source.Core.GameObject;
import Burst.Engine.Source.Core.UI.ImGuiPanel;
import Burst.Engine.Source.Editor.Panel.PropertiesPanel;
import Burst.Engine.Source.Runtime.Animation.StateMachine;
import Burst.Engine.Source.Core.util.AssetManager;

import java.util.ArrayList;
import java.util.List;

public abstract class SceneInitializer {
    protected List<ImGuiPanel> panels = new ArrayList<>();


    public void init(Scene scene) {

    }


    public void loadResources(Scene scene) {
        AssetManager.loadAllResources();


        for (GameObject g : scene.getGameObjects()) {
            if (g.getComponent(SpriteRenderer.class) != null) {
                SpriteRenderer spr = g.getComponent(SpriteRenderer.class);
                if (spr.getTexture() != null) {
                    spr.setTexture(AssetManager.getTexture(spr.getTexture().getFilepath()));
                }
            }

            if (g.getComponent(StateMachine.class) != null) {
                StateMachine stateMachine = g.getComponent(StateMachine.class);
                stateMachine.refreshTextures();
            }
        }
    }

    public void imgui() {
        for (ImGuiPanel panel : panels) {
            panel.imgui();
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

    public void mouseListener(){

    }
}
