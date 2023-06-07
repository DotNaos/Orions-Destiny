package Burst.Engine.Source.Core.Util;

import Burst.Engine.Source.Core.Assets.Graphics.Texture;
import Burst.Engine.Source.Core.Component;
import Burst.Engine.Source.Core.UI.ImGui.BImGui;
import com.google.gson.JsonArray;
import imgui.ImGui;
import imgui.type.ImInt;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Map;

public interface ImGuiValueManager {
    void getInitialValues();
    void ImGuiShowFields();

    default void displayField(Field field, Component c, Map<String, Object> initialValues) throws IllegalAccessException {
      boolean isTransient = Modifier.isTransient(field.getModifiers());
      if (isTransient) {
        return;
      }

      boolean isPrivate = Modifier.isPrivate(field.getModifiers());
      boolean isProtected = Modifier.isProtected(field.getModifiers());
      boolean isStatic = Modifier.isStatic(field.getModifiers());
      if (isPrivate || isProtected ||isStatic) {
          field.setAccessible(true);
      }

      Object value = field.get(c);
      Class type = field.getType();
      String name = field.getName();

      Object initialValue = initialValues.get(name);
      if (initialValue == null) {
        initialValues.put(name, value);
        initialValue = value;
      }

      ImGui.tableNextColumn();
      ImGui.text(field.getName());
      ImGui.tableNextColumn();
      if (type.equals(int.class)) {
        int val = (int) value;
        field.set(this, BImGui.dragInt(name, val, (int) initialValue));
      }
      else if (type.equals(float.class))
      {
        float val =  (float) value;
        field.set(this, BImGui.dragFloat(name, val, (float) initialValue));
      }
      else if (type.equals(double.class))
      {
        double val = (double) value;
        field.set(this, BImGui.dragDouble(name, val, (double) initialValue));
      }
      else if (type.equals(long.class))
      {
        long val = (long) value;
        ImGui.text(String.valueOf(val));
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

        BImGui.drawVec2Control(name, val, new Vector2f((Vector2f) initialValue));
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
      else if (type.equals(Texture.class))
      {
        Texture val = (Texture) value;
        // if hovered show preview of texture
        // Also show file path below
        ImGui.image(val.getTexID(), 64, 64);
        if (ImGui.isItemHovered())
        {
          ImGui.beginTooltip();
          ImGui.image(val.getTexID(), 128, 128);
          ImGui.text(val.getFilepath());
          ImGui.endTooltip();
        }
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

    default  <T extends Enum<T>> String[] getEnumValues(Class<T> enumType) {
      String[] enumValues = new String[enumType.getEnumConstants().length];
      int i = 0;
      for (T enumIntegerValue : enumType.getEnumConstants()) {
        enumValues[i] = enumIntegerValue.name();
        i++;
      }
      return enumValues;
    }

    default int indexOf(String str, String[] arr) {
      for (int i = 0; i < arr.length; i++) {
        if (str.equals(arr[i])) {
          return i;
        }
      }

      return -1;
    }
}
