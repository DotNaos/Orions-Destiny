package Burst.Engine.Source.Core.Physics.Components;

import Burst.Engine.Source.Core.Actor.Actor;
import Burst.Engine.Source.Core.Component;
import Burst.Engine.Source.Core.Render.Debug.DebugDraw;
import org.joml.Vector3f;

public class Box2DCollider extends Component {
    private Vector3f halfSize = new Vector3f(1);
    private Vector3f origin = new Vector3f();
    private Vector3f offset = new Vector3f();

    public Box2DCollider(Actor actor) {
        super(actor);
    }

    public Vector3f getOffset() {
        return this.offset;
    }

    public void setOffset(Vector3f newOffset) {
        this.offset.set(newOffset);
    }

    public Vector3f getHalfSize() {
        return halfSize;
    }

    public void setHalfSize(Vector3f halfSize) {
        this.halfSize = halfSize;
    }

    public Vector3f getOrigin() {
        return this.origin;
    }

    @Override
    public void updateEditor(float dt) {
        Vector3f center = new Vector3f(this.actor.transform.position).add(this.offset);
        DebugDraw.addBox2D(center, this.halfSize, this.actor.transform.rotation);
    }
}
