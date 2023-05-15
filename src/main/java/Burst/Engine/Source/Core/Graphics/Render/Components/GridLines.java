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


    public static void update(float dt) {
    Viewport viewport = Window.getScene().getViewport();
    Vector2f viewportPos = viewport.position;
    Vector2f projectionSize = viewport.getProjectionSize();

    float firstX = viewportPos.x - projectionSize.x * viewport.getZoom(); 
    float firstY =  viewportPos.y - projectionSize.y * viewport.getZoom();

    int linesX = (int) (GridLines_Config.GRID_WIDTH);
    int linesY = (int) (GridLines_Config.GRID_HEIGHT);

   
    Vector3f color = new Vector3f(0.2f, 0.2f, 0.2f);
    int maxLines = Math.max(linesX, linesY);
    for (int i = 0; i < maxLines; i++) {
        float x = firstX - (i * GridLines_Config.GRID_WIDTH);
        float y = firstY - (i * GridLines_Config.GRID_HEIGHT);

        // X
        DebugDraw.addLine2D(    
                new Vector2f(firstX, y),
                new Vector2f(firstX + viewport.getSize().x * 2, y),
                color
        ); 

        // Y
        // DebugDraw.addLine2D(
        //         new Vector2f(firstX + (GridLines_Config.GRID_WIDTH * i), y),
        //         new Vector2f(firstX + (GridLines_Config.GRID_WIDTH * i), y + viewport.getSize().y),
        //         color
        // );


        List<Float> values = new ArrayList<>();


        // DebugPanel.plotValues("Width", values);
    }

    }

}
