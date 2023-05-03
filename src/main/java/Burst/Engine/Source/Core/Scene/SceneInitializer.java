package Burst.Engine.Source.Core.Scene;

import Burst.Engine.Source.Core.Graphics.Sprite.SpriteRenderer;
import Burst.Engine.Source.Runtime.Actor.GameObject;
import Burst.Engine.Source.Runtime.Animation.StateMachine;
import Burst.Engine.Source.Core.util.AssetManager;

public class SceneInitializer {

    public void init(Scene scene) {
        
    }

    public void loadResources(Scene scene) {
        AssetManager.loadAllResources();


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

    public void imgui() {
        
    }
}
