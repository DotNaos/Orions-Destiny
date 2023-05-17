package Burst.Engine.Source.Editor;

import Burst.Engine.Source.Core.Actor.Actor;
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
    private PickingTexture pickingTexture;
    private MouseControls mouseControls;
    private KeyControls keyControls;
    private EditorCamera editorCamera;

    public Editor(Scene scene) {
        super(scene);
    }

    public void init() {
        super.init();

        // Variables
        pickingTexture = new PickingTexture();
        editorCamera = new EditorCamera(this.scene.getViewport());

        // Panels
        scene.addPanel(new OutlinerPanel());
        scene.addPanel(new PropertiesPanel(this.pickingTexture));
        scene.addPanel(new ContentDrawer());


        // Level Editor Stuff
        this.mouseControls = new MouseControls();
        this.keyControls = new KeyControls();
    }

    @Override
    public void update(float dt) {
        this.scene.getViewport().adjustProjection();

        this.mouseControls.update(dt);
        this.keyControls.update(dt);
        GridLines.update(dt);
        this.editorCamera.update(dt);

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

        this.pickingTexture.imgui();
        this.editorCamera.imgui();
        this.mouseControls.imgui();
        this.keyControls.imgui();
        GridLines.imgui(0);

        ImGui.endTabBar();
        ImGui.end();
    }

    public MouseControls getMouseControls() {
        return this.mouseControls;
    }

    public KeyControls getKeyControls() {
        return this.keyControls;
    }
}
