package Burst.Engine.Source.Editor.Panel;

import Burst.Engine.Source.Core.Observer.EventSystem;
import Burst.Engine.Source.Core.Observer.Events.Event;
import Burst.Engine.Source.Core.Observer.Events.EventType;
import Burst.Engine.Source.Core.UI.ImGuiPanel;
import imgui.ImGui;


public class MenuBar extends ImGuiPanel {

    public void imgui() {
        ImGui.beginMenuBar();

        if (ImGui.beginMenu("File")) {
            if (ImGui.menuItem("Save", "Ctrl+S")) {
                EventSystem.notify(null, new Event(EventType.SaveLevel));
            }

            if (ImGui.menuItem("Load", "Ctrl+O")) {
                EventSystem.notify(null, new Event(EventType.LoadLevel));
            }

            ImGui.endMenu();
        }

        ImGui.endMenuBar();
    }
}
