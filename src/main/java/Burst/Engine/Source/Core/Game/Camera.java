package Burst.Engine.Source.Core.Game;

import Burst.Engine.Source.Core.Actor.Actor;
import Burst.Engine.Source.Core.UI.Window;
import Burst.Engine.Source.Core.UI.Viewport;
import Burst.Engine.Source.Core.Actor.PlayerController;
import org.joml.Vector4f;

public class Camera extends Actor {
    private Actor player;
    private transient Viewport viewport;
    private transient float cameraBuffer = 1.5f;
    private transient float playerBuffer = 0.25f;

    private Vector4f backgroundColor = new Vector4f(92.0f / 255.0f, 148.0f / 255.0f, 252.0f / 255.0f, 1.0f);
    private Vector4f undergroundColor = new Vector4f(0, 0, 0, 1);

    public Camera(Viewport viewport) {
        super("GameCamera");
        this.viewport = viewport;
    }

    @Override
    public void start() {
        this.player = Window.getScene().getGame().getActorWith(PlayerController.class);
        this.viewport.clearColor.set(backgroundColor);
    }

    @Override
    public void update(float dt) {
        if (player == null) return;

        viewport.position.x = player.transform.position.x;

    }
}
