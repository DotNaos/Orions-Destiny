package Burst.Engine.Source.Editor.Panel;

import Burst.Engine.Source.Core.UI.ImGuiPanel;
import imgui.ImGui;
import imgui.flag.ImGuiTreeNodeFlags;
import Burst.Engine.Source.Core.Actor.Actor;
import Burst.Engine.Source.Core.UI.Window;

import java.util.List;

public class OutlinerPanel extends ImGuiPanel {

    private static String payloadDragDropType = "Outliner";

    @Override
    public void imgui() {
        ImGui.begin("Outliner");

        
        List<Actor> actors = Window.getScene().getGame().getActors();
        int index = 0;
        for (Actor obj : actors) {
            if (!obj.doSerialization()) {
                continue;
            }

            boolean treeNodeOpen = doTreeNode(obj, index);
            if (treeNodeOpen) {
                ImGui.treePop();
            }
            index++;
        }
        ImGui.end();
    }

    private boolean doTreeNode(Actor obj, int index) {
        ImGui.pushID(index);
        boolean treeNodeOpen = ImGui.treeNodeEx(
                obj.name,
                ImGuiTreeNodeFlags.DefaultOpen |
                        ImGuiTreeNodeFlags.FramePadding |
                        ImGuiTreeNodeFlags.OpenOnArrow |
                        ImGuiTreeNodeFlags.SpanAvailWidth,
                obj.name
        );
        ImGui.popID();

        if (ImGui.beginDragDropSource()) {
            ImGui.setDragDropPayloadObject(payloadDragDropType, obj);
            ImGui.text(obj.name);
            ImGui.endDragDropSource();
        }

        if (ImGui.beginDragDropTarget()) {
            Object payloadObj = ImGui.acceptDragDropPayloadObject(payloadDragDropType);
            if (payloadObj != null) {
                if (payloadObj.getClass().isAssignableFrom(Actor.class)) {
                    Actor playerGameObj = (Actor)payloadObj;
                    System.out.println("Payload accepted '" + playerGameObj.name + "'");
                }
            }
            ImGui.endDragDropTarget();
        }

        return treeNodeOpen;
    }
}
