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
import imgui.flag.ImGuiCol;
import imgui.flag.ImGuiStyleVar;


public class ContentDrawer extends ImGuiPanel {
    private final List<Class<?>> actors;

    public ContentDrawer() {
        super();

        // Searching for all Actors in the Burst and Orion packages
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

        ImGui.pushStyleColor(ImGuiCol.ChildBg, 0.125f, 0.125f,0.125f, 0.75f);
        ImGui.begin("Content Drawer");

            float windowWidth = ImGui.getWindowWidth();
            float windowHeight = ImGui.getWindowHeight();
            float iconSize = Math.max(128, Math.min(windowWidth / 4, windowHeight / 4));

            // Add a border around the window
            ImGui.pushStyleColor(ImGuiCol.Border, 0.3f, 0.3f, 0.3f, 0.5f);
            // Add a padding between the border and the content
            ImGui.pushStyleVar(ImGuiStyleVar.ItemSpacing, 10, 10);
            ImGui.columns(Math.max((int)(ImGui.getContentRegionAvailX() / iconSize) -1 , 1), "", false);

            // Show all actors in a Grid Layout
            for (Class<?> actor : actors) {
                try {
                    // Get the icon field of the actor class
                    Field iconField = actor.getDeclaredField("icon");
                    iconField.setAccessible(true);

                    // Get the value of the icon field from the actor object
                    Object iconValue = iconField.get(actor);

                    // Button colors
                    ImGui.pushStyleColor(ImGuiCol.Button, 0.5f, 0.5f, 0.5f, 0.3f);
                    ImGui.pushStyleColor(ImGuiCol.ButtonHovered, 0.5f, 0.5f, 0.5f, 0.5f);
                    ImGui.pushStyleColor(ImGuiCol.ButtonActive, 0.5f, 0.5f, 0.5f, 1f);


                    if (ImGui.imageButton(((Texture) iconValue).getTexID(), iconSize, iconSize))
                    {
                        // If the button is clicked, create a new actor of the type

                    }

                    // Stop using the button colorss
                    ImGui.popStyleColor(3);

                    // Center the text below the image
                    ImVec2 textSize = new ImVec2();
                    ImGui.calcTextSize(textSize, actor.getSimpleName());
                    ImGui.setCursorPosX(ImGui.getCursorPosX() + (iconSize - textSize.x) / 2);

                    // Shows a text below the image
                    ImGui.text(actor.getSimpleName());
                  

                    // Move to the next column
                    ImGui.nextColumn();
                } catch (NoSuchFieldException | IllegalAccessException e) {
                     System.out.println("No icon field found for class: " + actor.getSimpleName() );
                }
            
            }
            // Resets columns and Style/Color changes
            ImGui.columns(1, "", false);
            ImGui.popStyleColor();
            ImGui.popStyleVar();


        ImGui.end();
        ImGui.popStyleColor();
    }
}
