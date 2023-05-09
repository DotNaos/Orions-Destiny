package Burst.Engine.Source.Core.Assets.Graphics;

import Burst.Engine.Source.Core.Assets.Asset;

import Burst.Engine.Source.Core.util.DebugMessage;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.List;

public class Spritesheet extends Asset {

    private Texture texture;
    private List<Sprite> sprites;
    private SpriteSheetUsage usage;
    private int spriteCount = 0;
    private static int MAX_SPRITES = -1;
    private int[] spritesPerRow;

    public Spritesheet(Texture texture, int spriteWidth, int spriteHeight, int spacing) {
        super(texture.getFilepath());
        this.sprites = new ArrayList<>();
        this.texture = texture;

        int rows = texture.getWidth() / (spriteWidth + spacing);
        int cols = texture.getHeight() / (spriteHeight + spacing);
        int currentX = 0;
        int currentY = texture.getHeight() - spriteHeight;
        for (int row=0; row < rows; row++) {
            for (int col = 0; col < cols; col++)
            {
                if (this.spriteCount >= MAX_SPRITES && MAX_SPRITES != -1) {
                    DebugMessage.printWarning("Spritesheet has reached maximum number of sprites: " + MAX_SPRITES);
                    return;
                }
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
                this.spriteCount++;
            }
        }


    }

    public Sprite getSprite(int index) {
        return this.sprites.get(index);
    }

    public int size() {
        return sprites.size();
    }

    public int getCount() {
        return this.spriteCount;
    }
}
