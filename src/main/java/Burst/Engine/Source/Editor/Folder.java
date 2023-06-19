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
import imgui.flag.*;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Folder {
  public ContentBrowser contentBrowser;
  public Folder parentFolder = null;
  public String name = "New Folder";
  private Sprite icon = AssetManager.getAssetFromType(Sprite.class, AssetConfig.Files.Images.Icons.FOLDER);
  private List<Folder> childFolders = new ArrayList<>();
  private List<Class<?>> items = new ArrayList<>();

  private boolean isExpanded = false;

  public Folder(String name, ContentBrowser contentBrowser) {
    this.name = name;
    this.contentBrowser = contentBrowser;
  }
  public Folder(ContentBrowser contentBrowser) {
    this.contentBrowser = contentBrowser;
  }

  public Folder addItems(List<Class<?>> items) {
    if (this.items == null) this.items = new ArrayList<>();
    this.items.addAll(items);

    return this;
  }

  public Folder addItems(Class<?> item) {
    if (this.items == null) this.items = new ArrayList<>();
    this.items.add(item);

    return this;
  }

  public Folder addItem(Class<?> item) {
    if (this.items == null) this.items = new ArrayList<>();
    this.items.add(item);

    return this;
  }

  public Folder addFolders(List<Folder> folders) {
    if (this.childFolders == null) this.childFolders = new ArrayList<>();
    this.childFolders.addAll(folders);
    for (Folder folder : folders) {
      folder.parentFolder = this;
    }

    return this;
  }

  public Folder addFolder(Folder folder) {
    if (this.childFolders == null) this.childFolders = new ArrayList<>();
    this.childFolders.add(folder);
    folder.parentFolder = this;

    return this;
  }

  public List<Folder> getSubFolders() {
    if (childFolders == null) childFolders = new ArrayList<>();
    return childFolders;
  }

  public List<Class<?>> getItems() {
    if (items == null) items = new ArrayList<>();
    return items;
  }

  public Folder removeFolder(Folder folder) {
    if (this.childFolders == null) return this;
    this.childFolders.remove(folder);

    return this;
  }

  public Folder removeItem(Class<?> item) {
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
    if (ImGui.treeNodeEx(name, ImGuiTreeNodeFlags.OpenOnArrow | ImGuiTreeNodeFlags.OpenOnDoubleClick | ImGuiTreeNodeFlags.DefaultOpen)) {
      if (ImGui.isItemClicked())
        contentBrowser.currentFolder = this;
      for (Folder folder : childFolders) {
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
    // Add a bar to display the current path

    if (ImGui.beginChild("Path", 0, 40, true, ImGuiWindowFlags.NoScrollWithMouse | ImGuiWindowFlags.NoScrollbar))
    {
      showPath();
      ImGui.endChild();
    }

    ImGui.pushStyleColor(ImGuiCol.Separator, 0.5f, 0.5f, 0.5f, 0.3f);
    ImGui.separator();
    ImGui.popStyleColor();



    if (ImGui.beginChild("Content", 0, 0, true))
    {
      ImGui.pushStyleVar(ImGuiStyleVar.ItemSpacing, 10, 20);
      float windowWidth = ImGui.getWindowWidth();
      float windowHeight = ImGui.getWindowHeight();
      float iconSize = Math.max(128, Math.min(windowWidth / 4, windowHeight / 4));

      // Add a border around the window
      ImGui.pushStyleColor(ImGuiCol.Border, 0.3f, 0.3f, 0.3f, 0.5f);
      // Add a padding between the border and the content
      int columns = Math.max((int) (ImGui.getContentRegionAvailX() / iconSize) - 1, 1);
      if (items.size() + childFolders.size() > 1)
      {
        columns = Math.min(columns, items.size() + childFolders.size());
      }

      ImGui.columns(columns, "", false);
      // Button colors
      ImGui.pushStyleColor(ImGuiCol.Button, 0.5f, 0.5f, 0.5f, 0.3f);
      ImGui.pushStyleColor(ImGuiCol.ButtonHovered, 0.5f, 0.5f, 0.5f, 0.5f);
      ImGui.pushStyleColor(ImGuiCol.ButtonActive, 0.5f, 0.5f, 0.5f, 1f);

      for (Folder subFolder : childFolders) {
        displayFolder(subFolder, iconSize);
        ImGui.nextColumn();
      }


      displayAllItems(iconSize);

      ImGui.popStyleColor(3);

      ImGui.popStyleColor();
      ImGui.popStyleVar();

      ImGui.endChild();
    }
  }

  protected void displayAllItems(float iconSize) {
    for (Class<?> item : items) {
      try {
        displayItem(item, iconSize);
        ImGui.nextColumn();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }


  private void displayFolder(Folder folder, float iconSize) {

    Sprite icon = folder.getIcon();
    String name = folder.name;

    if (icon != null) {

      ImGui.pushID(name);

      if (BImGui.imageButton(icon, iconSize, iconSize)) {
        contentBrowser.currentFolder = folder;
      }
      ImGui.popID();

      // Center the text below the image
      ImVec2 textSize = new ImVec2();
      ImGui.calcTextSize(textSize, name);
      ImGui.setCursorPosX(ImGui.getCursorPosX() + (iconSize - textSize.x) / 2);

      // Shows a text below the image
      ImGui.text(name);

    }
  }

  protected void displayItem(Class<?> item, float iconSize) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

    // Get the icon field from the actor
    Actor iconActor = (Actor) item.getConstructor().newInstance();
    Sprite actorIcon = iconActor.getIcon();

    ImGui.pushID(item.getSimpleName());
    if (BImGui.imageButton(actorIcon, iconSize, iconSize)) {
      Window.getScene().getGame().getComponent(MouseControls.class).pickupObject(iconActor);
    }
    ImGui.popID();

    // Center the text below the image
    ImVec2 textSize = new ImVec2();
    ImGui.calcTextSize(textSize, item.getSimpleName());
    ImGui.setCursorPosX(ImGui.getCursorPosX() + (iconSize - textSize.x) / 2);

    // Shows a text below the image
    ImGui.text(item.getSimpleName());
  }

  public String getPath() {
    Folder tempFolder = parentFolder;
    String path = name;
    while (tempFolder != null) {
      path = tempFolder.name + "/" + path;
      tempFolder = tempFolder.parentFolder;
    }
    return path;
  }

  public List<Folder> getParents() {
    List<Folder> parents = new ArrayList<>();
    Folder tempFolder = parentFolder;
    while (tempFolder != null) {
      parents.add(tempFolder);
      tempFolder = tempFolder.parentFolder;
    }
    return parents;
  }

  public void showPath() {
    List<Folder> parents = getParents();
    Collections.reverse(parents);
    for (Folder parent : parents) {
      ImGui.sameLine();
      if (parent.parentFolder != null) {
          ImGui.text(">");
          ImGui.sameLine();
      }

      if (ImGui.button(parent.name)) {
        contentBrowser.currentFolder = parent;
      }
      if (ImGui.isItemHovered()) {
        ImGui.setTooltip(parent.getPath());
      }
    }

    if (this.parentFolder != null) {
      ImGui.sameLine();
      ImGui.text(">");
    }

    ImGui.sameLine();
    ImGui.button(name);

    if (ImGui.isItemHovered()) {
      ImGui.setTooltip(this.getPath());
    }
  }

}
