package Burst.Engine.Source.Editor;

import Burst.Engine.Source.Core.Actor.Actor;
import Burst.Engine.Source.Core.Assets.AssetManager;
import Burst.Engine.Source.Core.Assets.Graphics.SpriteSheet;
import Burst.Engine.Source.Core.UI.ImGui.BImGui;
import Burst.Engine.Source.Core.UI.ImGui.ImGuiPanel;
import Burst.Engine.Source.Core.Util.ClassDerivativeSearch;
import Orion.blocks.Block;
import Orion.items.Item;
import Orion.playercharacters.*;
import Orion.res.AssetConfig;
import imgui.ImGui;
import imgui.flag.*;
import imgui.type.ImString;

import java.util.ArrayList;
import java.util.List;


public class ContentBrowser extends ImGuiPanel {
  private List<Class<?>> actors = new ArrayList<>();

  private Folder rootFolder = new Folder("Content");
  public Folder currentFolder = rootFolder;

  private boolean searchForActors = false;

  public ContentBrowser() {
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
    Folder actorFolder = new Folder("Actors").addItems(actors);
    rootFolder.addFolder(actorFolder);
    rootFolder.contentBrowser = this;
    actorFolder.contentBrowser = this;


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
    int windowFlags = 0;
    windowFlags |= ImGuiWindowFlags.MenuBar;
    windowFlags |= ImGuiWindowFlags.NoScrollbar;
    windowFlags |= ImGuiWindowFlags.NoScrollWithMouse;
    windowFlags |= ImGuiWindowFlags.NoTitleBar;
    windowFlags |= ImGuiWindowFlags.NoDecoration;
    windowFlags |= ImGuiWindowFlags.NoNav;


    if (ImGui.begin("Content Browser", windowFlags)) {

      // Make a content Browser window like in Unreal Engine
      if (ImGui.beginMenuBar()) {
        SpriteSheet icons = AssetManager.getAssetFromType(SpriteSheet.class, AssetConfig.Files.Images.SpriteSheets.ICONS);
        ImGui.pushStyleColor(ImGuiCol.Button, 0, 0, 0, 0);
        ImGui.pushStyleColor(ImGuiCol.ButtonActive, 1, 1, 1, 0.5f);
        ImGui.pushStyleColor(ImGuiCol.ButtonHovered, 1, 1, 1, 0.25f);



        BImGui.image(icons.getSprite(1, 9), 20, 20);
        ImGui.pushStyleVar(ImGuiStyleVar.ItemSpacing, 10, 0);
        if (ImGui.button("Add")) {

        }
        ImGui.popStyleVar();


        BImGui.image(icons.getSprite(8, 2), 20, 20);
        ImGui.pushStyleVar(ImGuiStyleVar.ItemSpacing, 10, 0);
        if (ImGui.button("Delete")) {
        }
        ImGui.popStyleVar();

        BImGui.image(icons.getSprite(7, 4), 20, 20);
        ImGui.pushStyleVar(ImGuiStyleVar.ItemSpacing, 10, 0);
        if (ImGui.button("Import")) {
        }
        ImGui.popStyleVar();

        ImGui.popStyleColor(3);

        ImGui.endMenuBar();
      }


      int contentBrowserFlags = 0;
      contentBrowserFlags |= ImGuiTableFlags.Resizable;

      if (ImGui.beginTable("Content Browser", 2, contentBrowserFlags)) {
        ImGui.tableNextColumn();
        ImGui.text("Folder Hierachy");
//            rootFolder.imGuiTree();

        ImGui.tableNextColumn();

        currentFolder.imgui();

        ImGui.endTable();
      }

      ImGui.end();
    }

    this.position.x = ImGui.getWindowPosX();
    this.position.y = ImGui.getWindowPosY();
  }
}