package Burst.Engine.Source.Core.Graphics.Render.Components;

import Burst.Engine.Config.Constants.GridLines_Config;
import Burst.Engine.Source.Core.Graphics.Debug.DebugDraw;
import Burst.Engine.Source.Core.UI.Viewport;
import Burst.Engine.Source.Core.UI.Window;
import org.joml.Vector2f;
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
public class GridLines {
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
    // Get the viewports attributes
      Viewport viewport = Window.getScene().getViewport();

      float zoom = viewport.getZoom();

    // Scaling factor snaps to the nearest power of 2 of zoom
      float scaling = (float) Math.pow(10, (int) Math.log10(zoom) - 1);
      scaling = 1;

      float gridSize = (GridLines_Config.SIZE * scaling);


    // Zoom is half the width of the viewport in world units
      float width = viewport.getWorldSize().x;
      float height = viewport.getWorldSize().y;

      width  = (float) Math.ceil(width);
      height = (float) Math.ceil(height);


    // Start to draw at this position
      float firstX = viewport.getPosition().x - width / 2;
      float firstY = viewport.getPosition().y - height / 2;

      // Align to the grid
      firstX = (float) Math.ceil(firstX) - gridSize / 2;
      firstY = (float) Math.ceil(firstY) - gridSize / 2;

    // Lines per axis, +2 for the lines that are at the edge
      int numLinesX = (int) (width / gridSize) + 2;
      int numLinesY = (int) (height / gridSize) + 2;


    System.out.println("Pos: " + firstX + " " + firstY + " | " + width + " " + height);

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

}

