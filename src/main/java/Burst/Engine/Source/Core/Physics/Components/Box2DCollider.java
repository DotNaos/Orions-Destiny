package Burst.Engine.Source.Core.Physics.Components;

import Burst.Engine.Source.Core.Actor.Actor;
import Burst.Engine.Source.Core.Actor.ActorComponent;
import Burst.Engine.Source.Core.Render.Debug.DebugDraw;
import org.joml.Vector2f;

/**
 * @author GamesWithGabe
 * The {@code Box2DCollider} class represents a component that defines a 2D box collider for an {@link Actor}.
 * <p>
 * It is used for collision detection and response within the physics simulation.
 *
 * <p>
 * This collider is defined by its half size, origin, and offset.
 * <p>
 * The half size determines the dimensions of the box, represented by a {@link Vector2f}.
 * <p>
 * The origin specifies the local position of the collider relative to the actor's transform.
 * <p>
 * The offset represents an additional translation applied to the collider's position.
 *
 * </p>
 * <p>
 * The {@code Box2DCollider} class extends the {@link ActorComponent} class and provides methods to get and set
 * <p>
 * the offset, half size, and origin of the collider. It also overrides the {@link ActorComponent#updateEditor(float)}
 * <p>
 * method to update the collider's position for debugging purposes.
 *
 * </p>
 * <p>
 * Note that the collider's position is determined by the actor's transform, the offset, and the origin.
 *
 * </p>
 *
 * @see Actor
 * @see ActorComponent
 * @see Vector2f
 * @see DebugDraw
 */
public class Box2DCollider extends ActorComponent {
  private Vector2f halfSize = new Vector2f(1);
  private Vector2f origin = new Vector2f();
  private Vector2f offset = new Vector2f();

  public Box2DCollider() {
    super();
  }

  public Vector2f getOffset() {
    return this.offset;
  }

  public void setOffset(Vector2f newOffset) {
    this.offset.set(newOffset);
  }

  public Vector2f getHalfSize() {
    return halfSize;
  }

  public void setHalfSize(Vector2f halfSize) {
    this.halfSize = halfSize;
  }

  public Vector2f getOrigin() {
    return this.origin;
  }

  @Override
  public void updateEditor(float dt) {
    super.updateEditor(dt);
    Vector2f center = new Vector2f(this.actor.getTransform().getPosition()).add(this.offset);
    DebugDraw.addBox(center, this.halfSize, this.actor.getTransform().getRotation());
  }

  @Override
  public void update(float dt) {
    super.update(dt);
  }
}
