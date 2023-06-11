package Burst.Engine.Source.Core.Physics.Enums;

/**
 * @author GamesWithGabe
 * The {@code BodyType} enum represents the different types of physics bodies in the game engine.
 * A physics body can have one of the following types:
 * <ul>
 * <li>{@link BodyType#Static}: A static body is immovable and unaffected by physics forces.</li>
 * <li>{@link BodyType#Dynamic}: A dynamic body is affected by physics forces and can move freely.</li>
 * <li>{@link BodyType#Kinematic}: A kinematic body is controlled manually and can be moved programmatically.</li>
 * </ul>
 *
 * @since 1.0
 */
public enum BodyType {
  Static, Dynamic, Kinematic
}
