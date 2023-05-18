package Burst.Engine.Source.Editor;

import Burst.Engine.Config.ImGuiStyleConfig;
import Burst.Engine.Source.Core.Actor.Actor;
import Burst.Engine.Source.Core.Component;
import Burst.Engine.Source.Game.Game;
import Burst.Engine.Source.Editor.Components.KeyControls;
import Burst.Engine.Source.Editor.Components.MouseControls;
import Burst.Engine.Source.Editor.Components.GridLines;
import Burst.Engine.Source.Core.Render.PickingTexture;
import Burst.Engine.Source.Core.Scene.Scene;
import Burst.Engine.Source.Editor.Panel.OutlinerPanel;
import Burst.Engine.Source.Editor.Panel.PropertiesPanel;
import imgui.ImGui;

public class Editor extends Game {

    public Editor(Scene scene) {
        super(scene);
    }

    public void init() {
        super.init();

        // Variables
        this.components.add(new PickingTexture());
        this.components.add(new EditorCamera(this.scene.getViewport()));

        // Panels
        scene.addPanel(new OutlinerPanel());
        scene.addPanel(new PropertiesPanel(getComponent(PickingTexture.class)));
        scene.addPanel(new ContentDrawer());


        // Level Editor Stuff
        this.components.add(new MouseControls());
        this.components.add(new KeyControls());
        this.components.add(new GridLines());
    }

    @Override
    public void update(float dt) {
        this.scene.getViewport().adjustProjection();

        super.update(dt);
        for (Actor actor : actors) {
            actor.update(dt);
        }
    }

    @Override
    public void imgui() {
        super.imgui();

        // ImGui for all editor features
        ImGui.begin("Editor");
        ImGui.beginTabBar("EditorTabs");
        ImGui.beginTabItem("Style");
        ImGuiStyleConfig.get().imgui();
        ImGui.endTabItem();

        // Components imgui
        for (Component component : components) {
//            ImGui.beginTabItem(component.getClass().getSimpleName());
            component.imgui();
//            ImGui.endTabItem();
        }


        ImGui.endTabBar();
        ImGui.end();
    }
}
