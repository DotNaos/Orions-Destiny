package Burst.Engine.Source.Core.Assets.Graphics;

import Burst.Engine.Source.Core.Assets.Asset;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class Sprite extends Asset {

    private float width, height;

    private Texture texture = null;
    private Vector2f[] texCoords = {
            new Vector2f(1, 1),
            new Vector2f(1, 0),
            new Vector2f(0, 0),
            new Vector2f(0, 1)
    };

    public Sprite() {
        super("");
        this.width = 0;
        this.height = 0;
    }

    public Sprite(Texture texture, Vector2f[] texCoords, int spriteWidth, int spriteHeight) {
        super("");
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
        for (int i = 0; i < sprite.getTexture().getWidth(); i++) {
            for (int j = 0; j < sprite.getTexture().getHeight(); j++) {

                return false;

            }
        }
        System.out.println("Transparent");
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

    public int getTexId() {
        return texture == null ? -1 : texture.getTexID();
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public void setTexCoords(Vector2f[] texCoords) {
        this.texCoords = texCoords;
    }

    @Override
    public Asset build() {
        return this;
    }
}
      