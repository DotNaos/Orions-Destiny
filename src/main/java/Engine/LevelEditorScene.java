package Engine;

import Util.AssetPool;
import components.SpriteRenderer;
import org.joml.Vector2f;
import org.joml.Vector4f;

public class LevelEditorScene extends Scene {

    public LevelEditorScene() {

    }

    @Override
    public void init() {
        // Camera center
        this.camera = new Camera(new Vector2f( -300, 0));

        // Create a game object
        GameObject obj1 = new GameObject("obj1", new Transform(new Vector2f(100, 256), new Vector2f(256, 256)));
        obj1.addComponent(new SpriteRenderer(AssetPool.getTexture("assets/images/Boden.png")));
        this.addGameObjectToScene(obj1);

        GameObject obj2 = new GameObject("obj2", new Transform(new Vector2f(400, 256), new Vector2f(256, 256)));
        obj2.addComponent(new SpriteRenderer(AssetPool.getTexture("assets/images/character.png")));
        this.addGameObjectToScene(obj2);

        loadResources();
    }

    private void loadResources() {
        AssetPool.getShader("assets/shaders/default.glsl");

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