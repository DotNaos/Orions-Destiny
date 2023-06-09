package Burst.Engine.Source.Core.Scene.Initializer;

import Burst.Engine.Source.Core.Actor.Actor;
import Burst.Engine.Source.Core.Assets.AssetManager;
import Burst.Engine.Source.Core.Assets.Graphics.Texture;
import Burst.Engine.Source.Game.Animation.StateMachine;
import Burst.Engine.Source.Game.Game;
import Burst.Engine.Source.Core.Render.SpriteRenderer;
import Burst.Engine.Source.Core.Scene.Scene;

public class SceneInitializer {

    protected Scene scene;

    public SceneInitializer(Scene scene) {
        this.scene = scene;
    }

    public void init() {
    }

    public void imgui() {
    }

    // TODO: FIX LOADRESOURCES IN SCENEINITIALIZER
    public void loadResources(Game scene) {
        for (Actor g : scene.getActors()) {
            if (g.getComponent(StateMachine.class) != null) {
                StateMachine stateMachine = g.getComponent(StateMachine.class);
                stateMachine.refreshTextures();
            }
        }
    }


}
