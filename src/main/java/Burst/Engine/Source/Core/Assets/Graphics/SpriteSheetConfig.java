package Burst.Engine.Source.Core.Assets.Graphics;
/**
 * @author Oliver Schuetz
 */
public class SpriteSheetConfig {
  public int[] spritesPerRow;
  public int spriteWidth, spriteHeight, spacing;
  public String filePath;
  public SpriteSheetUsage usage = SpriteSheetUsage.NONE;

  public SpriteSheetConfig(String filePath, SpriteSheetUsage usage, int spriteWidth, int spriteHeight, int spacing)
  {
    this(filePath, usage, spriteWidth, spriteHeight, spacing, null);
  }
  public SpriteSheetConfig(String filePath, SpriteSheetUsage usage, int spriteWidth, int spriteHeight, int spacing, int[] spritesPerRow)
  {
    this.filePath = filePath;
    this.usage = usage;
    this.spriteWidth = spriteWidth;
    this.spriteHeight = spriteHeight;
    this.spacing = spacing;
    this.spritesPerRow = spritesPerRow;
  }


}
