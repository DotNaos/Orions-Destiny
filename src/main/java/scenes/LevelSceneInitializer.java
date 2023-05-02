package scenes;

import components.*;
import Burst.*;
import imgui.ImGui;
import util.AssetHolder;
import util.AssetManager;

public class LevelSceneInitializer extends SceneInitializer {

    @Override
    public void init(Scene scene) {
        Spritesheet sprites = AssetManager.getSpritesheet(AssetHolder.BLOCKS);

        GameObject cameraObject = scene.createGameObject("GameCamera");
        cameraObject.addComponent(new GameCamera(scene.camera()));
        cameraObject.start();
        scene.addGameObjectToScene(cameraObject);
    }


    @Override
    public void imgui() {
//        ImGui.begin("Values");
//
//
//
//        ImGui.end();
    }
}
