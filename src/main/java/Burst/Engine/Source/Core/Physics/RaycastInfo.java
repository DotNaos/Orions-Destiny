package Burst.Engine.Source.Core.Physics;

import Burst.Engine.Source.Core.Actor;
import org.jbox2d.callbacks.RayCastCallback;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Fixture;
import org.joml.Vector2f;

public class RaycastInfo implements RayCastCallback {
    public Fixture fixture;
    public Vector2f point;
    public Vector2f normal;
    public float fraction;
    public boolean hit;
    public Actor hitObject;
    private Actor requestingObject;

    public RaycastInfo(Actor obj) {
        fixture = null;
        point = new Vector2f();
        normal = new Vector2f();
        fraction = 0.0f;
        hit = false;
        hitObject = null;
        this.requestingObject = obj;
    }

    @Override
    public float reportFixture(Fixture fixture, Vec2 point, Vec2 normal, float fraction) {
        if (fixture.m_userData == requestingObject) {
            return 1;
        }
        this.fixture = fixture;
        this.point = new Vector2f(point.x, point.y);
        this.normal = new Vector2f(normal.x, normal.y);
        this.fraction = fraction;
        this.hit = fraction != 0;
        this.hitObject = (Actor)fixture.m_userData;

        return fraction;
    }
}
