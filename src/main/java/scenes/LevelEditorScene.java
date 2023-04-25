package scenes;

import Burst.*;
import components.*;
import imgui.ImGui;
import imgui.ImVec2;
import org.joml.Vector2f;
import util.AssetPool;

public class LevelEditorScene extends Scene {

    private Spritesheet sprites;

    GameObject levelEditorStuff = this.createGameObject("LevelEditor");

    Transform obj1, obj2;


    public LevelEditorScene() {

    }
    @Override
    public void init() {
        loadResources();
        sprites = AssetPool.getSpritesheet("assets/images/spritesheets/blocks.png");
        Spritesheet gizmos = AssetPool.getSpritesheet("assets/images/gizmos.png");


        this.camera = new Camera(new Vector2f(-250, 0));
        levelEditorStuff.addComponent(new MouseControls());
        levelEditorStuff.addComponent(new GridLines());
        levelEditorStuff.addComponent(new EditorCamera(this.camera));
        levelEditorStuff.addComponent(new GizmoSystem(gizmos));

        levelEditorStuff.start();

    }

    private void loadResources() {
        AssetPool.getShader("assets/shaders/default.glsl");

        AssetPool.addSpritesheet("assets/images/spritesheets/blocks.png",
                new Spritesheet(AssetPool.getTexture("assets/images/spritesheets/blocks.png"),
                        16, 16, 81, 0));
        AssetPool.addSpritesheet("assets/images/gizmos.png",
                new Spritesheet(AssetPool.getTexture("assets/images/gizmos.png"),
                        24, 48, 3, 0));
        AssetPool.getTexture("assets/images/blendImage2.png");

        for (GameObject g : gameObjects) {
            if (g.getComponent(SpriteRenderer.class) != null) {
                SpriteRenderer spr = g.getComponent(SpriteRenderer.class);
                if (spr.getTexture() != null) {
                    spr.setTexture(AssetPool.getTexture(spr.getTexture().getFilepath()));
                }
            }
        }
    }

    float t = 0.0f;

    @Override
    public void update(float dt) {
        levelEditorStuff.update(dt);
        this.camera.adjustProjection();

        for (GameObject go : this.gameObjects) {
            go.update(dt);
        }


//                DebugDraw.drawRainbow2(t);
//                DebugDraw.addBox2D(new Vector2f(200, 200), new Vector2f((int) Math.abs(256 * Math.sin(t)), (int) Math.abs(128 * Math.cos(t))), (float) (360 * Math.tan(t)), new Vector3f(0, 1, 0), 1);
//                DebugDraw.addCircle(new Vector2f(200, 200), (int)(((Math.sin(t * 2) + 1) / 2)* 4 * 100), new Vector3f(1, 0, 0), 1);

        t += dt * 0.5f;
    }

    @Override
    public void render() {
        this.renderer.render();
    }

    @Override
    public void imgui() {
        ImGui.begin("Level Editor Stuff");
        levelEditorStuff.imgui();
        ImGui.end();

        ImGui.begin("Test window");

        ImVec2 windowPos = new ImVec2();
        ImGui.getWindowPos(windowPos);
        ImVec2 windowSize = new ImVec2();
        ImGui.getWindowSize(windowSize);
        ImVec2 itemSpacing = new ImVec2();
        ImGui.getStyle().getItemSpacing(itemSpacing);

        float windowX2 = windowPos.x + windowSize.x;
        for (int i=0; i < sprites.size(); i++) {
            Sprite sprite = sprites.getSprite(i);
            float spriteWidth = sprite.getWidth() * 4;
            float spriteHeight = sprite.getHeight() * 4;
            int id = sprite.getTexId();
            Vector2f[] texCoords = sprite.getTexCoords();

            ImGui.pushID(i);
            if (ImGui.imageButton(id, spriteWidth, spriteHeight, texCoords[2].x, texCoords[0].y, texCoords[0].x, texCoords[2].y)) {
                GameObject object = Prefabs.generateSpriteObject(sprite, 32, 32);
                levelEditorStuff.getComponent(MouseControls.class).pickupObject(object);
            }
            ImGui.popID();

            ImVec2 lastButtonPos = new ImVec2();
            ImGui.getItemRectMax(lastButtonPos);
            float lastButtonX2 = lastButtonPos.x;
            float nextButtonX2 = lastButtonX2 + itemSpacing.x + spriteWidth;
            if (i + 1 < sprites.size() && nextButtonX2 < windowX2) {
                ImGui.sameLine();
            }
        }

        ImGui.end();
    }
}