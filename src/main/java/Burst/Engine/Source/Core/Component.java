package Burst.Engine.Source.Core;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import Burst.Engine.Source.Core.Util.ImGuiValueManager;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import Burst.Engine.Source.Core.Assets.AssetManager;
import Burst.Engine.Source.Core.UI.ImGui.BImGui;
import Burst.Engine.Source.Core.Util.DebugMessage;
import Burst.Engine.Source.Core.Util.Util;
import imgui.ImGui;
import imgui.ImVec2;
import imgui.ImVec4;
import imgui.type.ImInt;

public abstract class Component implements ImGuiValueManager {
  private long ID = -1;
  private String name = "Component";
  protected transient boolean started = false;
  private transient boolean imGuiEditable = true;

  public Component() {
      this.ID = Util.generateHashID(this.getClass().getName());
      this.name = this.getClass().getSimpleName();
  }

  public Component(boolean imGuiEditable) {
    this();
    this.imGuiEditable = imGuiEditable;
  }

  public void start() {
    // get the initial values of this component
    getInitialValues();

    started = true;
  }


  public void update(float dt) {

  }

  public void updateEditor(float dt) {

  }



  public void imgui() {
    if (!started)
    {
      started = true;
      start();
    }

    ImGuiShowFields();
  }

  public void generateId() {
    if (this.ID == -1) {
      // Generate a unique ID for components that are different to actor ids

       this.ID = Util.generateHashID(this.getClass().getName());
    }
  }

  public void destroy() {
    //TODO: Destroy component
  }

  public long getID() {
    return this.ID;
  }

  public boolean isEditableInImGui() {
    return imGuiEditable;
  }

  public void setImGuiNotEditable() {
    this.imGuiEditable = false;
  }

  public boolean isStarted() {
    return started;
  }
}
