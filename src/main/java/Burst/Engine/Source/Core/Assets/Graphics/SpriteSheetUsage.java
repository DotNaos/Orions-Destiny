package Burst.Engine.Source.Core.Assets.Graphics;

/**
 * An enumeration of the different usage types of sprite sheets within the Burst Engine.
 * <p>
 * The sprite sheet usage types include actor, animation, UI, and debug.
 *
 * @author Oliver Schütz
 * @version 1.0
 * @since 2023-05-08
 */
public enum SpriteSheetUsage {
    NONE,

    /**
     * Represents a sprite sheet that is used for default actors in the game world.
     * These have no properties, they are simply displayed. 
     */
    ACTOR,

    /**
     * Represents a sprite sheet that is used for the player in the game world.
     */
    PLAYER,

    /**
     * Used for Building blocks in the game world. They have Physics properties.
     */
    BLOCK,

    /**
     * Represents a sprite sheet that is used for items in the game world.
     */
    ITEM,

    /**
     * Represents a sprite sheet that is used for animations / flipBook in the game world.
     */
    ANIMATION,

    /**
     * Represents a sprite sheet that is used for UI elements.
     */
    UI,

    /**
     * Represents a sprite sheet that is used for debug purposes.
     */
    DEBUG 
}
