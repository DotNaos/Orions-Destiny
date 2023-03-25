package Engine;

import Util.AssetPool;

import components.SpriteRenderer;
import components.Spritesheet;
import org.joml.Vector2f;
import org.joml.Vector4f;

import static org.lwjgl.glfw.GLFW.*;

public class LevelEditorScene extends Scene {

    private GameObject obj1;
    private Spritesheet sprites;


    public LevelEditorScene() {

    }

    @Override
    public void init() {

        loadResources();

        // Camera center
        this.camera = new Camera(new Vector2f(-350, -100));


        sprites = AssetPool.getSpritesheet("assets/images/Walk.png");

        // Create a game object
        obj1 = new GameObject("Object 1", new Transform(new Vector2f(100, 100), new Vector2f(256, 256)));
        obj1.addComponent(new SpriteRenderer(sprites.getSprite(0)));
        this.addGameObjectToScene(obj1);



    }

    private void loadResources() {
        AssetPool.getShader("assets/shaders/default.glsl");

        AssetPool.addSpritesheet("assets/images/Walk.png",
                new Spritesheet(AssetPool.getTexture("assets/images/Walk.png"),
                        117, 26, 8, 0));

    }

    private int spriteIndex = 0;
    private float spriteFlipTime = 0.1f;
    private float spriteFlipTimeLeft = 0;

    @Override
    public void update(float dt) {

        spriteFlipTimeLeft -= dt;
        if (spriteFlipTimeLeft <= 0) {
            spriteFlipTimeLeft = spriteFlipTime;
            spriteIndex++;
            if (spriteIndex > 7) {
                spriteIndex = 0;
            }
            obj1.getComponent(SpriteRenderer.class).setSprite(sprites.getSprite(spriteIndex));
        }


        for (GameObject go : this.gameObjects) {
            go.update(dt);
        }

        this.renderer.render();
    }
}