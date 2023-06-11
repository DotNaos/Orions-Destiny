package Burst.Engine.Source.Core.UI.ImGui;

import Burst.Engine.Source.Core.UI.Window;
import org.joml.Vector2f;
/**
 * @author Oliver Schuetz
 */
public abstract class ImGuiPanel {
  protected boolean show = true;
  protected Vector2f position = new Vector2f(100, 100);
  protected Vector2f size = new Vector2f((float) Window.getWidth() / 3, (float) Window.getHeight() / 3);

  public ImGuiPanel() {

  }

  public abstract void imgui();


}
