package Burst.Engine.Source.Core.Physics;

import Burst.Engine.Source.Core.Actor.Actor;
import Burst.Engine.Source.Core.Physics.Components.*;
import Burst.Engine.Source.Core.UI.Window;
import Orion.blocks.Ground;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import org.joml.Vector2f;

/**
 * @author GamesWithGabe
 * The Physics2D class provides a 2D physics simulation environment using the JBox2D library.
 */
public class Physics2D {
  private Vec2 gravity = new Vec2(0, -10.0f);
  private World world = new World(gravity);

  private float time = 0.0f;
  private float timeStep = 1.0f / 60.0f;
  private int velocityIterations = 8;
  private int positionIterations = 3;

  public Physics2D() {
    world.setContactListener(new BurstContactListener());
  }

  public static boolean checkOnGround(Actor actor, float innerPlayerWidth, float height) {
    Vector2f raycastBegin = new Vector2f(actor.getTransform().getPosition());
    raycastBegin.sub(innerPlayerWidth / 2.0f, 0.0f);
    Vector2f raycastEnd = new Vector2f(raycastBegin).add(0.0f, height);

    RaycastInfo info = Window.getPhysics().raycast(actor, raycastBegin, raycastEnd);

    Vector2f raycast2Begin = new Vector2f(raycastBegin).add(innerPlayerWidth, 0.0f);
    Vector2f raycast2End = new Vector2f(raycastEnd).add(innerPlayerWidth, 0.0f);
    RaycastInfo info2 = Window.getPhysics().raycast(actor, raycast2Begin, raycast2End);

    return (info.hit && info.hitObject != null && info.hitObject.getComponent(Ground.class) != null) || (info2.hit && info2.hitObject != null && info2.hitObject.getComponent(Ground.class) != null);
  }

  public Vector2f getGravity() {
    return new Vector2f(world.getGravity().x, world.getGravity().y);
  }

  public void add(Actor actor) {
    Rigidbody2D rb = actor.getComponent(Rigidbody2D.class);
    if (rb != null && rb.getRawBody() == null) {
      Transform transform = actor.getTransform();

      BodyDef bodyDef = new BodyDef();
      bodyDef.angle = (float) Math.toRadians(transform.getRotation());
      bodyDef.position.set(transform.getPosition().x, transform.getPosition().y);
      bodyDef.angularDamping = rb.getAngularDamping();
      bodyDef.linearDamping = rb.getLinearDamping();
      bodyDef.fixedRotation = rb.isFixedRotation();
      bodyDef.bullet = rb.isContinuousCollision();
      bodyDef.gravityScale = rb.gravityScale;
      bodyDef.angularVelocity = rb.angularVelocity;
      bodyDef.userData = rb.actor;

      switch (rb.getBodyType()) {
        case Kinematic:
          bodyDef.type = BodyType.KINEMATIC;
          break;
        case Static:
          bodyDef.type = BodyType.STATIC;
          break;
        case Dynamic:
          bodyDef.type = BodyType.DYNAMIC;
          break;
      }

      Body body = this.world.createBody(bodyDef);
      body.m_mass = rb.getMass();
      rb.setRawBody(body);

      CircleCollider circleCollider;
      Box2DCollider boxCollider;
      PillboxCollider pillboxCollider;

      if ((circleCollider = actor.getComponent(CircleCollider.class)) != null) {
        addCircleCollider(rb, circleCollider);
      }

      if ((boxCollider = actor.getComponent(Box2DCollider.class)) != null) {
        addBox2DCollider(rb, boxCollider);
      }

      if ((pillboxCollider = actor.getComponent(PillboxCollider.class)) != null) {
        addPillboxCollider(rb, pillboxCollider);
      }
    }
  }

  public void destroyActor(Actor actor) {
    Rigidbody2D rb = actor.getComponent(Rigidbody2D.class);
    if (rb != null) {
      if (rb.getRawBody() != null) {
        world.destroyBody(rb.getRawBody());
        rb.setRawBody(null);
      }
    }
  }

  public void update(float dt) {
    time += dt;
    if (time >= 0.0f) {
      time -= timeStep;
      world.step(timeStep, velocityIterations, positionIterations);
    }
  }

  public void setIsSensor(Rigidbody2D rb) {
    Body body = rb.getRawBody();
    if (body == null) return;

    Fixture fixture = body.getFixtureList();
    while (fixture != null) {
      fixture.m_isSensor = true;
      fixture = fixture.m_next;
    }
  }

  public void setNotSensor(Rigidbody2D rb) {
    Body body = rb.getRawBody();
    if (body == null) return;

    Fixture fixture = body.getFixtureList();
    while (fixture != null) {
      fixture.m_isSensor = false;
      fixture = fixture.m_next;
    }
  }

  public void resetCircleCollider(Rigidbody2D rb, CircleCollider circleCollider) {
    Body body = rb.getRawBody();
    if (body == null) return;

    int size = fixtureListSize(body);
    for (int i = 0; i < size; i++) {
      body.destroyFixture(body.getFixtureList());
    }

    addCircleCollider(rb, circleCollider);
    body.resetMassData();
  }

  public void resetBox2DCollider(Rigidbody2D rb, Box2DCollider boxCollider) {
    Body body = rb.getRawBody();
    if (body == null) return;

    int size = fixtureListSize(body);
    for (int i = 0; i < size; i++) {
      body.destroyFixture(body.getFixtureList());
    }

    addBox2DCollider(rb, boxCollider);
    body.resetMassData();
  }

  public void resetPillboxCollider(Rigidbody2D rb, PillboxCollider pb) {
    Body body = rb.getRawBody();
    if (body == null) return;

    int size = fixtureListSize(body);
    for (int i = 0; i < size; i++) {
      body.destroyFixture(body.getFixtureList());
    }

    addPillboxCollider(rb, pb);
    body.resetMassData();
  }

  public void addPillboxCollider(Rigidbody2D rb, PillboxCollider pb) {
    Body body = rb.getRawBody();
    assert body != null : "Raw body must not be null";

    addBox2DCollider(rb, pb.getBox());
    addCircleCollider(rb, pb.getBottomCircle());
  }

  public void addBox2DCollider(Rigidbody2D rb, Box2DCollider boxCollider) {
    Body body = rb.getRawBody();
    assert body != null : "Raw body must not be null";

    PolygonShape shape = new PolygonShape();
    Vector2f halfSize = new Vector2f(boxCollider.getHalfSize()).mul(0.5f);
    Vector2f offset = boxCollider.getOffset();
    Vector2f origin = new Vector2f(boxCollider.getOrigin());
    shape.setAsBox(halfSize.x, halfSize.y, new Vec2(offset.x, offset.y), 0);

    FixtureDef fixtureDef = new FixtureDef();
    fixtureDef.shape = shape;
    fixtureDef.density = 1.0f;
    fixtureDef.friction = rb.getFriction();
    fixtureDef.userData = boxCollider.actor;
    fixtureDef.isSensor = rb.isSensor();
    body.createFixture(fixtureDef);
  }

  public void addCircleCollider(Rigidbody2D rb, CircleCollider circleCollider) {
    Body body = rb.getRawBody();
    assert body != null : "Raw body must not be null";

    CircleShape shape = new CircleShape();
    shape.setRadius(circleCollider.getRadius());
    shape.m_p.set(circleCollider.getOffset().x, circleCollider.getOffset().y);

    FixtureDef fixtureDef = new FixtureDef();
    fixtureDef.shape = shape;
    fixtureDef.density = 1.0f;
    fixtureDef.friction = rb.getFriction();
    fixtureDef.userData = circleCollider.actor;
    fixtureDef.isSensor = rb.isSensor();
    body.createFixture(fixtureDef);
  }

  public RaycastInfo raycast(Actor requestinactorbject, Vector2f point1, Vector2f point2) {
    RaycastInfo callback = new RaycastInfo(requestinactorbject);
    world.raycast(callback, new Vec2(point1.x, point1.y), new Vec2(point2.x, point2.y));
    return callback;
  }

  private int fixtureListSize(Body body) {
    int size = 0;
    Fixture fixture = body.getFixtureList();
    while (fixture != null) {
      size++;
      fixture = fixture.m_next;
    }
    return size;
  }

  public boolean isLocked() {
    return world.isLocked();
  }
}
