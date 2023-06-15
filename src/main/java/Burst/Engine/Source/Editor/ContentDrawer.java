package Burst.Engine.Source.Editor;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import Burst.Engine.Source.Core.Actor.Actor;
import Burst.Engine.Source.Core.Assets.Graphics.Sprite;
import Burst.Engine.Source.Core.Assets.Graphics.Texture;
import Burst.Engine.Source.Core.UI.ImGui.BImGui;
import Burst.Engine.Source.Core.UI.ImGui.ImGuiPanel;
import Burst.Engine.Source.Core.UI.Window;
import Burst.Engine.Source.Core.Util.ClassDerivativeSearch;
import Burst.Engine.Source.Editor.Panel.PropertiesPanel;
import Burst.Engine.Source.Game.Camera;
import Orion.blocks.Block;

import Orion.playercharacters.*;


import Orion.items.Item;
import imgui.ImGui;
import imgui.ImVec2;
import imgui.flag.ImGuiCol;
import imgui.flag.ImGuiStyleVar;


public class ContentDrawer extends ImGuiPanel {
    private List<Class<?>> actors = new ArrayList<>();

    private boolean searchForActors = false;

    public ContentDrawer() {
        super();

        if (searchForActors) {
            searchForActors();
        } else {
            // Add all actors
            actors.add(Actor.class);

            // Add all blocks
            actors.add(Block.class);

            // Add all players
            actors.add(Apex.class);
            actors.add(Aura.class);
            actors.add(Genesis.class);
            actors.add(Helix.class);
            actors.add(Solaris.class);

            // Add all enemies
//            actors.add(Enemy.class);

            // Add all items
            actors.add(Item.class);
        }

    }

    private void searchForActors() {
        // Searching for all Actors in the Burst and Orion packages
        ClassDerivativeSearch actorSearcher = new ClassDerivativeSearch(Actor.class);
        actorSearcher.addPackage("Burst");
        actorSearcher.addPackage("Orion");

        this.actors.addAll(actorSearcher.search());
        // Print all found actors
        for (Class<?> actor : actors) {
            System.out.println(actor.getSimpleName());
        }
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

                    // Button colors
                    ImGui.pushStyleColor(ImGuiCol.Button, 0.5f, 0.5f, 0.5f, 0.3f);
                    ImGui.pushStyleColor(ImGuiCol.ButtonHovered, 0.5f, 0.5f, 0.5f, 0.5f);
                    ImGui.pushStyleColor(ImGuiCol.ButtonActive, 0.5f, 0.5f, 0.5f, 1f);

                    // Get the icon field from the actor
                    Actor iconActor = (Actor) actor.getConstructor().newInstance();
                    Sprite icon = iconActor.getIcon();



                    ImGui.pushID(actor.getSimpleName());
                    // Flip the image

                    if (BImGui.imageButton(icon, iconSize, iconSize))
                    {
                        Actor newActor = (Actor) actor.getConstructor().newInstance();
                        Window.getScene().getGame().addActor(newActor);
                        Window.getScene().getPanel(PropertiesPanel.class).setActiveActor(newActor);
                    }
                    ImGui.popID();

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
                } catch (IllegalAccessException e) {
                     System.out.println("No icon field found for class: " + actor.getSimpleName() );
                } catch (InvocationTargetException e) {
                    throw new RuntimeException(e);
                } catch (InstantiationException e) {
                    throw new RuntimeException(e);
                } catch (NoSuchMethodException e) {
                    throw new RuntimeException(e);
                }

            }
            // Resets columns and Style/Color changes
            ImGui.columns(1, "", false);
            ImGui.popStyleColor();
            ImGui.popStyleVar();


        // Update the position of the Panel
        this.position.x =ImGui.getWindowPosX();
        this.position.y = ImGui.getWindowPosY();

        ImGui.end();
        ImGui.popStyleColor();
    }
}