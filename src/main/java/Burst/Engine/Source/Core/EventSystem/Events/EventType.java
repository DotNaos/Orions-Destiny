package Burst.Engine.Source.Core.EventSystem.Events;

/**
 * @author GamesWithGabe
 * Represents the type of an event in the game engine.
 * This enum contains the following event types:
 * {@link EventType#GameEngineStartPlay} - Represents the event of starting gameplay in the game engine.
 * {@link EventType#GameEngineStopPlay} - Represents the event of stopping gameplay in the game engine.
 * {@link EventType#SaveLevel} - Represents the event of saving a level in the game engine.
 * {@link EventType#LoadLevel} - Represents the event of loading a level in the game engine.
 * {@link EventType#UserEvent} - Represents a user-defined event in the game engine.
 */
public enum EventType {
  GameEngineStartPlay, GameEngineStopPlay, SaveLevel, LoadLevel, UserEvent
}
