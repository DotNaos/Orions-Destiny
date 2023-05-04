package Burst.Engine.Source.Core.Observer;


import Burst.Engine.Source.Core.GameObject;
import Burst.Engine.Source.Core.Observer.Events.Event;

public interface Observer {
    void onNotify(GameObject object, Event event);
}
