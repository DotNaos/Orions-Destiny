package Burst.Engine.Source.Core.Physics.Components;

import Burst.Engine.Source.Core.Actor.Actor;
import Burst.Engine.Source.Core.UI.ImGui.BImGui;
import org.joml.Vector3f;

public class Transform {

    public Vector3f position = new Vector3f(0, 0, 0 );
    public Vector3f scale = new Vector3f(1.0f, 1.0f, 1.0f);
    public Vector3f size = new Vector3f(1f, 1f, 1f);
    public float rotation = 0.0f;
    public int zIndex = 0;
    private Actor actor = null;

    public Transform(Actor actor) {
        this.actor = actor;
    }

    public Transform(Vector3f position) {
        this.position = position;
    }

    public Transform(Vector3f position, Vector3f scale) {
        this.position = position;
        this.scale = scale;
    }

    public Transform(Vector3f position, Vector3f scale, float rotation) {
        this.position = position;
        this.scale = scale;
        this.rotation = rotation;
    }


    public Transform copy() {
        return new Transform(new Vector3f(this.position), new Vector3f(this.scale), this.rotation);
    }


    public void imgui() {
        actor.setName(BImGui.inputText("Name: ", actor.getName()));
        BImGui.drawVec3Control("Position", this.position);
        BImGui.drawVec3Control("Size", this.size, 32.0f);
        BImGui.drawVec3Control("Scale", this.scale, 1.0f);
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
