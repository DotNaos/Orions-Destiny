package physics2d.rigidbody;

import org.joml.Vector2f;
import physics2d.primitives.*;
import renderer.Line2D;
import util.BMath;


public class IntersectionDetector2D {
    // ========================================
    // Point vs. Primitive Tests
    // ========================================

    public static boolean pointOnLine(Vector2f point, Line2D line)
    {
        // Calculate the slope of the line
        float dy = line.getEnd().y - line.getStart().y;
        float dx = line.getEnd().x - line.getStart().x;
        if (dx == 0f)
        {
            // Vertical line
            return BMath.compare(point.x, line.getStart().x) && point.y <= Math.max(line.getStart().y, line.getEnd().y) && point.y >= Math.min(line.getStart().y, line.getEnd().y);
        }

        // Non-vertical line
        float m = dy / dx;
        System.out.println("m: " + m);

        // Calculate the y-intercept
        float b = line.getEnd().y - (m * line.getEnd().x);

        // Check if point is on line
        return point.y == (m * point.x) + b;
    }

    public static boolean pointInCircle(Vector2f point, Circle circle)
    {
        // Calculate the center of the circle
        Vector2f circleCenter = circle.getCenter();

        // Calculate the vector from the circle center to the point
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

    public static boolean lineAndAABB(Line2D line, AABB box)
    {
        if (pointInAABB(line.getStart(), box) || pointInAABB(line.getEnd(), box)) return true;

        Vector2f unitVector = new Vector2f(line.getEnd()).sub(line.getStart());
        unitVector.normalize();
        unitVector.x = (unitVector.x != 0) ? 1.0f / unitVector.x : 0f;
        unitVector.y = (unitVector.y != 0) ? 1.0f / unitVector.y : 0f;

        Vector2f min = box.getMin();
        min.sub(line.getStart()).mul(unitVector);
        Vector2f max = box.getMax();
        max.sub(line.getStart()).mul(unitVector);

        float tmin = Math.max(Math.min(min.x, max.x), Math.min(min.y, max.y));
        float tmax = Math.min(Math.max(min.x, max.x), Math.max(min.y, max.y));
        if (tmax < 0 || tmin > tmax) return false;

        float t = (tmin < 0f) ? tmax : tmin;
        return t > 0f && t * t < line.lengthSquared();
    }

    public static boolean lineAndBox2D(Line2D line, Box2D box)
    {
        float theta = -box.getRigidbody().getRotation();
        Vector2f center = box.getRigidbody().getPosition();
        Vector2f localStart = new Vector2f(line.getStart());
        Vector2f localEnd = new Vector2f(line.getEnd());
        BMath.rotate(localStart, theta, center);
        BMath.rotate(localEnd, theta, center);

        Line2D localLine = new Line2D(localStart, localEnd);
        AABB aabb = new AABB(box.getMin(), box.getMax());

        return lineAndAABB(localLine, aabb);
    }

    // Raycasts
    public static boolean raycast(Circle circle, Ray2D ray, RaycastResult result)
    {
        RaycastResult.reset(result);

        Vector2f originToCircle = new Vector2f(circle.getCenter()).sub(ray.getOrigin());
        float radiusSquared = circle.getRadius() * circle.getRadius();
        float originToCircleLengthSquared = originToCircle.lengthSquared();

        // Project the vector from the ray origin onto the direction of the ray
        float a = originToCircle.dot(ray.getDirection());
        float bSquared = originToCircleLengthSquared - (a * a);
        if (radiusSquared - bSquared < 0.0f) return false;

        float f = (float)Math.sqrt(radiusSquared - bSquared);
        float t = 0;

        if (originToCircleLengthSquared < radiusSquared)
        {
            // Ray starts inside the circle
            t = a + f;
        } else {
            t = a - f;
        }

        if (result != null)
        {
            Vector2f point = new Vector2f(ray.getOrigin()).add(new Vector2f(ray.getDirection()).mul(t));
            Vector2f normal = new Vector2f(point).sub(circle.getCenter());
            normal.normalize();

            result.init(point, normal, t, true);
        }

        return true;
    }



}
