package physics2D.rigidbody;

import org.joml.Vector2f;
import physics2D.primitives.AABB;
import physics2D.primitives.Box2D;
import physics2D.primitives.Circle;
import renderer.Line2D;
import util.BMath;


public class IntersectionDetector2D {
    // ========================================
    // Point vs. Primitive Tests
    // ========================================

    public static boolean pointOnLine(Vector2f point, Line2D line)
    {
        float dy = line.getEnd().y - line.getStart().y;
        float dx = line.getEnd().x - line.getStart().x;
        float m = dy / dx;

        float b = line.getEnd().y - (m * line.getEnd().x);

        // Check if point is on line
        return point.y == (m * point.x) + b;
    }

    public static boolean pointInCircle(Vector2f point, Circle circle)
    {
        Vector2f circleCenter = circle.getCenter();
        Vector2f centerToPoint = new Vector2f(point).sub(circleCenter);

        // Check if point is in circle
        return centerToPoint.lengthSquared() <= (circle.getRadius() * circle.getRadius());
    }

    public static boolean pointInAABB(Vector2f point, AABB box)
    {

        Vector2f min = box.getMin();
        Vector2f max = box.getMax();

        // Check if point is in Box
        return point.x <= max.x && min.x <= point.x &&
                point.y <= max.y && min.y <= point.y;
    }

    public static boolean pointInBox2D(Vector2f point, Box2D box)
    {
        // Translate the point into local space
        Vector2f pointLocalBox = new Vector2f(point);
        BMath.rotate(pointLocalBox,
                box.getRigidbody().getRotation(),
                box.getRigidbody().getPosition()
        );

        Vector2f min = box.getMin();
        Vector2f max = box.getMax();

        // Check if point is in Box
        return pointLocalBox.x <= max.x && min.x <= pointLocalBox.x &&
                pointLocalBox.y <= max.y && min.y <= pointLocalBox.y;
    }





    // ========================================
    // Line vs. Primitive Tests
    // ========================================

}
