package Burst.Engine.Source.Editor;

import Burst.Engine.Source.Core.Actor.Actor;
import Burst.Engine.Source.Core.Assets.AssetManager;
import Burst.Engine.Source.Core.Assets.Graphics.Sprite;
import Burst.Engine.Source.Core.UI.ImGui.BImGui;
import Orion.res.AssetConfig;
import imgui.ImGui;

import java.util.ArrayList;
import java.util.List;

public class Folder {
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

  public Folder imGuiTree() {
    BImGui.image(icon, 24, 24);
    ImGui.sameLine();
    if (ImGui.treeNode(name)) {
      for (Folder folder : folders) {
        folder.imGuiTree();
      }
      for (Class<?> item : items) {


        if (item.isAssignableFrom(Actor.class))
        {
          ImGui.text(item.getSimpleName());
//          try {
//            Actor iconActor = (Actor) item.getConstructor().newInstance();
//            BImGui.image(iconActor.icon, 24, 24);
//            ImGui.sameLine();
//            ImGui.text(iconActor.getClass().getSimpleName());
//          } catch (Exception e) {
//            BImGui.image(AssetManager.getAssetFromType(Sprite.class, AssetConfig.Files.Images.Icons.ACTOR), 24, 24);
//            ImGui.sameLine();
//            ImGui.text(item.getSimpleName());
//            e.printStackTrace();
//          }
        }
      }

      ImGui.treePop();
    }

    return this;
  }

  public boolean isExpanded() {
    return isExpanded;
  }
}
