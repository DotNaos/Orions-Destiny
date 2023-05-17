package Burst.Engine.Source.Core.UI.ImGui;

import Burst.Engine.Source.Core.UI.Window;
import imgui.ImGui;
import org.joml.Vector3f;

public abstract class ImGuiPanel {
    protected boolean show = true;
    protected Vector3f position = new Vector3f(100, 100, 0);
    protected Vector3f size = new Vector3f((float) Window.getWidth() / 3, (float) Window.getHeight() / 3, 0);

    public ImGuiPanel() {
        ImGui.setNextWindowSize(size.x, size.y);
        ImGui.setNextWindowPos(position.x, position.y);
    }

    public abstract void imgui();

}
