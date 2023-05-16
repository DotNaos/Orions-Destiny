package Burst.Engine.Source.Core.Graphics.Render.Components;

import Burst.Engine.Config.Constants.GridLines_Config;
import Burst.Engine.Source.Core.Graphics.Debug.DebugDraw;
import Burst.Engine.Source.Core.Graphics.Input.MouseListener;
import Burst.Engine.Source.Core.UI.Viewport;
import Burst.Engine.Source.Core.UI.Window;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;


public class GridLines {

    /**
     * 
     * Updates the grid lines based on the current viewport's position, size, zoom
     * level, and projection size.
     * 
     * @paspectRatioam dt the delta time since the last frame was rendered
     * @see Window#getScene()
     * @see Viewport#getViewport()
     * @see Viewport#getSize()
     * @see DebugDraw#addLine2D(Vector2f, Vector2f, Vector3f)
     * @see GridLines_Config#SIZE
     * @see GridLines_Config#SIZE
     * @see Math#floor(double)
     * @see Vector2f
     * @see Vector3f
     */
    public static void update(float dt) {
        // Get the viewports attributes
        Viewport viewport = Window.getScene().getViewport();
        Vector2f viewportPos = viewport.getPosition();
        viewportPos.x = (float) Math.floor(viewportPos.x);
        viewportPos.y = (float) Math.floor(viewportPos.y);
        Vector2f viewportSize = viewport.getSize();
        float zoom = viewport.getZoom();
        float aspectRatio = viewportSize.x / viewportSize.y;

        // Calculate the scaling factor
        // float scaling = (float) Math.pow(10, (int) Math.log10(zoom) - 1);
        float scaling = 1;
        float gridSize = (GridLines_Config.SIZE * scaling);

        float width = zoom * 2;
        float height = zoom * 2 / aspectRatio;

        width = (float) Math.floor(width);
        height = (float) Math.floor(height);


        // Viewportpos is in the center of the viewport, so we need to offset it by half
        float firstX = viewportPos.x - zoom;
        float firstY = viewportPos.y - zoom / aspectRatio;

        firstX = (float) Math.floor(firstX) - gridSize / 2;
        firstY = (float) Math.floor(firstY) - gridSize / 2;
        

        int numLinesX = (int) (width / gridSize);
        int numLinesY = (int) (height / gridSize);


        // DebugDraw.addBox2D(new Vector2f(), new Vector2f(1f, 1f), 0.0f, new Vector4f(0, 0, 1, 1));

        // Draw a Line from the viewport's position to the bottom left corner of the viewport
        // DebugDraw.addLine2D(viewportPos, new Vector2f(firstX, firstY), new Vector3f(0, 1, 0.1f));


        System.out.println("ViewportPos: " + viewportPos.x + ", " + viewportPos.y + " | " + firstX + ", " + firstY + " | " + zoom + " | | | " + width + ", " + height);


        int numLines = Math.max(numLinesX, numLinesY);
        Vector3f color = new Vector3f(0.2f, 0.2f, 0.2f);
        for (int i = 0; i < numLines; i++) {
            DebugDraw.addLine2D(new Vector2f(firstX + i * gridSize, firstY), new Vector2f(firstX + i * gridSize, firstY + height), color);
            DebugDraw.addLine2D(new Vector2f(firstX, firstY + i * gridSize), new Vector2f(firstX + width, firstY + i * gridSize), color);
        }





    }

}

