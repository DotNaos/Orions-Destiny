package Burst.Engine.Source.Editor;

import Burst.Engine.Source.Core.Actor.Actor;
import Burst.Engine.Source.Core.Assets.AssetManager;
import Burst.Engine.Source.Core.Assets.Graphics.Sprite;
import Burst.Engine.Source.Core.UI.ImGui.BImGui;
import Burst.Engine.Source.Core.UI.Window;
import Burst.Engine.Source.Editor.Components.MouseControls;
import Orion.res.AssetConfig;
import imgui.ImGui;
import imgui.ImVec2;
import imgui.flag.ImGuiCol;
import imgui.flag.ImGuiStyleVar;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class Folder {
  public ContentBrowser contentBrowser;
  public String name = "New Folder";
  private Sprite icon = AssetManager.getAssetFromType(Sprite.class, AssetConfig.Files.Images.Icons.FOLDER);
  private List<Folder> folders = new ArrayList<>();
  private List<Class<?>> items = new ArrayList<>();

  private boolean isExpanded = false;

  public Folder(String name) {
    this.name = name;
  }

  public Folder(List<Folder> folders) {
    this.folders = folders;
  }

  public Folder addItems(List<Class<?>> items) {
    if (this.items == null) this.items = new ArrayList<>();
    this.items.addAll(items);

    return this;
  }

  public Folder addItem(Class item) {
    if (this.items == null) this.items = new ArrayList<>();
    this.items.add(item);

    return this;
  }

  public Folder addFolders(List<Folder> folders) {
    if (this.folders == null) this.folders = new ArrayList<>();
    this.folders.addAll(folders);

    return this;
  }

  public Folder addFolder(Folder folder) {
    if (this.folders == null) this.folders = new ArrayList<>();
    this.folders.add(folder);

    return this;
  }

  public List<Folder> getSubFolders() {
    if (folders == null) folders = new ArrayList<>();
    return folders;
  }

  public List<Class<?>> getItems() {
    if (items == null) items = new ArrayList<>();
    return items;
  }

  public Folder removeFolder(Folder folder) {
    if (this.folders == null) return this;
    this.folders.remove(folder);

    return this;
  }

  public Folder removeItem(Class item) {
    if (this.items == null) return this;
    this.items.remove(item);

    return this;
  }

  public Sprite getIcon() {
    return this.icon;
  }

  public void imGuiTree() {
    BImGui.image(icon, 24, 24);
    ImGui.sameLine();
    if (ImGui.treeNode(name)) {
      if (ImGui.isItemActivated()) contentBrowser.currentFolder = this;
      for (Folder folder : folders) {
        folder.imGuiTree();
      }
      for (Class<?> item : items) {
        try {
          Actor iconActor = (Actor) item.getConstructor().newInstance();
          BImGui.image(iconActor.icon, 24, 24);
          ImGui.sameLine();
          ImGui.text(iconActor.getClass().getSimpleName());
        } catch (Exception e) {
          BImGui.image(AssetManager.getAssetFromType(Sprite.class, AssetConfig.Files.Images.Icons.ACTOR), 24, 24);
          ImGui.sameLine();
          ImGui.text(item.getSimpleName());
          e.printStackTrace();
        }
      }
      ImGui.treePop();
    }
  }

  public boolean isExpanded() {
    return isExpanded;
  }

  public void imgui() {
    float windowWidth = ImGui.getWindowWidth();
    float windowHeight = ImGui.getWindowHeight();
    float iconSize = Math.max(128, Math.min(windowWidth / 4, windowHeight / 4));

    // Add a border around the window
    ImGui.pushStyleColor(ImGuiCol.Border, 0.3f, 0.3f, 0.3f, 0.5f);
    // Add a padding between the border and the content
    int columns = Math.max((int)(ImGui.getContentRegionAvailX() / iconSize) -1 , 1);
    columns = Math.min(columns, items.size() + folders.size());
    ImGui.pushStyleVar(ImGuiStyleVar.ItemSpacing, 10, 20);

    ImGui.columns(columns, "", false);

    for (Folder subFolder : folders) {
      displayFolder(subFolder, iconSize);
      ImGui.nextColumn();
    }


    for (Class<?> item : items) {
      try{
        displayItem(item, iconSize);
        ImGui.nextColumn();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    ImGui.popStyleColor();
    ImGui.popStyleVar();

  }


  private void displayFolder(Folder folder, float iconSize) {
    // Button colors
    ImGui.pushStyleColor(ImGuiCol.Button, 0.5f, 0.5f, 0.5f, 0.3f);
    ImGui.pushStyleColor(ImGuiCol.ButtonHovered, 0.5f, 0.5f, 0.5f, 0.5f);
    ImGui.pushStyleColor(ImGuiCol.ButtonActive, 0.5f, 0.5f, 0.5f, 1f);

    Sprite icon = folder.getIcon();
    String name = folder.name;

    if (icon != null) {
      ImGui.pushID(folder.getClass().getSimpleName());

      if (BImGui.imageButton(icon, iconSize, iconSize)) {
        contentBrowser.currentFolder = folder;
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

  private void displayItem(Class<?> item, float iconSize) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        // Button colors
        ImGui.pushStyleColor(ImGuiCol.Button, 0.5f, 0.5f, 0.5f, 0.3f);
        ImGui.pushStyleColor(ImGuiCol.ButtonHovered, 0.5f, 0.5f, 0.5f, 0.5f);
        ImGui.pushStyleColor(ImGuiCol.ButtonActive, 0.5f, 0.5f, 0.5f, 1f);

        // Get the icon field from the actor
        Actor iconActor = (Actor) item.getConstructor().newInstance();
        Sprite actorIcon = iconActor.getIcon();


        ImGui.pushID(item.getSimpleName());
        if (BImGui.imageButton(actorIcon, iconSize, iconSize)) {
          Window.getScene().getGame().getComponent(MouseControls.class).pickupObject(iconActor);
        }
        ImGui.popID();

        ImGui.popStyleColor(3);

        // Center the text below the image
        ImVec2 textSize = new ImVec2();
        ImGui.calcTextSize(textSize, item.getSimpleName());
        ImGui.setCursorPosX(ImGui.getCursorPosX() + (iconSize - textSize.x) / 2);

        // Shows a text below the image
        ImGui.text(item.getSimpleName());
  }
}
