package Burst.Engine.Source.Core.Util;

import Burst.Engine.Source.Core.Assets.Graphics.Sprite;
import Burst.Engine.Source.Core.Assets.Graphics.SpriteSheet;
import Burst.Engine.Source.Core.Assets.Graphics.Texture;
import Burst.Engine.Source.Core.UI.ImGui.BImGui;
import Burst.Engine.Source.Core.UI.Window;
import imgui.ImGui;
import imgui.flag.ImGuiStyleVar;
import imgui.type.ImInt;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface ImGuiValueManager {

  private static void revertAccess(Field field) {
    boolean isPublic = Modifier.isPublic(field.getModifiers());
    if (!isPublic) {
      field.setAccessible(false);
    }
  }

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
    if (isPrivate || isProtected || isStatic) {
      field.setAccessible(true);
    }

    return true;
  }

  default void getInitialValues(Object obj, List<String> ignoreFields, Map<String, Object> initialValues) throws IllegalAccessException {
    initialValues.clear();

    List<Field> fields = new ArrayList<>();
    Class<?> clazz = obj.getClass();
    // Also add all superclass fields from all superclasses
    Class<?> superClass = clazz.getSuperclass();
    while (superClass.getSuperclass() != null) {
      fields.addAll(List.of(superClass.getDeclaredFields()));
      superClass = superClass.getSuperclass();
    }

    fields.addAll(List.of(clazz.getDeclaredFields()));

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
    } else if (type.equals(float.class)) {
      float val = (float) value;
      field.set(obj, BImGui.dragFloat(name, val, (float) initialValue));
    } else if (type.equals(double.class)) {
      double val = (double) value;
      field.set(obj, BImGui.dragDouble(name, val, (double) initialValue));
    } else if (type.equals(long.class)) {
      long val = (long) value;
      ImGui.text(String.valueOf(val));
    } else if (type.equals(boolean.class)) {
      boolean val = (boolean) value;
      ImGui.pushID(name);
      if (ImGui.checkbox("", val)) {
        field.set(obj, !val);
      }
      ImGui.popID();
    } else if (type.equals(String.class)) {
      String val = (String) value;
      field.set(obj, BImGui.inputText(name, val));
    } else if (type.equals(Vector2f.class)) {
      Vector2f val = (Vector2f) value;
      BImGui.drawVec2Control(name, val, new Vector2f((Vector2f) initialValue));
    } else if (type.equals(Vector3f.class)) {
      Vector3f val = (Vector3f) value;
      BImGui.drawVec3Control(name, val);
    } else if (type.equals(Vector4f.class)) {
      Vector4f val = (Vector4f) value;
      BImGui.colorPicker4(name, val);
    } else if (type.equals(Texture.class)) {
      Texture val = (Texture) value;

      int defaultSpriteWidth = 128;

      float textureWidth = val.getWidth();
      float textureHeight = val.getHeight();

      float spriteWidthRatio = textureWidth / defaultSpriteWidth;

      Vector2f textureSize = new Vector2f(defaultSpriteWidth, textureHeight / spriteWidthRatio);

      // if hovered show preview of texture
      // Also show file path below
      if (ImGui.imageButton(val.getTexID(), textureSize.x, textureSize.y)) {
        ImGui.openPopup("Texture Preview");
      }
      if (ImGui.beginPopup("Texture Preview")) {
        Window.openPopup();
        ImGui.image(val.getTexID(), textureSize.x * 1.5f, textureSize.y * 1.5f);
        // give the ability to change the texture
        if (ImGui.button("Change Texture")) {
          Window.openDialog();
          String filepath = BImGui.openFile("png");
          if (filepath != null) {
            if (val.setFilepath(filepath)) {
              val.init();
            }
          }
          Window.closeDialog();
        }

        ImGui.text(val.getFilepath());
        ImGui.endPopup();
        Window.closePopup();
      }


    } else if (type.equals(Sprite.class)) {
      Sprite val = (Sprite) value;
      Vector2f[] texCoords = val.getTexCoords();

      float uv0X = texCoords[1].x;
      float uv0Y = texCoords[1].y;
      float uv1X = texCoords[3].x;
      float uv1Y = texCoords[3].y;

      int defaultSpriteWidth = 256;

      float spriteWidth = val.getWidth();
      float spriteHeight = val.getHeight();

      float spriteWidthRatio = spriteWidth / defaultSpriteWidth;

      Vector2f spriteSize = new Vector2f(defaultSpriteWidth, spriteHeight / spriteWidthRatio);

      // if hovered show preview of texture
      // Also show file path below
      if (ImGui.imageButton(val.getTexture().getTexID(), spriteSize.x, spriteSize.y, uv0X, uv0Y, uv1X, uv1Y)) {
        ImGui.openPopup("Sprite Preview");
      }
      if (ImGui.beginPopup("Sprite Preview")) {
        Window.openPopup();

        ImGui.image(val.getTexture().getTexID(), spriteSize.x * 1.5f, spriteSize.y * 1.5f, uv0X, uv0Y, uv1X, uv1Y);

        // give the ability to change the texture
        if (ImGui.button("Change Texture")) {
          Window.openDialog();
          String filepath = BImGui.openFile("png");
          if (filepath != null) {
            val.setTexture(new Texture(filepath));
          }
          Window.closeDialog();
        }

        ImGui.text(val.getTexture().getFilepath());


        // give the ability to change the texture coords

        // Create a new Font

        ImGui.text("Texture Coords");


        BImGui.drawVec2Control("TexCord 1", texCoords[0]);
        BImGui.drawVec2Control("TexCord 2", texCoords[1]);
        BImGui.drawVec2Control("TexCord 3", texCoords[2]);
        BImGui.drawVec2Control("TexCord 4", texCoords[3]);

        val.setTexCoords(texCoords);

        Vector2f defaultItemSpacing = BImGui.getDefaultItemSpacing();
        Vector2f defaultTextBoxSize = BImGui.getDefaultTextBoxSize();


        ImGui.pushStyleVar(ImGuiStyleVar.ItemSpacing, defaultItemSpacing.x, defaultItemSpacing.y);

        if (BImGui.resetButton(BImGui.getDefaultButtonSize(), "purple")) {
          val.resetTexCoords();
        }
        ImGui.sameLine();
        ImGui.pushItemWidth(defaultTextBoxSize.x);
        ImGui.text("Reset");
        ImGui.popItemWidth();

        if (BImGui.resetButton(BImGui.getDefaultButtonSize(), "BLUE")) {
          val.setTexCoords(new Vector2f[]{new Vector2f(1, 1), new Vector2f(1, 0), new Vector2f(0, 0), new Vector2f(0, 1)});

        }
        ImGui.sameLine();
        ImGui.pushItemWidth(defaultTextBoxSize.x);
        ImGui.text("Default");
        ImGui.popItemWidth();

        ImGui.popStyleVar();


        ImGui.endPopup();
        Window.closePopup();
      }


    } else if (type.equals(SpriteSheet.class)) {
      SpriteSheet val = (SpriteSheet) value;
        // Only show the first sprite
      if (val == null) {
          ImGui.text("None");
      }
      else {
        if (val.getTexture() != null) {
          ImGui.text(val.getTexture().getFilepath());

          if(ImGui.button("Refresh")) {
            val.init();

//            val.refresh();
          }

          ImGui.image(val.getTexture().getTexID(), 128, 128);
        }
      }

    } else if (type.isEnum()) {
      String[] enumValues = getEnumValues(type);
      String enumType = ((Enum) value).name();
      ImInt index = new ImInt(indexOf(enumType, enumValues));

      if (ImGui.combo(field.getName(), index, enumValues, enumValues.length)) {
        field.set(obj, type.getEnumConstants()[index.get()]);
      }
    } else if (type == String.class) {
      field.set(obj, BImGui.inputText(field.getName() + ": ", (String) value));
    }

    ImGui.tableNextRow();

    revertAccess(field);
  }

  default void ImGuiShowFields(Object obj, List<String> ignoreFields, Map<String, Object> initialValues) {
    try {
      Class<?> clazz = obj.getClass();
      List<Field> fields = new ArrayList<>();

      // Also add all superclass fields from all superclasses
      Class<?> superClass = clazz.getSuperclass();
      while (superClass.getSuperclass() != null) {
        fields.addAll(List.of(superClass.getDeclaredFields()));
        superClass = superClass.getSuperclass();
      }

      fields.addAll(List.of(clazz.getDeclaredFields()));
      for (Field field : fields) {

        // Do not display ignored fields
        if (ignoreFields.contains(field.getName()) || field.getName().equals("$assertionsDisabled") || field.getName().equals("type") || field.getName().equals("icon"))
          continue;

        displayField(field, obj, initialValues);
      }
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }
  }

  default <T extends Enum<T>> String[] getEnumValues(Class<T> enumType) {
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
