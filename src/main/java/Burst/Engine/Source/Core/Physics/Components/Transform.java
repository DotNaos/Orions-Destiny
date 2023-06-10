package Burst.Engine.Source.Core.Physics.Components;

import Burst.Engine.Source.Core.Actor.Actor;
import Burst.Engine.Source.Core.Actor.ActorComponent;
import org.joml.Vector2f;

public class Transform extends ActorComponent {

  public Vector2f position = new Vector2f(0, 0);
  public Vector2f scale = new Vector2f(1.0f, 1.0f);

  public Vector2f size = new Vector2f(1f, 1f);
  private Vector2f scaledSize = new Vector2f(size).mul(scale);
  public float rotation = 0;
  public int zIndex = 0;

  public Transform() {
    super(null);
  }

  public Transform(Vector2f position) {
    super(null);
    this.setPosition(position);
  }

  public Transform(Vector2f position, Vector2f scale) {
    super(null);
    this.setPosition(position);
    this.setScale(scale);
  }

  public Transform(Vector2f position, Vector2f scale, float rotation) {
    super(null);
    this.setPosition(position);
    this.setScale(scale);
    this.setRotation(rotation);
  }

  public Transform(Actor actor) {
    super(actor);
  }

  public Transform(Actor actor, Vector2f position) {
    super(actor);
    this.setPosition(position);
  }

  public Transform(Actor actor, Vector2f position, Vector2f scale) {
    super(actor);
    this.setPosition(position);
    this.setScale(scale);
  }

  public Transform(Actor actor, Vector2f position, Vector2f scale, float rotation) {
    super(actor);
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

  public Transform copy() {
    Transform t = new Transform(this.actor);
    t.position = new Vector2f(this.position);
    t.scale = new Vector2f(this.scale);
    t.size = new Vector2f(this.size);
    t.rotation = this.rotation;
    t.zIndex = this.zIndex;

    return t;
  }

  public Transform copy(Transform from) {
    this.getPosition().set(from.getPosition());
    this.getScale().set(from.getScale());
    this.setRotation(from.getRotation());
    this.setZIndex(from.getZIndex());
    return this;
  }

  public boolean equals(Object o) {
    if (o == null) return false;
    if (!(o instanceof Transform t)) return false;

    return t.position.x == this.position.x && t.position.y == this.position.y
            && t.scale.x == this.scale.x && t.scale.y == this.scale.y
            && t.size.x == this.size.x && t.size.y == this.size.y
            && t.rotation == this.rotation
            && t.zIndex == this.zIndex;
  }

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
