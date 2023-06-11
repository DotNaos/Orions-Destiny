package Burst.Engine.Source.Core.Physics.Components;

import Burst.Engine.Source.Core.Actor.Actor;
import Burst.Engine.Source.Core.Actor.ActorComponent;
import Burst.Engine.Source.Core.Physics.Enums.BodyType;
import Burst.Engine.Source.Core.UI.Window;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.joml.Vector2f;

/**
 * @author GamesWithGabe
 * Represents a 2D rigid body component attached to an actor.
 * It simulates physics behavior such as movement, collisions, and forces.
 */
public class Rigidbody2D extends ActorComponent {
  private Vector2f velocity = new Vector2f(); // The current velocity of the rigid body.
  private float angularDamping = 0.8f; // The angular damping applied to the rigid body.
  private float linearDamping = 0.9f; // The linear damping applied to the rigid body.
  private float mass = 0; // The mass of the rigid body.
  private BodyType bodyType = BodyType.Dynamic; // The type of the rigid body.
  private float friction = 0.1f; // The friction applied to the rigid body.
  public float angularVelocity = 0.0f; // The angular velocity of the rigid body.
  public float gravityScale = 1.0f; // The gravity scale applied to the rigid body.
  private boolean isSensor = false; // Indicates if the rigid body is a sensor.

  private boolean fixedRotation = false;
  private boolean continuousCollision = true;

  private transient Body rawBody = null;

  public Rigidbody2D(Actor actor) {
    super(actor);
  }

  @Override
  public void updateEditor(float dt) {
    super.updateEditor(dt);
  }

  @Override
  public void update(float dt) {
    super.update(dt);
    if (rawBody != null) {
      if (this.bodyType == BodyType.Dynamic || this.bodyType == BodyType.Kinematic) {
        this.actor.getTransform().getPosition().set(rawBody.getPosition().x, rawBody.getPosition().y);
        this.actor.getTransform().setRotation((float) Math.toDegrees(rawBody.getAngle()));
        Vec2 vel = rawBody.getLinearVelocity();
        this.velocity.set(vel.x, vel.y);
      } else if (this.bodyType == BodyType.Static) {
        this.rawBody.setTransform(new Vec2(this.actor.getTransform().getPosition().x, this.actor.getTransform().getPosition().y), this.actor.getTransform().getRotation());
      }
    }
  }

  public void addVelocity(Vector2f forceToAdd) {
    if (rawBody != null) {
      rawBody.applyForceToCenter(new Vec2(forceToAdd.x, forceToAdd.y));
    }
  }

  public void addImpulse(Vector2f impulse) {
    if (rawBody != null) {
      rawBody.applyLinearImpulse(new Vec2(velocity.x, velocity.y), rawBody.getWorldCenter(), true);
    }
  }

  public Vector2f getVelocity() {
    return velocity;
  }

  public void setVelocity(Vector2f velocity) {
    this.velocity.set(velocity);
    if (rawBody != null) {
      this.rawBody.setLinearVelocity(new Vec2(velocity.x, velocity.y));
    }
  }

  public void setPosition(Vector2f newPos) {
    if (rawBody != null) {
      rawBody.setTransform(new Vec2(newPos.x, newPos.y), actor.getTransform().getRotation());
    }
  }

  public void setAngularVelocity(float angularVelocity) {
    this.angularVelocity = angularVelocity;
    if (rawBody != null) {
      this.rawBody.setAngularVelocity(angularVelocity);
    }
  }

  public void setGravityScale(float gravityScale) {
    this.gravityScale = gravityScale;
    if (rawBody != null) {
      this.rawBody.setGravityScale(gravityScale);
    }
  }

  public void setIsSensor() {
    isSensor = true;
    if (rawBody != null) {
      Window.getPhysics().setIsSensor(this);
    }
  }

  public void setNotSensor() {
    isSensor = false;
    if (rawBody != null) {
      Window.getPhysics().setNotSensor(this);
    }
  }

  public float getFriction() {
    return this.friction;
  }

  public boolean isSensor() {
    return this.isSensor;
  }

  public float getAngularDamping() {
    return angularDamping;
  }

  public void setAngularDamping(float angularDamping) {
    this.angularDamping = angularDamping;
  }

  public float getLinearDamping() {
    return linearDamping;
  }

  public void setLinearDamping(float linearDamping) {
    this.linearDamping = linearDamping;
  }

  public float getMass() {
    return mass;
  }

  public void setMass(float mass) {
    this.mass = mass;
  }

  public BodyType getBodyType() {
    return bodyType;
  }

  public void setBodyType(BodyType bodyType) {
    this.bodyType = bodyType;
  }

  public boolean isFixedRotation() {
    return fixedRotation;
  }

  public void setFixedRotation(boolean fixedRotation) {
    this.fixedRotation = fixedRotation;
  }

  public boolean isContinuousCollision() {
    return continuousCollision;
  }

  public void setContinuousCollision(boolean continuousCollision) {
    this.continuousCollision = continuousCollision;
  }

  public Body getRawBody() {
    return rawBody;
  }

  public void setRawBody(Body rawBody) {
    this.rawBody = rawBody;
  }
}
