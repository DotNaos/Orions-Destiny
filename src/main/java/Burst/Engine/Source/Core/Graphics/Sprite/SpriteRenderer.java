package Burst.Engine.Source.Core.Graphics.Sprite;

import Burst.Engine.Source.Core.Graphics.Render.Texture;
import Burst.Engine.Source.Editor.BImGui;
import Burst.Engine.Source.Core.Physics.Components.Transform;
import Burst.Engine.Source.Core.Component;
import org.joml.Vector2f;
import org.joml.Vector4f;
import Burst.Engine.Source.Core.util.AssetManager;

public class SpriteRenderer extends Component {

    private Vector4f color = new Vector4f(1, 1, 1, 1);
    private Sprite sprite = new Sprite();

    private transient Transform lastTransform;
    private transient boolean isDirty = true;

    @Override
    public void start() {
        if (this.sprite.getTexture() != null) {
            this.sprite.setTexture(AssetManager.getTexture(this.sprite.getTexture().getFilepath()));
        }
        this.lastTransform = gameObject.transform.copy();
    }

    @Override
    public void update(float dt) {
        if (!this.lastTransform.equals(this.gameObject.transform)) {
            this.gameObject.transform.copy(this.lastTransform);
            isDirty = true;
        }
    }

    @Override
    public void editorUpdate(float dt) {
        if (!this.lastTransform.equals(this.gameObject.transform)) {
            this.gameObject.transform.copy(this.lastTransform);
            isDirty = true;
        }
    }

    @Override
    public void imgui() {
        if (BImGui.colorPicker4("Color Pickier", this.color)) {
            this.isDirty = true;
        }
    }

    public void setDirty() {
        this.isDirty = true;
    }

    public Vector4f getColor() {
        return this.color;
    }

    public Texture getTexture() {
        return sprite.getTexture();
    }

    public Vector2f[] getTexCoords() {
        return sprite.getTexCoords();
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
        this.isDirty = true;
    }

    public void setColor(Vector4f color) {
        if (!this.color.equals(color)) {
            this.isDirty = true;
            this.color.set(color);
        }
    }

    public boolean isDirty() {
        return this.isDirty;
    }

    public void setClean() {
        this.isDirty = false;
    }

    public void setTexture(Texture texture) {
        this.sprite.setTexture(texture);
    }
}