package Burst.Engine.Source.Editor.Panel;

import Burst.Engine.Source.Core.Actor.Actor;
import Burst.Engine.Source.Core.Assets.AssetManager;
import Burst.Engine.Source.Core.Assets.Graphics.Sprite;
import Burst.Engine.Source.Core.Assets.Graphics.Texture;
import Burst.Engine.Source.Core.Physics.Components.Box2DCollider;
import Burst.Engine.Source.Core.Physics.Components.CircleCollider;
import Burst.Engine.Source.Core.Physics.Components.Rigidbody2D;
import Burst.Engine.Source.Core.Physics.Components.Transform;
import Burst.Engine.Source.Core.Render.PickingTexture;
import Burst.Engine.Source.Core.Render.SpriteRenderer;
import Burst.Engine.Source.Core.UI.ImGui.ImGuiPanel;
import Orion.res.AssetConfig;
import imgui.ImGui;
import imgui.type.ImInt;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.List;

public class PropertiesPanel extends ImGuiPanel {
  private List<Actor> activeActors;
  private Actor activeActor = null;
  private PickingTexture pickingTexture;
  private transient Actor propertiesActor = null;
  private transient Vector2f deltaPosition = new Vector2f(0, 0);
  private transient Vector2f deltaScale = new Vector2f(1, 1);
  private transient Vector2f deltaSize = new Vector2f(1, 1);
  private transient float deltaRotation = 0;
  private transient int deltaZIndex = 0;

  private transient ImInt selected = new ImInt(0);

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
      selected.set(0);
      ImGui.begin("Properties");
      ImGui.textColored(1.0f, 0.3f, 0.3f, 0.8f, "No Actor Selected");
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
          if (activeActor.getComponent(Box2DCollider.class) == null && activeActor.getComponent(CircleCollider.class) == null) {
            activeActor.addComponent(new Box2DCollider(activeActor));
          }
        }

        if (ImGui.menuItem("Add Circle Collider")) {
          if (activeActor.getComponent(CircleCollider.class) == null && activeActor.getComponent(Box2DCollider.class) == null) {
            activeActor.addComponent(new CircleCollider(activeActor));
          }
        }

        ImGui.endPopup();
      }

      activeActor.imgui();

      // Update the position of the Panel
      this.position.x = ImGui.getWindowPosX();
      this.position.y = ImGui.getWindowPosY();

    } else if (activeActors.size() > 1) {
      // Show properties of a actor
      // This actor is only for displaying the properties of the selected actors
      if (propertiesActor == null) {
        propertiesActor = new Actor(-1).setName("Selected Actors").addComponent(new Transform());
        propertiesActor.setSerializable();
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
            if (actor.getComponent(Box2DCollider.class) == null && actor.getComponent(CircleCollider.class) == null) {
              actor.addComponent(new Box2DCollider(actor));
            }
          }
        }

        if (ImGui.menuItem("Add Circle Collider")) {
          propertiesActor.addComponent(new CircleCollider(propertiesActor));
          for (Actor actor : activeActors) {
            if (actor.getComponent(CircleCollider.class) == null && actor.getComponent(Box2DCollider.class) == null) {
              actor.addComponent(new CircleCollider(actor));
            }
          }
        }

        ImGui.endPopup();
      }

      ImGui.textColored(0.3f, 1.0f, 0.3f, 0.8f, "Selected");
      ImGui.sameLine();
      ImGui.text(" " + activeActors.size() + " ");
      ImGui.sameLine();
      ImGui.textColored(0.3f, 1.0f, 0.3f, 0.8f, "Actors");

      List<String> actorNames = new ArrayList<>();
      actorNames.add("All");
      for (Actor a : activeActors) {
        actorNames.add(a.getName());
      }

      String[] names = actorNames.toArray(new String[0]);

      // Make the listbox bigger
      ImGui.setNextItemWidth(ImGui.getWindowWidth());
      // set the height of the listbox to 1/3 of the window height
      ImGui.listBox("##SelectedActors", selected, names, 5);


      if (selected.get() > 0) {
        activeActors.get(selected.get() - 1).imgui();

      } else {
        // Some properties are not available when multiple actors are selected
        propertiesActor.addIgnoreField("icon");
        propertiesActor.addIgnoreField("name");
        propertiesActor.addIgnoreField("ID");
        propertiesActor.addIgnoreField("components");

        propertiesActor.imgui();

        Transform addTransform = propertiesActor.getComponent(Transform.class);

        // Get last delta values
        Vector2f lastDeltaPosition = new Vector2f(deltaPosition);
        Vector2f lastDeltaScale = new Vector2f(deltaScale);
        Vector2f lastDeltaSize = new Vector2f(deltaSize);
        float lastDeltaRotation = deltaRotation;
        int lastDeltaZIndex = deltaZIndex;


        deltaPosition = addTransform.getPosition();
        deltaScale = addTransform.getScale();
        deltaSize = new Vector2f(addTransform.size);
        deltaRotation = addTransform.getRotation();
        deltaZIndex = addTransform.getZIndex();

        // Update the properties of the selected actors
        for (Actor actor : activeActors) {

          actor.setSerializable(propertiesActor.isSerializedActor());

          if (propertiesActor.shouldAdjustToSize()) {
            actor.adjustSizeToTexture();
          }

          actor.setPickable(propertiesActor.isPickable());

          // Update the transform of the actors
          Transform transform = actor.getComponent(Transform.class);

          // add the delta values to the transform
          transform.position.add(new Vector2f(deltaPosition).sub(lastDeltaPosition));
          transform.scale.add(new Vector2f(deltaScale).sub(lastDeltaScale));
          transform.size.add(new Vector2f(deltaSize).sub(lastDeltaSize));
          transform.rotation += deltaRotation - lastDeltaRotation;
          transform.zIndex += deltaZIndex - lastDeltaZIndex;
        }
        propertiesActor.setNotAdjustToSize();
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
