package Burst.Engine.Source.Editor;

import Burst.Engine.Source.Core.Actor.Actor;
import Burst.Engine.Source.Core.Assets.AssetManager;
import Burst.Engine.Source.Core.Assets.Graphics.Sprite;
import Burst.Engine.Source.Core.Assets.Graphics.SpriteSheet;
import Burst.Engine.Source.Core.UI.ImGui.BImGui;
import Burst.Engine.Source.Core.UI.ImGui.ImGuiPanel;
import Burst.Engine.Source.Core.UI.Window;
import Burst.Engine.Source.Core.Util.ClassDerivativeSearch;
import Burst.Engine.Source.Editor.Components.MouseControls;
import Burst.Engine.Source.Game.Camera;
import Orion.blocks.Block;
import Orion.items.Item;
import Orion.playercharacters.*;
import Orion.res.AssetConfig;
import imgui.ImGui;
import imgui.flag.ImGuiCol;
import imgui.flag.ImGuiStyleVar;
import imgui.flag.ImGuiTableFlags;
import imgui.flag.ImGuiWindowFlags;

import java.util.ArrayList;
import java.util.List;


public class ContentBrowser extends ImGuiPanel {

  private Folder rootFolder = new Folder("Content", this);
  public Folder currentFolder = rootFolder;

  private boolean searchForActors = false;

  public ContentBrowser() {
    super();
    Folder actorFolder = new Folder("Actors", this);
    actorFolder.setIcon(AssetManager.getAssetFromType(Sprite.class, AssetConfig.Files.Images.Icons.ACTOR));
    if (searchForActors) {
      List<Class<?>> actors = new ArrayList<>();
      searchForActors(actors);
      actorFolder.addItems(actors);
    }

    rootFolder.addFolder(actorFolder);

    // Blocks
    {
      Folder blockFolder = new Folder("Blocks", this) {
        @Override
        protected void displayAllItems(float iconSize) {
          // Display all Blocks
          for (int i = 0; i < Block.getSpriteSheet().size(); i++) {
            // Display the Block
            try {


              Sprite sprite = Block.getSpriteSheet().getSprite(i);

              if (sprite.isTransparent()) continue;
              ImGui.pushID(Block.class.getSimpleName() + " " + i);
              if (BImGui.imageButton(sprite, iconSize, iconSize)) {
                Block block = Block.class.getConstructor().newInstance();
                block.setIcon(sprite);
                // Set row and column of the Block
                block.setPos(sprite.getPos());

                Window.getScene().getGame().getComponent(MouseControls.class).pickupObject(block);
              }
              ImGui.popID();
              ImGui.nextColumn();
            } catch (Exception e) {
              e.printStackTrace();
            }
          }
        }
      };
      blockFolder.addItems(Block.class);
      blockFolder.setIcon(AssetManager.getAssetFromType(Sprite.class, AssetConfig.Files.Images.Icons.BLOCK));
      actorFolder.addFolder(blockFolder);
    }

    {
      Folder itemFolder = new Folder("Items", this).addItems(Item.class);
      itemFolder.setIcon(AssetManager.getAssetFromType(Sprite.class, AssetConfig.Files.Images.Icons.ITEM));
      actorFolder.addFolder(itemFolder);
    }

    // Player Characters
    {
      List<Class<?>> playerCharacters = new ArrayList<>();

      playerCharacters.add(Apex.class);
      playerCharacters.add(Aura.class);
      playerCharacters.add(Genesis.class);
      playerCharacters.add(Helix.class);
      playerCharacters.add(Solaris.class);

      Folder playerCharacterFolder = new Folder("Player Characters", this).addItems(playerCharacters);
      playerCharacterFolder.setIcon(AssetManager.getAssetFromType(Sprite.class, AssetConfig.Files.Images.Icons.PLAYER));
      actorFolder.addFolder(playerCharacterFolder);
    }

    // Camera
    {
      actorFolder.addItems(Camera.class);
    }

  }


  private void searchForActors(List<Class<?>> actors) {
    // Searching for all Actors in the Burst and Orion packages
    ClassDerivativeSearch actorSearcher = new ClassDerivativeSearch(Actor.class);
    actorSearcher.addPackage("Burst");
    actorSearcher.addPackage("Orion");

    actors.addAll(actorSearcher.search());
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
        rootFolder.imGuiTree();

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