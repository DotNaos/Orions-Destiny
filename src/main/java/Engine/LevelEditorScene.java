package Engine;

import Util.AssetPool;
import components.Sprite;
import components.SpriteRenderer;
import components.Spritesheet;
import org.joml.Vector2f;
import org.joml.Vector4f;

public class LevelEditorScene extends Scene {

    public LevelEditorScene() {

    }

    @Override
    public void init() {
        loadResources();

        // Camera center
        this.camera = new Camera(new Vector2f( -300, 0));

        Spritesheet sprites = AssetPool.getSpritesheet("assets/images/Walk.png");

        // Create a game object
        GameObject obj1 = new GameObject("obj1", new Transform(new Vector2f(100, 256), new Vector2f(256, 256)));
        obj1.addComponent(new SpriteRenderer(sprites.getSprite(0)));
        this.addGameObjectToScene(obj1);

        GameObject obj2 = new GameObject("obj2", new Transform(new Vector2f(400, 256), new Vector2f(256, 256)));
        obj2.addComponent(new SpriteRenderer(sprites.getSprite(5)));
        this.addGameObjectToScene(obj2);


    }

    private void loadResources() {
        AssetPool.getShader("assets/shaders/default.glsl");

        AssetPool.addSpritesheet("assets/images/Walk.png",
                new Spritesheet(AssetPool.getTexture("assets/images/Walk.png"),
                        117, 26, 8, 0));

    }

    @Override
    public void update(float dt) {
//        System.out.println("FPS: " + 1.0f / dt);
        for (GameObject go : this.gameObjects) {
            go.update(dt);
        }

        this.renderer.render();
    }
}