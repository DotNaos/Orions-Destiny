package Burst.Engine.Source.Core.Render;


import Burst.Engine.Source.Core.Actor.Actor;
import Burst.Engine.Source.Core.Actor.ActorComponent;
import Burst.Engine.Source.Core.Assets.AssetManager;
import Burst.Engine.Source.Core.Assets.Graphics.Sprite;
import Burst.Engine.Source.Core.Assets.Graphics.Texture;
import Burst.Engine.Source.Core.Physics.Components.Transform;
import org.joml.Vector2f;
import org.joml.Vector4f;

public class SpriteRenderer extends ActorComponent {

    private Vector4f color = new Vector4f(1, 1, 1, 1);
    private transient Vector4f lastColor = new Vector4f(color);
    private Sprite sprite = new Sprite();
    private transient Transform lastTransform;

    public SpriteRenderer(Actor actor) {
        super(actor);
    }

    @Override
    public void init() {
        super.init();
        this.sprite.setTexture(AssetManager.getAssetFromType(this.sprite.getFilepath(), Texture.class));
        this.lastTransform = this.actor.getTransform().copy();
    }

    @Override
    public void update(float dt) {
        super.update(dt);
        if (this.lastTransform == null) lastTransform = this.actor.getTransform().copy();
        if (this.lastColor == null) lastColor = new Vector4f(color);

        // Print values of transform and lastTransform side by side
//        System.out.println(this.actor.getTransform().getPosition().x + " " + this.lastTransform.getPosition().x);
//        System.out.println(this.actor.getTransform().getPosition().y + " " + this.lastTransform.getPosition().y);
//        System.out.println(this.actor.getTransform().size.x + " " + this.lastTransform.size.x);
//        System.out.println(this.actor.getTransform().size.y + " " + this.lastTransform.size.y);
//        System.out.println(this.actor.getTransform().getScale().x + " " + this.lastTransform.getScale().x);
//        System.out.println(this.actor.getTransform().getScale().y + " " + this.lastTransform.getScale().y);
//        System.out.println(this.actor.getTransform().getRotation() + " " + this.lastTransform.getRotation());
//
//        System.out.println("\n");


        if (!this.lastTransform.equals(this.actor.getTransform())) {
            lastTransform = this.actor.getTransform().copy();
            isDirty = true;
        }
        else if (!this.lastColor.equals(this.color)) {
            this.lastColor.set(this.color);
            isDirty = true;
        }
        else if (sprite.isDirty()) {
            isDirty = true;
            sprite.setClean();
        }
        else if (this.actor.getTransform().isDirty())
        {
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
        return sprite.getTexture();
    }
    public Sprite getSprite() {
        return sprite;
    }

    public void setTexture(Texture texture) {
        this.sprite.setTexture(texture);
    }

    public Vector2f[] getTexCoords() {
        return sprite.getTexCoords();
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
        this.isDirty = true;
    }
}
