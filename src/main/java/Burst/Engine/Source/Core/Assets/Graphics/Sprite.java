package Burst.Engine.Source.Core.Assets.Graphics;

import Burst.Engine.Source.Core.Assets.Asset;
import org.joml.Vector2f;

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

    public void setTexture(Texture tex) {
        this.texture = tex;
    }

    public Vector2f[] getTexCoords() {
        return this.texCoords;
    }

    public void setTexCoords(Vector2f[] texCoords) {
        this.texCoords = texCoords;
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
        return texture == null ? -1 : texture.getId();
    }
}
