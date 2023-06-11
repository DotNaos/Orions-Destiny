package Burst.Engine.Source.Core.Actor;

import Burst.Engine.Source.Core.Component;
import org.jbox2d.dynamics.contacts.Contact;
import org.joml.Vector2f;
/**

 The ActorComponent class represents a component that can be attached to an Actor.
 It provides methods for handling collisions and updating the component in an editor.
 */
public class ActorComponent extends Component {
  public transient Actor actor = null;

  public ActorComponent(Actor actor) {
    super();
    this.actor = actor;
  }

  public void beginCollision(Actor collidinactorbject, Contact contact, Vector2f hitNormal) {

  }

  public void endCollision(Actor collidinactorbject, Contact contact, Vector2f hitNormal) {

  }

  public void preSolve(Actor collidinactorbject, Contact contact, Vector2f hitNormal) {

  }

  public void postSolve(Actor collidinactorbject, Contact contact, Vector2f hitNormal) {

  }

  @Override
  public void updateEditor(float dt) {
    super.updateEditor(dt);
  }

  @Override
  public void imgui() {
    super.imgui();
  }

}
