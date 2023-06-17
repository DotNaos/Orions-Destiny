package Burst.Engine.Source.Core.Render;

import Burst.Engine.Source.Core.Actor.Actor;
import Burst.Engine.Source.Core.Assets.Graphics.Shader;
import Burst.Engine.Source.Core.Assets.Graphics.Texture;
import org.joml.Vector2i;

import java.util.ArrayList;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.List;

/**
 * @author GamesWithGabe
 * The ViewportRenderer class is responsible for rendering graphics in the game.
 * It provides methods to render various elements on the screen.
 * It extends the Renderer class and provides methods to manipulate the viewport and its properties.
 * It also provides methods to add actors to the renderer.
 */
public class ViewportRenderer extends Renderer {
  private static Shader currentShader;
  private final int MAX_BATCH_SIZE = 2000;
  private List<RenderBatch> batches = new ArrayList<>();

  private static Vector2i viewportSize = new Vector2i(1920, 1080);

  public ViewportRenderer() {

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

  private List<Actor> actorsToAdd = new ArrayList<>();


  public void addActor(Actor actor) {
    SpriteRenderer spr = actor.getComponent(SpriteRenderer.class);

    if (spr != null) {
      addSpriteRenderer(spr);
    }
  }

  private void addSpriteRenderer(SpriteRenderer sprite) {
    boolean added = false;
    for (RenderBatch batch : batches) {
      if (batch.hasRoom() && batch.zIndex() == sprite.actor.getTransform().getZIndex()) {
        Texture tex = sprite.getTexture();
        if (tex == null || (batch.hasTexture(tex) || batch.hasTextureRoom())) {
          batch.addSprite(sprite);
          added = true;
          break;
        }
      }
    }

    if (!added) {
      RenderBatch newBatch = new RenderBatch(MAX_BATCH_SIZE, sprite.actor.getTransform().getZIndex(), this);
      newBatch.start();
      batches.add(newBatch);
      newBatch.addSprite(sprite);
      Collections.sort(batches);
    }
  }

  /**
   * <p>
   * This method is thread safe. It will add the actor to the renderer on the next render call.
   * Not calling this method could throw a ConcurrentModificationException
   * </p>
   *
   * @param actorToAdd Actor to add to the renderer
   */
  public void addActorLater(Actor actorToAdd) {
    actorsToAdd.add(actorToAdd);
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
    for (Actor actor : actorsToAdd) {
      if (actor.isPickable()) {
        addActor(actor);
      }
    }
    actorsToAdd.clear();

    currentShader.use();
    try {
      for (RenderBatch batch : batches) {
        batch.render();
      }
    } catch (ConcurrentModificationException e) {
      // TODO: FIX CONCURRENT MODIFICATION EXCEPTION
//      e.printStackTrace();
    }

  }
}
