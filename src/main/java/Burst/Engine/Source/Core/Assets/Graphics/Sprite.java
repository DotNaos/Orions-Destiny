package Burst.Engine.Source.Core.Assets.Graphics;

import Burst.Engine.Source.Core.Assets.Asset;
import Burst.Engine.Source.Core.Util.DebugMessage;
import Orion.res.AssetConfig;
import org.joml.Vector2f;

import java.util.Arrays;
/**
 * @author Oliver Schuetz
 */
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

    private Vector2f[] resetTexCoords = copyTexCoords(texCoords);


    @Override
    public void init()
    {
        if (this.texture == null)
        {
            setTexture(new Texture(this.filepath));
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
        this.texture.init();
        this.width = texture.getWidth();
        this.height = texture.getHeight();
    }

    public Sprite(Texture texture, Vector2f[] texCoords, int spriteWidth, int spriteHeight) {
        super(texture.getFilepath());
        this.texture = texture;
        this.texCoords = texCoords;
        this.resetTexCoords = copyTexCoords(texCoords);
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
        if (this.texture == null) {
            this.texture = new Texture(this.filepath);
            this.texture.init();
        }
        return this.texture;
    }



    public Vector2f[] getTexCoords() {
        Vector2f[] texCoords = new Vector2f[] {
                new Vector2f(this.texCoords[0]),
                new Vector2f(this.texCoords[1]),
                new Vector2f(this.texCoords[2]),
                new Vector2f(this.texCoords[3])
        };

        return texCoords;
    }


    public float getWidth() {
        return width;
    }


    public float getHeight() {
        return height;
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

        // set it to dirty
        this.isDirty = true;
    }

    @Override
    public Asset build() {
        return this;
    }

  public void resetTexCoords() {
    this.texCoords = copyTexCoords(resetTexCoords);
  }

  private Vector2f[] copyTexCoords(Vector2f[] texCoords) {
    Vector2f[] newTexCoords = new Vector2f[texCoords.length];
    for (int i = 0; i < texCoords.length; i++) {
      newTexCoords[i] = new Vector2f(texCoords[i]);
    }

    return newTexCoords;
  }


}
      