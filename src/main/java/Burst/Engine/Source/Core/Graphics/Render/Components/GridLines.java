package Burst.Engine.Source.Core.Graphics.Render.Components;

import Burst.Engine.Config.Constants.GridLines_Config;
import Burst.Engine.Source.Core.Graphics.Debug.DebugDraw;
import Burst.Engine.Source.Core.Graphics.Input.MouseListener;
import Burst.Engine.Source.Core.UI.DebugPanel;
import Burst.Engine.Source.Core.UI.Viewport;
import Burst.Engine.Source.Core.UI.Window;
import org.joml.Vector2f;
import org.joml.Vector3f;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GridLines {

    /**
     * 
     * Updates the grid lines based on the current viewport's position, size, zoom
     * level, and projection size.
     * 
     * @param dt the delta time since the last frame was rendered
     * @see Window#getScene()
     * @see Viewport#getViewport()
     * @see Viewport#getSize()
     * @see Viewport#getProjectionSize()
     * @see DebugDraw#addLine2D(Vector2f, Vector2f, Vector3f)
     * @see GridLines_Config#GRID_WIDTH
     * @see GridLines_Config#GRID_HEIGHT
     * @see Math#floor(double)
     * @see Vector2f
     * @see Vector3f
     */
    public static void update(float dt) {
        Viewport viewport = Window.getScene().getViewport();
        Vector2f viewportPos = viewport.position;
        Vector2f viewportSize = viewport.getSize();
        Vector2f projectionSize = viewport.getProjectionSize();

        float firstX = ((int) Math.floor(viewportPos.x - viewportSize.x / GridLines_Config.GRID_WIDTH))
                * GridLines_Config.GRID_WIDTH;
        float firstY = ((int) Math.floor(viewportPos.y - viewportSize.y / GridLines_Config.GRID_HEIGHT))
                * GridLines_Config.GRID_HEIGHT;

        int numVtLines = (int) (projectionSize.x * viewport.getZoom() / GridLines_Config.GRID_WIDTH) + 2;
        int numHzLines = (int) (projectionSize.y * viewport.getZoom() / GridLines_Config.GRID_HEIGHT) + 2;

        float width = (int) (projectionSize.x * viewport.getZoom()) + (5 * GridLines_Config.GRID_WIDTH);
        float height = (int) (projectionSize.y * viewport.getZoom()) + (5 * GridLines_Config.GRID_HEIGHT);

        int maxLines = Math.max(numVtLines, numHzLines);
        Vector3f color = new Vector3f(0.2f, 0.2f, 0.2f);
        for (int i = 0; i < maxLines; i++) {
            float x = firstX + (GridLines_Config.GRID_WIDTH * i);
            float y = firstY + (GridLines_Config.GRID_HEIGHT * i);

            if (i < numVtLines) {
                DebugDraw.addLine2D(new Vector2f(x, firstY), new Vector2f(x, firstY + height), color);
            }

            if (i < numHzLines) {
                DebugDraw.addLine2D(new Vector2f(firstX, y), new Vector2f(firstX + width, y), color);
            }
        }

    }

}
