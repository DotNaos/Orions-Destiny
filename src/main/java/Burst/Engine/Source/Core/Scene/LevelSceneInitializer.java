package Burst.Engine.Source.Core.Scene;

import Burst.Engine.Source.Core.Assets.Graphics.Spritesheet;
import Burst.Engine.Source.Core.Actor;
import Burst.Engine.Source.Core.UI.Window;
import Burst.Engine.Source.Runtime.Game;
import Burst.Engine.Source.Runtime.GameCamera;
import Orion.res.Assets;
import Burst.Engine.Source.Core.Assets.AssetManager;

public class LevelSceneInitializer extends SceneInitializer {

    @Override
    public void init(Game game) {
        Spritesheet sprites = AssetManager.getAssetFromType(Assets.BLOCKS, Spritesheet.class);

        Actor cameraObject = game.spawnActor("GameCamera");
        cameraObject.addComponent(new GameCamera(Window.getScene().getCamera()));
        cameraObject.start();
        game.addActor(cameraObject);
    }


    @Override
    public void imgui() {
        super.imgui();
    }


}
