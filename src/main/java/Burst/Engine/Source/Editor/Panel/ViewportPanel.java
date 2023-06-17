package Burst.Engine.Source.Editor.Panel;

import Burst.Engine.Config.Config;
import Burst.Engine.Config.Config.ImGuiStyle;
import Burst.Engine.Source.Core.Assets.AssetManager;
import Burst.Engine.Source.Core.Assets.Graphics.SpriteSheet;
import Burst.Engine.Source.Core.EventSystem.EventSystem;
import Burst.Engine.Source.Core.EventSystem.Events.Event;
import Burst.Engine.Source.Core.EventSystem.Events.EventType;
import Burst.Engine.Source.Core.Input.MouseListener;
import Burst.Engine.Source.Core.Scene.SceneType;
import Burst.Engine.Source.Core.UI.ImGui.BImGui;
import Burst.Engine.Source.Core.UI.ImGui.ImGuiPanel;
import Burst.Engine.Source.Core.UI.Window;
import Orion.res.AssetConfig;
import imgui.ImGui;
import imgui.ImGuiViewport;
import imgui.ImVec2;
import imgui.flag.ImGuiCond;
import imgui.flag.ImGuiKey;
import imgui.flag.ImGuiWindowFlags;
import org.joml.Vector2f;
import org.joml.Vector3f;

/**
 * @author Oliver Schuetz
 */
public class ViewportPanel extends ImGuiPanel {
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

    if (ImGui.beginMenuBar()) {
      if (BImGui.imageButton(AssetManager.getAssetFromType(AssetConfig.Files.Images.SpriteSheets.BUTTONS, SpriteSheet.class).getSprite(1), 24, 24))
      {
        if (Window.getScene().inEditor())
          EventSystem.notify(null, new Event(EventType.GameEngineStartPlay));
      }
      if (BImGui.imageButton(AssetManager.getAssetFromType(AssetConfig.Files.Images.SpriteSheets.BUTTONS, SpriteSheet.class).getSprite(6, 3), 24, 24))
      {
        if (Window.getScene().inGame())
          EventSystem.notify(null, new Event(EventType.GameEngineStopPlay));
      }

      ImGui.endMenuBar();
    }

    // When the escape key is pressed, the game stops playing
    if (ImGui.isKeyPressed(256) && Window.getScene().inGame()) {
      EventSystem.notify(null, new Event(EventType.GameEngineStopPlay));
    }

    ImGui.setCursorPos(ImGui.getCursorPosX(), ImGui.getCursorPosY());
    ImVec2 windowSize = getLargestSizeForViewport();
    this.viewportSize = new Vector3f(windowSize.x, windowSize.y, 0);

    ImVec2 windowPos = getCenteredPositionForViewport(windowSize);
    ImGui.setCursorPos(windowPos.x, windowPos.y);

    int textureId = Window.getFramebuffer().getTextureId();
    ImGui.imageButton(textureId, windowSize.x, windowSize.y);


    // If the mouse is out of the viewports bounds it's not hovered
    this.windowIsHovered = !(
            // Max viewport pos
            (MouseListener.getViewX() > Window.getWidth() || MouseListener.getViewY() > Window.getHeight()) ||
                    // Min viewport pos
                    (MouseListener.getViewX() < 0 || MouseListener.getViewY() < 0));

    // Update the position of the Panel
    this.position.x = ImGui.getWindowPosX() + Config.get(ImGuiStyle.class).getWindowPadding().x;
    // + 2x padding because of the menu bar
    this.position.y = ImGui.getWindowPosY() + Config.get(ImGuiStyle.class).getWindowPadding().y * (ImGui.isWindowDocked() ? 5 : 2);

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
