package Burst.Engine.Source.Core.Graphics.Debug;

import Burst.Engine.Config.ShaderConfig;
import Burst.Engine.Config.Constants.Color_Config;
import Burst.Engine.Source.Core.Assets.AssetManager;
import Burst.Engine.Source.Core.Assets.Graphics.Shader;
import Burst.Engine.Source.Core.UI.Viewport;
import Burst.Engine.Source.Core.UI.Window;
import Burst.Engine.Source.Core.Util.BMath;
import org.joml.Vector3f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class DebugDraw {
    private static final int MAX_LINES = 3000;

    private static List<Line2D> lines = new ArrayList<>();

    private static final int vertexSize = 7;
    // 7 floats per vertex, 2 vertices per line
    private static float[] vertexArray = new float[MAX_LINES * vertexSize * 2];
    private static Shader shader = (Shader) AssetManager.getAssetFromType(ShaderConfig.SHADER_DEBUG, Shader.class);

    private static int vaoID;
    private static int vboID;

    private static boolean started = false;

    public static float lineWidth = 2f;

    public static void start() {
        // Generate the vao
        vaoID = glGenVertexArrays();
        glBindVertexArray(vaoID);

        // Create the vbo and buffer some memory
        vboID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glBufferData(GL_ARRAY_BUFFER, (long) vertexArray.length * Float.BYTES, GL_DYNAMIC_DRAW);

        // Enable the vertex array attributes
        glVertexAttribPointer(0, 3, GL_FLOAT, false, vertexSize * Float.BYTES, 0);
        glEnableVertexAttribArray(0);

        glVertexAttribPointer(1, 3, GL_FLOAT, false, vertexSize * Float.BYTES, 3 * Float.BYTES);
        glEnableVertexAttribArray(1);

        glLineWidth(lineWidth);
    }

    public static void beginFrame() {
        if (!started) {
            start();
            started = true;
        }

        // Remove dead lines
        for (int i = 0; i < lines.size(); i++) {
            if (lines.get(i).beginFrame() < 0) {
                lines.remove(i);
                i--;
            }
        }
    }


    public static void draw() {
        if (lines.size() <= 0) return;

        int index = 0;
        for (Line2D line : lines) {
            for (int i = 0; i < 2; i++) {
                Vector3f position = i == 0 ? line.getFrom() : line.getTo();
                Vector4f color = new Vector4f(line.getColor().x, line.getColor().y, line.getColor().z, line.getColor().w);

                // Load position
                vertexArray[index] = position.x;
                vertexArray[index + 1] = position.y;
                vertexArray[index + 2] = position.z -10.0f;

                // Load the color
                vertexArray[index + 3] = color.x;
                vertexArray[index + 4] = color.y;
                vertexArray[index + 5] = color.z;
                vertexArray[index + 6] = color.w;
                index += vertexSize;
            }
        }

        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glBufferData(GL_ARRAY_BUFFER, vertexArray, GL_DYNAMIC_DRAW);

        // Use our shader
        shader.use();
        shader.uploadMat4f("uProjection", Window.getScene().getViewport().getProjectionMatrix());
        shader.uploadMat4f("uView", Window.getScene().getViewport().getViewMatrix());

        // Bind the vao
        glBindVertexArray(vaoID);
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        // Draw the batch
        glDrawArrays(GL_LINES, 0, lines.size());

        // Disable Location
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glBindVertexArray(0);

        // Unbind shader
        shader.detach();
    }

    // ==================================================
    // Add line2D methods
    // ==================================================
    public static void addLine2D(Vector3f from, Vector3f to) {
        addLine2D(from, to, new Vector4f(Color_Config.GREEN));
    }

    public static void addLine2D(Vector3f from, Vector3f to, Vector4f color) {
        addLine2D(from, to, color, 2);
    }

    public static void addLine2D(Vector3f from, Vector3f to, Vector3f color, int lifetime) {
        addLine2D(from, to, new Vector4f(color, 1.0f), lifetime);
    }

    public static void addLine2D(Vector3f from, Vector3f to, Vector4f color, int lifetime) {
        Viewport viewport = Window.getScene().getViewport();

        // TODO: Fix Camera viewport size, not used currently

//        Vector3f cameraLeft = viewport.getPosition().sub(new Vector3f(viewport.getSize().x / 4, viewport.getSize().y / 4, 0));
//        Vector3f cameraRight = viewport.getPosition().add(new Vector3f(viewport.getSize().x / 4, viewport.getSize().y / 4, 0));
//
//
        boolean lineInView = true;
//                ((from.x >= cameraLeft.x && from.x <= cameraRight.x) && (from.y >= cameraLeft.y && from.y <= cameraRight.y)) ||
//                        ((to.x >= cameraLeft.x && to.x <= cameraRight.x) && (to.y >= cameraLeft.y && to.y <= cameraRight.y));
         if (lines.size() >= MAX_LINES || !lineInView) {
             return;
         }

        DebugDraw.lines.add(new Line2D(new Vector3f(from), new Vector3f(to), new Vector4f(color), lifetime));
    }

    // ==================================================
    // Add Box2D methods
    // ==================================================
    public static void addBox2D(Vector3f center, Vector3f dimensions, float rotation) {
        addBox2D(center, dimensions, rotation, Color_Config.GREEN, 2);
    }

    public static void addBox2D(Vector3f center, Vector3f dimensions, float rotation, Vector3f color) {
        addBox2D(center, dimensions, rotation, color, 2);
    }

    public static void addBox2D(Vector3f center, Vector3f dimensions, float rotation, Vector4f color) {
        addBox2D(center, dimensions, rotation, color, 2);
    }

    public static void addBox2D(Vector3f center, Vector3f dimensions, float rotation,
                                Vector3f color, int lifetime) {
        addBox2D(center, dimensions, rotation, new Vector4f(color.x, color.y, color.z, 1), 2);
    }

    public static void addBox2D(Vector3f center, Vector3f dimensions, float rotation,
                                Vector4f color, int lifetime) {
        Vector3f min = new Vector3f(center).sub(new Vector3f(dimensions).mul(0.5f));
        Vector3f max = new Vector3f(center).add(new Vector3f(dimensions).mul(0.5f));

        Vector3f[] vertices = {
                new Vector3f(min.x, min.y, 0), new Vector3f(min.x, max.y,0),
                new Vector3f(max.x, max.y, 0), new Vector3f(max.x, min.y, 0)
        };

        if (rotation != 0.0f) {
            for (Vector3f vert : vertices) {
                BMath.rotate(vert, rotation, center);
            }
        }

        addLine2D(vertices[0], vertices[1], color, lifetime);
        addLine2D(vertices[0], vertices[3], color, lifetime);
        addLine2D(vertices[1], vertices[2], color, lifetime);
        addLine2D(vertices[2], vertices[3], color, lifetime);
    }

    // ==================================================
    // Add Circle methods
    // ==================================================
    public static void addCircle(Vector3f center, float radius) {

        addCircle(center, radius, Color_Config.GREEN, 2);
    }

    public static void addCircle(Vector3f center, float radius, Vector3f color) {
        addCircle(center, radius, new Vector4f(color, 1.0f), 2);
    }

    public static void addCircle(Vector3f center, float radius, Vector4f color, int lifetime) {
        Vector3f[] points = new Vector3f[20];
        int increment = 360 / points.length;
        int currentAngle = 0;

        for (int i = 0; i < points.length; i++) {
            Vector3f tmp = new Vector3f(0, radius,0);
            BMath.rotate(tmp, currentAngle, new Vector3f());
            points[i] = new Vector3f(tmp).add(center);

            if (i > 0) {
                addLine2D(points[i - 1], points[i], color, lifetime);
            }
            currentAngle += increment;
        }

        addLine2D(points[points.length - 1], points[0], color, lifetime);
    }
}


