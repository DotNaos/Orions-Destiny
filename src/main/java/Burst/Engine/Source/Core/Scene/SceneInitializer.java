package Burst.Engine.Source.Core.Scene;

import Burst.Engine.Source.Core.Assets.Graphics.Texture;
import Burst.Engine.Source.Core.Graphics.Render.SpriteRenderer;
import Burst.Engine.Source.Core.Actor;
import Burst.Engine.Source.Runtime.Animation.StateMachine;
import Burst.Engine.Source.Core.Assets.AssetManager;
import Burst.Engine.Source.Runtime.Game;

public class SceneInitializer {

    protected Scene scene;

    public SceneInitializer(Scene scene) {
        this.scene = scene;
    }

    protected void init() {}

    public void imgui() {}

    public void loadResources(Game scene) {
        AssetManager.loadAllAssets();

        for (Actor g : scene.getGameObjects()) {
            if (g.getComponent(SpriteRenderer.class) != null) {
                SpriteRenderer spr = g.getComponent(SpriteRenderer.class);
                if (spr.getTexture() != null) {
                    spr.setTexture(AssetManager.getAssetFromType(spr.getTexture().getFilepath(), Texture.class));
                }
            }

            if (g.getComponent(StateMachine.class) != null) {
                StateMachine stateMachine = g.getComponent(StateMachine.class);
                stateMachine.refreshTextures();
            }
        }
    }






}
