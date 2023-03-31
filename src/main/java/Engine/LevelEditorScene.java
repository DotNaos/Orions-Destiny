package Engine;

import Util.AssetPool;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import components.Rigidbody;
import components.Sprite;
import components.SpriteRenderer;
import components.Spritesheet;
import imgui.ImGui;
import imgui.ImVec2;
import org.joml.Vector2f;
import org.joml.Vector4f;




public class LevelEditorScene extends Scene {

    private GameObject obj1;
    private Spritesheet sprites;
    private SpriteRenderer obj1Sprite;

    public LevelEditorScene() {

    }

    @Override
    public void init() {

        loadResources();

        // Camera center
        this.camera = new Camera(new Vector2f(-350, -100));
        sprites = AssetPool.getSpritesheet("assets/images/Textures-16.png");
        if (levelLoaded) {
            this.activeGameObject = gameObjects.get(0);
            return;
        }






        // Create a game object
        obj1 = new GameObject("Object 1",
                new Transform(new Vector2f(50, 100), new Vector2f(256, 256)), -1);

        obj1Sprite = new SpriteRenderer();
        obj1Sprite.setColor(new Vector4f(1, 1, 0, 1));
        obj1.addComponent(obj1Sprite);
        obj1.addComponent(new Rigidbody());
        this.addGameObjectToScene(obj1);


        GameObject obj2 = new GameObject("Object 2",
                new Transform(new Vector2f(150, 100), new Vector2f(256, 256)), 2
        );
        SpriteRenderer obj2SpriteRenderer = new SpriteRenderer();
        Sprite obj2Sprite = new Sprite();
        obj2Sprite.setTexture(AssetPool.getTexture("assets/images/blendImage2.png"));
        obj2SpriteRenderer.setSprite(obj2Sprite);
        obj2.addComponent(obj2SpriteRenderer);
        this.addGameObjectToScene(obj2);

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(Component.class, new ComponentDeserializer())
                .registerTypeAdapter(GameObject.class, new GameObjectDeserializer())
                .create();
        String serialized = gson.toJson(obj1);
        System.out.println(serialized);
        GameObject obj = gson.fromJson(serialized, GameObject.class);
        System.out.println(obj);


    }

    private void loadResources() {
        AssetPool.getShader("assets/shaders/default.glsl");


        AssetPool.addSpritesheet("assets/images/Textures-16.png",
                new Spritesheet(AssetPool.getTexture("assets/images/Textures-16.png"),
                        16, 16, 200, 0));
        AssetPool.getTexture("assets/images/blendImage2.png");

    }



    @Override
    public void update(float dt) {
//        Gson gson = new GsonBuilder().setPrettyPrinting().create();
//        System.out.println(gson.toJson(obj1Sprite));

        MouseListener.getOrthoX();
        for (GameObject go : this.gameObjects) {
            go.update(dt);
        }



        this.renderer.render();
    }



    @Override
    public void imgui() {
        ImGui.begin("Level Editor");

        ImVec2 windowPos = new ImVec2();
        ImGui.getWindowPos(windowPos);
        ImVec2 windowSize = new ImVec2();
        ImGui.getWindowSize(windowSize);
        ImVec2 itemSpacing = new ImVec2();
        ImGui.getStyle().getItemSpacing(itemSpacing);

        float windowX2 = windowPos.x + windowSize.x;
        for(int i = 0; i < sprites.size(); i++) {
            Sprite sprite = sprites.getSprite(i);
            float spriteWidth = sprite.getWidth() * 4;
            float spriteHeight = sprite.getHeight() * 4;
            int id = sprite.getTexId();
            Vector2f[] texCoords = sprite.getTexCoords();

            ImGui.pushID(i);
            if (ImGui.imageButton(id, spriteWidth, spriteHeight, texCoords[0].x, texCoords[0].y, texCoords[2].x, texCoords[2].y))
            {
                System.out.println("Clicked on sprite " + i);
            }
            ImGui.popID();

            ImVec2 lastButtonPos = new ImVec2();
            ImGui.getItemRectMax(lastButtonPos);
            float lastButtonX2 = lastButtonPos.x;
            float nextButtonX2 = lastButtonX2 + itemSpacing.x + spriteWidth;
            if (i + 1 < sprites.size() && nextButtonX2 < windowX2) ImGui.sameLine();

        }
        ImGui.end();
        }


}
