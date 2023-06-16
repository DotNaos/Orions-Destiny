package Burst.Engine.Source.Core.Render.Debug;

import Burst.Engine.Config.Constants.Color_Config;
import Burst.Engine.Config.Shader_Config;
import Burst.Engine.Source.Core.Assets.AssetManager;
import Burst.Engine.Source.Core.Assets.Graphics.Shader;
import Burst.Engine.Source.Core.UI.Viewport;
import Burst.Engine.Source.Core.UI.Window;
import Burst.Engine.Source.Core.Util.BMath;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

/**
 * @author GamesWithGabe
 * The DebugDraw class provides methods for drawing debug shapes and lines in a 2D environment.
 * It uses OpenGL for rendering and supports various types of shapes including lines, boxes, and circles.
 * The class utilizes a vertex array object (VAO) and vertex buffer object (VBO) for efficient rendering.
 */
public class DebugDraw {
  private static List<DebugDraw> layers = new ArrayList<>();

  /**
   * The maximum number of lines that can be drawn.
   */
  private static final int MAX_LINES = 3000;

  private List<Line2D> lines = new ArrayList<>();

  private static final int vertexSize = 7;
  // 7 floats per vertex, 2 vertices per line
  private float[] vertexArray = new float[(MAX_LINES) * vertexSize * 2];
  private Shader shader = (Shader) AssetManager.getAssetFromType(Shader_Config.SHADER_DEBUG, Shader.class);

  private int vaoID;
  private int vboID;

  private boolean started = false;

  public static float lineWidth = 2f;
  private int zIndex = 0;

  private DebugDraw(int zIndex) {
    this.zIndex = zIndex;
  }

  public static DebugDraw getLayer(int zIndex) {
    if (layers == null) {
      layers = new ArrayList<>();
    }

    for (DebugDraw instance : layers) {
      if (instance.zIndex == zIndex) {
        return instance;
      }
    }
    return null;
  }

  private static DebugDraw get() {
    return getLayer(0);
  }

  public static void addLayer(int zIndex) {
    if (layers == null) {
      layers = new ArrayList<>();
    }

    // Sort the instances by their z index
    layers.add(new DebugDraw(zIndex));
    layers.sort((o1, o2) -> {
      if (o1.zIndex > o2.zIndex) {
        return 1;
      } else if (o1.zIndex < o2.zIndex) {
        return -1;
      } else {
        return 0;
      }
    });
  }

  private void start() {
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
    for (DebugDraw instance : layers) {
      if (!instance.started) {
        instance.start();
        instance.started = true;
      }
      instance.removeDeadLines();
    }
  }

  private void removeDeadLines() {
    // Remove dead lines
    for (int i = 0; i < lines.size(); i++) {
      if (lines.get(i).beginFrame() < 0) {
        lines.remove(i);
        i--;
      }
    }
  }

  public static void draw() {
    for (DebugDraw instance : layers) {
      instance.drawLines();
    }
  }

  private void drawLines() {
    if(lines.size() == 0) return;

    int index = 0;
      for (Line2D line : lines) {
        for (int i = 0; i < 2; i++) {
          Vector2f position = i == 0 ? line.getFrom() : line.getTo();
          Vector4f color = new Vector4f(line.getColor().x, line.getColor().y, line.getColor().z, line.getColor().w);

          // Load position
          vertexArray[index] = position.x;
          vertexArray[index + 1] = -position.y;
          vertexArray[index + 2] = 10.0f;

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


  //!==================================================
  //! Add line2D methods
  //!==================================================
  public static void addLine(Vector2f from, Vector2f to) {
    addLine(from, to, new Vector4f(Color_Config.GREEN));
  }

  public static void addLine(Vector2f from, Vector2f to, Vector4f color) {
    addLine(from, to, color, 2);
  }
  public static void addGridLine(Vector2f from, Vector2f to, Vector4f color)
  {
    if (getLayer(-1) == null) {
      System.out.println("GridLines layer not added");
      return;
    }

    Viewport viewport = Window.getScene().getViewport();

    boolean lineInView = true;

    if (getLayer(-1).lines.size() >= MAX_LINES || !lineInView) {
      return;
    }

    getLayer(-1).lines.add(new Line2D(new Vector2f(from), new Vector2f(to), new Vector4f(color), 2));
  }


  public static void addLine(Vector2f from, Vector2f to, Vector3f color, int lifetime) {
    get().addLine(from, to, new Vector4f(color, 1.0f), lifetime);
  }

  public static void addLine(Vector2f from, Vector2f to, Vector4f color, int lifetime) {
      Viewport viewport = Window.getScene().getViewport();

      boolean lineInView = true;

      if (get().lines.size() >= MAX_LINES || !lineInView) {
        return;
      }

      get().lines.add(new Line2D(new Vector2f(from), new Vector2f(to), new Vector4f(color), lifetime));
  }

  //!==================================================
  // Add Box2D methods
  //!==================================================
  public static void addBox(Vector2f center, Vector2f dimensions, float rotation) {
    addBox(center, dimensions, rotation, Color_Config.GREEN, 2);
  }

  public static void addBox(Vector2f center, Vector2f dimensions, float rotation, Vector3f color) {
    addBox(center, dimensions, rotation, color, 2);
  }

  public static void addBox(Vector2f center, Vector2f dimensions, float rotation, Vector4f color) {
    addBox(center, dimensions, rotation, color, 2);
  }

  public static void addBox(Vector2f center, Vector2f dimensions, float rotation, Vector3f color, int lifetime) {
    addBox(center, dimensions, rotation, new Vector4f(color.x, color.y, color.z, 1), 2);
  }

  public static void addBox(Vector2f center, Vector2f dimensions, float rotation, Vector4f color, int lifetime) {
    Vector2f min = new Vector2f(center).sub(new Vector2f(dimensions).mul(0.5f));
    Vector2f max = new Vector2f(center).add(new Vector2f(dimensions).mul(0.5f));

    Vector2f[] vertices = {new Vector2f(min.x, min.y), new Vector2f(min.x, max.y), new Vector2f(max.x, max.y), new Vector2f(max.x, min.y)};

    if (rotation != 0.0f) {
      for (Vector2f vert : vertices) {
        BMath.rotate(vert, rotation, center);
      }
    }

    get().addLine(vertices[0], vertices[1], color, lifetime);
    get().addLine(vertices[0], vertices[3], color, lifetime);
    get().addLine(vertices[1], vertices[2], color, lifetime);
    get().addLine(vertices[2], vertices[3], color, lifetime);
  }

  //!==================================================
  // Add Circle methods
  //!==================================================
  public static void addCircle(Vector2f center, float radius) {

    addCircle(center, radius, Color_Config.GREEN, 2);
  }

  public static void addCircle(Vector2f center, float radius, Vector3f color) {
    addCircle(center, radius, new Vector4f(color, 1.0f), 2);
  }

  public static void addCircle(Vector2f center, float radius, Vector4f color, int lifetime) {
    Vector2f[] points = new Vector2f[20];
    int increment = 360 / points.length;
    int currentAngle = 0;

    for (int i = 0; i < points.length; i++) {
      Vector2f tmp = new Vector2f(0, radius);
      BMath.rotate(tmp, currentAngle, new Vector2f());
      points[i] = new Vector2f(tmp).add(center);

      if (i > 0) {
        get().addLine(points[i - 1], points[i], color, lifetime);
      }
      currentAngle += increment;
    }

    get().addLine(points[points.length - 1], points[0], color, lifetime);
  }
}


