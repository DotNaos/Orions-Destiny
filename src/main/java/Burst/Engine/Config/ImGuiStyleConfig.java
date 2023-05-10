package Burst.Engine.Config;

import imgui.ImGui;
import imgui.ImGuiStyle;
import imgui.flag.ImGuiCol;
import imgui.flag.ImGuiDir;

public class ImGuiStyleConfig {
    static boolean active = true;
    static boolean dark = true;
    public static void style()
    {
        if (!active) return;

        ImGuiStyle style = ImGui.getStyle();
        style.setFrameRounding(4);
        style.setWindowRounding(10);
        style.setScrollbarRounding(0);
        style.setScrollbarSize(15);
        style.setWindowBorderSize(0);
        style.setChildBorderSize(0);
        style.setPopupBorderSize(0);
        style.setFrameBorderSize(0);
        style.setTabBorderSize(1);
        style.setTabRounding(2);
        style.setWindowPadding(8, 8);
        style.setFramePadding(4, 3);
        style.setItemSpacing(8, 4);
        style.setItemInnerSpacing(4, 4);
        style.setIndentSpacing(21);
        style.setScrollbarSize(15);
        style.setGrabMinSize(5);
        style.setWindowMenuButtonPosition(ImGuiDir.None);
        style.setChildRounding(0);
        style.setPopupRounding(0);
        style.setScrollbarRounding(0);

        // Background
            style.setColor(ImGuiCol.WindowBg,  (0xFF1A1A1A));
            style.setColor(ImGuiCol.DockingEmptyBg, 0x00000000);
            style.setColor(ImGuiCol.PopupBg, 0.08f, 0.08f, 0.08f, 0.9f);


        // Tabs
            style.setColor(ImGuiCol.Tab, 0.9f, 0.1f, 0.2f, 0.1f);
            style.setColor(ImGuiCol.TabHovered, 0.9f, 0.1f, 0.2f, 0.5f);
            style.setColor(ImGuiCol.TabActive, 0.9f, 0.1f, 0.2f, 0.4f);
            style.setColor(ImGuiCol.TabUnfocused, 0.9f, 0.1f, 0.2f, 0.1f);
            style.setColor(ImGuiCol.TabUnfocusedActive, 0.9f, 0.1f, 0.2f, 0.1f);

        // Title
            style.setColor(ImGuiCol.TitleBg, 0.15f, 0.15f, 0.15f, 1.0f);
            style.setColor(ImGuiCol.TitleBgActive, 0.2f, 0.2f, 0.2f, 1.0f);
            style.setColor(ImGuiCol.TitleBgCollapsed, 0.15f, 0.15f, 0.15f, 1.0f);

            style.setColor(ImGuiCol.MenuBarBg, 0.0f, 0.0f, 0.0f, 0.0f);

        // Header
            style.setColor(ImGuiCol.Header, 0.4f, 0.4f, 0.4f, 0.4f);
            style.setColor(ImGuiCol.HeaderHovered, 0.4f, 0.4f, 0.4f, 0.2f);
            style.setColor(ImGuiCol.HeaderActive, 0.4f, 0.4f, 0.4f, 0.4f);

        // Frame
            style.setColor(ImGuiCol.FrameBg, 0.13f, 0.13f, 0.13f, 0.9f);
            style.setColor(ImGuiCol.FrameBgHovered, 0.24f, 0.24f, 0.24f, 0.5f);
            style.setColor(ImGuiCol.FrameBgActive, 0.24f, 0.24f, 0.24f, 0.5f);


        // Border
            style.setColor(ImGuiCol.Border, 0.9f, 0.1f, 0.2f, 0.3f);
            style.setColor(ImGuiCol.BorderShadow, 0.9f, 0.1f, 0.2f, 1f);

        // Seperator
            style.setColor(ImGuiCol.Separator , 0.0f, 0.0f, 0.0f, 0.0f);
            style.setColor(ImGuiCol.SeparatorHovered , 0.0f, 0.0f, 0.0f, 0.0f);
            style.setColor(ImGuiCol.SeparatorActive , 0.0f, 0.0f, 0.0f, 0.0f);

        // Text
            style.setColor(ImGuiCol.TextSelectedBg, 0.26f, 0.59f, 0.98f, 0.35f);
            style.setColor(ImGuiCol.ModalWindowDimBg, 0.0f, 0.0f, 0.0f, 0.6f);
            style.setColor(ImGuiCol.Text, 1.0f, 1.0f, 1.0f, 1.0f);
            style.setColor(ImGuiCol.TextDisabled, 0.5f, 0.5f, 0.5f, 1.0f);




        // Button
            style.setColor(ImGuiCol.Button, 0.0f, 0.0f, 0.0f, 0.0f);
            style.setColor(ImGuiCol.ButtonHovered, 0.4f, 0.4f, 0.4f, 0.2f);
            style.setColor(ImGuiCol.ButtonActive, 0.4f, 0.4f, 0.4f, 0.4f);

        // Check
            style.setColor(ImGuiCol.CheckMark, 0.9f, 0.9f, 0.9f, 1.0f);

        // Slider
            style.setColor(ImGuiCol.SliderGrab, 0.9f, 0.9f, 0.9f, 0.4f);
            style.setColor(ImGuiCol.SliderGrabActive, 0.9f, 0.9f, 0.9f, 1.0f);

        // Resizer
            style.setColor(ImGuiCol.ResizeGrip, 0.9f, 0.1f, 0.2f, 0.4f);
            style.setColor(ImGuiCol.ResizeGripHovered, 0.9f, 0.1f, 0.2f, 0.5f);
            style.setColor(ImGuiCol.ResizeGripActive, 0.9f, 0.1f, 0.2f, 0.6f);




        // Plot
            style.setColor(ImGuiCol.PlotLines, 0.6f, 0.6f, 0.6f, 1.0f);
            style.setColor(ImGuiCol.PlotLinesHovered, 1.0f, 0.43f, 0.35f, 1.0f);
            style.setColor(ImGuiCol.PlotHistogram, 0.9f, 0.9f, 0.9f, 1.0f);
            style.setColor(ImGuiCol.PlotHistogramHovered, 1.0f, 0.43f, 0.35f, 1.0f);

    }
}
