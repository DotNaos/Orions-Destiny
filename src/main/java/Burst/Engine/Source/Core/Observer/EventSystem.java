package Burst.Engine.Source.Core.Observer;



import Burst.Engine.Source.Core.Actor;
import Burst.Engine.Source.Core.Observer.Events.Event;

import java.util.ArrayList;
import java.util.List;

public class EventSystem {
    private static List<Observer> observers = new ArrayList<>();

    public static void addObserver(Observer observer) {
        observers.add(observer);
    }

    public static void notify(Actor obj, Event event) {
        for (Observer observer : observers) {
            observer.onNotify(obj, event);
        }
    }
}
