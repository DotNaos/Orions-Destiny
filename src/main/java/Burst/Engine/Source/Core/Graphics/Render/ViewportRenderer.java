package Burst.Engine.Source.Core.Graphics.Render;

import Burst.Engine.Source.Core.Actor.Actor;
import Burst.Engine.Source.Core.Assets.Graphics.Shader;
import Burst.Engine.Source.Core.Assets.Graphics.Texture;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ViewportRenderer extends Renderer{
    private final int MAX_BATCH_SIZE = 2000;
    private List<RenderBatch> batches;
    private static Shader currentShader;

    public ViewportRenderer() {
        this.batches = new ArrayList<>();
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
            RenderBatch newBatch = new RenderBatch(MAX_BATCH_SIZE,
                    sprite.actor.transform.zIndex, this);
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

    public static void bindShader(Shader shader) {
        currentShader = shader;
    }

    public static Shader getBoundShader() {
        return currentShader;
    }

    @Override
    public void render() {
        currentShader.use();
        for (RenderBatch batch : batches) {
            batch.render();
        }
    }
}
