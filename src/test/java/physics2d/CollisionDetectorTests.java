package physics2d;


import org.joml.Vector2f;
import org.junit.Test;
import physics2d.rigidbody.IntersectionDetector2D;
import renderer.Line2D;




import static junit.framework.TestCase.assertTrue;

public class CollisionDetectorTests {
    private final float EPSILON = 0.000001f;

    @Test
    public void pointOnStartPosition_shouldReturnTrue()
    {
        Line2D line = new Line2D(new Vector2f(0, 0), new Vector2f(12, 4));
        Vector2f point = new Vector2f(0, 0);

        assertTrue(IntersectionDetector2D.pointOnLine(point, line));
    }

    @Test
    public void pointOnEndPosition_shouldReturnTrue()
    {
        Line2D line = new Line2D(new Vector2f(0, 0), new Vector2f(12, 4));
        Vector2f point = new Vector2f(12, 4);

        assertTrue(IntersectionDetector2D.pointOnLine(point, line));
    }

    @Test
    public void pointOnVerticalLine_shouldReturnTrue()
    {
        Line2D line = new Line2D(new Vector2f(0, 0), new Vector2f(0, 10));
        Vector2f point = new Vector2f(0, 5);

        assertTrue(IntersectionDetector2D.pointOnLine(point, line));
    }
}