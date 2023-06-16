package Burst.Engine.Source.Core.Physics.Components;

import Burst.Engine.Source.Core.Actor.Actor;
import Burst.Engine.Source.Core.Actor.ActorComponent;
import org.joml.Vector2f;

/**
 * @author Oliver Schuetz
 * The Transform class represents the transformation properties of an actor in a game world.
 * It includes position, scale, rotation, size, and rendering order (z-index) information.
 */
public class Transform extends ActorComponent {
  /**
   * The position of the actor in 2D space.
   */

  public Vector2f position = new Vector2f(0, 0);

  /**
   * The scale of the actor in 2D space.
   */
  public Vector2f scale = new Vector2f(1.0f, 1.0f);
  /**
   * The size of the actor in 2D space.
   */

  public Vector2f size = new Vector2f(1f, 1f);
  /**
   * The scaled size of the actor, calculated by multiplying the size and scale vectors.
   */
  private Vector2f scaledSize = new Vector2f(size).mul(scale);
  /**
   * The rotation angle of the actor in degrees.
   */
  public float rotation = 0;
  /**
   * The scaled size of the actor, calculated by multiplying the size and scale vectors.
   */
  public int zIndex = 0;


  public Transform() {
    super();
  }
  public Transform(Vector2f position) {
    super();
    this.setPosition(position);
  }

  public Transform(Vector2f position, Vector2f scale) {
    super();
    this.setPosition(position);
    this.setScale(scale);
  }

  public Transform(Vector2f position, Vector2f scale, float rotation) {
    super();
    this.setPosition(position);
    this.setScale(scale);
    this.setRotation(rotation);
  }

  @Override
  public void updateEditor(float dt) {
    super.updateEditor(dt);
    update(dt);
  }

  @Override
  public void update(float dt) {
    super.update(dt);
    scaledSize = new Vector2f(size).mul(scale);
  }

  /**
   * Creates a copy of the Transform object.
   *
   * @return A new Transform object with the same property values as the original.
   */

  public Transform copy() {
    Transform t = new Transform();
    t.position = new Vector2f(this.position);
    t.scale = new Vector2f(this.scale);
    t.size = new Vector2f(this.size);
    t.rotation = this.rotation;
    t.zIndex = this.zIndex;

    return t;
  }


  /**
   * Copies the property values from another Transform object to this Transform object.
   *
   * @param from The Transform object to copy from.
   * @return This Transform object with updated property values.
   */
  public Transform copy(Transform from) {
    this.getPosition().set(from.getPosition());
    this.getScale().set(from.getScale());
    this.setRotation(from.getRotation());
    this.setZIndex(from.getZIndex());
    return this;
  }

  /**
   * Creates a copy of the Transform object.
   *
   * @return A new Transform object with the same property values as the original.
   */

  public boolean equals(Object o) {
    if (o == null) return false;
    if (!(o instanceof Transform t)) return false;

    return t.position.x == this.position.x && t.position.y == this.position.y && t.scale.x == this.scale.x && t.scale.y == this.scale.y && t.size.x == this.size.x && t.size.y == this.size.y && t.rotation == this.rotation && t.zIndex == this.zIndex;
  }

  /**
   * Sets the property values of this Transform object to match the values of another Transform object.
   *
   * @param transform The Transform object to set the values from.
   */
  public void set(Transform transform) {
    this.setPosition(transform.getPosition());
    this.setScale(transform.getScale());
    this.setRotation(transform.getRotation());
    this.setZIndex(transform.getZIndex());
  }

  public Vector2f getScaledSize() {
    return new Vector2f(scaledSize);
  }

  public Vector2f getPosition() {
    return new Vector2f(position);
  }

  public Vector2f getScale() {
    return new Vector2f(scale);
  }

  public int getZIndex() {
    return zIndex;
  }

  public float getRotation() {
    return rotation;
  }

  public void setPosition(Vector2f position) {
    this.position = position;
  }

  public void setSize(Vector2f size) {
    this.size = size;
    this.scaledSize = new Vector2f(size).mul(scale);
  }

  public void setScale(Vector2f scale) {
    this.scale = scale;
    this.scaledSize = new Vector2f(size).mul(scale);
  }

  public void setRotation(float rotation) {
    this.rotation = rotation;
  }

  public void setZIndex(int zIndex) {
    this.zIndex = zIndex;
  }

  public void setSize(float v, float y) {
    this.size = new Vector2f(v, y);
  }
}
