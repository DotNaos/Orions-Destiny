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

        private float COLUMS = 2;
        private float ROWS = 2;
        private float buttonSpacing = 20;
        private float currentX = 0;
        private float currentY = 0;

        private float ALIGNMENT_X = 100 / 50;
        private float ALIGNMENT_Y = 100 / 50;

        private float MARGIN_Y = 200;
        private float MARGIN_X = 0;

        @Override
        public void imgui()
        {
            super.imgui();
            // Window centered in the glfw window
            ImGui.begin("StartMenu",
                     ImGuiWindowFlags.NoTitleBar |
                                    ImGuiWindowFlags.NoResize |
                                    ImGuiWindowFlags.NoMove |
                                    ImGuiWindowFlags.NoCollapse |
                                    ImGuiWindowFlags.NoScrollbar |
                                    ImGuiWindowFlags.NoScrollWithMouse
            );

//            Dock the window into the main viewport
            ImGui.setWindowPos(0, 0);
            ImGui.setWindowSize(Window.getWidth(), Window.getHeight());
            ImGui.pushStyleColor(ImGuiCol.Button, BUTTON_COLOR);
            ImGui.pushStyleColor(ImGuiCol.ButtonHovered,  255);
            ImGui.pushStyleColor(ImGuiCol.ButtonActive, 255);

            ImGui.pushStyleVar(ImGuiStyleVar.FrameRounding, 10);

            // BOrdereinstellungen
            ImGui.pushStyleVar(ImGuiStyleVar.FrameBorderSize, 1);




            currentX = (Window.getWidth() - ((BUTTON_WIDTH * COLUMS) - (buttonSpacing * (COLUMS-1)))) / ALIGNMENT_X + MARGIN_X;
            currentY = (Window.getHeight() - ((BUTTON_HEIGHT * ROWS) - (buttonSpacing * (ROWS-1)))) / ALIGNMENT_Y + MARGIN_Y;
            ImGui.setCursorPosX(currentX);
            ImGui.setCursorPosY(currentY);
            if (ImGui.button("Editor", BUTTON_WIDTH, BUTTON_HEIGHT)) {
                Window.changeScene(new LevelEditorSceneInitializer());
            }


            ImGui.sameLine();
            currentX -= -buttonSpacing - BUTTON_WIDTH;
            ImGui.setCursorPosX(currentX);
            if (ImGui.button("Play", BUTTON_WIDTH, BUTTON_HEIGHT)) {
                Window.changeScene(new LevelSceneInitializer());
            }

            currentX = (Window.getWidth() - ((BUTTON_WIDTH * COLUMS) - (buttonSpacing * (COLUMS-1)))) / ALIGNMENT_X + MARGIN_X;
            currentY += buttonSpacing + BUTTON_HEIGHT;
            ImGui.setCursorPosX(currentX);
            ImGui.setCursorPosY(currentY);
            if (ImGui.button("Settings", BUTTON_WIDTH, BUTTON_HEIGHT)) {
                Window.changeScene(new SettingsSceneInitializer());
            }


//            Texture texture = new Texture(512, 512);
//            texture.init(Assets.TEXTURES + "testImage.png");
//            ImGui.imageButton(texture.getId(), 1024, 1024, 0, 1, 1, 0);

            ImGui.popStyleColor(3);
            ImGui.popStyleVar(2);
            ImGui.end();
        }

    }

