package Burst.Engine.Source.Core.EventSystem;


import Burst.Engine.Source.Core.Actor.Actor;
import Burst.Engine.Source.Core.EventSystem.Events.Event;

/**
 * @author GamesWithGabe
 * The Observer interface defines the contract for objects that can receive notifications from the EventSystem.
 */
public interface Observer {
  /**
   * The Observer interface defines the contract for objects that can receive notifications from the EventSystem.
   */
  void onNotify(Actor object, Event event);
}
