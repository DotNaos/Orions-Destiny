package Burst.Engine.Source.Core;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

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

public abstract class Component {
  protected transient String filePath = null;
  private long ID = -1;
  private transient boolean started = false;
  protected Map<String, Object> initialValues;
  private transient boolean imGuiEditable = true;

  public Component() {
    this.ID = Util.generateUniqueID();
  }

  public Component(boolean imGuiEditable) {
    this();
    this.imGuiEditable = imGuiEditable;
  }

  public void start() {
    // get the filepath of this component
    this.filePath = AssetManager.getFilePath(this);
    // get the initial values of this component
    this.initialValues = new HashMap<>();

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

    try {
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

        Class type = field.getType();
        Object value = field.get(this);
        String name = field.getName();

        ImGui.tableNextColumn();
        ImGui.text(field.getName());
        ImGui.tableNextColumn();
        if (type.equals(int.class)) {
          int val = (int) value;
          field.set(this, BImGui.dragInt(name, val, (int) initialValues.get(field.getName())));
        }
        else if (type.equals(float.class))
        {
          float val =  (float)value;
          field.set(this, BImGui.dragFloat(name, val, (float) initialValues.get(field.getName())));
        }
        else if (type.equals(boolean.class))
        {
          boolean val = (boolean) value;
          ImGui.pushID(name);
          if (ImGui.checkbox("", val))
          {
            field.set(this, !val);
          }
          ImGui.popID();
        }
        else if (type.equals(Vector2f.class))
        {
          Vector2f val = (Vector2f) value;

          BImGui.drawVec2Control(name, val, new Vector2f((Vector2f) initialValues.get(field.getName())));
        }
        else if (type.equals(Vector3f.class))
        {
          Vector3f val = (Vector3f) value;
          BImGui.drawVec3Control(name, val);
        }
        else if (type.equals(Vector4f.class))
        {
          Vector4f val = (Vector4f) value;
          BImGui.colorPicker4(name, val);
        }
        else if (type.isEnum())
        {
          String[] enumValues = getEnumValues(type);
          String enumType = ((Enum) value).name();
          ImInt index = new ImInt(indexOf(enumType, enumValues));

          if (ImGui.combo(field.getName(), index, enumValues, enumValues.length))
          {
            field.set(this, type.getEnumConstants()[index.get()]);
          }
        }
        else if (type == String.class)
        {
          field.set(this, BImGui.inputText(field.getName() + ": ", (String) value));
        }

        ImGui.tableNextRow();

        if (isPrivate) {
          field.setAccessible(false);
        }
      }
    } catch (IllegalAccessException e) {
//      System.out.println("Error: Could not access field: " + e.getMessage());
    }
  }

  public void generateId() {
    if (this.ID == -1) {
      this.ID = Util.generateUniqueID();
    }
  }

  private <T extends Enum<T>> String[] getEnumValues(Class<T> enumType) {
    String[] enumValues = new String[enumType.getEnumConstants().length];
    int i = 0;
    for (T enumIntegerValue : enumType.getEnumConstants()) {
      enumValues[i] = enumIntegerValue.name();
      i++;
    }
    return enumValues;
  }

  private int indexOf(String str, String[] arr) {
    for (int i = 0; i < arr.length; i++) {
      if (str.equals(arr[i])) {
        return i;
      }
    }

    return -1;
  }

  public void destroy() {

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
}
