package Burst.Engine.Source.Core.Scene;

import Burst.Engine.Source.Core.Graphics.Sprite.Spritesheet;
import Burst.Engine.Source.Core.Scene.Scene;
import Burst.Engine.Source.Core.Scene.SceneInitializer;
import Burst.Engine.Source.Core.GameObject;
import Burst.Engine.Source.Runtime.GameCamera;
import Orion.res.Assets;
import Burst.Engine.Source.Core.util.AssetManager;

public class LevelSceneInitializer extends SceneInitializer {

    @Override
    public void init(Scene scene) {
        Spritesheet sprites = AssetManager.getSpritesheet(Assets.BLOCKS);

        GameObject cameraObject = scene.createGameObject("GameCamera");
        cameraObject.addComponent(new GameCamera(scene.camera()));
        cameraObject.start();
        scene.addGameObjectToScene(cameraObject);
    }


    @Override
    public void imgui() {
        super.imgui();
    }


}
