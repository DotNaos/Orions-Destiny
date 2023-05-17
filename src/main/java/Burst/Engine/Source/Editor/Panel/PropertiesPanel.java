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
    private List<Vector4f> activeGameObjectsOgColor;
    private Actor activeActor = null;
    private PickingTexture pickingTexture;

    public PropertiesPanel(PickingTexture pickingTexture) {
        super();
        this.activeActors = new ArrayList<>();
        this.pickingTexture = pickingTexture;
        this.activeGameObjectsOgColor = new ArrayList<>();
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
            ImGui.end();
        }
    }

    public Actor getActiveGameObject() {
        return activeActors.size() == 1 ? this.activeActors.get(0) :
                null;
    }

    public void setActiveGameObject(Actor actor) {
        if (actor != null) {
            clearSelected();
            this.activeActors.add(actor);
        }
    }

    public List<Actor> getActiveGameObjects() {
        return this.activeActors;
    }

    public void clearSelected() {
        if (activeGameObjectsOgColor.size() > 0) {
            int i = 0;
            for (Actor actor : activeActors) {
                SpriteRenderer spr = actor.getComponent(SpriteRenderer.class);
                if (spr != null) {
                    spr.setColor(activeGameObjectsOgColor.get(i));
                }
                i++;
            }
        }
        this.activeActors.clear();
        this.activeGameObjectsOgColor.clear();
    }

    public void addActiveGameObject(Actor actor) {
        SpriteRenderer spr = actor.getComponent(SpriteRenderer.class);
        if (spr != null) {
            this.activeGameObjectsOgColor.add(new Vector4f(spr.getColor()));
            spr.setColor(new Vector4f(0.8f, 0.8f, 0.0f, 0.8f));
        } else {
            this.activeGameObjectsOgColor.add(new Vector4f());
        }
        this.activeActors.add(actor);
    }

    public PickingTexture getPickingTexture() {
        return this.pickingTexture;
    }
}
