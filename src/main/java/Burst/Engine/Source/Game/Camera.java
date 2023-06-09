package Burst.Engine.Source.Game;

import Burst.Engine.Source.Core.Actor.Actor;
import Burst.Engine.Source.Core.Actor.PlayerController;
import Burst.Engine.Source.Core.Assets.AssetManager;
import Burst.Engine.Source.Core.Assets.Graphics.Texture;
import Burst.Engine.Source.Core.UI.Viewport;
import Burst.Engine.Source.Core.UI.Window;
import Orion.res.AssetConfig;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class Camera extends Actor {
    public static final transient Texture icon = AssetManager.getAssetFromType(AssetConfig.ICON_CAMERA, Texture.class);
    private final transient Viewport viewport;

    /**
     * The size of the camera in world units.
     */
    private final transient Vector3f cameraSize = new Vector3f(6.0f, 3.0f, 0);
    private final transient float cameraBuffer = 1.5f;
    private final transient float playerBuffer = 0.25f;
    private final transient Vector4f backgroundColor = new Vector4f(92.0f / 255.0f, 148.0f / 255.0f, 252.0f / 255.0f, 1.0f);
    private Actor player;


    public Camera() {
        super();
        this.name = "Game Camera";
        this.viewport = Window.getScene().getViewport();
    }

    @Override
    protected void init() {
        super.init();
        this.player = Window.getScene().getEditor().getActorWith(PlayerController.class);
        this.viewport.clearColor.set(backgroundColor);
    }

    @Override
    public void update(float dt) {
        if (player == null) return;

        // viewport.position.x = player.transform.position.x;
        viewport.position.x = player.getTransform().position.x;
        viewport.position.y = player.getTransform().position.y;
    }
}
