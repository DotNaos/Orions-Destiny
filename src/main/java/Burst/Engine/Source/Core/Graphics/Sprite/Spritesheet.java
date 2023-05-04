package Burst.Engine.Source.Core.Graphics.Sprite;

import Burst.Engine.Source.Core.Graphics.Render.Texture;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.List;

public class Spritesheet {

    private Texture texture;
    private List<Sprite> sprites;

    public Spritesheet(Texture texture, int spriteWidth, int spriteHeight, int spacing) {
        this.sprites = new ArrayList<>();

        this.texture = texture;

        int rows = texture.getWidth() / (spriteWidth + spacing);
        int cols = texture.getHeight() / (spriteHeight + spacing);
        int numSprites = rows * cols;
        int currentX = 0;
        int currentY = texture.getHeight() - spriteHeight;
        for (int row=0; row < rows; row++) {
            for (int col = 0; col < cols; col++)
            {
                float topY = (currentY + spriteHeight) / (float)texture.getHeight();
                float rightX = (currentX + spriteWidth) / (float)texture.getWidth();
                float leftX = currentX / (float)texture.getWidth();
                float bottomY = currentY / (float)texture.getHeight();

                Vector2f[] texCoords = {
                        new Vector2f(rightX, topY),
                        new Vector2f(rightX, bottomY),
                        new Vector2f(leftX, bottomY),
                        new Vector2f(leftX, topY)
                };
                Sprite sprite = new Sprite();
                sprite.setTexture(this.texture);
                sprite.setTexCoords(texCoords);
                sprite.setWidth(spriteWidth);
                sprite.setHeight(spriteHeight);

                boolean noTexture = Sprite.isEmpty(sprite);
                // check if the sprite is empty, if so, skip the rest of the row
                if (noTexture) {
                    break;
                }

                this.sprites.add(sprite);

                currentX += spriteWidth + spacing;
                if (currentX >= texture.getWidth()) {
                    currentX = 0;
                    currentY -= spriteHeight + spacing;
                }
            }
        }
    }

    public Sprite getSprite(int index) {
        return this.sprites.get(index);
    }

    public List<Sprite> getSprites() { return this.sprites; }

    public int size() {
        return sprites.size();
    }
}