package Burst.Engine.Source.Core.Physics.Components;

import Burst.Engine.Source.Core.Actor.Actor;
import Burst.Engine.Source.Core.Actor.ActorComponent;
import Burst.Engine.Source.Core.UI.ImGui.BImGui;
import org.joml.Vector2f;

public class Transform extends ActorComponent {

  public Vector2f position = new Vector2f(0, 0);
  public Vector2f scale = new Vector2f(1.0f, 1.0f);
  public Vector2f size = new Vector2f(1f, 1f);
  public float rotation = 0.0f;
  public int zIndex = 0;
  private Actor actor = null;

  public Transform() {
    super(null);
  }

  public Transform(Vector2f position) {
    super(null);
    this.position = position;
  }

  public Transform(Vector2f position, Vector2f scale) {
    super(null);
    this.position = position;
    this.scale = scale;
  }

  public Transform(Vector2f position, Vector2f scale, float rotation) {
    super(null);
    this.position = position;
    this.scale = scale;
    this.rotation = rotation;
  }

  public Transform(Actor actor) {
    super(actor);
  }

  public Transform(Actor actor, Vector2f position) {
    super(actor);
    this.position = position;
  }

  public Transform(Actor actor, Vector2f position, Vector2f scale) {
    super(actor);
    this.position = position;
    this.scale = scale;
  }

  public Transform(Actor actor, Vector2f position, Vector2f scale, float rotation) {
    super(actor);
    this.position = position;
    this.scale = scale;
    this.rotation = rotation;
  }

  public Transform copy() {
    return new Transform(this.actor, new Vector2f(this.position), new Vector2f(this.scale), this.rotation);
  }

  public void imgui() {
    actor.setName(BImGui.inputText("Name: ", actor.getName()));
    BImGui.drawVec2Control("Position", this.position, new Vector2f(0));
    BImGui.drawVec2Control("Size", this.size, new Vector2f(1));
    BImGui.drawVec2Control("Scale", this.scale, new Vector2f(1));
    this.rotation = BImGui.dragFloat("Rotation", this.rotation, 0.1f);
    this.zIndex = BImGui.dragInt("Z-Index", this.zIndex, 1);
  }

  public Transform copy(Transform from) {
    this.position.set(from.position);
    this.scale.set(from.scale);
    this.rotation = from.rotation;
    this.zIndex = from.zIndex;
    return this;
  }

  public boolean equals(Object o) {
    if (o == null) return false;
    if (!(o instanceof Transform t)) return false;

    return t.position.equals(this.position) && t.scale.equals(this.scale) && t.size.equals(this.size) &&
            t.rotation == this.rotation && t.zIndex == this.zIndex;
  }

    public void set(Transform transform) {
          this.position.set(transform.position);
            this.scale.set(transform.scale);
            this.size.set(transform.size);
            this.rotation = transform.rotation;
            this.zIndex = transform.zIndex;
    }
}
