package Burst.Engine.Source.Editor.Panel;

import Burst.Engine.Config.ImGuiStyleConfig;
import Burst.Engine.Source.Core.Input.MouseListener;
import Burst.Engine.Source.Core.EventSystem.EventSystem;
import Burst.Engine.Source.Core.EventSystem.Events.Event;
import Burst.Engine.Source.Core.EventSystem.Events.EventType;
import Burst.Engine.Source.Core.Render.Debug.DebugDraw;
import Burst.Engine.Source.Core.Scene.SceneType;
import Burst.Engine.Source.Core.UI.ImGui.BImGui;
import Burst.Engine.Source.Core.UI.ImGui.ImGuiPanel;
import Burst.Engine.Source.Core.UI.Window;
import imgui.ImGui;
import imgui.ImGuiViewport;
import imgui.ImVec2;
import imgui.flag.ImGuiCol;
import imgui.flag.ImGuiCond;
import imgui.flag.ImGuiTableColumnFlags;
import imgui.flag.ImGuiWindowFlags;
import org.joml.Vector2f;
import org.joml.Vector2i;
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
        int windowFlags = 0;

        String title = "Viewport";
        if (inGame) {
            // The next window is displayed in the center of the screen in the viewport
            ImGuiViewport mainViewport = ImGui.getMainViewport();
            ImGui.setNextWindowPos(mainViewport.getWorkPosX() + mainViewport.getWorkSizeX() / 2, mainViewport.getWorkPosY() + mainViewport.getWorkSizeY() / 2, ImGuiCond.Always, 0.5f, 0.5f);
            ImGui.setNextWindowSize(mainViewport.getWorkSizeX(), mainViewport.getWorkSizeY());
            ImGui.setNextWindowViewport(mainViewport.getID());
            windowFlags |= ImGuiWindowFlags.NoDocking;
            ImGui.setNextWindowPos(0, 0, ImGuiCond.None, 0.0f, 0.0f);
            title = "Orions Destiny";
        }
        ImGui.begin(title, ImGuiWindowFlags.NoTitleBar | ImGuiWindowFlags.NoScrollbar | ImGuiWindowFlags.NoScrollWithMouse | ImGuiWindowFlags.MenuBar | windowFlags);

        if (ImGui.beginMenuBar())
        {
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

        // When the escape key is pressed, the game stops playing
        if (ImGui.isKeyPressed(256)) {
            isPlaying = false;
            EventSystem.notify(null, new Event(EventType.GameEngineStopPlay));
        }




        ImGui.setCursorPos(ImGui.getCursorPosX(), ImGui.getCursorPosY());
        ImVec2 windowSize = getLargestSizeForViewport();
        this.viewportSize = new Vector3f(windowSize.x, windowSize.y, 0);

        ImVec2 windowPos = getCenteredPositionForViewport(windowSize);
        ImGui.setCursorPos(windowPos.x, windowPos.y);

        int textureId = Window.getFramebuffer().getTextureId();
        ImGui.imageButton(textureId, windowSize.x, windowSize.y
                , 0, 1, 1, 0
        );


        // If the mouse is out of the viewports bounds it's not hovered
        this.windowIsHovered = !(
                // Max viewport pos
                    (MouseListener.getViewX() > Window.getWidth() || MouseListener.getViewY() > Window.getHeight())
                ||
                // Min viewport pos
                    (MouseListener.getViewX() < 0 || MouseListener.getViewY() < 0)
        );

        // Update the position of the Panel
        this.position.x = ImGui.getWindowPosX() + ImGuiStyleConfig.get().getWindowPadding().x;
        // + 2x padding because of the menu bar
        this.position.y = ImGui.getWindowPosY() + ImGuiStyleConfig.get().getWindowPadding().y * (ImGui.isWindowDocked() ? 5 : 2);

        MouseListener.setGameViewportPos(new Vector2f(this.position.x, this.position.y));

        // + 8 because of the padding
        MouseListener.setGameViewportSize(new Vector2f(windowSize.x + 8, windowSize.y));

        ImGui.end();
    }

    public boolean getWantCaptureMouse() {
        if (Window.isPopupOpen()) {
            return false;
        }

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
