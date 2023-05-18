package Burst.Engine.Source.Editor;

import Burst.Engine.Source.Core.Actor.Actor;
import Burst.Engine.Source.Core.Assets.AssetManager;
import Burst.Engine.Source.Core.Assets.Graphics.Sprite;
import Burst.Engine.Source.Core.Assets.Graphics.Spritesheet;
import Burst.Engine.Source.Core.UI.ImGui.ImGuiPanel;
import Burst.Engine.Source.Core.UI.Window;
import Burst.Engine.Source.Editor.Components.MouseControls;
import Orion.res.Assets;
import imgui.ImGui;
import imgui.ImVec2;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class ContentDrawer extends ImGuiPanel {
    private List<Spritesheet> spritesheets = new ArrayList<>();

    public ContentDrawer() {
        super();
    }

    public void addSpritesheet(Spritesheet spritesheet) {
        spritesheets.add(spritesheet);
    }

    public void removeSpritesheet(Spritesheet spritesheet) {
        for (int i = 0; i < spritesheets.size(); i++) {
            if (spritesheets.get(i).getID() == spritesheet.getID()) {
                spritesheets.remove(i);
                return;
            }
        }
    }


    /**
     *
     */
    @Override
    public void imgui() {
        Spritesheet sprites = AssetManager.getAssetFromType(Assets.BLOCKS, Spritesheet.class);

        ImGui.begin("Content Drawer");
        if (ImGui.beginTabBar("WindowTabBar")) {
            if (ImGui.beginTabItem("Building Blocks")) {
                ImVec2 windowPos = new ImVec2();
                ImGui.getWindowPos(windowPos);
                ImVec2 windowSize = new ImVec2();
                ImGui.getWindowSize(windowSize);
                ImVec2 itemSpacing = new ImVec2();
                ImGui.getStyle().getItemSpacing(itemSpacing);

                float windowX2 = windowPos.x + windowSize.x;
                for (int i = 0; i < sprites.size(); i++) {
                    Sprite sprite = sprites.getSprite(i);
                    float spriteWidth = sprite.getWidth() * 4;
                    float spriteHeight = sprite.getHeight() * 4;
                    int id = sprite.getTexId();
                    Vector3f[] texCoords = sprite.getTexCoords();

                    ImGui.pushID(i);
                    if (ImGui.imageButton(id, spriteWidth, spriteHeight, texCoords[2].x, texCoords[0].y, texCoords[0].x, texCoords[2].y)) {
                        // TODO: ContentDrawer Pickup Object
                        Actor actor = new Actor(sprite, 1.0f, 1.0f);
                        Window.getScene().getEditor().getComponent(MouseControls.class).pickupObject(actor);

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
            }
            ImGui.endTabItem();
        }

        ImGui.endTabBar();


        ImGui.end();
    }
}
