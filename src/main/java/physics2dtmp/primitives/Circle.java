package physics2dtmp.primitives;

import org.joml.Vector2f;
import physics2dtmp.rigidbody.Rigidbody2D;

public class Circle extends Collider2D {


    public float radius = 1.0f;
    private Rigidbody2D rigidbody = null;


    public float getRadius() {
        return radius;
    }

    public Vector2f getCenter() {
        return rigidbody.getPosition();
    }


    public void setRadius(float radius) {
        this.radius = radius;
    }

    public void setRigidbody(Rigidbody2D rigidbody2D) {
        this.rigidbody = rigidbody2D;
    }
}
