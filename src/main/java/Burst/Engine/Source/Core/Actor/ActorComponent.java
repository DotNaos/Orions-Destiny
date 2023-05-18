package Burst.Engine.Source.Core.Actor;

import Burst.Engine.Source.Core.Component;
import org.jbox2d.dynamics.contacts.Contact;
import org.joml.Vector3f;

public class ActorComponent extends Component {
  public transient Actor actor = null;
  public ActorComponent(Actor actor) {
    super();
    this.actor = actor;
  }

  public void beginCollision(Actor collidinactorbject, Contact contact, Vector3f hitNormal) {

  }

  public void endCollision(Actor collidinactorbject, Contact contact, Vector3f hitNormal) {

  }

  public void preSolve(Actor collidinactorbject, Contact contact, Vector3f hitNormal) {

  }

  public void postSolve(Actor collidinactorbject, Contact contact, Vector3f hitNormal) {

  }
}