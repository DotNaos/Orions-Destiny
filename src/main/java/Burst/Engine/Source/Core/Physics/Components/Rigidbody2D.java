package Burst.Engine.Source.Core.Physics.Components;

import Burst.Engine.Source.Core.Actor.Actor;
import Burst.Engine.Source.Core.Actor.ActorComponent;
import Burst.Engine.Source.Core.Physics.Enums.BodyType;
import Burst.Engine.Source.Core.Component;
import Burst.Engine.Source.Core.UI.Window;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.joml.Vector3f;

public class Rigidbody2D extends ActorComponent {
    private Vector3f velocity = new Vector3f();
    private float angularDamping = 0.8f;
    private float linearDamping = 0.9f;
    private float mass = 0;
    private BodyType bodyType = BodyType.Dynamic;
    private float friction = 0.1f;
    public float angularVelocity = 0.0f;
    public float gravityScale = 1.0f;
    private boolean isSensor = false;

    private boolean fixedRotation = false;
    private boolean continuousCollision = true;

    private transient Body rawBody = null;

    public Rigidbody2D(Actor actor) {
        super(actor);
    }

    @Override
    public void update(float dt) {
        if (rawBody != null) {
            if (this.bodyType == BodyType.Dynamic || this.bodyType == BodyType.Kinematic) {
                this.actor.transform.position.set(
                        rawBody.getPosition().x, rawBody.getPosition().y, 0
                );
                this.actor.transform.rotation = (float) Math.toDegrees(rawBody.getAngle());
                Vec2 vel = rawBody.getLinearVelocity();
                this.velocity.set(vel.x, vel.y, 0);
            } else if (this.bodyType == BodyType.Static) {
                this.rawBody.setTransform(
                        new Vec2(this.actor.transform.position.x, this.actor.transform.position.y),
                        this.actor.transform.rotation
                );
            }
        }
    }

    public void addVelocity(Vector3f forceToAdd) {
        if (rawBody != null) {
            rawBody.applyForceToCenter(new Vec2(forceToAdd.x, forceToAdd.y));
        }
    }

    public void addImpulse(Vector3f impulse) {
        if (rawBody != null) {
            rawBody.applyLinearImpulse(new Vec2(velocity.x, velocity.y), rawBody.getWorldCenter(), true);
        }
    }

    public Vector3f getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector3f velocity) {
        this.velocity.set(velocity);
        if (rawBody != null) {
            this.rawBody.setLinearVelocity(new Vec2(velocity.x, velocity.y));
        }
    }

    public void setPosition(Vector3f newPos) {
        if (rawBody != null) {
            rawBody.setTransform(new Vec2(newPos.x, newPos.y), actor.transform.rotation);
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
