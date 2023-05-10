package Burst.Engine.Source.Core.Game;

import Burst.Engine.Source.Core.Actor.Actor;
import Burst.Engine.Source.Core.Assets.AssetManager;
import Burst.Engine.Source.Core.Assets.Graphics.Sprite;
import Burst.Engine.Source.Core.Assets.Graphics.Spritesheet;
import Burst.Engine.Source.Core.Graphics.Input.Components.KeyControls;
import Burst.Engine.Source.Core.Graphics.Input.Components.MouseControls;
import Burst.Engine.Source.Core.Graphics.Render.Components.GridLines;
import Burst.Engine.Source.Core.Graphics.Render.PickingTexture;
import Burst.Engine.Source.Core.Scene.Scene;
import Burst.Engine.Source.Core.UI.Window;
import Burst.Engine.Source.Core.util.DebugMessage;
import Burst.Engine.Source.Core.util.Prefabs;
import Burst.Engine.Source.Editor.ContentDrawer;
import Burst.Engine.Source.Editor.EditorCamera;
import Burst.Engine.Source.Editor.Gizmo.GizmoSystem;
import Burst.Engine.Source.Editor.Panel.OutlinerPanel;
import Burst.Engine.Source.Editor.Panel.PropertiesPanel;
import Orion.res.Assets;
import imgui.ImGui;
import imgui.ImVec2;
import org.joml.Vector2f;

public class Editor extends Game {
    private Actor levelEditorStuff;
    private PickingTexture pickingTexture;
    private GridLines gridLines;
    private MouseControls mouseControls;
    private KeyControls keyControls;

    private GizmoSystem gizmoSystem;


    public Editor(Scene scene) {
        super(scene);
    }

    public void init()
    {
        super.init();

        // Variables
            pickingTexture = new PickingTexture();
            gridLines = new GridLines();

        // Panels
            scene.addPanel(new OutlinerPanel());
            scene.addPanel(new PropertiesPanel(this.pickingTexture));
            scene.addPanel(new ContentDrawer());



        // Level Editor Stuff
            this.mouseControls = new MouseControls();
            this.keyControls = new KeyControls();

            levelEditorStuff = spawnActor("LevelEditor");
            levelEditorStuff.serializedActor = false;
            levelEditorStuff.addComponent(new EditorCamera(Window.getScene().getViewport()));
            addActor(levelEditorStuff);
    }

    @Override
    public void update(float dt) {
        this.scene.getViewport().adjustProjection();

        this.mouseControls.update(dt);
        this.keyControls.update(dt);
        this.gridLines.update(dt);

        super.update(dt);
        for (Actor actor : actors) {
            actor.update(dt);
        }
    }

    @Override
    public void imgui() {
        super.imgui();

        ImGui.begin("Settings");
        levelEditorStuff.imgui();
        ImGui.end();
    }

    public MouseControls getMouseControls() {
        return this.mouseControls;
    }

    public KeyControls getKeyControls() {
        return this.keyControls;
    }
}
