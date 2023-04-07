package physics2d.primitives;

import org.joml.Vector2f;
import physics2d.rigidbody.Rigidbody2D;

public class Circle {


    public float radius = 1.0f;
    private Rigidbody2D body = null;


    public float getRadius() {
        return radius;
    }

    public Vector2f getCenter() {
        return body.getPosition();
    }

}
