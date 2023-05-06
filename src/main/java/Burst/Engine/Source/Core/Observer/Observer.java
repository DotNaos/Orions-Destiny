package Burst.Engine.Source.Core.Observer;


import Burst.Engine.Source.Core.Actor;
import Burst.Engine.Source.Core.Observer.Events.Event;

public interface Observer {
    void onNotify(Actor object, Event event);
}
