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
import java.util.HashMap;
import java.util.Map;

public class GridLines {


    public static void update(float dt) {
        Viewport viewport = Window.getScene().getViewport();
        Vector2f viewportPos = viewport.position;
        Vector2f projectionSize = viewport.getProjectionSize();

        float firstX = ((int) Math.floor(viewportPos.x
                / GridLines_Config.GRID_WIDTH)) * GridLines_Config.GRID_HEIGHT;
        float firstY = ((int) Math.floor(viewportPos.y / GridLines_Config.GRID_HEIGHT)) * GridLines_Config.GRID_HEIGHT;

        Map<String, Float> values = new HashMap<>();
        values.put("firstX", firstX);
        values.put("firstY", firstY);
        values.put("projectionSize.x", projectionSize.x);
        DebugPanel.plotValues("Gridlines", values);

        int numVtLines = (int) (projectionSize.x * viewport.getZoom() / GridLines_Config.GRID_WIDTH) * 2 + 2;
        int numHzLines = (int)  (projectionSize.y * viewport.getZoom() / GridLines_Config.GRID_HEIGHT) * 2 + 2;

        float width = (int) (projectionSize.x * viewport.getZoom()) + (5 * GridLines_Config.GRID_WIDTH);
        float height = (int) (projectionSize.y * viewport.getZoom()) + (5 * GridLines_Config.GRID_HEIGHT);

        int maxLines = Math.max(numVtLines, numHzLines);
        Vector3f color = new Vector3f(0.2f, 0.2f, 0.2f);
        for (int i = 0; i < maxLines; i++) {
            float x = firstX - (projectionSize.x * viewport.getZoom() / 2) + (GridLines_Config.GRID_WIDTH * i);
            float y = firstY - (projectionSize.y * viewport.getZoom() / 2) + (GridLines_Config.GRID_HEIGHT * i);

            if (i < numVtLines) {
                DebugDraw.addLine2D(new Vector2f(x, firstY - (projectionSize.y * viewport.getZoom() / 2)), new Vector2f(x, firstY + height - (projectionSize.y * viewport.getZoom() / 2)), color);
            }

            if (i < numHzLines) {
                DebugDraw.addLine2D(new Vector2f(firstX - (projectionSize.x * viewport.getZoom() / 2), y), new Vector2f(firstX + width - (projectionSize.x * viewport.getZoom() / 2), y), color);
            }
        }
    }

}
