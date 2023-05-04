package Burst.Engine.Source.Editor.UI.Panel;

import imgui.ImGui;
import imgui.flag.ImGuiCol;
import imgui.flag.ImGuiTreeNodeFlags;
import Burst.Engine.Source.Core.GameObject;
import Burst.Engine.Source.Editor.UI.Window;

import java.util.List;

public class OutlinerPanel {

    private static String payloadDragDropType = "Outliner";

    public void imgui() {
        ImGui.begin("Outliner");

        List<GameObject> gameObjects = Window.getScene().getGameObjects();
        int index = 0;
        for (GameObject obj : gameObjects) {
            if (!obj.doSerialization()) {
                continue;
            }

            boolean treeNodeOpen = doTreeNode(obj, index);
            if (treeNodeOpen) {
                ImGui.treePop();
            }
            index++;
        }
        // Push color variables
        ImGui.pushStyleColor(ImGuiCol.TitleBg, (float) 255 / 20, (float) 255 / 20, (float) 255 / 20, 1.0f);
        ImGui.pushStyleColor(ImGuiCol.WindowBg, (float) 255 / 100, (float) 255 / 100, (float) 255 / 100, 1.0f);

        // Pop color variables
        ImGui.popStyleColor(2);

        ImGui.end();
    }

    private boolean doTreeNode(GameObject obj, int index) {
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
                if (payloadObj.getClass().isAssignableFrom(GameObject.class)) {
                    GameObject playerGameObj = (GameObject)payloadObj;
                    System.out.println("Payload accepted '" + playerGameObj.name + "'");
                }
            }
            ImGui.endDragDropTarget();
        }

        return treeNodeOpen;
    }
}
