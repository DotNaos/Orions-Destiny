package Burst.Engine.Source.Core.Scene;

import Burst.Engine.Source.Core.Assets.Graphics.Spritesheet;
import Burst.Engine.Source.Core.Actor;
import Burst.Engine.Source.Runtime.GameCamera;
import Orion.res.Assets;
import Burst.Engine.Source.Core.Assets.AssetManager;

public class LevelSceneInitializer extends SceneInitializer {

    @Override
    public void init(Game scene) {
        Spritesheet sprites = AssetManager.getAssetFromType(Assets.BLOCKS, Spritesheet.class);

        Actor cameraObject = scene.spawnActor("GameCamera");
        cameraObject.addComponent(new GameCamera(scene.getCamera()));
        cameraObject.start();
        scene.addActor(cameraObject);
    }


    @Override
    public void imgui() {
        super.imgui();
    }


}
