package Engine;

import Util.AssetPool;

import components.Sprite;
import components.SpriteRenderer;
import components.Spritesheet;
import imgui.ImGui;
import org.joml.Vector2f;
import org.joml.Vector4f;


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
        obj1 = new GameObject("Object 1",
                new Transform(new Vector2f(50, 100), new Vector2f(256, 256)), -1);
        obj1.addComponent(new SpriteRenderer(
           new Vector4f(1, 0, 0, 1)
        ));
        this.addGameObjectToScene(obj1);
        this.activeGameObject = obj1;

        GameObject obj2 = new GameObject("Object 2",
                new Transform(new Vector2f(150, 100), new Vector2f(256, 256)), 2
        );
        obj2.addComponent(new SpriteRenderer(
                new Sprite(
                        AssetPool.getTexture("assets/images/blendImage1.png")
                )
        ));
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

        for (GameObject go : this.gameObjects) {
            go.update(dt);
        }

        this.renderer.render();
    }

    @Override
    public void imgui() {
        ImGui.begin("Level Editor");
        ImGui.text("Level Editor");
        ImGui.end();
    }
}