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

    private static float defaultColumnWidth = 220.0f;

    public static void drawVec2Control(String label, Vector2f values) {
        drawVec2Control(label, values, new Vector2f(), defaultColumnWidth);
    }

    public static void drawVec2Control(String label, Vector2f values, Vector2f resetValue) {
        drawVec2Control(label, values, resetValue, defaultColumnWidth);
    }

    public static void drawVec2Control(String label, Vector2f values, Vector2f resetValue, float columnWidth) {
        ImGui.pushID(label);
        ImGui.pushStyleVar(ImGuiStyleVar.ItemSpacing, 0, 0);

        float lineHeight = ImGui.getFontSize() + ImGui.getStyle().getFramePaddingY() * 2.0f;
        Vector2f buttonSize = new Vector2f(lineHeight + 3.0f, lineHeight);
        float widthEach = (ImGui.calcItemWidth() - buttonSize.x * 2.0f) / 2.0f;

        if (resetButton("X", widthEach, buttonSize, Color_Config.RED)){
            values.x = resetValue.x;
        }


        ImGui.sameLine();
        float[] vecValuesX = {values.x};
        ImGui.dragFloat("##X", vecValuesX, 0.1f);
        ImGui.popItemWidth();
        ImGui.sameLine();

        if (resetButton("Y", widthEach, buttonSize, Color_Config.GREEN)) {
            values.y = resetValue.y;
        }

        ImGui.sameLine();
        float[] vecValuesY = {values.y};
        ImGui.dragFloat("##Y", vecValuesY, 0.1f);
        ImGui.popItemWidth();

        values.x = vecValuesX[0];
        values.y = vecValuesY[0];

        ImGui.popStyleVar();
        ImGui.popID();
    }

    public static void drawVec3Control(String label, Vector3f values) {
        drawVec3Control(label, values, new Vector3f(), defaultColumnWidth);
    }

    public static void drawVec3Control(String label, Vector3f values, Vector3f resetValue) {
        drawVec3Control(label, values, resetValue, defaultColumnWidth);
    }

    public static void drawVec3Control(String label, Vector3f values, Vector3f resetValue, float columnWidth) {
        ImGui.pushID(label);
        ImGui.pushStyleVar(ImGuiStyleVar.ItemSpacing, 0, 0);

        float lineHeight = ImGui.getFontSize() + ImGui.getStyle().getFramePaddingY() * 2.0f;
        Vector2f buttonSize = new Vector2f(lineHeight + 3.0f, lineHeight);
        float widthEach = (ImGui.calcItemWidth() - buttonSize.x * 2.0f) / 2.0f;

        if (resetButton("X", widthEach, buttonSize, Color_Config.RED)){
            values.x = resetValue.x;
        }


        ImGui.sameLine();
        float[] vecValuesX = {values.x};
        ImGui.dragFloat("##X", vecValuesX, 0.1f);
        ImGui.popItemWidth();
        ImGui.sameLine();

        if (resetButton("Y", widthEach, buttonSize, Color_Config.GREEN)) {
            values.y = resetValue.y;
        }

        ImGui.sameLine();
        float[] vecValuesY = {values.y};
        ImGui.dragFloat("##Y", vecValuesY, 0.1f);
        ImGui.popItemWidth();
        ImGui.columns(1);
        ImGui.sameLine();

        if (resetButton("Z", widthEach, buttonSize, Color_Config.BLUE)) {
            values.z = resetValue.z;
        }

        ImGui.sameLine();
        float[] vecValuesZ = {values.z};
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
        ImGui.pushStyleVar(ImGuiStyleVar.ItemSpacing, 0, 0);

        float lineHeight = ImGui.getFontSize() + ImGui.getStyle().getFramePaddingY() * 2.0f;
        Vector2f buttonSize = new Vector2f(lineHeight + 3.0f, lineHeight);
        float widthEach = (ImGui.calcItemWidth() - buttonSize.x * 2.0f) / 2.0f;

        boolean reset = resetButton("*", widthEach, buttonSize);

        ImGui.sameLine();
        float[] valArr = {value};
        ImGui.dragFloat("##*", valArr, 0.1f);
        ImGui.popItemWidth();

        ImGui.popStyleVar();
        ImGui.popID();

        return reset ? resetValue : valArr[0];
    }

    public static int dragInt(String label, int value, int resetValue)
    {
        ImGui.pushID(label);
        ImGui.pushStyleVar(ImGuiStyleVar.ItemSpacing, 0, 0);

        float lineHeight = ImGui.getFontSize() + ImGui.getStyle().getFramePaddingY() * 2.0f;
        Vector2f buttonSize = new Vector2f(lineHeight + 3.0f, lineHeight);
        float widthEach = (ImGui.calcItemWidth() - buttonSize.x * 2.0f) / 2.0f;

        boolean reset = resetButton("*", widthEach, buttonSize);

        ImGui.sameLine();
        int[] valArr = {value};
        ImGui.dragInt("##*", valArr, 0.1f);
        ImGui.popItemWidth();

        ImGui.popStyleVar();
        ImGui.popID();

        return reset ? resetValue : valArr[0];
    }

    private static boolean resetButton(String label, float widthEach, Vector2f buttonSize) {
        ImGui.pushItemWidth(widthEach);
        SpriteSheet sprites = AssetManager.getAssetFromType(AssetConfig.BUTTONS, SpriteSheet.class);
        Sprite sprite = sprites.getSprite(3 , 10);
        Vector2f[] texCoords = sprite.getTexCoords();
        boolean reset = ImGui.imageButton(sprite.getTexId(), buttonSize.x / 2f, buttonSize.y / 1.5f, texCoords[2].x, texCoords[0].y, texCoords[0].x, texCoords[2].y);
        return reset;
    }

    private static boolean resetButton(String label, float widthEach, Vector2f buttonSize, Vector4f color) {
        ImGui.pushItemWidth(widthEach);
        buttonColor(color);
        boolean reset = ImGui.button(label, buttonSize.x, buttonSize.y);
        ImGui.popStyleColor(3);
        return reset;
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
