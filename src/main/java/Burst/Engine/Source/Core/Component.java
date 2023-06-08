package Burst.Engine.Source.Core;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Burst.Engine.Source.Core.Util.ImGuiValueManager;
import imgui.ImGui;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import Burst.Engine.Source.Core.Util.DebugMessage;
import Burst.Engine.Source.Core.Util.Util;
import imgui.ImVec2;
import imgui.ImVec4;
import imgui.type.ImInt;

public abstract class Component implements ImGuiValueManager {
  private long ID = -1;

  protected String name = "Component";
  protected transient boolean started = false;
  private transient boolean imGuiEditable = true;
  Map<String, Object> initialValues = new HashMap<>();
  private transient List<String> ignoreFields = new ArrayList<>();

  public Component() {
      this.ID = Util.generateHashID(this.getClass().getName());
      this.name = this.getClass().getSimpleName();
  }

  public Component(boolean imGuiEditable) {
    this();
    this.imGuiEditable = imGuiEditable;
  }

  public void start() {
    // get the initial values of this componen
    if (this.initialValues == null) {
      this.initialValues = new HashMap<>();
    }
    if (this.ignoreFields == null) {
      this.ignoreFields = new ArrayList<>();
    }
    try {
      getInitialValues(this, this.ignoreFields, this.initialValues);
    } catch (IllegalAccessException e) {
        e.printStackTrace();
    }

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

    ImGuiShowFields(this, this.ignoreFields, this.initialValues);
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
