package Burst.Engine.Source.Editor.Panel;

import Burst.Engine.Source.Core.Actor.Actor;
import Burst.Engine.Source.Core.Actor.ActorComponent;
import Burst.Engine.Source.Core.Component;
import Burst.Engine.Source.Core.Render.PickingTexture;
import Burst.Engine.Source.Core.Render.SpriteRenderer;
import Burst.Engine.Source.Core.Physics.Components.Box2DCollider;
import Burst.Engine.Source.Core.Physics.Components.CircleCollider;
import Burst.Engine.Source.Core.Physics.Components.Rigidbody2D;
import Burst.Engine.Source.Core.UI.ImGui.ImGuiPanel;
import imgui.ImGui;
import imgui.flag.ImGuiCol;
import imgui.flag.ImGuiStyleVar;
import imgui.flag.ImGuiTableColumnFlags;
import org.joml.Vector4f;

import java.util.ArrayList;
import java.util.List;

public class PropertiesPanel extends ImGuiPanel {
    private List<Actor> activeActors;
    private Actor activeActor = null;
    private PickingTexture pickingTexture;
    private Actor propertiesActor = new Actor(-1).setName("Selected Actors");

    public PropertiesPanel(PickingTexture pickingTexture) {
        super();
        this.activeActors = new ArrayList<>();
        this.pickingTexture = pickingTexture;
    }

    @Override
    public void imgui() {
        if (activeActors == null) return;
        if (activeActors.size() == 0) {
            propertiesActor = null;
            ImGui.begin("Properties");
            ImGui.text("No Actor Selected");
            ImGui.end();
            return;
        }

        ImGui.begin("Properties");
        if (activeActors.size() == 1) {
            activeActor = activeActors.get(0);
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

        }
        else if (activeActors.size() > 1) {
            // Show properties of a actor
            // This actor is only for displaying the properties of the selected actors
            if (propertiesActor == null) {
                propertiesActor = new Actor(-1).setName("Selected Actors");
            }

            if (ImGui.beginPopupContextWindow("ComponentAdder")) {
                if (ImGui.menuItem("Add Rigidbody")) {
                    propertiesActor.addComponent(new Rigidbody2D(propertiesActor));
                    for (Actor actor : activeActors) {
                        if (actor.getComponent(Rigidbody2D.class) == null) {
                            actor.addComponent(new Rigidbody2D(actor));
                        }
                    }
                }

                if (ImGui.menuItem("Add Box Collider")) {
                    propertiesActor.addComponent(new Box2DCollider(propertiesActor));
                    for (Actor actor : activeActors) {
                        if (actor.getComponent(Box2DCollider.class) == null &&
                                actor.getComponent(CircleCollider.class) == null) {
                            actor.addComponent(new Box2DCollider(actor));
                        }
                    }
                }

                if (ImGui.menuItem("Add Circle Collider")) {
                    propertiesActor.addComponent(new CircleCollider(propertiesActor));
                    for (Actor actor : activeActors) {
                        if (actor.getComponent(CircleCollider.class) == null &&
                                actor.getComponent(Box2DCollider.class) == null) {
                            actor.addComponent(new CircleCollider(actor));
                        }
                    }
                }

                ImGui.endPopup();
            }


            ImGui.text("\n");
            if (ImGui.beginTable("Fields to edit", 2)) {
                // The second column is max 2/3 of the size of the first column
                ImGui.tableSetupColumn("", ImGuiTableColumnFlags.WidthStretch, 0.5f);

                ImGui.pushStyleColor(ImGuiCol.Border, 1.0f, 1.0f, 1.0f, 0.2f);
                ImGui.pushStyleVar(ImGuiStyleVar.FrameBorderSize, 1.0f);
                ImGui.pushStyleVar(ImGuiStyleVar.ItemSpacing, 20f, 20f);

                ImGui.tableNextColumn();
                ImGui.text("Multiple Actors Selected");
                ImGui.tableNextColumn();
                for (Actor a : activeActors) {
                    ImGui.text(a.getName());
                }

                ImGui.tableNextRow();


                ImGui.popStyleColor();
                ImGui.popStyleVar(2);
                ImGui.endTable();
            }

            for (Component c : propertiesActor.getAllComponents()) {
                // If the Component's header is expanded, calls its imgui method
                if (ImGui.collapsingHeader(c.getClass().getSimpleName())) {
                    if (ImGui.beginTable("ActorComponents", 2)) {
                        // The second column is max 2/3 of the size of the first column
                        ImGui.tableSetupColumn("", ImGuiTableColumnFlags.WidthStretch, 0.5f);

                        ImGui.pushStyleColor(ImGuiCol.Border, 1.0f, 1.0f, 1.0f, 0.2f);
                        ImGui.pushStyleVar(ImGuiStyleVar.FrameBorderSize, 1.0f);
                        ImGui.pushStyleVar(ImGuiStyleVar.ItemSpacing, 20f, 20f);
                        c.imgui();
                        ImGui.popStyleColor();
                        ImGui.popStyleVar(2);
                        ImGui.endTable();
                    }
                }
            }








            // Update the position of the Panel
            this.position.x = ImGui.getWindowPosX();
            this.position.y = ImGui.getWindowPosY();
        }

        ImGui.end();
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
