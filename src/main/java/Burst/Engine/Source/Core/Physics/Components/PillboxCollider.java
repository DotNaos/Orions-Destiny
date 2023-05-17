package Burst.Engine.Source.Core.Physics.Components;

import Burst.Engine.Source.Core.Actor.Actor;
import Burst.Engine.Source.Core.Component;
import Burst.Engine.Source.Core.UI.Window;
import org.joml.Vector2f;

public class PillboxCollider extends Component {
    public float width = 0.1f;
    public float height = 0.2f;
    public Vector2f offset = new Vector2f();
    private transient CircleCollider bottomCircle;
    private transient Box2DCollider box;
    private transient boolean resetFixtureNextFrame = false;

    public PillboxCollider(Actor actor) {
        super(actor);
    }

    @Override
    public void start() {
        this.bottomCircle = new CircleCollider(this.actor);
        this.box = new Box2DCollider(this.actor);
        this.bottomCircle.actor = this.actor;
        this.box.actor = this.actor;
        recalculateColliders();
    }

    @Override
    public void updateEditor(float dt) {
        bottomCircle.updateEditor(dt);
        box.updateEditor(dt);
        recalculateColliders();

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
