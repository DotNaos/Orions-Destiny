package Burst.Engine.Config;

import org.joml.Vector2f;

import Burst.Engine.Source.Core.Component;
import Burst.Engine.Source.Core.Util.Scalar;
import imgui.ImGui;
import imgui.ImGuiStyle;
import imgui.flag.ImGuiCol;
import imgui.flag.ImGuiDir;

public class ImGuiStyleConfig extends Component {
    private transient boolean active = true;
    private boolean dark = true;
    private static transient ImGuiStyleConfig instance = new ImGuiStyleConfig();

    // All style
    private float frameRounding = 0.0f;
    private float childBorderSize = 1.0f;
    private float alpha = 1.0f;
    private boolean antiAliasedFill = true;
    private boolean antiAliasedLines = true;
    private boolean antiAliasedLinesUseTex = true;
    private Vector2f buttonTextAlign = new Vector2f(0.5f, 0.5f);
    private Vector2f cellPadding = new Vector2f(4.0f, 4.0f);
    private float childRounding = 0.0f;
    private float circleSegmentMaxError = 1.5f;
    private int colorButtonPosition = 0;
    private float columnsMinSpacing = 6.0f;
    private float curveTessellationTol = 1.25f;
    private Vector2f displaySafeAreaPadding = new Vector2f(3.0f, 3.0f);
    private Vector2f displayWindowPadding = new Vector2f(4.0f, 4.0f);
    private float frameBorderSize = 1.0f;
    private Vector2f framePadding = new Vector2f(8.0f, 4.0f);
    private float grabMinSize = 10.0f;
    private float grabRounding = 0.0f;
    private float indentSpacing = 21.0f;
    private Vector2f itemInnerSpacing = new Vector2f(4.0f, 4.0f);
    private Vector2f itemSpacing = new Vector2f(8.0f, 4.0f);
    private float logSliderDeadzone = 4.0f;
    private float mouseCursorScale = 1.0f;
    private float popupBorderSize = 1.0f;
    private float popupRounding = 0.0f;
    private float scrollbarRounding = 0.0f;
    private float scrollbarSize = 16.0f;
    private Vector2f selectableTextAlign = new Vector2f(0.0f, 0.0f);
    private float tabBorderSize = 0.0f;
    private float tabMinWidthForCloseButton = 0.0f;
    private float tabRounding = 0.0f;
    private Vector2f touchExtraPadding = new Vector2f(0.0f, 0.0f);
    private float windowBorderSize = 1.0f;
    private int windowMenuButtonPosition = 1;
    private Vector2f windowMinSize = new Vector2f(32.0f, 32.0f);
    private Vector2f windowPadding = new Vector2f(8.0f, 8.0f);
    private float windowRounding = 0.0f;
    private Vector2f windowTitleAlign = new Vector2f(0.0f, 0.5f);


    @Override
    public void imgui()
    {
        super.imgui();
    }
    public void style()
    {
        if (!active) return;

        ImGuiStyle style = ImGui.getStyle();
        style.setFrameRounding(this.frameRounding);
        style.setChildBorderSize(this.childBorderSize);
        style.setAlpha(this.alpha);
        style.setAntiAliasedFill(this.antiAliasedFill);
        style.setAntiAliasedLines (this.antiAliasedLines);
        style.setAntiAliasedLinesUseTex (this.antiAliasedLinesUseTex);
        style.setButtonTextAlign (this.buttonTextAlign.x, this.buttonTextAlign.y);
        style.setCellPadding (this.cellPadding.x, this.cellPadding.y);
        style.setChildBorderSize (this.childBorderSize);
        style.setChildRounding (this.childRounding);
        style.setCircleSegmentMaxError (this.circleSegmentMaxError);
        style.setColorButtonPosition (this.colorButtonPosition);
        style.setColumnsMinSpacing (this.columnsMinSpacing);
        style.setCurveTessellationTol (this.curveTessellationTol);
        style.setDisplaySafeAreaPadding (this.displaySafeAreaPadding.x, this.displaySafeAreaPadding.y);
        style.setDisplayWindowPadding (this.displayWindowPadding.x, this.displayWindowPadding.y);
        style.setFrameBorderSize (this.frameBorderSize);
        style.setFramePadding (this.framePadding.x, this.framePadding.y);
        style.setGrabMinSize (this.grabMinSize);
        style.setGrabRounding (this.grabRounding);
        style.setIndentSpacing (this.indentSpacing);
        style.setItemInnerSpacing (this.itemInnerSpacing.x, this.itemInnerSpacing.y);
        style.setItemSpacing (this.itemSpacing.x, this.itemSpacing.y);
        style.setLogSliderDeadzone (this.logSliderDeadzone);
        style.setMouseCursorScale (this.mouseCursorScale);
        style.setPopupBorderSize (this.popupBorderSize);
        style.setPopupRounding (this.popupRounding);
        style.setScrollbarRounding (this.scrollbarRounding); 
        style.setScrollbarSize (this.scrollbarSize); 
        style.setSelectableTextAlign    (this.selectableTextAlign.x, this.selectableTextAlign.y);
        style.setTabBorderSize (this.tabBorderSize);
        style.setTabMinWidthForCloseButton (this.tabMinWidthForCloseButton);
        style.setTabRounding (this.tabRounding);
        style.setTouchExtraPadding (this.touchExtraPadding.x, this.touchExtraPadding.y);
        style.setWindowBorderSize (this.windowBorderSize);
        style.setWindowMenuButtonPosition (this.windowMenuButtonPosition);
        style.setWindowMinSize (this.windowMinSize.x, this.windowMinSize.y);
        style.setWindowPadding (this.windowPadding.x, this.windowPadding.y);
        style.setWindowRounding (this.windowRounding);
        style.setWindowTitleAlign (this.windowTitleAlign.x, this.windowTitleAlign.y);


        // Background
            style.setColor(ImGuiCol.WindowBg,  dark ? (0xFF1A1A1A) : (0xFFE6E6E6));
            style.setColor(ImGuiCol.DockingEmptyBg, 0x00000000);
            style.setColor(ImGuiCol.PopupBg, 0.08f, 0.08f, 0.08f, 0.9f);

        // Docking
            style.setColor(ImGuiCol.DockingPreview, 0.9f, 0.1f, 0.2f, 0.5f);

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
            style.setColor(ImGuiCol.Separator , 0.0f, 0.0f, 0.0f, 0.3f);
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
            style.setColor(ImGuiCol.PlotLines, 1, 0, 0, 1f);
            style.setColor(ImGuiCol.PlotLinesHovered, 1, 1, 1, 1.0f);
            style.setColor(ImGuiCol.PlotHistogram, 0, 1, 0, 0.8f);
            style.setColor(ImGuiCol.PlotHistogramHovered, 0, 1, 0, 1.0f);

    }

    public static ImGuiStyleConfig get() {
        return instance;
    }
}
