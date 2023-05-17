package Burst.Engine.Source.Editor.Components;

import Burst.Engine.Config.Constants.GridLines_Config;
import Burst.Engine.Source.Core.Render.Debug.DebugDraw;
import Burst.Engine.Source.Core.UI.ImGui.BImGui;
import Burst.Engine.Source.Core.UI.Viewport;
import Burst.Engine.Source.Core.UI.Window;
import Burst.Engine.Source.Editor.EditorOption;
import imgui.ImGui;
import org.joml.Vector3f;
import org.joml.Vector4f;


/**
 * A class that draws grid lines on the screen.
 *
 * <p>The grid lines are drawn in the 2D plane, and are drawn in the viewport's
 * coordinate system. The grid lines are drawn in the color specified by the
 * {@link GridLines_Config#COLOR} constant.</p>
 *
 * <p>{@link GridLines_Config#SIZE} is used as size of the grid and which increases proportional to the zoom</p>
 *
 */
public class GridLines implements EditorOption {


  /**
   * The minimum Resolution of the grid lines in pixels.
   *
   * <p>
   *   This is used to prevent the grid lines from becoming too small when zooming in.
   *   The grid lines will be at least this many pixels wide and tall.
   *   This value must be a power of 2.
   *   This value must be greater than or equal to 16.
   * </p>
   *
   */
  public static int[] pixelsPerGrid = {1};
  public static int[] spriteResolution = {16};

  public static boolean[] enabled = {true};

  /**
   * Updates the grid lines based on the current viewport's position, size, zoom
   * level, and projection size.
   *
   * @param dt the delta time since the last frame was rendered
   * @see Window#getScene()
   * @see Viewport#getSize()
   * @see DebugDraw#addLine2D(Vector3f, Vector3f, Vector4f)
   * @see GridLines_Config#SIZE
   * @see GridLines_Config#SIZE
   * @see Math#floor(double)
   * @see Vector3f
   * @see Vector3f
   */
  public static void update(float dt) {
    if (!enabled[0]) return;

    // Get the viewports attributes
    Viewport viewport = Window.getScene().getViewport();

      float zoom = viewport.getZoom();

    // Scaling factor snaps to the nearest power of 2 of zoom

      int nearestPowerOf2 = (int) Math.pow(2, Math.floor(Math.log(zoom) / Math.log(2) + pixelsPerGrid[0] - 1));
      float scaling = ((float) 1 / spriteResolution[0] * nearestPowerOf2);

      float gridSize = (GridLines_Config.SIZE * scaling);

    // Zoom is half the width of the viewport in world units
      float width = viewport.getWorldSize().x;
      float height = viewport.getWorldSize().y;

      width  = (float) Math.ceil(width) + 3;
      height = (float) Math.ceil(height) + 3;


    // Start to draw at this position
      float firstX = viewport.getPosition().x - width / 2;
      float firstY = viewport.getPosition().y - height / 2;

      // Make sure firstX and firstY are even multiples of gridSize
        firstX = (float) Math.floor(firstX / gridSize) * gridSize;
        firstY = (float) Math.floor(firstY / gridSize) * gridSize;

      // Align to the grid
        firstX = (float) Math.ceil(firstX) ;
        firstY = (float) Math.ceil(firstY) ;

      // offset by half a sprite size in world units
        firstX +=  1f / (spriteResolution[0] / 8f);
        firstY +=  1f / (spriteResolution[0] / 8f);


    // Lines per axis, +2 for the lines that are at the edge
      int numLinesX = (int) (width / gridSize) + 2;
      int numLinesY = (int) (height / gridSize) + 2;



    // Maximum number of lines
    int numLines = Math.max(numLinesX, numLinesY);

    for (int i = 0; i < numLines; i++) {
      // Vertical lines
      float x = firstX + (i * gridSize);
      if (i < numLinesX)
        DebugDraw.addLine2D(new Vector3f(x, firstY, 0), new Vector3f(x, firstY + height, 0), GridLines_Config.COLOR);

      // Horizontal lines
      float y = firstY + (i * gridSize);
      if (i < numLinesY)
        DebugDraw.addLine2D(new Vector3f(firstX, y, 0), new Vector3f(firstX + width, y, 0), GridLines_Config.COLOR);
    }
  }

  /**
   *
   */

  public void imgui() {}
  public static void imgui(int v) {
    ImGui.beginTabItem("GridLines");
    if(ImGui.checkbox("Enabled", enabled[0])) enabled[0] = !enabled[0];

    BImGui.resetButton(pixelsPerGrid, 1);
    ImGui.sameLine();
    ImGui.sliderInt("Pixels per Grid", pixelsPerGrid, 0, 10);

    BImGui.resetButton(spriteResolution, 16);
    ImGui.sameLine();
    ImGui.sliderInt("Sprite Resolution", spriteResolution, 1, 64);

    float[] tempColor = {GridLines_Config.COLOR.x, GridLines_Config.COLOR.y, GridLines_Config.COLOR.z, GridLines_Config.COLOR.w};
    ImGui.colorEdit4("Color", tempColor);
    GridLines_Config.COLOR = new Vector4f(tempColor[0], tempColor[1], tempColor[2], tempColor[3]);
    ImGui.endTabItem();
  }
}

