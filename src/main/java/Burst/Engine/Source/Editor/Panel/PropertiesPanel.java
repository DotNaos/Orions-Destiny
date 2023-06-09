package Burst.Engine.Source.Editor.Panel;

import Burst.Engine.Source.Core.Actor.Actor;
import Burst.Engine.Source.Core.Render.PickingTexture;
import Burst.Engine.Source.Core.Render.SpriteRenderer;
import Burst.Engine.Source.Core.Physics.Components.Box2DCollider;
import Burst.Engine.Source.Core.Physics.Components.CircleCollider;
import Burst.Engine.Source.Core.Physics.Components.Rigidbody2D;
import Burst.Engine.Source.Core.UI.ImGui.ImGuiPanel;
import imgui.ImGui;
import org.joml.Vector4f;

import java.util.ArrayList;
import java.util.List;

public class PropertiesPanel extends ImGuiPanel {
    private List<Actor> activeActors;
    private Actor activeActor = null;
    private PickingTexture pickingTexture;

    public PropertiesPanel(PickingTexture pickingTexture) {
        super();
        this.activeActors = new ArrayList<>();
        this.pickingTexture = pickingTexture;
    }

    @Override
    public void imgui() {
        if (activeActors.size() == 1 && activeActors.get(0) != null) {
            activeActor = activeActors.get(0);
            ImGui.begin("Properties");

            if (ImGui.beginPopupContextWindow("ComponentAdder")) {
                if (ImGui.menuItem("Add Rigidbody")) {
                    if (activeActor.getComponent(Rigidbody2D.class) == null) {
                        activeActor.addComponent(new Rigidbody2D(activeActor));
                    }
                }

                if (ImGui.menuItem("Add Box Collider")) {
                    if (activeActor.getComponent(Box2DCollider.class) == null &&
                            activeActor.getComponent(CircleCollider.class) == null) {
                        activeActor.addComponent(new Box2DCollider(activeActor));
                    }
                }

                if (ImGui.menuItem("Add Circle Collider")) {
                    if (activeActor.getComponent(CircleCollider.class) == null &&
                            activeActor.getComponent(Box2DCollider.class) == null) {
                        activeActor.addComponent(new CircleCollider(activeActor));
                    }
                }

                ImGui.endPopup();
            }

            activeActor.imgui();

            // Update the position of the Panel
            this.position.x = ImGui.getWindowPosX();
            this.position.y = ImGui.getWindowPosY();

            ImGui.end();
        }
    }


    public Actor getActiveActor() {
        if (activeActor == null && activeActors.size() > 0) {
            activeActor = activeActors.get(0);
        }

        return activeActor;
    }

    public void setActiveActor(Actor actor) {
        clearSelected();
        addActiveActor(actor);
    }

    public List<Actor> getActiveActors() {
        return this.activeActors;
    }

    public void clearSelected() {
        for (Actor actor : this.activeActors) {
            actor.setDirty();
        }
        this.activeActors.clear();
        this.activeActor = null;
    }

    public void addActiveActor(Actor actor) {
        if (actor != null) {
            this.activeActors.add(actor);
            actor.setDirty();
            this.activeActor = actor;
        }
    }

     public boolean isActiveActor(Actor actor) {
        return this.activeActors.contains(actor);
    }

    public PickingTexture getPickingTexture() {
        return this.pickingTexture;
    }
}
