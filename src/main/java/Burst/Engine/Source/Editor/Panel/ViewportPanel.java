package Burst.Engine.Source.Editor.Panel;

import Burst.Engine.Source.Core.Input.MouseListener;
import Burst.Engine.Source.Core.EventSystem.EventSystem;
import Burst.Engine.Source.Core.EventSystem.Events.Event;
import Burst.Engine.Source.Core.EventSystem.Events.EventType;
import Burst.Engine.Source.Core.Scene.SceneType;
import Burst.Engine.Source.Core.UI.ImGui.ImGuiPanel;
import Burst.Engine.Source.Core.UI.Window;
import imgui.ImGui;
import imgui.ImGuiViewport;
import imgui.ImVec2;
import imgui.flag.ImGuiCond;
import imgui.flag.ImGuiWindowFlags;
import org.joml.Vector3f;

public class ViewportPanel extends ImGuiPanel {

    private boolean isPlaying = false;
    private boolean windowIsHovered;
    private Vector3f viewportSize = new Vector3f();

    public ViewportPanel() {
        super();
    }

    @Override
    public void imgui() {
        boolean inGame = Window.getScene().getOpenScene() == SceneType.GAME;
        int inGameFlags = 0;

        if (inGame) {
            // The next window is displayed in the center of the screen in the viewport
            ImGuiViewport mainViewport = ImGui.getMainViewport();
            ImGui.setNextWindowPos(mainViewport.getWorkPosX() + mainViewport.getWorkSizeX() / 2, mainViewport.getWorkPosY() + mainViewport.getWorkSizeY() / 2, ImGuiCond.Always, 0.5f, 0.5f);
            ImGui.setNextWindowSize(mainViewport.getWorkSizeX(), mainViewport.getWorkSizeY());
            ImGui.setNextWindowViewport(mainViewport.getID());
            inGameFlags |= ImGuiWindowFlags.NoTitleBar | ImGuiWindowFlags.NoResize | ImGuiWindowFlags.NoMove | ImGuiWindowFlags.NoCollapse | ImGuiWindowFlags.NoScrollbar | ImGuiWindowFlags.NoScrollWithMouse | ImGuiWindowFlags.NoDocking;
            ImGui.setNextWindowPos(0, 0, ImGuiCond.Always, 0.0f, 0.0f);

        }

        ImGui.begin("Viewport", ImGuiWindowFlags.NoTitleBar | ImGuiWindowFlags.NoScrollbar | ImGuiWindowFlags.NoScrollWithMouse | ImGuiWindowFlags.MenuBar | inGameFlags);

        if (!inGame) {
            ImGui.beginMenuBar();
            if (ImGui.menuItem("Play", "", isPlaying, !isPlaying)) {
                isPlaying = true;
                EventSystem.notify(null, new Event(EventType.GameEngineStartPlay));
            }
            if (ImGui.menuItem("Stop", "", !isPlaying, isPlaying)) {
                isPlaying = false;
                EventSystem.notify(null, new Event(EventType.GameEngineStopPlay));
            }
            ImGui.endMenuBar();
        }


        ImGui.setCursorPos(ImGui.getCursorPosX(), ImGui.getCursorPosY());
        ImVec2 windowSize = getLargestSizeForViewport();
        this.viewportSize = new Vector3f(windowSize.x, windowSize.y, 0);

        ImVec2 windowPos = getCenteredPositionForViewport(windowSize);
        ImGui.setCursorPos(windowPos.x, windowPos.y);

        int textureId = Window.getFramebuffer().getTextureId();
        ImGui.imageButton(textureId, windowSize.x, windowSize.y, 0, 1, 1, 0);
        windowIsHovered = ImGui.isItemHovered();

        MouseListener.setGameViewportPos(new Vector3f(windowPos.x + 10, windowPos.y, 0));
        MouseListener.setGameViewportSize(new Vector3f(windowSize.x, windowSize.y, 0));

        ImGui.end();
    }

    public boolean getWantCaptureMouse() {
        return windowIsHovered;
    }

    private ImVec2 getLargestSizeForViewport() {
        ImVec2 windowSize = new ImVec2();
        ImGui.getContentRegionAvail(windowSize);

        float ar = (float) Window.getWidth() / (float) Window.getHeight();
        float aspectWidth = windowSize.x;
        float aspectHeight = aspectWidth / ar;
        if (aspectHeight > windowSize.y) {
            // We must switch to pillarbox mode
            aspectHeight = windowSize.y;
            aspectWidth = aspectHeight * ar;
        }

//        return new ImVec2(aspectWidth, aspectHeight);


        return new ImVec2(windowSize.x, windowSize.y);
    }

    private ImVec2 getCenteredPositionForViewport(ImVec2 aspectSize) {
        ImVec2 windowSize = new ImVec2();
        ImGui.getContentRegionAvail(windowSize);

        float viewportX = (windowSize.x / 2.0f) - (aspectSize.x / 2.0f);
        float viewportY = (windowSize.y / 2.0f) - (aspectSize.y / 2.0f);

        return new ImVec2(viewportX + ImGui.getCursorPosX(), viewportY + ImGui.getCursorPosY());
    }

    public Vector3f getSize() {
        return new Vector3f(viewportSize);
    }
}
