package Burst.Engine.Source.Core.Assets.Graphics;

import Burst.Engine.Source.Core.Assets.Asset;
import Orion.res.AssetConfig;
import imgui.ImFont;
import imgui.ImGui;


public class Font extends Asset {
    private ImFont imGuiFont;
    private float FontSize = 20;

    public Font(String filepath) {
        super(filepath);
    }

    @Override
    public Asset build() {
//        ImFont font = ImGui.getIO().getFonts().addFontFromFileTTF(filepath, FontSize);

        return this;
    }

    public ImFont getImGuiFont() {
        if (imGuiFont == null) {
//            imGuiFont = ImGui.getIO().getFonts().addFontFromFileTTF(filepath, FontSize);
        }
        return imGuiFont;
    }

    public void setFontSize(float size) {
        FontSize = size;
//        this.imGuiFont = ImGui.getIO().getFonts().addFontFromFileTTF(filepath, FontSize);
    }
}
