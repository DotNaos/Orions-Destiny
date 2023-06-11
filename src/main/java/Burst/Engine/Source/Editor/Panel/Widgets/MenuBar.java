package Burst.Engine.Source.Editor.Panel.Widgets;

import Burst.Engine.Source.Core.EventSystem.EventSystem;
import Burst.Engine.Source.Core.EventSystem.Events.Event;
import Burst.Engine.Source.Core.EventSystem.Events.EventType;
import imgui.ImGui;

/**
 * @author Oliver Schuetz
 */
public class MenuBar {

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
