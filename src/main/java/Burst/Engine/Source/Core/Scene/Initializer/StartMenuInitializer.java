package Burst.Engine.Source.Core.Scene.Initializer;

import Burst.Engine.Source.Core.Assets.AssetManager;
import Burst.Engine.Source.Core.Assets.Graphics.Sprite;
import Burst.Engine.Source.Core.Assets.Graphics.SpriteSheet;
import Burst.Engine.Source.Core.Assets.Graphics.Texture;
import Burst.Engine.Source.Core.Scene.Scene;
import Burst.Engine.Source.Core.Scene.SceneType;
import Burst.Engine.Source.Core.UI.ImGui.BImGui;
import Burst.Engine.Source.Core.UI.ImGui.Menu;
import Burst.Engine.Source.Core.UI.Window;
import Orion.res.AssetConfig;
import imgui.ImGui;
import imgui.ImGuiViewport;
import imgui.flag.ImGuiCol;
import imgui.flag.ImGuiCond;
import imgui.flag.ImGuiStyleVar;
import imgui.flag.ImGuiWindowFlags;
import org.joml.Vector2f;

/**
 * @Author Oliver Schuetz
 */
public class StartMenuInitializer extends MenuInitializer {

  // button colors
  private final int BUTTON_COLOR = 0xA0;
  private final int BUTTON_HOVER_COLOR = 0x80;

  private final int BUTTON_ACTIVE_COLOR = 0x60;


    private Sprite spriteGame = AssetManager.getAssetFromType(SpriteSheet.class, AssetConfig.Files.Images.SpriteSheets.FLAT_BUTTONS_GAME).getSprite(7, 1);
    private Sprite spriteGameHovered = AssetManager.getAssetFromType(SpriteSheet.class, AssetConfig.Files.Images.SpriteSheets.FLAT_BUTTONS_GAME).getSprite(9, 1);
    private Sprite spriteGameCurrent = spriteGame;
    private Sprite spriteEditor = AssetManager.getAssetFromType(SpriteSheet.class, AssetConfig.Files.Images.SpriteSheets.FLAT_BUTTONS_EDITOR).getSprite(7, 1);
    private Sprite spriteEditorHovered = AssetManager.getAssetFromType(SpriteSheet.class, AssetConfig.Files.Images.SpriteSheets.FLAT_BUTTONS_EDITOR).getSprite(9, 1);
    private Sprite spriteEditorCurrent = spriteEditor;
    private Sprite spriteSettings = AssetManager.getAssetFromType(SpriteSheet.class, AssetConfig.Files.Images.SpriteSheets.FLAT_BUTTONS_SETTINGS).getSprite(7, 1);
    private Sprite spriteSettingsHovered = AssetManager.getAssetFromType(SpriteSheet.class, AssetConfig.Files.Images.SpriteSheets.FLAT_BUTTONS_SETTINGS).getSprite(9, 1);
    private Sprite spriteSettingsCurrent = spriteSettings;
  public StartMenuInitializer(Scene scene) {
    super(scene);
  }


  @Override
  public void imgui() {
    // The next window is displayed in the center of the screen in the viewport
    ImGuiViewport mainViewport = ImGui.getMainViewport();
    ImGui.setNextWindowPos(mainViewport.getWorkPosX() + mainViewport.getWorkSizeX() / 2, mainViewport.getWorkPosY() + mainViewport.getWorkSizeY() / 2, ImGuiCond.Always, 0.5f, 0.5f);
    ImGui.setNextWindowSize(mainViewport.getWorkSizeX(), mainViewport.getWorkSizeY());
    ImGui.setNextWindowViewport(mainViewport.getID());

    // Window centered in the glfw window
    ImGui.begin("StartMenu", ImGuiWindowFlags.NoTitleBar | ImGuiWindowFlags.NoResize | ImGuiWindowFlags.NoMove | ImGuiWindowFlags.NoCollapse | ImGuiWindowFlags.NoScrollbar | ImGuiWindowFlags.NoScrollWithMouse | ImGuiWindowFlags.NoDocking);

    // Window Position
    ImGui.setWindowPos(0, 0);
    ImGui.setWindowSize(Window.getWidth(), Window.getHeight());

    // Window color
    // Bordereinstellungen
    ImGui.pushStyleVar(ImGuiStyleVar.FrameRounding, 10);
    ImGui.pushStyleVar(ImGuiStyleVar.FrameBorderSize, 1);

    // Button Colors
    ImGui.pushStyleColor(ImGuiCol.Button, BUTTON_COLOR);
    ImGui.pushStyleColor(ImGuiCol.ButtonHovered, BUTTON_HOVER_COLOR);
    ImGui.pushStyleColor(ImGuiCol.ButtonActive, BUTTON_ACTIVE_COLOR);


    float buttonSpacing = 20;

    Vector2f alignment = new Vector2f(100 / 50, 100 / 50);
    Vector2f buttonSize = new Vector2f(400, 100);


    Vector2f margin = new Vector2f(0, 200);

    BImGui.image(AssetManager.getAssetFromType(AssetConfig.Files.Images.Backgrounds.MOUNTAINS, Texture.class), Window.getWidth(), Window.getHeight());

    Menu buttonMenu = new Menu(2, 2, buttonSize, alignment, buttonSpacing, margin);


    // Display Text in the middle of the button
    if (BImGui.imageButton(spriteEditorCurrent, buttonSize.x, buttonSize.y)) {
      Window.changeScene(SceneType.EDITOR);
    }
    if (ImGui.isItemHovered()) {
        spriteEditorCurrent = spriteEditorHovered;
    } else {
        spriteEditorCurrent = spriteEditor;
    }

    buttonMenu.nextColumn();

    if (BImGui.imageButton(spriteGameCurrent, buttonSize.x, buttonSize.y)) {
      Window.changeScene(SceneType.GAME);
    }
    if (ImGui.isItemHovered()) {
        spriteGameCurrent = spriteGameHovered;
    } else {
        spriteGameCurrent = spriteGame;
    }

    buttonMenu.nextRow();

    if (BImGui.imageButton(spriteSettingsCurrent, buttonSize.x, buttonSize.y)) {
      Window.changeScene(SceneType.SETTINGS_MENU);
    }
    if (ImGui.isItemHovered()) {
        spriteSettingsCurrent = spriteSettingsHovered;
    } else {
        spriteSettingsCurrent = spriteSettings;
    }



    ImGui.popStyleColor(3);
    ImGui.popStyleVar(2);
    ImGui.end();

  }


}

