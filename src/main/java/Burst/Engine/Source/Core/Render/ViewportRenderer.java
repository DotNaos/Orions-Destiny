package Burst.Engine.Source.Core.Render;

import Burst.Engine.Source.Core.Actor.Actor;
import Burst.Engine.Source.Core.Assets.Graphics.Shader;
import Burst.Engine.Source.Core.Assets.Graphics.Texture;
import org.joml.Vector2i;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ViewportRenderer extends Renderer {
    private static Shader currentShader;
    private final int MAX_BATCH_SIZE = 2000;
    private List<RenderBatch> batches;

    private static Vector2i viewportSize = new Vector2i(1920, 1080);

    public ViewportRenderer() {
        this.batches = new ArrayList<>();
    }

    public static void bindShader(Shader shader) {
        currentShader = shader;
    }

    public static Shader getBoundShader() {
        return currentShader;
    }

    public static Vector2i getViewportSize() {
        return viewportSize;
    }

    public int getViewportWidth() {
        return viewportSize.x;
    }

    public int getViewportHeight() {
        return viewportSize.y;
    }

    public void add(Actor actor) {
        SpriteRenderer spr = actor.getComponent(SpriteRenderer.class);
        if (spr != null) {
            add(spr);
        }
    }

    private void add(SpriteRenderer sprite) {
        boolean added = false;
        for (RenderBatch batch : batches) {
            if (batch.hasRoom() && batch.zIndex() == sprite.actor.transform.zIndex) {
                Texture tex = sprite.getTexture();
                if (tex == null || (batch.hasTexture(tex) || batch.hasTextureRoom())) {
                    batch.addSprite(sprite);
                    added = true;
                    break;
                }
            }
        }

        if (!added) {
            RenderBatch newBatch = new RenderBatch(MAX_BATCH_SIZE, sprite.actor.transform.zIndex, this);
            newBatch.start();
            batches.add(newBatch);
            newBatch.addSprite(sprite);
            Collections.sort(batches);
        }
    }

    public void destroyActor(Actor actor) {
        if (actor.getComponent(SpriteRenderer.class) == null) return;
        for (RenderBatch batch : batches) {
            if (batch.destroyIfExists(actor)) {
                return;
            }
        }
    }

    @Override
    public void render() {
        currentShader.use();
        for (RenderBatch batch : batches) {
            batch.render();
        }
    }
}
