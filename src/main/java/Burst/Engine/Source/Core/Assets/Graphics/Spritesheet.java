package Burst.Engine.Source.Core.Assets.Graphics;

import Burst.Engine.Source.Core.Assets.Asset;
import Burst.Engine.Source.Core.Util.DebugMessage;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class Spritesheet extends Asset {

    private static int MAX_SPRITES = -1;
    private Texture texture;
    private List<Sprite> sprites;
    private SpriteSheetUsage usage = SpriteSheetUsage.NONE;
    private Class<?> usedBy = null;

    private int spriteCount = 0;
    private int[] spritesPerRow;
    private boolean emptySpritesDefined = false;
    private int spriteWidth, spriteHeight, spacing, rows, cols;

    public Spritesheet(String filepath) {
        super(filepath);
    }

    public void configure(SpriteSheetUsage usage, int spriteWidth, int spriteHeight, int spacing) {
        configure(usage, spriteWidth, spriteHeight, spacing, new int[rows]);
        emptySpritesDefined = false;
    }

    public void configure(SpriteSheetUsage usage, int spriteWidth, int spriteHeight, int spacing, int[] spritesPerRow) {
        this.sprites = new ArrayList<>();
        this.spriteWidth = spriteWidth;
        this.spriteHeight = spriteHeight;
        this.spacing = spacing;
        this.rows = texture.getWidth() / (spriteWidth + spacing);
        this.cols = texture.getHeight() / (spriteHeight + spacing);
        this.spritesPerRow = spritesPerRow;
        this.emptySpritesDefined = true;
        this.usage = usage;
        createSprites(rows, cols);
    }




    private void createSprites(int rows, int cols) {

        int currentX = 0;
        int currentY = texture.getHeight() - spriteHeight;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (this.spriteCount >= MAX_SPRITES && MAX_SPRITES != -1) {
                    DebugMessage.printWarning("Spritesheet has reached maximum number of sprites: " + MAX_SPRITES);
                    return;
                }

                float topY = (currentY + spriteHeight) / (float) texture.getHeight();
                float rightX = (currentX + spriteWidth) / (float) texture.getWidth();
                float leftX = currentX / (float) texture.getWidth();
                float bottomY = currentY / (float) texture.getHeight();

                Vector3f[] texCoords = {
                        new Vector3f(rightX, topY, 0),
                        new Vector3f(rightX, bottomY, 0),
                        new Vector3f(leftX, bottomY, 0),
                        new Vector3f(leftX, topY, 0)
                };
                Sprite sprite = new Sprite(this.texture, texCoords, this.spriteWidth, this.spriteHeight);

                if (!emptySpritesDefined)
                {
                    this.sprites.add(sprite);
                }
                else if (!(col >= this.spritesPerRow[row] && this.spritesPerRow[row] != -1)) {
                    this.sprites.add(sprite);
                }

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

    public Sprite getSprite(int row, int col) {
        int rowOfSprite = ((row - 1) * this.cols);
        return this.sprites.get(rowOfSprite + col - 1);
    }



    public Texture getSheetTexture() {
        return this.texture;
    }

    public Class<?> getUsedBy() {
        return usedBy;
    }

    public int size() {
        return sprites.size();
    }

    public SpriteSheetUsage getUsage() {
        return usage;
    }

    @Override
    public Asset build() {
        this.texture = new Texture(this.filepath);
        return this;
    }

}
