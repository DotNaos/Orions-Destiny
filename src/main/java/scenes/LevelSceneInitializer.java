package scenes;

import components.*;
import Burst.*;
import util.AssetManager;

public class LevelSceneInitializer extends SceneInitializer {
    public LevelSceneInitializer(Scene scene) {
        super(scene);
    }

    @Override
    public void init(Scene scene) {
        Spritesheet sprites = AssetManager.getSpritesheet("assets/images/spritesheets/decorationsAndBlocks.png");

        GameObject cameraObject = scene.createGameObject("GameCamera");
        cameraObject.addComponent(new GameCamera(scene.camera()));
        cameraObject.start();
        scene.addGameObjectToScene(cameraObject);
    }

    @Override
    public void loadResources(Scene scene) {
        AssetManager.loadAllResources();


//        AssetManager.addSound("assets/sounds/main-theme-overworld.ogg", true);
//        AssetManager.addSound("assets/sounds/flagpole.ogg", false);
//        AssetManager.addSound("assets/sounds/break_block.ogg", false);
//        AssetManager.addSound("assets/sounds/bump.ogg", false);
//        AssetManager.addSound("assets/sounds/coin.ogg", false);
//        AssetManager.addSound("assets/sounds/gameover.ogg", false);
//        AssetManager.addSound("assets/sounds/jump-small.ogg", false);
//        AssetManager.addSound("assets/sounds/mario_die.ogg", false);
//        AssetManager.addSound("assets/sounds/pipe.ogg", false);
//        AssetManager.addSound("assets/sounds/powerup.ogg", false);
//        AssetManager.addSound("assets/sounds/powerup_appears.ogg", false);
//        AssetManager.addSound("assets/sounds/stage_clear.ogg", false);
//        AssetManager.addSound("assets/sounds/stomp.ogg", false);
//        AssetManager.addSound("assets/sounds/kick.ogg", false);
//        AssetManager.addSound("assets/sounds/invincible.ogg", false);
//
//        AssetManager.getSound(("assets/sounds/main-theme-overworld.ogg")).play();

        for (GameObject g : scene.getGameObjects()) {
            if (g.getComponent(SpriteRenderer.class) != null) {
                SpriteRenderer spr = g.getComponent(SpriteRenderer.class);
                if (spr.getTexture() != null) {
                    spr.setTexture(AssetManager.getTexture(spr.getTexture().getFilepath()));
                }
            }

            if (g.getComponent(StateMachine.class) != null) {
                StateMachine stateMachine = g.getComponent(StateMachine.class);
                stateMachine.refreshTextures();
            }
        }
    }

    @Override
    public void imgui() {

    }
}
