package physics2d.rigidbody;

import org.joml.Vector2f;
import physics2d.primitives.AABB;
import physics2d.primitives.Box2D;
import physics2d.primitives.Circle;
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
        if (dx == 0f)
        {
            return BMath.compare(point.x, line.getStart().x) && point.y <= Math.max(line.getStart().y, line.getEnd().y) && point.y >= Math.min(line.getStart().y, line.getEnd().y);
        }

        float m = dy / dx;
        System.out.println("m: " + m);

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

    public static boolean lineInCircle(Line2D line, Circle circle)
    {
        if (pointInCircle(line.getStart(), circle) || pointInCircle(line.getEnd(), circle)) return true;

        Vector2f ab = new Vector2f(line.getEnd()).sub(line.getStart());

        // Project point (circle center) onto line
        // parameterized position t
        Vector2f circleCenter = circle.getCenter();
        Vector2f centerToLineStart = new Vector2f(circleCenter).sub(line.getStart());
        float t = centerToLineStart.dot(ab) / ab.dot(ab);

        if (t < 0.0f || t > 1.0f) return false;

        // Find the closest point to the line segment
        Vector2f closestPoint = new Vector2f(line.getStart()).add(ab.mul(t));

        return pointInCircle(closestPoint, circle);

    }

}
