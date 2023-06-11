package Burst.Engine.Source.Core.EventSystem;


import Burst.Engine.Source.Core.Actor.Actor;
import Burst.Engine.Source.Core.EventSystem.Events.Event;

import java.util.ArrayList;
import java.util.List;

/**
 * @author GamesWithGabe
 * The EventSystem class provides functionality for managing observers and notifying them of events.
 */
public class EventSystem {
  private static List<Observer> observers = new ArrayList<>();

  /**
   * Adds an observer to the event system.
   *
   * @param observer the observer to be added
   */
  public static void addObserver(Observer observer) {
    observers.add(observer);
  }

  /**
   * Notifies all registered observers about an event.
   *
   * @param obj   the actor object associated with the event
   * @param event the event to be notified
   */
  public static void notify(Actor obj, Event event) {
    for (Observer observer : observers) {
      observer.onNotify(obj, event);
    }
  }
}
