package Burst.Engine.Source.Core;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
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
  private String name = "Component";
  protected transient boolean started = false;
  private transient boolean imGuiEditable = true;
  Map<String, Object> initialValues = new HashMap<>();

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

  @Override
  public void getInitialValues()
  {
    initialValues = new HashMap<>();

    Field[] fields = this.getClass().getDeclaredFields();
    for (Field field : fields) {
      boolean isTransient = Modifier.isTransient(field.getModifiers());
      if (isTransient) {
        continue;
      }

      boolean isPrivate = Modifier.isPrivate(field.getModifiers());
      if (isPrivate) {
        field.setAccessible(true);
      }

      try {
        Object value = field.get(this);

        // Make a copy of the value
        if (value instanceof Vector2f) {
          value = new Vector2f((Vector2f) value);
        } else if (value instanceof Vector3f) {
          value = new Vector3f((Vector3f) value);
        } else if (value instanceof Vector4f) {
          value = new Vector4f((Vector4f) value);
        } else if (value instanceof ImInt) {
          value = new ImInt((ImInt) value);
        } else if (value instanceof ImVec2) {
          value = new ImVec2((ImVec2) value);
        } else if (value instanceof ImVec4) {
          value = new ImVec4((ImVec4) value);
        }
        initialValues.put(field.getName(), value);
      } catch (IllegalAccessException e) {
        DebugMessage.loadFail("Failed to get initial value of field: " + field.getName());
      }

      if (isPrivate) {
        field.setAccessible(false);
      }
    }
  }

  @Override
  public void ImGuiShowFields() {
    try {
      Field[] fields = this.getClass().getDeclaredFields();
      for (Field field : fields) {
        displayField(field, this, initialValues);
      }
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }
  }
}
