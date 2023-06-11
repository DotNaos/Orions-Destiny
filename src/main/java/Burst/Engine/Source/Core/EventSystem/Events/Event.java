package Burst.Engine.Source.Core.EventSystem.Events;

/**
 * @author GamesWithGabe
 * The {@code Event} class represents an event in the event system.
 * Events can be of various types and are used to communicate between different parts of the application.
 */
public class Event {
  public EventType type;

  public Event(EventType type) {
    this.type = type;
  }

  public Event() {
    this.type = EventType.UserEvent;
  }
}
