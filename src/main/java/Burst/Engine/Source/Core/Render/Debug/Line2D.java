package Burst.Engine.Source.Core.Render.Debug;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

/**
 * @author GamesWithGabe
 * The Line2D class represents a 2D line segment in a rendering context.
 * It defines the starting and ending points of the line, as well as the color
 * and lifetime of the line.
 */
public class Line2D {
  private Vector2f from;
  private Vector2f to;
  private Vector4f color;
  private int lifetime;

  public Line2D(Vector2f from, Vector2f to) {
    this.from = from;
    this.to = to;
  }

  public Line2D(Vector2f from, Vector2f to, Vector3f color, int lifetime) {
    this.from = from;
    this.to = to;
    this.color = new Vector4f(color, 1);
    this.lifetime = lifetime;
  }

  public Line2D(Vector2f from, Vector2f to, Vector4f color, int lifetime) {
    this.from = from;
    this.to = to;
    this.color = color;
    this.lifetime = lifetime;
  }


  public int beginFrame() {
    this.lifetime--;
    return this.lifetime;
  }

  public Vector2f getFrom() {
    return from;
  }

  public Vector2f getTo() {
    return to;
  }

  public Vector2f getStart() {
    return this.from;
  }

  public Vector2f getEnd() {
    return this.to;
  }

  public Vector4f getColor() {
    return color;
  }

  public float lengthSquared() {
    return new Vector2f(to).sub(from).lengthSquared();
  }
}
