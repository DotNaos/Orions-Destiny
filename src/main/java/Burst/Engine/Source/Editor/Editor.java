package Burst.Engine.Source.Editor;

import Burst.Engine.Config.ImGuiStyleConfig;
import Burst.Engine.Source.Core.Actor.Actor;
import Burst.Engine.Source.Core.Component;
import Burst.Engine.Source.Core.UI.Window;
import Burst.Engine.Source.Core.Util.DebugMessage;
import Burst.Engine.Source.Game.Game;
import Burst.Engine.Source.Editor.Components.KeyControls;
import Burst.Engine.Source.Editor.Components.MouseControls;
import Burst.Engine.Source.Editor.Components.GridLines;
import Burst.Engine.Source.Core.Render.PickingTexture;
import Burst.Engine.Source.Core.Scene.Scene;
import Burst.Engine.Source.Editor.Panel.OutlinerPanel;
import Burst.Engine.Source.Editor.Panel.PropertiesPanel;
import imgui.ImGui;
import imgui.flag.ImGuiCol;
import imgui.flag.ImGuiStyleVar;

public class Editor extends Game {

    public Editor(Scene scene) {
        super(scene);
    }

    public void init() {
        super.init();

        // Variables
        this.components.add(Window.get().getPickingTexture());
        this.components.add(new EditorCamera(this.scene.getViewport()));

        // Panels
        DebugMessage.header("Editor Panels");
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

        // Start the tab bar
        ImGui.begin("Editor");
        if (ImGui.beginTabBar("EditorTabs")) {
            if (ImGui.beginTabItem("Style"))
            {
                ImGui.beginTable("EditorTable", 2);
                ImGui.pushStyleColor(ImGuiCol.Border, 1.0f, 1.0f, 1.0f, 0.2f);
                ImGui.pushStyleVar(ImGuiStyleVar.FrameBorderSize, 1.0f);
                ImGui.pushStyleVar(ImGuiStyleVar.ItemSpacing, 20f, 20f);
                ImGuiStyleConfig.get().imgui();

                ImGui.popStyleColor();
                ImGui.popStyleVar(2);

                ImGui.endTable();
                ImGui.endTabItem();

            }

            // Components imgui
            if (ImGui.beginTabItem("Components"))
            {

                for (Component component : components) {

                    if (!component.isEditableInImGui()) continue;
                    if(ImGui.collapsingHeader(component.getClass().getSimpleName())) {
                        ImGui.beginTable("EditorTable", 2);
                        ImGui.pushStyleColor(ImGuiCol.Border, 1.0f, 1.0f, 1.0f, 0.2f);
                        ImGui.pushStyleVar(ImGuiStyleVar.FrameBorderSize, 1.0f);
                        ImGui.pushStyleVar(ImGuiStyleVar.ItemSpacing, 20f, 20f);

                        component.imgui();

                        ImGui.popStyleColor();
                        ImGui.popStyleVar(2);

                        ImGui.endTable();
                    }
                }
                ImGui.endTabItem();
            }



            ImGui.endTabBar();
        }



        ImGui.end();
    }
}
