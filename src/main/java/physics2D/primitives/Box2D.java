package physics2D.primitives;

import org.joml.Vector2f;
import physics2D.rigidbody.Rigidbody2D;

public class Box2D {
    private Vector2f size = new Vector2f();
    private Vector2f halfSize = new Vector2f();
    private Rigidbody2D rigidbody = null;

    public Box2D()
    {
        this.halfSize = new Vector2f(this.size).mul(0.5f);
    }

    public Box2D(Vector2f min, Vector2f max)
    {
        this.size = new Vector2f(max).sub(min);
        this.halfSize = new Vector2f(this.size).mul(0.5f);
    }

    public Vector2f getMin()
    {
        return new Vector2f(this.rigidbody.getPosition()).sub(this.halfSize);
    }

    public Vector2f getMax()
    {
        return new Vector2f(this.rigidbody.getPosition()).add(this.halfSize);
    }

    public Vector2f[] getVertices()
    {
        Vector2f min = this.getMin();
        Vector2f max = this.getMax();

        Vector2f[] vertices = {
            new Vector2f(min.x, min.y), new Vector2f(min.x, max.y),
            new Vector2f(max.x, min.y), new Vector2f(max.x, max.y)
        };



        if (rigidbody.getRotation() != 0.0f) {
            for (Vector2f vert : vertices)
            {
                // TODO: Implement
                // Rotates point(Vector2f) around center(Vector2f) by rotation(float in degrees))
//                BMath.rotate(vert, this.rigidbody.getPosition(), this.rigidbody.getRotation());
            }
        }

        return vertices;
    }

    public Rigidbody2D getRigidbody() {
        return this.rigidbody;
    }

}
