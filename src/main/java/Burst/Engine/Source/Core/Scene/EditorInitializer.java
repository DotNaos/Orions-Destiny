package Burst.Engine.Source.Core.Scene;

import Burst.Engine.Source.Core.Graphics.Render.PickingTexture;
import Burst.Engine.Source.Core.Graphics.Render.Components.GridLines;
import Burst.Engine.Source.Core.Assets.Graphics.Sprite;
import Burst.Engine.Source.Core.Assets.Graphics.Spritesheet;
import Burst.Engine.Source.Core.Graphics.Input.Components.MouseControls;
import Burst.Engine.Source.Core.Physics.Components.Box2DCollider;
import Burst.Engine.Source.Core.Physics.Components.Rigidbody2D;
import Burst.Engine.Source.Core.Physics.Enums.BodyType;
import Burst.Engine.Source.Core.Actor;
import Burst.Engine.Source.Core.UI.Window;
import Burst.Engine.Source.Core.util.Prefabs;
import Burst.Engine.Source.Core.Assets.Audio.Sound;
import Burst.Engine.Source.Editor.Direction;
import Burst.Engine.Source.Editor.EditorCamera;
import Burst.Engine.Source.Editor.Panel.OutlinerPanel;
import Burst.Engine.Source.Editor.Panel.PropertiesPanel;
import Burst.Engine.Source.Runtime.Actor.Pawn;
import Burst.Engine.Source.Runtime.Game;
import Orion.blocks.Ground;
import Burst.Engine.Source.Editor.Gizmo.GizmoSystem;
import Burst.Engine.Source.Core.Graphics.Input.Components.KeyControls;
import imgui.ImGui;
import imgui.ImVec2;
import org.joml.Vector2f;
import Orion.res.Assets;
import Burst.Engine.Source.Core.Assets.AssetManager;


import java.io.File;
import java.util.List;

public class EditorInitializer extends SceneInitializer {

    private Spritesheet sprites;
    private Actor levelEditorStuff;
    private PickingTexture pickingTexture;

    public EditorInitializer(Scene scene) {
        super(scene);
    }


    @Override
    public void init() {
        scene.addPanel(new OutlinerPanel());
        pickingTexture = new PickingTexture();
        scene.addPanel(new PropertiesPanel(this.pickingTexture));
        sprites = AssetManager.getAssetFromType(Assets.BLOCKS, Spritesheet.class);
        Spritesheet gizmos = AssetManager.getAssetFromType(Assets.GIZMOS, Spritesheet.class);

        levelEditorStuff = scene.getGame().spawnActor("LevelEditor");
        levelEditorStuff.setNoSerialize();
        levelEditorStuff.addComponent(new MouseControls());
        levelEditorStuff.addComponent(new KeyControls());
        levelEditorStuff.addComponent(new GridLines());
        levelEditorStuff.addComponent(new EditorCamera(Window.getScene().getCamera()));
        levelEditorStuff.addComponent(new GizmoSystem(gizmos));
        scene.getGame().addActor(levelEditorStuff);
    }


    @Override
    public void imgui() {
        ImGui.begin("Settings");
        levelEditorStuff.imgui();
        ImGui.end();

        ImGui.begin("Content Drawer");

        if (ImGui.beginTabBar("WindowTabBar")) {



            if (ImGui.beginTabItem("Decoration Blocks")) {
                ImVec2 windowPos = new ImVec2();
                ImGui.getWindowPos(windowPos);
                ImVec2 windowSize = new ImVec2();
                ImGui.getWindowSize(windowSize);
                ImVec2 itemSpacing = new ImVec2();
                ImGui.getStyle().getItemSpacing(itemSpacing);

                float windowX2 = windowPos.x + windowSize.x;
                for (int i = 34; i < 61; i++) {
                    if (i >= 35 && i < 38) continue;
                    if (i >= 42 && i < 45) continue;

                    Sprite sprite = sprites.getSprite(i);
                    float spriteWidth = sprite.getWidth() * 4;
                    float spriteHeight = sprite.getHeight() * 4;
                    int id = sprite.getTexId();
                    Vector2f[] texCoords = sprite.getTexCoords();

                    ImGui.pushID(i);
                    if (ImGui.imageButton(id, spriteWidth, spriteHeight, texCoords[2].x, texCoords[0].y, texCoords[0].x, texCoords[2].y)) {
                        Actor object = Prefabs.generateSpriteObject(sprite, 0.25f, 0.25f);
                        levelEditorStuff.getComponent(MouseControls.class).pickupObject(object);
                    }
                    ImGui.popID();

                    ImVec2 lastButtonPos = new ImVec2();
                    ImGui.getItemRectMax(lastButtonPos);
                    float lastButtonX2 = lastButtonPos.x;
                    float nextButtonX2 = lastButtonX2 + itemSpacing.x + spriteWidth;
                    if (i + 1 < sprites.size() && nextButtonX2 < windowX2) {
                        ImGui.sameLine();
                    }
                }

                ImGui.endTabItem();
            }

            if (ImGui.beginTabItem("Sounds")) {
                List<Sound> sounds = (List<Sound>) AssetManager.getAllAssetsFromType(Sound.class);
                for (Sound sound : sounds) {
                    File tmp = new File(sound.getFilepath());
                    if (ImGui.button(tmp.getName())) {
                        if (!sound.isPlaying()) {
                            sound.play();
                        } else {
                            sound.stop();
                        }
                    }

                    if (ImGui.getContentRegionAvailX() > 100) {
                        ImGui.sameLine();
                    }
                }

                ImGui.endTabItem();
            }
            ImGui.endTabBar();
        }

        ImGui.end();
    }

    public PickingTexture getPickingTexture() {
        return pickingTexture;
    }



}
