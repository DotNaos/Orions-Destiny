package Burst.Engine.Source.Core.UI.ImGui;

import Burst.Engine.Config.Constants.Color_Config;
import Burst.Engine.Source.Core.Assets.AssetManager;
import Burst.Engine.Source.Core.Assets.Graphics.Sprite;
import Burst.Engine.Source.Core.Assets.Graphics.SpriteSheet;
import Orion.res.AssetConfig;
import imgui.ImGui;
import imgui.flag.ImGuiCol;
import imgui.flag.ImGuiStyleVar;
import imgui.type.ImString;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

/**
 * This class has various methods to draw ImGui controls.
 */
public class BImGui {
    private static Vector2f defaultItemSpacing = new Vector2f(5.0f, 0.0f);
    private static Vector2f defaultButtonSize = new Vector2f(18, 18);
    private static Vector2f defaultTextBoxSize = new Vector2f(100, 18);

    public static void drawVec2Control(String label, Vector2f values) {
        drawVec2Control(label, values, new Vector2f());
    }

    public static void drawVec2Control(String label, Vector2f values, Vector2f resetValue) {
        ImGui.pushID(label);
        ImGui.pushStyleVar(ImGuiStyleVar.ItemSpacing, defaultItemSpacing.x, defaultItemSpacing.y);

        if (resetButton(defaultButtonSize, "Red")){
            values.x = resetValue.x;
        }


        ImGui.sameLine();
        float[] vecValuesX = {values.x};
        ImGui.pushItemWidth(defaultTextBoxSize.x);
        ImGui.dragFloat("##X", vecValuesX, 0.1f);
        ImGui.popItemWidth();
        
        ImGui.sameLine();

        if (resetButton(defaultButtonSize, "Green")){
            values.y = resetValue.y;
        }

        ImGui.sameLine();
        float[] vecValuesY = {values.y};
        ImGui.pushItemWidth(defaultTextBoxSize.x);
        ImGui.dragFloat("##Y", vecValuesY, 0.1f);
        ImGui.popItemWidth();
        

        values.x = vecValuesX[0];
        values.y = vecValuesY[0];

        ImGui.popStyleVar();
        ImGui.popID();
    }

    public static void drawVec3Control(String label, Vector3f values) {
        drawVec3Control(label, values, new Vector3f());
    }

    public static void drawVec3Control(String label, Vector3f values, Vector3f resetValue) {
        ImGui.pushID(label);
        ImGui.pushStyleVar(ImGuiStyleVar.ItemSpacing, defaultItemSpacing.x, defaultItemSpacing.y);


        if (resetButton(defaultButtonSize, "Red")){
            values.x = resetValue.x;
        }


        ImGui.sameLine();
        float[] vecValuesX = {values.x};
        ImGui.pushItemWidth(defaultTextBoxSize.x);
        ImGui.dragFloat("##X", vecValuesX, 0.1f);
        ImGui.popItemWidth();
        
        ImGui.sameLine();

        if (resetButton(defaultButtonSize, "Green")){
            values.y = resetValue.y;
        }

        ImGui.sameLine();
        float[] vecValuesY = {values.y};
        ImGui.pushItemWidth(defaultTextBoxSize.x);
        ImGui.dragFloat("##Y", vecValuesY, 0.1f);
        ImGui.popItemWidth();
        
        ImGui.columns(1);
        ImGui.sameLine();

        if (resetButton(defaultButtonSize, "Blue")){
            values.z = resetValue.z;
        }

        ImGui.sameLine();
        float[] vecValuesZ = {values.z};
        ImGui.pushItemWidth(defaultTextBoxSize.x);
        ImGui.dragFloat("##Z", vecValuesZ, 0.1f);
        ImGui.popItemWidth();
        

        values.x = vecValuesX[0];
        values.y = vecValuesY[0];
        values.z = vecValuesZ[0];

        ImGui.popStyleVar();
        ImGui.popID();
    }

    public static float dragFloat(String label, float value, float resetValue) {
        ImGui.pushID(label);
        ImGui.pushStyleVar(ImGuiStyleVar.ItemSpacing, defaultItemSpacing.x, defaultItemSpacing.y);

        boolean reset = resetButton(defaultButtonSize);

        ImGui.sameLine();
        float[] valArr = {value};
        ImGui.pushItemWidth(defaultTextBoxSize.x);
        ImGui.dragFloat("##*", valArr, 0.1f);
        ImGui.popItemWidth();
        

        ImGui.popStyleVar();
        ImGui.popID();

        return reset ? resetValue : valArr[0];
    }

    public static double dragDouble(String label, double value, double resetValue) {
        return dragFloat(label, (float) value, (float) resetValue);
    }

    public static int dragInt(String label, int value, int resetValue)
    {
        ImGui.pushID(label);
        ImGui.pushStyleVar(ImGuiStyleVar.ItemSpacing, defaultItemSpacing.x, defaultItemSpacing.y);

        boolean reset = resetButton(defaultButtonSize);

        ImGui.sameLine();
        int[] valArr = {value};
        // Set the Width of the DragInt to a fixed value
        ImGui.pushItemWidth(defaultTextBoxSize.x);
        ImGui.dragInt("##*", valArr, 0.1f);
        ImGui.popItemWidth();

        ImGui.popStyleVar();
        ImGui.popID();

        return reset ? resetValue : valArr[0];
    }


    /**
     * <p>
     *   Calculates the position of the sprite based on the color
     * </p>
     * @param Color
     * <ul>
     *  <li>(Blue) : 1 <br>X : 3 | Y : 10</li>
     *  <li>(Black) : 2 <br>X : 3 | Y : 4</li>
     *  <li>(Yellow) : 3 <br>X : 6 | Y : 4</li>
     *  <li>(Cyan) : 4 <br>X : 6 | Y : 10</li>
     *  <li>(Red) : 5 <br>X : 9 | Y : 4</li>
     *  <li>(Pink) : 6 <br>X : 9 | Y : 10</li>
     *  <li>(Green) : 7 <br>X : 12 | Y : 4</li>
     *  <li>(Purple) : 8 <br>X : 12 | Y : 10</li>
     * </ul>
     * @param size Size of the button
     * @return
     */
    private static boolean resetButton(Vector2f size, int Color) {
        SpriteSheet sprites = AssetManager.getAssetFromType(AssetConfig.BUTTONS, SpriteSheet.class);

        if (Color > 8) {
            Color = 8;
        } else if (Color < 1) {
            Color = 1;
        }

        int col = Color % 2 == 0 ? 10 : 4;
        int colorRow = Color % 2 == 0 ? Color / 2 : (Color + 1) / 2;
        int row = colorRow * 3;


        Sprite sprite = sprites.getSprite(row , col);
        Vector2f[] texCoords = sprite.getTexCoords();
        boolean reset = ImGui.imageButton(sprite.getTexId(), size.x, size.y, texCoords[2].x, texCoords[0].y, texCoords[0].x, texCoords[2].y);
        return reset;
    }

    private static boolean resetButton(Vector2f size) {
        return resetButton(size, 2);
    }

    private static boolean resetButton(Vector2f size, String Color) {
        return switch (Color) {
            case "Blue" -> resetButton(size, 1);
            case "Black" -> resetButton(size, 2);
            case "Yellow" -> resetButton(size, 3);
            case "Cyan" -> resetButton(size, 4);
            case "Red" -> resetButton(size, 5);
            case "Pink" -> resetButton(size, 6);
            case "Green" -> resetButton(size, 7);
            case "Purple" -> resetButton(size, 8);

            // Defaults to black
            default -> resetButton(size, 2);
        };

    }
    private static void buttonColor(Vector4f color) {
        ImGui.pushStyleColor(ImGuiCol.Button, color.x * 0.8f, color.y * 0.8f, color.z * 0.8f, color.w);
        ImGui.pushStyleColor(ImGuiCol.ButtonHovered, color.x * 0.9f, color.y * 0.9f, color.z * 0.9f, color.w);
        ImGui.pushStyleColor(ImGuiCol.ButtonActive, color.x, color.y, color.z, color.w);
    }

    public static boolean colorPicker4(String label, Vector4f color) {
        boolean res = false;
        ImGui.pushID(label);

        float[] imColor = {color.x, color.y, color.z, color.w};
        if (ImGui.colorEdit4("##colorPicker", imColor)) {
            color.set(imColor[0], imColor[1], imColor[2], imColor[3]);

            // Copies the color to the clipboard
            String hex = String.format("0x%02X%02X%02X%02X",
                    (int) (color.x * 255),
                    (int) (color.y * 255),
                    (int) (color.z * 255),
                    (int) (color.w * 255));
            ImGui.setClipboardText(hex);

            res = true;
        }
        ImGui.popID();
        return res;
    }

    public static String inputText(String label, String text) {
        ImGui.pushID(label);

        ImString outString = new ImString(text, 256);
        if (ImGui.inputText("##" + label, outString)) {
            ImGui.columns(1);
            ImGui.popID();

            return outString.get();
        }

        ImGui.popID();
        return text;
    }


}
