package Burst.Engine.Source.Core.EventSystem.Events;

public class Event {
    public EventType type;

    public Event(EventType type) {
        this.type = type;
    }

    public Event() {
        this.type = EventType.UserEvent;
    }
}
