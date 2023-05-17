package Burst.Engine.Source.Core.Graphics.Debug;

import org.joml.Vector3f;
import org.joml.Vector4f;

public class Line2D {
    private Vector3f from;
    private Vector3f to;
    private Vector4f color;
    private int lifetime;

    public Line2D(Vector3f from, Vector3f to) {
        this.from = from;
        this.to = to;
    }

    public Line2D(Vector3f from, Vector3f to, Vector3f color, int lifetime) {
        this.from = from;
        this.to = to;
        this.color = new Vector4f(color, 1);
        this.lifetime = lifetime;
    }

    public Line2D(Vector3f from, Vector3f to, Vector4f color, int lifetime) {
        this.from = from;
        this.to = to;
        this.color = color;
        this.lifetime = lifetime;
    }


    public int beginFrame() {
        this.lifetime--;
        return this.lifetime;
    }

    public Vector3f getFrom() {
        return from;
    }

    public Vector3f getTo() {
        return to;
    }

    public Vector3f getStart() {
        return this.from;
    }

    public Vector3f getEnd() {
        return this.to;
    }

    public Vector4f getColor() {
        return color;
    }

    public float lengthSquared() {
        return new Vector3f(to).sub(from).lengthSquared();
    }
}
