package Burst.Engine.Source.Core.EventSystem;


import Burst.Engine.Source.Core.Actor.Actor;
import Burst.Engine.Source.Core.EventSystem.Events.Event;

public interface Observer {
    void onNotify(Actor object, Event event);
}
