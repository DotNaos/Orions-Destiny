package Burst.Engine.Source.Core.Scene;

import Burst.Engine.Source.Core.Graphics.Render.Texture;
import Burst.Engine.Source.Core.UI.Window;
import Orion.res.Assets;
import imgui.ImGui;
import imgui.flag.ImGuiCol;
import imgui.flag.ImGuiStyleVar;
import imgui.flag.ImGuiWindowFlags;


public class StartMenuSceneInitializer extends SceneInitializer {

        private float BUTTON_WIDTH = 400;
        private float BUTTON_HEIGHT = 50;

        // button colors
        private final int BUTTON_COLOR = 0xA0;
        private final int BUTTON_HOVER_COLOR = 0x80;

        @Override
        public void imgui()
        {
            super.imgui();
            // GUI-Elemente zeichnen
            ImGui.begin("StartMenu", ImGuiWindowFlags.NoTitleBar | ImGuiWindowFlags.NoResize | ImGuiWindowFlags.NoMove);
            ImGui.setWindowPos(0, 0);
            ImGui.setWindowSize(Window.getWidth(), Window.getHeight());
            ImGui.pushStyleColor(ImGuiCol.Button, BUTTON_COLOR);
            ImGui.pushStyleColor(ImGuiCol.ButtonHovered,  255);
            ImGui.pushStyleColor(ImGuiCol.ButtonActive, 255);

            ImGui.pushStyleVar(ImGuiStyleVar.FrameRounding, 10);

            // BOrdereinstellungen
            ImGui.pushStyleVar(ImGuiStyleVar.FrameBorderSize, 1);

            ImGui.pushStyleVar(ImGuiStyleVar.ItemSpacing, 20, 20);

            Texture texture = new Texture(512, 512);
            texture.init(Assets.TEXTURES + "testImage.png");






            if (ImGui.button("Editor", BUTTON_WIDTH, BUTTON_HEIGHT)) {
                Window.changeScene(new LevelEditorSceneInitializer());
            }
            ImGui.sameLine();

            if (ImGui.button("Play", BUTTON_WIDTH, BUTTON_HEIGHT)) {
                Window.changeScene(new LevelSceneInitializer());
            }


            if (ImGui.button("Settings", BUTTON_WIDTH, BUTTON_HEIGHT)) {
                Window.changeScene(new SettingsSceneInitializer());
            }



            ImGui.imageButton(texture.getId(), 1024, 1024, 0, 1, 1, 0);

            ImGui.popStyleColor(3);
            ImGui.popStyleVar(3);
            ImGui.end();
        }

    }

