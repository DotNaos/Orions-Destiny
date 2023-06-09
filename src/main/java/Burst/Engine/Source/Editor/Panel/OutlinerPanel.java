package Burst.Engine.Source.Editor.Panel;

import Burst.Engine.Source.Core.Actor.Actor;
import Burst.Engine.Source.Core.UI.ImGui.BImGui;
import Burst.Engine.Source.Core.UI.ImGui.ImGuiPanel;
import Burst.Engine.Source.Core.UI.Window;
import imgui.ImGui;
import imgui.flag.ImGuiTreeNodeFlags;

import java.util.List;
/**
 * @author Oliver Schuetz
 */
public class OutlinerPanel extends ImGuiPanel {

  private static String payloadDragDropType = "Outliner";

  public OutlinerPanel() {
    super();
  }

  @Override
  public void imgui() {
    ImGui.begin("Outliner");
    List<Actor> actors = Window.getScene().getGame().getActors();
    int index = 0;
    for (Actor actor : actors) {
      if (!actor.isSerializedActor()) {
        continue;
      }


      if (doTreeNode(actor, index)) {
        // Set active actor
        if (ImGui.isItemClicked()) {
          Window.getScene().getPanel(PropertiesPanel.class).setActiveActor(actor);
        }
        ImGui.treePop();
      }
      index++;
    }

    // Update the position of the Panel
    this.position.x = ImGui.getWindowPosX();
    this.position.y = ImGui.getWindowPosY();

    ImGui.end();
  }

  private boolean doTreeNode(Actor actor, int index) {
    ImGui.pushID(index);
    boolean treeNodeOpen = ImGui.treeNodeEx(actor.getName(),
            ImGuiTreeNodeFlags.DefaultOpen | ImGuiTreeNodeFlags.FramePadding | ImGuiTreeNodeFlags.OpenOnArrow | ImGuiTreeNodeFlags.SpanAvailWidth, actor.getName());
    ImGui.popID();

    if (ImGui.beginDragDropSource()) {
      // Icon of the actor type
      ImGui.setDragDropPayload(payloadDragDropType, actor);
      ImGui.text(actor.getName());
      ImGui.endDragDropSource();
    }

    if (ImGui.beginDragDropTarget()) {
      Object payloadObj = ImGui.acceptDragDropPayload(payloadDragDropType);
      if (payloadObj != null) {
        if (payloadObj.getClass().isAssignableFrom(Actor.class)) {
          Actor playerActor = (Actor) payloadObj;
          System.out.println("Payload accepted '" + playerActor.getName() + "'");
        }
      }
      ImGui.endDragDropTarget();
    }

    return treeNodeOpen;
  }
}
