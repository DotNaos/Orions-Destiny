package Burst.Engine.Config;

import Burst.Engine.Source.Core.Component;
import imgui.ImGui;
import imgui.ImGuiStyle;
import imgui.flag.ImGuiCol;
import imgui.flag.ImGuiDir;
import imgui.flag.ImGuiMouseCursor;
import org.joml.Vector2f;
import org.joml.Vector4f;

import static Burst.Engine.Source.Core.Util.Util.hexToVec4f;

public class ImGuiStyleConfig extends Component {
  private static transient ImGuiStyleConfig instance = new ImGuiStyleConfig();
  private transient boolean active = true;
  private boolean dark = true;
  //=========================================================================
  //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  //------------------------------[Style Vars]-------------------------------
  //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  //=========================================================================
  private float frameRounding = 2.5f;
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
  private Vector2f itemSpacing = new Vector2f(2);
  private float logSliderDeadzone = 4.0f;
  private float mouseCursorScale = 1.0f;
  private float popupBorderSize = 1.0f;
  private float popupRounding = 0.0f;
  private float scrollbarRounding = 0.0f;
  private float scrollbarSize = 16.0f;
  private Vector2f selectableTextAlign = new Vector2f(0.0f, 0.0f);
  private float tabBorderSize = 0.0f;
  private float tabMinWidthForCloseButton = 0.0f;
  private float tabRounding = 5.0f;
  private Vector2f touchExtraPadding = new Vector2f(0.0f, 0.0f);
  private float windowBorderSize = 1.0f;
  private int windowMenuButtonPosition = ImGuiDir.None;
  private Vector2f windowMinSize = new Vector2f(32.0f, 32.0f);
  private Vector2f windowPadding = new Vector2f(8.0f, 8.0f);
  private float windowRounding = 0.0f;
  private Vector2f windowTitleAlign = new Vector2f(0.0f, 0.5f);

  //=========================================================================
  //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  //-------------------------------[Colors]----------------------------------
  //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  //=========================================================================

  private Vector4f Text = hexToVec4f(0xffffffff);
  private Vector4f TextDisabled = hexToVec4f(0xff000001);
  /**
   * Background of normal windows
   */
  private Vector4f WindowBg = hexToVec4f(0xff151515);
  /**
   * Background of child windows
   */
  private Vector4f ChildBg = hexToVec4f(0xff151515);
  /**
   * Background of popups, menus, tooltips windows
   */
  private Vector4f PopupBg = hexToVec4f(0xff000004);
  private Vector4f Border = hexToVec4f(0x00000000);
  private Vector4f BorderShadow = hexToVec4f(0x00000000);
  /**
   * Background of checkbox, radio button, plot, slider, text input
   */
  private Vector4f FrameBg = hexToVec4f(0xff0f0f0f);
  private Vector4f FrameBgHovered = hexToVec4f(0xff0f0f0f);
  private Vector4f FrameBgActive = hexToVec4f(0xff0064FF);
  private Vector4f TitleBg = hexToVec4f(0xff151515);
  private Vector4f TitleBgActive = hexToVec4f(0xff151515);
  private Vector4f TitleBgCollapsed = hexToVec4f(0xff151515);
  private Vector4f MenuBarBg = hexToVec4f(0xff242424);
  private Vector4f ScrollbarBg = hexToVec4f(0xff00000e);
  private Vector4f ScrollbarGrab = hexToVec4f(0xff575757);
  private Vector4f ScrollbarGrabHovered = hexToVec4f((0xff575757 + (0x00111111 * 2)));
  private Vector4f ScrollbarGrabActive = hexToVec4f((0xff575757 + (0x00111111 * 2)));
  private Vector4f CheckMark = hexToVec4f(0xffffffff);
  private Vector4f SliderGrab = hexToVec4f(0xff000013);
  private Vector4f SliderGrabActive = hexToVec4f(0xff000014);
  private Vector4f Button = hexToVec4f(0x00000000);
  private Vector4f ButtonHovered = hexToVec4f(0xff000016);
  private Vector4f ButtonActive = hexToVec4f(0xff000017);
  /**
   * Header* colors are used for CollapsingHeader, TreeNode, Selectable, MenuItem
   */
  private Vector4f Header = hexToVec4f(0xff2f2f2f);
  private Vector4f HeaderHovered = hexToVec4f(0xff2f2f2f);
  private Vector4f HeaderActive = hexToVec4f(0xff2f2f2f);
  private Vector4f Separator = hexToVec4f(0xff151515);
  private Vector4f SeparatorHovered = hexToVec4f(0xff404040);
  private Vector4f SeparatorActive = hexToVec4f(0xff404040);
  private Vector4f ResizeGrip = hexToVec4f(0xff00001e);
  private Vector4f ResizeGripHovered = hexToVec4f(0xff00001f);
  private Vector4f ResizeGripActive = hexToVec4f(0xff000020);
  private Vector4f Tab = hexToVec4f(0x00000000);
  private Vector4f TabHovered = hexToVec4f(0xAA242424);
  private Vector4f TabActive = hexToVec4f(0xff242424);
  private Vector4f TabUnfocused = hexToVec4f(0xff242424);
  private Vector4f TabUnfocusedActive = hexToVec4f(0xff242424);
  /**
   * Preview overlay color when about to docking something
   */
  private Vector4f DockingPreview = hexToVec4f(0xff000026);
  /**
   * Background color for empty node (e.g. CentralNode with no window docked Vector4fo it)
   */
  private Vector4f DockingEmptyBg = hexToVec4f(0xff000027);
  private Vector4f PlotLines = hexToVec4f(0xff000028);
  private Vector4f PlotLinesHovered = hexToVec4f(0xff000029);
  private Vector4f PlotHistogram = hexToVec4f(0xff00002a);
  private Vector4f PlotHistogramHovered = hexToVec4f(0xff00002b);
  /**
   * Table header background
   */
  private Vector4f TableHeaderBg = hexToVec4f(0xff00002c);
  /**
   * Table outer and header borders (prefer using Alpha= hexToVec4f(1.0 here)
   */
  private Vector4f TableBorderStrong = hexToVec4f(0xff00002d);
  /**
   * Table inner borders (prefer using Alpha= hexToVec4f(1.0 here)
   */
  private Vector4f TableBorderLight = hexToVec4f(0xff00002e);
  /**
   * Table row background (even rows)
   */
  private Vector4f TableRowBg = hexToVec4f(0xff00002f);
  /**
   * Table row background (odd rows)
   */
  private Vector4f TableRowBgAlt = hexToVec4f(0xff000030);
  private Vector4f TextSelectedBg = hexToVec4f(0xff000031);
  private Vector4f DragDropTarget = hexToVec4f(0xff000032);
  /**
   * Gamepad/keyboard: current highlighted item
   */
  private Vector4f NavHighlight = hexToVec4f(0xff000033);
  /**
   * Highlight window when using CTRL+TAB
   */
  private Vector4f NavWindowingHighlight = hexToVec4f(0xff000034);
  /**
   * Darken/colorize entire screen behind the CTRL+TAB window list, when active
   */
  private Vector4f NavWindowingDimBg = hexToVec4f(0xff000035);
  /**
   * Darken/colorize entire screen behind a modal window, when one is active
   */
  private Vector4f ModalWindowDimBg = hexToVec4f(0xff000036);

  public static ImGuiStyleConfig get() {
    return instance;
  }

  //=========================================================================
  //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  //-------------------------------------------------------------------------
  //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  //=========================================================================
  @Override
  public void imgui() {
    super.imgui();
  }

  public void style() {
    if (!active) return;

    // Style
    ImGuiStyle style = ImGui.getStyle();
    style.setFrameRounding(this.frameRounding);
    style.setChildBorderSize(this.childBorderSize);
    style.setAlpha(this.alpha);
    style.setAntiAliasedFill(this.antiAliasedFill);
    style.setAntiAliasedLines(this.antiAliasedLines);
    style.setAntiAliasedLinesUseTex(this.antiAliasedLinesUseTex);
    style.setButtonTextAlign(this.buttonTextAlign.x, this.buttonTextAlign.y);
    style.setCellPadding(this.cellPadding.x, this.cellPadding.y);
    style.setChildBorderSize(this.childBorderSize);
    style.setChildRounding(this.childRounding);
    style.setCircleSegmentMaxError(this.circleSegmentMaxError);
    style.setColorButtonPosition(this.colorButtonPosition);
    style.setColumnsMinSpacing(this.columnsMinSpacing);
    style.setCurveTessellationTol(this.curveTessellationTol);
    style.setDisplaySafeAreaPadding(this.displaySafeAreaPadding.x, this.displaySafeAreaPadding.y);
    style.setDisplayWindowPadding(this.displayWindowPadding.x, this.displayWindowPadding.y);
    style.setFrameBorderSize(this.frameBorderSize);
    style.setFramePadding(this.framePadding.x, this.framePadding.y);
    style.setGrabMinSize(this.grabMinSize);
    style.setGrabRounding(this.grabRounding);
    style.setIndentSpacing(this.indentSpacing);
    style.setItemInnerSpacing(this.itemInnerSpacing.x, this.itemInnerSpacing.y);
    style.setItemSpacing(this.itemSpacing.x, this.itemSpacing.y);
    style.setLogSliderDeadzone(this.logSliderDeadzone);
    style.setMouseCursorScale(this.mouseCursorScale);
    style.setPopupBorderSize(this.popupBorderSize);
    style.setPopupRounding(this.popupRounding);
    style.setScrollbarRounding(this.scrollbarRounding);
    style.setScrollbarSize(this.scrollbarSize);
    style.setSelectableTextAlign(this.selectableTextAlign.x, this.selectableTextAlign.y);
    style.setTabBorderSize(this.tabBorderSize);
    style.setTabMinWidthForCloseButton(this.tabMinWidthForCloseButton);
    style.setTabRounding(this.tabRounding);
    style.setTouchExtraPadding(this.touchExtraPadding.x, this.touchExtraPadding.y);
    style.setWindowBorderSize(this.windowBorderSize);
    style.setWindowMenuButtonPosition(this.windowMenuButtonPosition);
    style.setWindowMinSize(this.windowMinSize.x, this.windowMinSize.y);
    style.setWindowPadding(this.windowPadding.x, this.windowPadding.y);
    style.setWindowRounding(this.windowRounding);
    style.setWindowTitleAlign(this.windowTitleAlign.x, this.windowTitleAlign.y);

    // Colors
    style.setColor(ImGuiCol.Text, this.Text.x, this.Text.y, this.Text.z, this.Text.w);

    style.setColor(ImGuiCol.TextDisabled, this.TextDisabled.x, this.TextDisabled.y, this.TextDisabled.z, this.TextDisabled.w);
    style.setColor(ImGuiCol.WindowBg, this.WindowBg.x, this.WindowBg.y, this.WindowBg.z, this.WindowBg.w);
    style.setColor(ImGuiCol.ChildBg, this.ChildBg.x, this.ChildBg.y, this.ChildBg.z, this.ChildBg.w);
    style.setColor(ImGuiCol.PopupBg, this.PopupBg.x, this.PopupBg.y, this.PopupBg.z, this.PopupBg.w);
    style.setColor(ImGuiCol.Border, this.Border.x, this.Border.y, this.Border.z, this.Border.w);
    style.setColor(ImGuiCol.BorderShadow, this.BorderShadow.x, this.BorderShadow.y, this.BorderShadow.z, this.BorderShadow.w);
    style.setColor(ImGuiCol.FrameBg, this.FrameBg.x, this.FrameBg.y, this.FrameBg.z, this.FrameBg.w);
    style.setColor(ImGuiCol.FrameBgHovered, this.FrameBgHovered.x, this.FrameBgHovered.y, this.FrameBgHovered.z, this.FrameBgHovered.w);
    style.setColor(ImGuiCol.FrameBgActive, this.FrameBgActive.x, this.FrameBgActive.y, this.FrameBgActive.z, this.FrameBgActive.w);
    style.setColor(ImGuiCol.TitleBg, this.TitleBg.x, this.TitleBg.y, this.TitleBg.z, this.TitleBg.w);
    style.setColor(ImGuiCol.TitleBgActive, this.TitleBgActive.x, this.TitleBgActive.y, this.TitleBgActive.z, this.TitleBgActive.w);
    style.setColor(ImGuiCol.TitleBgCollapsed, this.TitleBgCollapsed.x, this.TitleBgCollapsed.y, this.TitleBgCollapsed.z, this.TitleBgCollapsed.w);
    style.setColor(ImGuiCol.MenuBarBg, this.MenuBarBg.x, this.MenuBarBg.y, this.MenuBarBg.z, this.MenuBarBg.w);
    style.setColor(ImGuiCol.ScrollbarBg, this.ScrollbarBg.x, this.ScrollbarBg.y, this.ScrollbarBg.z, this.ScrollbarBg.w);
    style.setColor(ImGuiCol.ScrollbarGrab, this.ScrollbarGrab.x, this.ScrollbarGrab.y, this.ScrollbarGrab.z, this.ScrollbarGrab.w);
    style.setColor(ImGuiCol.ScrollbarGrabHovered, this.ScrollbarGrabHovered.x, this.ScrollbarGrabHovered.y, this.ScrollbarGrabHovered.z, this.ScrollbarGrabHovered.w);
    style.setColor(ImGuiCol.ScrollbarGrabActive, this.ScrollbarGrabActive.x, this.ScrollbarGrabActive.y, this.ScrollbarGrabActive.z, this.ScrollbarGrabActive.w);
    style.setColor(ImGuiCol.CheckMark, this.CheckMark.x, this.CheckMark.y, this.CheckMark.z, this.CheckMark.w);
    style.setColor(ImGuiCol.SliderGrab, this.SliderGrab.x, this.SliderGrab.y, this.SliderGrab.z, this.SliderGrab.w);
    style.setColor(ImGuiCol.SliderGrabActive, this.SliderGrabActive.x, this.SliderGrabActive.y, this.SliderGrabActive.z, this.SliderGrabActive.w);
    style.setColor(ImGuiCol.Button, this.Button.x, this.Button.y, this.Button.z, this.Button.w);
    style.setColor(ImGuiCol.ButtonHovered, this.ButtonHovered.x, this.ButtonHovered.y, this.ButtonHovered.z, this.ButtonHovered.w);
    style.setColor(ImGuiCol.ButtonActive, this.ButtonActive.x, this.ButtonActive.y, this.ButtonActive.z, this.ButtonActive.w);
    style.setColor(ImGuiCol.Header, this.Header.x, this.Header.y, this.Header.z, this.Header.w);
    style.setColor(ImGuiCol.HeaderHovered, this.HeaderHovered.x, this.HeaderHovered.y, this.HeaderHovered.z, this.HeaderHovered.w);
    style.setColor(ImGuiCol.HeaderActive, this.HeaderActive.x, this.HeaderActive.y, this.HeaderActive.z, this.HeaderActive.w);
    style.setColor(ImGuiCol.Separator, this.Separator.x, this.Separator.y, this.Separator.z, this.Separator.w);
    style.setColor(ImGuiCol.SeparatorHovered, this.SeparatorHovered.x, this.SeparatorHovered.y, this.SeparatorHovered.z, this.SeparatorHovered.w);
    style.setColor(ImGuiCol.SeparatorActive, this.SeparatorActive.x, this.SeparatorActive.y, this.SeparatorActive.z, this.SeparatorActive.w);
    style.setColor(ImGuiCol.ResizeGrip, this.ResizeGrip.x, this.ResizeGrip.y, this.ResizeGrip.z, this.ResizeGrip.w);
    style.setColor(ImGuiCol.ResizeGripHovered, this.ResizeGripHovered.x, this.ResizeGripHovered.y, this.ResizeGripHovered.z, this.ResizeGripHovered.w);
    style.setColor(ImGuiCol.ResizeGripActive, this.ResizeGripActive.x, this.ResizeGripActive.y, this.ResizeGripActive.z, this.ResizeGripActive.w);
    style.setColor(ImGuiCol.Tab, this.Tab.x, this.Tab.y, this.Tab.z, this.Tab.w);
    style.setColor(ImGuiCol.TabHovered, this.TabHovered.x, this.TabHovered.y, this.TabHovered.z, this.TabHovered.w);
    style.setColor(ImGuiCol.TabActive, this.TabActive.x, this.TabActive.y, this.TabActive.z, this.TabActive.w);
    style.setColor(ImGuiCol.TabUnfocused, this.TabUnfocused.x, this.TabUnfocused.y, this.TabUnfocused.z, this.TabUnfocused.w);
    style.setColor(ImGuiCol.TabUnfocusedActive, this.TabUnfocusedActive.x, this.TabUnfocusedActive.y, this.TabUnfocusedActive.z, this.TabUnfocusedActive.w);
    style.setColor(ImGuiCol.DockingPreview, this.DockingPreview.x, this.DockingPreview.y, this.DockingPreview.z, this.DockingPreview.w);
    style.setColor(ImGuiCol.DockingEmptyBg, this.DockingEmptyBg.x, this.DockingEmptyBg.y, this.DockingEmptyBg.z, this.DockingEmptyBg.w);
    style.setColor(ImGuiCol.PlotLines, this.PlotLines.x, this.PlotLines.y, this.PlotLines.z, this.PlotLines.w);
    style.setColor(ImGuiCol.PlotLinesHovered, this.PlotLinesHovered.x, this.PlotLinesHovered.y, this.PlotLinesHovered.z, this.PlotLinesHovered.w);
    style.setColor(ImGuiCol.PlotHistogram, this.PlotHistogram.x, this.PlotHistogram.y, this.PlotHistogram.z, this.PlotHistogram.w);
    style.setColor(ImGuiCol.PlotHistogramHovered, this.PlotHistogramHovered.x, this.PlotHistogramHovered.y, this.PlotHistogramHovered.z, this.PlotHistogramHovered.w);
    style.setColor(ImGuiCol.TableHeaderBg, this.TableHeaderBg.x, this.TableHeaderBg.y, this.TableHeaderBg.z, this.TableHeaderBg.w);
    style.setColor(ImGuiCol.TableBorderStrong, this.TableBorderStrong.x, this.TableBorderStrong.y, this.TableBorderStrong.z, this.TableBorderStrong.w);
    style.setColor(ImGuiCol.TableBorderLight, this.TableBorderLight.x, this.TableBorderLight.y, this.TableBorderLight.z, this.TableBorderLight.w);
    style.setColor(ImGuiCol.TableRowBg, this.TableRowBg.x, this.TableRowBg.y, this.TableRowBg.z, this.TableRowBg.w);
    style.setColor(ImGuiCol.TableRowBgAlt, this.TableRowBgAlt.x, this.TableRowBgAlt.y, this.TableRowBgAlt.z, this.TableRowBgAlt.w);
    style.setColor(ImGuiCol.TextSelectedBg, this.TextSelectedBg.x, this.TextSelectedBg.y, this.TextSelectedBg.z, this.TextSelectedBg.w);
    style.setColor(ImGuiCol.DragDropTarget, this.DragDropTarget.x, this.DragDropTarget.y, this.DragDropTarget.z, this.DragDropTarget.w);
    style.setColor(ImGuiCol.NavHighlight, this.NavHighlight.x, this.NavHighlight.y, this.NavHighlight.z, this.NavHighlight.w);
    style.setColor(ImGuiCol.NavWindowingHighlight, this.NavWindowingHighlight.x, this.NavWindowingHighlight.y, this.NavWindowingHighlight.z, this.NavWindowingHighlight.w);
    style.setColor(ImGuiCol.NavWindowingDimBg, this.NavWindowingDimBg.x, this.NavWindowingDimBg.y, this.NavWindowingDimBg.z, this.NavWindowingDimBg.w);
    style.setColor(ImGuiCol.ModalWindowDimBg, this.ModalWindowDimBg.x, this.ModalWindowDimBg.y, this.ModalWindowDimBg.z, this.ModalWindowDimBg.w);

  }
}
