package Burst.Engine.Source.Core.Render;


import Burst.Engine.Source.Core.Actor.Actor;
import Burst.Engine.Source.Core.Actor.ActorComponent;
import Burst.Engine.Source.Core.Assets.AssetManager;
import Burst.Engine.Source.Core.Assets.Graphics.Sprite;
import Burst.Engine.Source.Core.Assets.Graphics.Texture;
import Burst.Engine.Source.Core.Physics.Components.Transform;
import org.joml.Vector2f;
import org.joml.Vector4f;

/**
 * @author Oliver Schuetz
 * The SpriteRenderer class is responsible for rendering sprites on the screen.
 * It extends the ActorComponent class and provides methods to manipulate the sprite and its properties.
 */
public class SpriteRenderer extends ActorComponent {

  private Vector4f color = new Vector4f(1, 1, 1, 1);
  private transient Vector4f lastColor = new Vector4f(color);
  private Sprite sprite = new Sprite();
  private transient Transform lastTransform;

  public SpriteRenderer() {
    super();
  }

  @Override
  public void init() {
    super.init();
    Sprite freshSprite = new Sprite();
    freshSprite.setTexture(new Texture(sprite.getFilepath()));

    Vector2f[] texCoords = sprite.getTexCoords();
    Vector2f[] freshTexCoords = {new Vector2f(texCoords[0]), new Vector2f(texCoords[1]), new Vector2f(texCoords[2]), new Vector2f(texCoords[3])};

    freshSprite.setTexCoords(freshTexCoords);

    this.sprite = freshSprite;

    this.lastTransform = this.actor.getTransform().copy();
    setDirty();
  }

  @Override
  public void updateEditor(float dt) {
    super.updateEditor(dt);
    update(dt);
  }

  @Override
  public void update(float dt) {
    super.update(dt);
    if (this.lastTransform == null) lastTransform = this.actor.getTransform().copy();
    if (this.lastColor == null) lastColor = new Vector4f(color);

    if (!this.lastTransform.equals(this.actor.getTransform())) {
      lastTransform = this.actor.getTransform().copy();
      isDirty = true;
    } else if (!this.lastColor.equals(this.color)) {
      this.lastColor.set(this.color);
      isDirty = true;
    } else if (sprite.isDirty()) {
      isDirty = true;
      sprite.setClean();
    } else if (this.actor.getTransform().isDirty()) {
      isDirty = true;
      this.actor.getTransform().setClean();
    }

    this.lastTransform.copy(this.actor.getTransform());
  }


  public Vector4f getColor() {
    return this.color;
  }

  public void setColor(Vector4f color) {
    if (!this.color.equals(color)) {
      this.isDirty = true;
      this.color.set(color);
    }
  }

  public Texture getTexture() {
    return getSprite().getTexture();
  }

  public Sprite getSprite() {
    if (sprite == null) {
      this.sprite = new Sprite();
      this.sprite.setTexture(AssetManager.getAssetFromType(this.sprite.getFilepath(), Texture.class));
    }
    return sprite;
  }

  public SpriteRenderer setTexture(Texture texture) {
    this.sprite.setTexture(texture);
    this.isDirty = true;
    return this;
  }

  public Vector2f[] getTexCoords() {
    return sprite.getTexCoords();
  }

  public SpriteRenderer setSprite(Sprite sprite) {
    this.sprite = sprite;
    this.isDirty = true;
    return this;
  }
}
