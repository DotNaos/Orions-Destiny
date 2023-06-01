package Burst.Engine.Source.Editor;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import Burst.Engine.Source.Core.Actor.Actor;
import Burst.Engine.Source.Core.Assets.AssetManager;
import Burst.Engine.Source.Core.Assets.Graphics.SpriteSheetUsage;
import Burst.Engine.Source.Core.Assets.Graphics.Texture;
import Burst.Engine.Source.Core.Assets.Graphics.SpriteSheet;
import Burst.Engine.Source.Core.UI.ImGui.ImGuiPanel;
import Burst.Engine.Source.Core.Util.ClassDerivativeSearch;
import imgui.ImGui;
import imgui.ImVec2;


public class ContentDrawer extends ImGuiPanel {
    private List<List<SpriteSheet>> categories = new ArrayList<>();
    private List<SpriteSheetUsage> usages = new ArrayList<>();
    private List<Class<?>> actors = new ArrayList<>();

    public ContentDrawer() {
        super();

        SpriteSheetUsage usage = SpriteSheetUsage.ACTOR;

        List<SpriteSheet> actors = AssetManager.getSpriteSheets(usage);
        categories.add(actors);
        usages.add(usage);

        usage = SpriteSheetUsage.BLOCK;
        List<SpriteSheet> blocks = AssetManager.getSpriteSheets(usage);
        categories.add(blocks);
        usages.add(usage);

        usage = SpriteSheetUsage.ITEM;
        List<SpriteSheet> items = AssetManager.getSpriteSheets(usage);
        categories.add(items);
        usages.add(usage);

        usage = SpriteSheetUsage.PLAYER;
        List<SpriteSheet> players = AssetManager.getSpriteSheets(usage);
        categories.add(players);
        usages.add(usage);

        ClassDerivativeSearch actorSearcher = new ClassDerivativeSearch(Actor.class);
        actorSearcher.addPackage("Burst");
        actorSearcher.addPackage("Orion");

        this.actors = actorSearcher.search();
    }

    /**
     *
     */
    @Override
    public void imgui() {

        ImGui.begin("Content Drawer");

            float windowWidth = ImGui.getWindowWidth();
            float windowHeight = ImGui.getWindowHeight();
            float iconSize = Math.max(64, Math.min(windowWidth / 4, windowHeight / 2));


            ImGui.columns(Math.max((int)(ImGui.getContentRegionAvailX() / iconSize), 1), "", false);

            // Show all actors in a Grid Layout
            for (Class<?> actor : actors) {
                ImGui.text(actor.getSimpleName());
                try {
                    // Get the icon field of the actor class
                    Field iconField = actor.getDeclaredField("icon");
                    iconField.setAccessible(true);

                    // Get the value of the icon field from the actor object
                    Object iconValue = iconField.get(actor);


                    // Cast the icon value to a Texture and show it
                    // Also adapt the size of the icon to the size of the window


                    // Background
                    ImGui.imageButton(((Texture) iconValue).getTexID(), iconSize, iconSize);

                    // Show the text below the image
                    ImGui.text(actor.getSimpleName());
                  

                    // Move to the next column
                    ImGui.nextColumn();
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    // Handle any exceptions that may occur
                    // System.out.println("No icon field found for class: " + actor.getSimpleName() );
                }
            
            }
            // Reset the columns
            ImGui.columns(1, "", false);
            
        /* 
        if (ImGui.beginTabBar("WindowTabBar")) {
            for (int category = 0; category < categories.size(); category++) {


                if (category >= 0) continue;



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
                                        Block block = new Block();
                                        block.setSprite(sprite);
                                        block.addComponent(new NonPickable());
                                    }

                                    case PLAYER -> {
                                        // TODO: FIX THIS
                                        Class<? extends PlayerCharacter> selectedPlayer;
                                        selectedPlayer = (Class<? extends PlayerCharacter>) sprites.getUsedBy();


                                        PlayerCharacter player;
                                        try {
                                            player = PlayerCharacter.getNewPlayerCharacter(selectedPlayer);
                                            player.addComponent(new NonPickable());

                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                            
                                    }

                                    case ITEM -> {
                                        Item item = new Item();
                                        item.addComponent(new NonPickable());
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
        }*/
        
        ImGui.end();
    }
}
