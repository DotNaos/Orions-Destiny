package Burst.Engine.Source.Core.Render;


import Burst.Engine.Source.Core.Actor.Actor;
import Burst.Engine.Source.Core.Actor.ActorComponent;
import Burst.Engine.Source.Core.Assets.AssetManager;
import Burst.Engine.Source.Core.Assets.Graphics.Sprite;
import Burst.Engine.Source.Core.Assets.Graphics.Texture;
import Burst.Engine.Source.Core.Component;
import Burst.Engine.Source.Core.Physics.Components.Transform;
import Burst.Engine.Source.Core.UI.ImGui.BImGui;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class SpriteRenderer extends ActorComponent {

    private Vector4f color = new Vector4f(1, 1, 1, 1);
    private Sprite sprite = new Sprite();

    private transient Transform lastTransform;
    private transient boolean isDirty = true;

    public SpriteRenderer(Actor actor) {
        super(actor);
    }

    @Override
    public void start() {
        super.start();
        this.sprite.setTexture(AssetManager.getAssetFromType(this.sprite.getFilepath(), Texture.class));
        this.lastTransform = actor.getTransform().copy();
    }

    @Override
    public void update(float dt) {
        if (this.lastTransform == null) start();
        if (!this.lastTransform.equals(this.actor.getTransform())) {
            this.actor.getTransform().copy(this.lastTransform);
            isDirty = true;
        }
    }

    @Override
    public void updateEditor(float dt) {
        if (!this.lastTransform.equals(this.actor.getTransform())) {
            this.actor.getTransform().copy(this.lastTransform);
            isDirty = true;
        }
    }

    public void setDirty() {
        this.isDirty = true;
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

    public boolean isDirty() {
        return this.isDirty;
    }

    public void setClean() {
        this.isDirty = false;
    }
}
