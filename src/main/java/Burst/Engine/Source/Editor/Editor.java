package Burst.Engine.Source.Editor;

import Burst.Engine.Source.Core.Actor.Actor;
import Burst.Engine.Source.Core.Game.Game;
import Burst.Engine.Source.Core.Graphics.Input.Components.KeyControls;
import Burst.Engine.Source.Core.Graphics.Input.Components.MouseControls;
import Burst.Engine.Source.Core.Graphics.Render.Components.GridLines;
import Burst.Engine.Source.Core.Graphics.Render.PickingTexture;
import Burst.Engine.Source.Core.Scene.Scene;
import Burst.Engine.Source.Editor.ContentDrawer;
import Burst.Engine.Source.Editor.EditorCamera;
import Burst.Engine.Source.Editor.Panel.OutlinerPanel;
import Burst.Engine.Source.Editor.Panel.PropertiesPanel;

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

    }

    public MouseControls getMouseControls() {
        return this.mouseControls;
    }

    public KeyControls getKeyControls() {
        return this.keyControls;
    }
}
