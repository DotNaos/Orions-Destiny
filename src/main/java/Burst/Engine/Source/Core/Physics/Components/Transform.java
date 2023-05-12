package Burst.Engine.Source.Core.Physics.Components;

import Burst.Engine.Source.Core.Actor.Actor;
import Burst.Engine.Source.Core.UI.BImGui;
import org.joml.Vector2f;

public class Transform {

    public Vector2f position = new Vector2f();
    public Vector2f scale = new Vector2f(1.0f, 1.0f);
    public Vector2f size = new Vector2f(32.0f, 32.0f);
    public float rotation = 0.0f;
    public int zIndex = 0;
    private Actor actor = null;

    public Transform(Actor actor) {
        this.actor = actor;
    }

    public Transform(Vector2f position) {
        this.position = position;
    }

    public Transform(Vector2f position, Vector2f scale) {
        this.position = position;
        this.scale = scale;
    }
    public Transform(Vector2f position, Vector2f scale, float rotation) {
        this.position = position;
        this.scale = scale;
        this.rotation = rotation;
    }


    public Transform copy() {
        return new Transform(new Vector2f(this.position), new Vector2f(this.scale), this.rotation);
    }


    public void imgui() {
        actor.name = BImGui.inputText("Name: ", actor.name);
        BImGui.drawVec2Control("Position", this.position);
        BImGui.drawVec2Control("Size", this.size, 32.0f);
        BImGui.drawVec2Control("Scale", this.scale, 1.0f);
        this.rotation = BImGui.dragFloat("Rotation", this.rotation);
        this.zIndex = BImGui.dragInt("Z-Index", this.zIndex);
    }

    public void copy(Transform from) {
        this.position.set(from.position);
        this.scale.set(from.scale);
        this.rotation = from.rotation;
        this.zIndex = from.zIndex;
    }

    public boolean equals(Object o) {
        if (o == null) return false;
        if (!(o instanceof Transform t)) return false;

        return t.position.equals(this.position) && t.scale.equals(this.scale) && t.size.equals(this.size) &&
                t.rotation == this.rotation && t.zIndex == this.zIndex;
    }
}
