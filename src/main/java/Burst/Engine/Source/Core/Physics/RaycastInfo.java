package Burst.Engine.Source.Core.Physics;

import Burst.Engine.Source.Core.Actor.Actor;
import org.jbox2d.callbacks.RayCastCallback;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Fixture;
import org.joml.Vector3f;

/**
 * @author GamesWithGabe
 * The {@code RaycastInfo} class is used to store information about a raycast.
 * It is used to call the appropriate methods in the {@link ActorComponent} class.
 */
public class RaycastInfo implements RayCastCallback {
  public Fixture fixture;
  public Vector3f point;
  public Vector3f normal;
  public float fraction;
  public boolean hit;
  public Actor hitObject;
  private Actor requestinactorbject;

  public RaycastInfo(Actor obj) {
    fixture = null;
    point = new Vector3f();
    normal = new Vector3f();
    fraction = 0.0f;
    hit = false;
    hitObject = null;
    this.requestinactorbject = obj;
  }

  @Override
  public float reportFixture(Fixture fixture, Vec2 point, Vec2 normal, float fraction) {
    if (fixture.m_userData == requestinactorbject) {
      return 1;
    }
    this.fixture = fixture;
    this.point = new Vector3f(point.x, point.y, 0);
    this.normal = new Vector3f(normal.x, normal.y, 0);
    this.fraction = fraction;
    this.hit = fraction != 0;
    this.hitObject = (Actor) fixture.m_userData;

    return fraction;
  }
}
