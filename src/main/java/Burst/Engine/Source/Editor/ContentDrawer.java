package Burst.Engine.Source.Editor;

import java.util.ArrayList;
import java.util.List;

import Burst.Engine.Source.Core.Actor.Actor;
import Burst.Engine.Source.Core.Assets.AssetManager;
import Burst.Engine.Source.Core.Assets.Graphics.Sprite;
import Burst.Engine.Source.Core.Assets.Graphics.SpriteSheet;
import Burst.Engine.Source.Core.UI.ImGui.BImGui;
import Burst.Engine.Source.Core.UI.ImGui.ImGuiPanel;
import Burst.Engine.Source.Core.UI.Window;
import Burst.Engine.Source.Core.Util.ClassDerivativeSearch;
import Burst.Engine.Source.Editor.Components.MouseControls;
import Orion.blocks.Block;

import Orion.playercharacters.*;


import Orion.items.Item;
import Orion.res.AssetConfig;
import imgui.ImGui;
import imgui.ImVec2;
import imgui.flag.ImGuiCol;
import imgui.flag.ImGuiStyleVar;
import imgui.flag.ImGuiWindowFlags;


public class ContentDrawer extends ImGuiPanel {
    private List<Class<?>> actors = new ArrayList<>();

    private Folder rootFolder = new Folder("Content");
    private Folder currentFolder = rootFolder;

    private boolean searchForActors = false;

    public ContentDrawer() {
        super();

        if (searchForActors) {
            searchForActors();
        }
        else
        {
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

        rootFolder.addFolder(new Folder("Actors").addItems(actors));
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
        int windowFlags = 0;
//        windowFlags |= ImGuiWindowFlags.NoScrollbar;
//        windowFlags |= ImGuiWindowFlags.NoScrollWithMouse;
//        windowFlags |= ImGuiWindowFlags.NoCollapse;
//        windowFlags |= ImGuiWindowFlags.NoResize;
//        windowFlags |= ImGuiWindowFlags.NoMove;
        windowFlags |= ImGuiWindowFlags.MenuBar;

        ImGui.begin("Content Browser", windowFlags);

        // Make a content Browser window like in Unreal Engine
        if (ImGui.beginMenuBar()){
            SpriteSheet icons = AssetManager.getAssetFromType(SpriteSheet.class, AssetConfig.Files.Images.SpriteSheets.ICONS);
            ImGui.pushStyleColor(ImGuiCol.Button, 0, 0, 0, 0);
            ImGui.pushStyleColor(ImGuiCol.ButtonActive, 1, 1, 1, 0.5f);
            ImGui.pushStyleColor(ImGuiCol.ButtonHovered, 1, 1, 1, 0.25f);

            BImGui.image(icons.getSprite(1, 9), 20, 20);
            ImGui.button("Add");

            BImGui.image(icons.getSprite(8, 2), 20, 20);
            ImGui.button("Import");

            BImGui.image(icons.getSprite(7, 4), 20, 20);
            ImGui.button("Save All");

            ImGui.endMenuBar();
            ImGui.popStyleColor(3);
        }

        // Show two columns
        // The first column is for the folders
        // The second column is for the content
        // The columns are separated by a vertical line and can be resized

        ImGui.pushStyleColor(ImGuiCol.Separator, 0.5f, 0.5f, 0.5f, 1f);

        ImGui.columns(2);

        // Show the folders
        currentFolder = rootFolder.imGuiTree();

        ImGui.pushStyleVar(ImGuiStyleVar.ItemSpacing, 20, 20);


        // Show the content
        ImGui.nextColumn();

        displayFolder(currentFolder);

        ImGui.popStyleVar();
        ImGui.popStyleColor();


        ImGui.end();
        ImGui.popStyleColor();

        this.position.x = ImGui.getWindowPosX();
        this.position.y = ImGui.getWindowPosY();
    }

    private void displayItem(Folder folder, float iconSize)
    {
        // Button colors
        ImGui.pushStyleColor(ImGuiCol.Button, 0.5f, 0.5f, 0.5f, 0.3f);
        ImGui.pushStyleColor(ImGuiCol.ButtonHovered, 0.5f, 0.5f, 0.5f, 0.5f);
        ImGui.pushStyleColor(ImGuiCol.ButtonActive, 0.5f, 0.5f, 0.5f, 1f);

        Sprite icon = folder.getIcon();
        String name = folder.name;

        if (icon != null){
            ImGui.pushID(folder.getClass().getSimpleName());

            if (BImGui.imageButton(icon, iconSize, iconSize))
            {
                currentFolder = folder;
            }

            // Center the text below the image
            ImVec2 textSize = new ImVec2();
            ImGui.calcTextSize(textSize, name);
            ImGui.setCursorPosX(ImGui.getCursorPosX() + (iconSize - textSize.x) / 2);

            // Shows a text below the image
            ImGui.text(name);
            ImGui.popID();
        }


        // Stop using the button colorss
        ImGui.popStyleColor(3);
    }

    private void displayItem(Class<?> item, float iconSize)
    {
        // Button colors
        ImGui.pushStyleColor(ImGuiCol.Button, 0.5f, 0.5f, 0.5f, 0.3f);
        ImGui.pushStyleColor(ImGuiCol.ButtonHovered, 0.5f, 0.5f, 0.5f, 0.5f);
        ImGui.pushStyleColor(ImGuiCol.ButtonActive, 0.5f, 0.5f, 0.5f, 1f);


        Sprite icon = null;
        String name = "!ERROR!";
        try{
            if (item.isAssignableFrom(Actor.class))
            {
                // Get the icon field from the actor
                Actor iconActor = (Actor) item.getConstructor().newInstance();
                icon = iconActor.getIcon();
                name = iconActor.getName();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (icon != null){
            ImGui.pushID(item.getSimpleName());


            if (BImGui.imageButton(icon, iconSize, iconSize))
            {
                try {
                    if (item.isAssignableFrom(Actor.class))
                    {
                        Actor newActor = (Actor) item.getConstructor().newInstance();
                        Window.getScene().getGame().getComponent(MouseControls.class).pickupObject(newActor);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            // Center the text below the image
            ImVec2 textSize = new ImVec2();
            ImGui.calcTextSize(textSize, name);
            ImGui.setCursorPosX(ImGui.getCursorPosX() + (iconSize - textSize.x) / 2);

            // Shows a text below the image
            ImGui.text(name);
            ImGui.popID();
        }


        // Stop using the button colorss
        ImGui.popStyleColor(3);
    }

    private void displayFolder(Folder folder)
    {
        float windowWidth = ImGui.getWindowWidth();
        float windowHeight = ImGui.getWindowHeight();
        float iconSize = Math.max(128, Math.min(windowWidth / 4, windowHeight / 4));

        // Add a border around the window
        ImGui.pushStyleColor(ImGuiCol.Border, 0.3f, 0.3f, 0.3f, 0.5f);
        // Add a padding between the border and the content
        ImGui.pushStyleVar(ImGuiStyleVar.ItemSpacing, 10, 10);
//        ImGui.columns(Math.max((int)(ImGui.getContentRegionAvailX() / iconSize) -1 , 1), "", false);

        for (Folder subFolder : folder.getSubFolders())
        {
            displayItem(subFolder, iconSize);
        }

        for (Class<?> item : folder.getItems())
        {
            displayItem(item, iconSize);
        }

        ImGui.popStyleColor();
        ImGui.popStyleVar();
    }


}