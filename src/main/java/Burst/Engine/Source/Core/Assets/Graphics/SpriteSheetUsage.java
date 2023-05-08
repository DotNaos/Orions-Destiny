package Burst.Engine.Source.Core.Assets.Graphics;

/**

 An enumeration of the different usage types of sprite sheets within the Burst Engine.

 The sprite sheet usage types include actor, animation, UI, and debug.

 @author Oliver Schütz

 @version 1.0

 @since 2023-05-08
 */
public enum SpriteSheetUsage {

    /**
     Represents a sprite sheet that is used for actors in the game world.
     */
    ACTOR,

    /**
     Represents a sprite sheet that is used for animations / flipBook in the game world.
     */
    ANIMATION,

    /**
     Represents a sprite sheet that is used for UI elements.
     */
    UI,

    /**
     Represents a sprite sheet that is used for debug purposes.
     */
    DEBUG
}
