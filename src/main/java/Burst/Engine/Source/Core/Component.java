package Burst.Engine.Source.Core;

import Burst.Engine.Source.Core.Actor.Actor;
import Burst.Engine.Source.Core.Assets.AssetManager;
import Burst.Engine.Source.Core.UI.ImGui.BImGui;
import Burst.Engine.Source.Core.Util.DebugMessage;
import Burst.Engine.Source.Core.Util.Util;
import imgui.ImGui;
import imgui.type.ImInt;
import org.jbox2d.dynamics.contacts.Contact;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

public abstract class Component {
  public transient Actor actor = null;
  protected transient String filePath = null;
  private long ID = -1;
  private transient boolean started = false;
  protected Map<String, Object> initialValues;

  public Component(Actor actor) {
    this.ID = Util.generateUniqueID();
    this.actor = actor;
  }

  public Component() {
    this(null);
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

  public void beginCollision(Actor collidinactorbject, Contact contact, Vector3f hitNormal) {

  }

  public void endCollision(Actor collidinactorbject, Contact contact, Vector3f hitNormal) {

  }

  public void preSolve(Actor collidinactorbject, Contact contact, Vector3f hitNormal) {

  }

  public void postSolve(Actor collidinactorbject, Contact contact, Vector3f hitNormal) {

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

        if (type == int.class) {
          int val = (int) value;
          field.set(this, BImGui.dragInt(name, val));
          ImGui.sameLine();
          if (BImGui.resetButton(field.getName())) {
            Object initialValue = initialValues.get(field.getName());
              field.set(this, initialValue);
          }
        } else if (type == float.class) {
          float val = (float) value;
          field.set(this, BImGui.dragFloat(name, val));
          ImGui.sameLine();
          if (BImGui.resetButton(field.getName())) {
            Object initialValue = initialValues.get(field.getName());
              field.set(this, initialValue);
          }
        } else if (type == boolean.class) {
          boolean val = (boolean) value;
          if (ImGui.checkbox(name, val)) {
            field.set(this, !val);
          }
        } else if (type == Vector2f.class) {
          Vector2f val = (Vector2f) value;
          BImGui.drawVec2Control(name, val);
        } else if (type == Vector3f.class) {
          Vector3f val = (Vector3f) value;
          float[] imVec = {val.x, val.y, val.z};
          if (ImGui.dragFloat3(name + ": ", imVec)) {
            val.set(imVec[0], imVec[1], imVec[2]);
          }
        } else if (type == Vector4f.class) {
          Vector4f val = (Vector4f) value;
          BImGui.colorPicker4(name, val);
        } else if (type.isEnum()) {
          String[] enumValues = getEnumValues(type);
          String enumType = ((Enum) value).name();
          ImInt index = new ImInt(indexOf(enumType, enumValues));
          if (ImGui.combo(field.getName(), index, enumValues, enumValues.length)) {
            field.set(this, type.getEnumConstants()[index.get()]);
          }
        } else if (type == String.class) {
          field.set(this, BImGui.inputText(field.getName() + ": ", (String) value));
        }


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

}
