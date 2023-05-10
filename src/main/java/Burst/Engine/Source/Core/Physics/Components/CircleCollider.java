package Burst.Engine.Source.Core.Physics.Components;

import Burst.Engine.Source.Core.Actor.Actor;
import Burst.Engine.Source.Core.Graphics.Debug.DebugDraw;
import Burst.Engine.Source.Core.Component;
import Burst.Engine.Source.Core.UI.Window;
import org.joml.Vector2f;

public class CircleCollider extends Component {
    private float radius = 1f;
    private transient boolean resetFixtureNextFrame = false;
    protected Vector2f offset = new Vector2f();

    public CircleCollider(Actor actor) {
        super(actor);
    }

    public float getRadius() {
        return radius;
    }

    public Vector2f getOffset() {
        return this.offset;
    }

    public void setOffset(Vector2f newOffset) { this.offset.set(newOffset); }

    public void setRadius(float radius) {
        resetFixtureNextFrame = true;
        this.radius = radius;
    }

    @Override
    public void updateEditor(float dt) {
        Vector2f center = new Vector2f(this.actor.transform.position).add(this.offset);
        DebugDraw.addCircle(center, this.radius);

        if (resetFixtureNextFrame) {
            resetFixture();
        }
    }

    @Override
    public void update(float dt) {
        if (resetFixtureNextFrame) {
            resetFixture();
        }
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
                Window.getPhysics().resetCircleCollider(rb, this);
            }
        }
    }
}
