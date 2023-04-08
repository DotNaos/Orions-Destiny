package components;

import org.joml.Vector2f;
import renderer.Texture;

import java.util.ArrayList;
import java.util.List;

public class Spritesheet {

    private Texture texture;
    private List<Sprite> sprites;
    private int MAX_SPRITES = 20;
    private boolean hasTransparency = false;



    public Spritesheet(Texture texture, int spriteWidth, int spriteHeight, int spacing, boolean hasTransparency, int maxSprites) {
        this.sprites = new ArrayList<>();

        this.texture = texture;
        this.hasTransparency = hasTransparency;
        this.MAX_SPRITES = maxSprites;
        int currentX = 0;
        int currentY = texture.getHeight() - spriteHeight;


        // Calculate the max number of sprites that can fit in the texture
        int maxSpritesX = texture.getWidth() / (spriteWidth + spacing);
        int maxSpritesY = texture.getHeight() / (spriteHeight + spacing);

        int spriteCount = Math.min(MAX_SPRITES, maxSpritesX * maxSpritesY);
        for (int i=0; i < spriteCount; i++) {
            // Check if the current sprite area is fully transparent
            if (hasTransparency)
            {
                if (isTransparent(currentX, currentY, spriteWidth, spriteHeight)) {
                    // Skip creating this sprite if the area is fully transparent
                    // dont check the rest of the row if the first sprite is transparent
                    currentX = 0;
                    currentY -= spriteHeight + spacing;
                    continue;
                }
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
            this.sprites.add(sprite);

            currentX += spriteWidth + spacing;
            if (currentX >= texture.getWidth()) {
                currentX = 0;
                currentY -= spriteHeight + spacing;
            }
        }
    }

    public Sprite getSprite(int index) {
        return this.sprites.get(index);
    }

    public int size() {
        return this.sprites.size();
    }

    private boolean isTransparent(int startX, int startY, int width, int height) {
        // Iterate through the pixels in the specified area of the texture
//        for (int y = startY; y < startY + height; y++) {
//            for (int x = startX; x < startX + width; x++) {
//                // Get the color of the current pixel
//                int pixel = texture.getPixel(x, y);
//
//                // Check if the alpha value is 0 (fully transparent)
//                if ((pixel >> 24) == 0) {
//                    // Return true if the area is fully transparent
//                    return true;
//                }
//            }
//        }

        // Return false if the area is not fully transparent
        return false;
    }
}