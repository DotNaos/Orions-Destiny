package Burst.Engine.Source.Editor;

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector3f;

import Burst.Engine.Source.Core.Actor.Actor;
import Burst.Engine.Source.Core.Assets.AssetManager;
import Burst.Engine.Source.Core.Assets.Graphics.Sprite;
import Burst.Engine.Source.Core.Assets.Graphics.SpriteSheetUsage;
import Burst.Engine.Source.Core.Assets.Graphics.Spritesheet;
import Burst.Engine.Source.Core.UI.ImGui.ImGuiPanel;
import Orion.blocks.Block;
import Orion.characters.PlayerCharacter;
import imgui.ImGui;
import imgui.ImVec2;

public class ContentDrawer extends ImGuiPanel {
    private List<List<Spritesheet>> categories = new ArrayList<>();
    private List<SpriteSheetUsage> usages = new ArrayList<>();

    public ContentDrawer() {
        super();

        SpriteSheetUsage usage = SpriteSheetUsage.ACTOR;

        List<Spritesheet> actors = AssetManager.getSpriteSheets(usage);
        categories.add(actors);
        usages.add(usage);

        usage = SpriteSheetUsage.BLOCK;
        List<Spritesheet> blocks = AssetManager.getSpriteSheets(usage);
        categories.add(blocks);
        usages.add(usage);

        usage = SpriteSheetUsage.ITEM;
        List<Spritesheet> items = AssetManager.getSpriteSheets(usage);
        categories.add(items);
        usages.add(usage);

        usage = SpriteSheetUsage.PLAYER;
        List<Spritesheet> players = AssetManager.getSpriteSheets(usage);
        categories.add(players);
        usages.add(usage);
    }

    /**
     *
     */
    @Override
    public void imgui() {
        ImGui.begin("Content Drawer");
        if (ImGui.beginTabBar("WindowTabBar")) {
            for (int category = 0; category < categories.size(); category++) {
                if (ImGui.beginTabItem(usages.get(category).toString()))
                {
                    if (categories.get(category).isEmpty()) {
                    ImGui.text("No Spritesheets in this category");
                    }

                    // Add all Spritesheets to the tab
                    ImVec2 windowPos = new ImVec2();
                    ImGui.getWindowPos(windowPos);
                    ImVec2 windowSize = new ImVec2();
                    ImGui.getWindowSize(windowSize);
                    ImVec2 itemSpacing = new ImVec2();
                    ImGui.getStyle().getItemSpacing(itemSpacing);
                    
                    float windowX2 = windowPos.x + windowSize.x;
                    for (Spritesheet sprites : categories.get(category))
                    {
                        // Add all Sprites of the Spritesheet to the tab
                        for (int i = 0; i < sprites.size(); i++) {
                            Sprite sprite = sprites.getSprite(i);
                            float spriteWidth = sprite.getWidth() * 4;
                            float spriteHeight = sprite.getHeight() * 4;
                            int id = sprite.getTexId();
                            Vector3f[] texCoords = sprite.getTexCoords();
                            ImGui.pushID(i);
                            if (ImGui.imageButton(id, spriteWidth, spriteHeight, texCoords[2].x, texCoords[0].y, texCoords[0].x, texCoords[2].y)) {
                                // While dragging the mouse, show the sprite next to the cursor
                                // TODO: ContentDrawer Pickup Object
                                switch (usages.get(category)) {
                                    case ACTOR -> {
                                        Actor actor = new Actor();
                                        actor.setSprite(sprite);
                                        actor.addComponent(new NonPickable());
                                    }
                                    case BLOCK -> {
                                        Block block = new Block(sprite);
                                        block.addComponent(new NonPickable());
                                    }

                                    case PLAYER -> {
                                        Class <? extends PlayerCharacter> type;
                                        // 
                                        // TODO: FIX THIS
                                        PlayerCharacter player = PlayerCharacter.getNewPlayerCharacter(i);
                                        player.addComponent(new NonPickable());
                                    }

                                    case ITEM -> {
                                        // Item actor = new Item(sprite);
                                        // actor.addComponent(new NonPickable());
                                    }
                                    default -> throw new IllegalArgumentException("Unexpected value: " + usages.get(category));

                                }
                

                      
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
            
                // Show a text if there are no Spritesheets in the category

            }
            
            
            ImGui.endTabBar();
        }
        ImGui.end();
    }
}
