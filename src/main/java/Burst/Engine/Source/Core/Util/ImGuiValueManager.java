package Burst.Engine.Source.Core.Util;

import Burst.Engine.Source.Core.Assets.Graphics.Sprite;
import Burst.Engine.Source.Core.Assets.Graphics.Texture;
import Burst.Engine.Source.Core.UI.ImGui.BImGui;
import imgui.ImGui;
import imgui.ImVec2;
import imgui.ImVec4;
import imgui.type.ImInt;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ImGuiValueManager {

  private boolean shouldAccess(Field field) throws IllegalAccessException {
      // Check if the field has no modifiers -> notify the developer
      boolean noModifiers = field.getModifiers() == 0;
      if (noModifiers) {
        throw new IllegalAccessException("Field '" + field.getName() + "' has no modifiers");
      }

      boolean isTransient = Modifier.isTransient(field.getModifiers());
      if (isTransient) {
        return false;
      }

      boolean isPrivate = Modifier.isPrivate(field.getModifiers());
      boolean isProtected = Modifier.isProtected(field.getModifiers());
      boolean isStatic = Modifier.isStatic(field.getModifiers());
      if (isPrivate || isProtected ||isStatic) {
        field.setAccessible(true);
      }

      return true;
    }

  default void getInitialValues(Object obj, List<String> ignoreFields, Map<String, Object> initialValues) throws IllegalAccessException {
    initialValues.clear();

    Field[] fields = obj.getClass().getDeclaredFields();
    for (Field field : fields) {

      if (!shouldAccess(field)) continue;

      try {
        Object value = field.get(obj);

        // Make a copy of the value
        value = Util.copy(value);

        initialValues.put(field.getName(), value);
      } catch (IllegalAccessException e) {


        DebugMessage.loadFail("Failed to get initial value of field: " + field.getName());
        e.printStackTrace();
      }

      revertAccess(field);
    }
  }
  default void displayField(Field field, Object obj, Map<String, Object> initialValues) throws IllegalAccessException {
      if (!shouldAccess(field)) return;


      Object value = field.get(obj);
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
        field.set(obj, BImGui.dragInt(name, val, (int) initialValue));
      }
      else if (type.equals(float.class))
      {
        float val =  (float) value;
        field.set(obj, BImGui.dragFloat(name, val, (float) initialValue));
      }
      else if (type.equals(double.class))
      {
        double val = (double) value;
        field.set(obj, BImGui.dragDouble(name, val, (double) initialValue));
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
          field.set(obj, !val);
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

        int defaultSpriteWidth = 128;

        float textureWidth = val.getWidth();
        float textureHeight = val.getHeight();

        float spriteWidthRatio = textureWidth / defaultSpriteWidth;

        Vector2f textureSize = new Vector2f(defaultSpriteWidth,  textureHeight / spriteWidthRatio);

        // if hovered show preview of texture
        // Also show file path below
        ImGui.image(val.getTexID(), textureSize.x, textureSize.y);
        if (ImGui.isItemHovered())
        {
          ImGui.beginTooltip();
          ImGui.image(val.getTexID(), textureSize.x * 1.5f, textureSize.y * 1.5f);
          ImGui.text(val.getFilepath());
          ImGui.endTooltip();
        }
      }
      else if (type.equals(Sprite.class))
      {
        Sprite val = (Sprite) value;
        Vector2f[] texCoords = val.getTexCoords();

        float uv0X = texCoords[1].x;
        float uv0Y = texCoords[1].y;
        float uv1X = texCoords[3].x;
        float uv1Y  = texCoords[3].y;

        int defaultSpriteWidth = 256;

        float spriteWidth = val.getWidth();
        float spriteHeight = val.getHeight();

        float spriteWidthRatio = spriteWidth / defaultSpriteWidth;

        Vector2f spriteSize = new Vector2f(defaultSpriteWidth,  spriteHeight / spriteWidthRatio);


        // if hovered show preview of texture
        // Also show file path below
        ImGui.image(val.getTexID(), spriteSize.x, spriteSize.y, uv0X, uv0Y, uv1X, uv1Y);
        if (ImGui.isItemHovered())
        {
          ImGui.beginTooltip();
          ImGui.image(val.getTexID(), spriteSize.x * 1.5f, spriteSize.y * 1.5f, uv0X, uv0Y, uv1X, uv1Y);
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
          field.set(obj, type.getEnumConstants()[index.get()]);
        }
      }
      else if (type == String.class)
      {
        field.set(obj, BImGui.inputText(field.getName() + ": ", (String) value));
      }

      ImGui.tableNextRow();

      revertAccess(field);
    }

  private static void revertAccess(Field field) {
    boolean isPublic = Modifier.isPublic(field.getModifiers());
    if (!isPublic) {
      field.setAccessible(false);
    }
  }

  default void ImGuiShowFields(Object obj, List<String> ignoreFields,  Map<String, Object> initialValues) {
      try {
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {

          // Do not display ignored fields
          if (ignoreFields.contains(field.getName()) || field.getName().equals("$assertionsDisabled")) continue;

          displayField(field, obj, initialValues);
        }
      } catch (IllegalAccessException e) {
        e.printStackTrace();
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