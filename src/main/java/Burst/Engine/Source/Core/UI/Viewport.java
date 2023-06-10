package Burst.Engine.Source.Core.UI;

import java.lang.Math;
import java.lang.invoke.VarHandle;

import org.joml.*;
import org.joml.Vector3f;

import Burst.Engine.Source.Editor.Panel.ViewportPanel;

public class Viewport {

    /**
     * The position of the center of the viewport in world units.
     */
    public Vector2f position;
    private Vector2f size = new Vector2f();
    public Vector4f clearColor = new Vector4f(1f);
    private Matrix4f projectionMatrix, viewMatrix, inverseProjection, inverseView;

    private float zoom = 10.0f;

    public Viewport() {
        this.position = new Vector2f();
        this.size = new Vector2f();
        this.projectionMatrix = new Matrix4f();
        this.viewMatrix = new Matrix4f();
        this.inverseProjection = new Matrix4f();
        this.inverseView = new Matrix4f();
        adjustProjection();
    }

    public Viewport(Vector2f position) {
        this.position = position;
        this.projectionMatrix = new Matrix4f();
        this.viewMatrix = new Matrix4f();
        this.inverseProjection = new Matrix4f();
        this.inverseView = new Matrix4f();
        adjustProjection();
    }

    public void adjustProjection() {
        this.clearColor = new Vector4f(1.0f, 1.0f, 1.0f, 1.0f);
        ViewportPanel viewportPanel = Window.getScene().getPanel(ViewportPanel.class);
        float width = Window.getWidth();
        float height = Window.getHeight();
        if (viewportPanel != null) {
            width = viewportPanel.getSize().x;
            height = viewportPanel.getSize().y;
            this.size.x = width;
            this.size.y = height;
        }

        float left = -1 / 2.0f * zoom * (width / height);
        float right = 1 / 2.0f * zoom * (width / height);
        float bottom = -1 / 2.0f * zoom;
        float top = 1 / 2.0f * zoom;
        projectionMatrix.identity();
        projectionMatrix.ortho(left, right, bottom, top, 0.0f, 100.0f);
        inverseProjection = new Matrix4f(projectionMatrix).invert();
    }


    public Matrix4f getViewMatrix()
    {
        Vector3f cameraFront = new Vector3f(0.0f, 0.0f, -1.0f);
        Vector3f cameraUp = new Vector3f(0.0f, 1.0f, 0.0f);

        viewMatrix.identity();
        viewMatrix.lookAt(new Vector3f(position.x, position.y, 20.0f), cameraFront.add(position.x, position.y, 0.0f),
                cameraUp);
        inverseView = new Matrix4f(this.viewMatrix).invert();

        return this.viewMatrix;
    }

    public Matrix4f getProjectionMatrix() {
        return this.projectionMatrix;
    }

    public Matrix4f getInverseProjection() {
        return this.inverseProjection;
    }

    public Matrix4f getInverseView() {
        return this.inverseView;
    }

    public Vector2f getSize() {
        return new Vector2f(this.size);
    }

    public float getZoom() {
        return zoom;
    }

    public void setZoom(float zoom) {
        this.zoom = zoom;
    }

    public void addZoom(float value) {
        this.zoom += value;
        if (this.zoom < 1f) {
            this.zoom = 1f;
        }
        else 
        {
            this.zoom = (float) (Math.floor(this.zoom * 10) / 10);
        }
    }

    public Vector2f getPosition() {
        return new Vector2f(this.position);
    }

    public Vector2f getStartPosition() {
        return new Vector2f(this.position.x - this.size.x / 2, this.position.y - this.size.y / 2);
    }

    public Vector2f getEndPosition() {
        return new Vector2f(this.position.x + this.size.x / 2, this.position.y + this.size.y / 2);
    }

    public Vector2f getWorldSize()
    {
        return new Vector2f(this.zoom * 2 * (this.size.x / this.size.y), zoom * 2);
    }




}
