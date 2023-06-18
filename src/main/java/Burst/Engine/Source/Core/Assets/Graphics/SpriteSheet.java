package Burst.Engine.Source.Core.Assets.Graphics;

import Burst.Engine.Source.Core.Assets.Asset;
import Burst.Engine.Source.Core.Util.DebugMessage;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;
/**
 * @author Oliver Schuetz
 */
public class SpriteSheet extends Asset {
    /**
     * <p>
     * Maximum number of sprites in a SpriteSheet. -1 for no limit.
     * </p>
     *
     * <p>
     *   Global limit for all SpriteSheets
     * </p>
     */
    private static final int MAX_SPRITES = -1;
    private Texture texture;
    private List<Sprite> sprites;
    private int spriteCount = 0;
    private int rows, cols = -1;


    private SpriteSheetConfig config;

    public SpriteSheet(String filepath) {
        super(filepath);
        this.texture = new Texture(filepath);
    }

    @Override
    public void init()
    {
        super.init();
        refresh();
    }

    public void refresh() {
        this.texture = new Texture(this.filepath);
//        createSprites(rows, cols);
    }

    public void setConfig(SpriteSheetConfig config) {
        this.config = config;
        cols = (texture.getWidth() / (config.spriteWidth + config.spacing));
        rows = texture.getHeight() / (config.spriteHeight + config.spacing);
        createSprites(rows, cols);
    }

    private void createSprites(int rows, int cols) {
        this.sprites = new ArrayList<>();
        int currentX = this.config.spacing;
        int currentY = this.config.spacing;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (this.spriteCount >= MAX_SPRITES && MAX_SPRITES != -1) {
                    DebugMessage.printWarning("Spritesheet has reached maximum number of sprites: " + MAX_SPRITES);
                    return;
                }

                float topY = (currentY + config.spriteHeight) / (float) texture.getHeight();
                float rightX = (currentX + config.spriteWidth) / (float) texture.getWidth();
                float leftX = currentX / (float) texture.getWidth();
                float bottomY = currentY / (float) texture.getHeight();

                Vector2f[] texCoords = {
                        new Vector2f(rightX, topY),
                        new Vector2f(rightX, bottomY),
                        new Vector2f(leftX, bottomY),
                        new Vector2f(leftX, topY)
                };
                Sprite sprite = new Sprite(this.texture, texCoords, this.config.spriteWidth, this.config.spriteHeight);

                if (config.spritesPerRow == null)
                {
                    this.sprites.add(sprite);
                }
                else if (!(col >= this.config.spritesPerRow[row])) {
                    this.sprites.add(sprite);
                }

                currentX += config.spriteWidth + config.spacing;
                if (currentX >= texture.getWidth() - config.spacing) {
                    currentX = config.spacing;
                    currentY += config.spriteHeight + config.spacing;
                }
                this.spriteCount++;
            }
        }
    }

    public Sprite getSprite(int index) {
        // Check if the sprite is out of bounds
        // Too low then return the first sprite
        // Too high then return the last sprite

        if (index >= this.sprites.size()) {
            DebugMessage.printWarning("Sprite index out of bounds: " + index);
            index = this.sprites.size() - 1;
        } else if (index < 0) {
            DebugMessage.printWarning("Sprite index out of bounds: " + index);
            index = 0;
        }

        return this.sprites.get(index);
    }

    public Sprite getSprite(int row, int col) {
        row = Math.max(1, row);
        col = Math.max(1, col);

        int rowOfSprite = ((row - 1) * this.cols);

        int index = rowOfSprite + col - 1;

        // Check if the sprite is out of bounds
        // Then return the last sprite
        if (index >= this.sprites.size()) {
            DebugMessage.printWarning("Sprite index out of bounds: " + index);
            index = this.sprites.size() - 1;
        } else if (index < 0) {
            DebugMessage.printWarning("Sprite index out of bounds: " + index);
            index = 0;
        }



        return this.sprites.get(index);
    }



    public Texture getTexture() {
        return this.texture;
    }

    public int size() {
        return sprites.size();
    }

    public SpriteSheetUsage getUsage() {
        return config.usage;
    }

    @Override
    public Asset build() {
        this.texture = new Texture(this.filepath);
        return this;
    }

}
