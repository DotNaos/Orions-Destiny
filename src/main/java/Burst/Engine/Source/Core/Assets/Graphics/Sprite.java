package Burst.Engine.Source.Core.Assets.Graphics;

import Burst.Engine.Source.Core.Assets.Asset;
import Burst.Engine.Source.Core.Util.DebugMessage;
import org.joml.Vector2f;

public class Sprite extends Asset {

    private float width, height;

    private transient Texture texture = null;
    /**
     * The texture coordinates of the sprite
     * <p>
     *   The texture coordinates are in the following order:
     * <p>
     *   1, 1 | 1, 0
     * <p>
     *   0, 0 | 0, 1
     * <p>
     *
     */
    private Vector2f[] texCoords = {
            new Vector2f(1, 1),
            new Vector2f(1, 0),
            new Vector2f(0, 0),
            new Vector2f(0, 1)
    };

    @Override
    public void init()
    {
        if (this.texture == null)
        {
            this.texture = new Texture(this.filepath);
        }
    }

    public Sprite() {
        super("Generated");
        this.width = 0;
        this.height = 0;
    }

    public Sprite(String filePath) {
        super(filePath);
        this.texture = new Texture(filePath);
        this.width = texture.getWidth();
        this.height = texture.getHeight();
    }

    public Sprite(Texture texture, Vector2f[] texCoords, int spriteWidth, int spriteHeight) {
        super(texture.getFilepath());
        this.texture = texture;
        this.texCoords = texCoords;
        this.width = spriteWidth;
        this.height = spriteHeight;
    }

    /**
     * Checks if the Sprite has only transparent pixels
     *
     * @return true if the sprite is empty, false otherwise
     */
    public static boolean isEmpty(Sprite sprite) {
        if (sprite == null)
        {
            DebugMessage.info("Sprite is null");
            return true;
        }

        // Get the texture of the sprite
        Texture texture = sprite.getTexture();

        // Get a buffer of the texture with texture coordinates of the sprite
        float[] buffer = texture.getBuffer(sprite.getTexCoords());

        // Check if the buffer is empty
        for (float pixel : buffer) {
            if (pixel != 0) return false;
        }
        return true;
    }


    public Texture getTexture() {
        return this.texture;
    }



    public Vector2f[] getTexCoords() {
        return this.texCoords;
    }


    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public int getTexID() {
        return texture == null ? -1 : texture.getTexID();
    }

    public Sprite setTexture(Texture texture) {
        if (texture == null) return this;

        this.texture = texture;
        if(!this.texture.isInitialized()) this.texture.init();
        this.filepath = texture.getFilepath();

        this.width = texture.getWidth();
        this.height = texture.getHeight();

        return this;
    }

    public void setTexCoords(Vector2f[] texCoords) {
        this.texCoords = texCoords;
    }

    @Override
    public Asset build() {
        return this;
    }
}
      