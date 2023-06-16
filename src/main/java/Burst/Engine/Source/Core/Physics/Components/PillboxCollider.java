package Burst.Engine.Source.Core.Physics.Components;

import Burst.Engine.Source.Core.Actor.Actor;
import Burst.Engine.Source.Core.Actor.ActorComponent;
import Burst.Engine.Source.Core.UI.Window;
import org.joml.Vector2f;

/**
 * @author GamesWithGabe
 * The {@code PillboxCollider} class represents a pillbox-shaped collider component that can be attached to an actor.
 * It extends the {@link ActorComponent} class.
 *
 * <p>
 * The pillbox collider is defined by its width, height, and offset. It consists of a circle collider at the bottom
 * and a box collider above it. The bottom circle collider is used to handle collisions with circular objects,
 * while the box collider is used to handle collisions with rectangular objects.
 *
 * <p>
 * The pillbox collider is initialized and updated automatically by the engine. Whenever the width or height of the collider
 * is changed, the colliders are recalculated to match the new dimensions.
 *
 * <p>
 * The pillbox collider provides methods to set the width and height, and to reset the collider's fixture.
 *
 * <p>
 * The pillbox collider also provides access to the bottom circle collider and the box collider through
 * the {@link #getBottomCircle()} and {@link #getBox()} methods, respectively.
 *
 */

public class PillboxCollider extends ActorComponent {
  public float width = 0.1f;
  public float height = 0.2f;
  public Vector2f offset = new Vector2f();
  private transient CircleCollider bottomCircle;
  private transient Box2DCollider box;
  private transient boolean resetFixtureNextFrame = false;

  public PillboxCollider() {
    super();
  }

  @Override
  public void init() {
    this.bottomCircle = new CircleCollider();
    this.box = new Box2DCollider();
    this.bottomCircle.actor = this.actor;
    this.box.actor = this.actor;
    recalculateColliders();
  }


  @Override
  public void updateEditor(float dt) {
    super.updateEditor(dt);
    bottomCircle.updateEditor(dt);
    box.updateEditor(dt);
    recalculateColliders();
  }

  @Override
  public void update(float dt) {
    super.update(dt);

    recalculateColliders();
  }

  public void setWidth(float newVal) {
    this.width = newVal;
    recalculateColliders();
    resetFixture();
  }

  public void setHeight(float newVal) {
    this.height = newVal;
    recalculateColliders();
    resetFixture();
  }

  public void resetFixture() {
    if (Window.getPhysics().isLocked()) {
      resetFixtureNextFrame = true;
      return;
    }
    resetFixtureNextFrame = false;

    if (actor != null) {
      Rigidbody2D rb = actor.getComponent(Rigidbody2D.class);
      if (rb != null) {
        Window.getPhysics().resetPillboxCollider(rb, this);
      }
    }
  }

  public void recalculateColliders() {
    float circleRadius = width / 2.0f;
    float boxHeight = height - circleRadius;
    bottomCircle.setRadius(circleRadius);
    bottomCircle.setOffset(new Vector2f(offset).sub(0, (height - circleRadius * 2.0f) / 2.0f));
    box.setHalfSize(new Vector2f(width - 0.01f, boxHeight));
    box.setOffset(new Vector2f(offset).add(0, (height - boxHeight) / 2.0f));
  }

  public CircleCollider getBottomCircle() {
    return bottomCircle;
  }

  public Box2DCollider getBox() {
    return box;
  }
}
