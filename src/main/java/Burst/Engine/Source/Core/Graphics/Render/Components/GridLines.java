package Burst.Engine.Source.Core.Graphics.Render.Components;

import Burst.Engine.Config.Constants.GridLines_Config;
import Burst.Engine.Source.Core.Graphics.Debug.DebugDraw;
import Burst.Engine.Source.Core.Graphics.Input.MouseListener;
import Burst.Engine.Source.Core.UI.Viewport;
import Burst.Engine.Source.Core.UI.Window;
import org.joml.Vector3f;
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
        Vector3f viewportPos = viewport.getPosition();
        viewportPos.x = (float) Math.floor(viewportPos.x);
        viewportPos.y = (float) Math.floor(viewportPos.y);
        Vector3f viewportSize = viewport.getSize();
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
        float firstX = viewportPos.x - zoom * aspectRatio / 2;
        float firstY = viewportPos.y - zoom * aspectRatio / 2;
        firstX = (float) Math.floor(firstX) - gridSize / 2 + 3;
        firstY = (float) Math.floor(firstY) - gridSize / 2 + 3;

        DebugDraw.addBox2D(new Vector3f(firstX, firstY, 0), new Vector3f(2), 0, new Vector4f(0, 0, 1, 1));

    
        int numLinesX = (int) (width / gridSize) + 2;
        int numLinesY = (int) (height / gridSize) + 2;


        // DebugDraw.addBox2D(new Vector3f(), new Vector3f(1f, 1f), 0.0f, new Vector4f(0, 0, 1, 1));

        // Draw a Line from the viewport's position to the bottom left corner of the viewport
        // DebugDraw.addLine2D(viewportPos, new Vector3f(firstX, firstY), new Vector3f(0, 1, 0.1f));


        // System.out.println("ViewportPos: " + viewportPos.x + ", " + viewportPos.y + " | " + firstX + ", " + firstY + " | " + zoom + " | | | " + width + ", " + height);


        int numLines = Math.max(numLinesX, numLinesY);
        Vector4f color = new Vector4f(0.2f, 0.2f, 0.2f, 1.0f);
        for (int i = 0; i < numLines; i++) {
            DebugDraw.addLine2D(new Vector3f(firstX + i * gridSize, firstY, 0), new Vector3f(firstX + i * gridSize, firstY + height, 0), color);
            DebugDraw.addLine2D(new Vector3f(firstX, firstY + i * gridSize, 0), new Vector3f(firstX + width, firstY + i * gridSize,0 ), color);
        }





    }

}

