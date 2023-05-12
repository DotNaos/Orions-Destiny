package Burst.Engine.Source.Editor.Panel;

import Burst.Engine.Source.Core.Actor.Actor;
import Burst.Engine.Source.Core.UI.ImGuiPanel;
import Burst.Engine.Source.Core.UI.Window;
import imgui.ImGui;
import imgui.flag.ImGuiTreeNodeFlags;

import java.util.List;

public class OutlinerPanel extends ImGuiPanel {

    private static String payloadDragDropType = "Outliner";

    @Override
    public void imgui() {
        ImGui.begin("Outliner");


        List<Actor> actors = Window.getScene().getGame().getActors();
        int index = 0;
        for (Actor actor : actors) {
            if (!actor.isSerializedActor()) {
                continue;
            }

            boolean treeNodeOpen = doTreeNode(actor, index);
            if (treeNodeOpen) {
                ImGui.treePop();
            }
            index++;
        }
        ImGui.end();
    }

    private boolean doTreeNode(Actor actor, int index) {
        ImGui.pushID(index);
        boolean treeNodeOpen = ImGui.treeNodeEx(actor.getName(), ImGuiTreeNodeFlags.DefaultOpen | ImGuiTreeNodeFlags.FramePadding | ImGuiTreeNodeFlags.OpenOnArrow | ImGuiTreeNodeFlags.SpanAvailWidth, actor.getName());
        ImGui.popID();

        if (ImGui.beginDragDropSource()) {
            ImGui.setDragDropPayloadObject(payloadDragDropType, actor);
            ImGui.text(actor.getName());
            ImGui.endDragDropSource();
        }

        if (ImGui.beginDragDropTarget()) {
            Object payloadObj = ImGui.acceptDragDropPayloadObject(payloadDragDropType);
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
