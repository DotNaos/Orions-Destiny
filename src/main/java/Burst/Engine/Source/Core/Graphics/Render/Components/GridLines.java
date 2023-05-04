package Burst.Engine.Source.Core.Graphics.Render.Components;

import Burst.Engine.Config.Constants.GridLines_Config;
import Burst.Engine.Source.Core.Graphics.Debug.DebugDraw;
import Burst.Engine.Source.Core.Camera;
import Burst.Engine.Source.Core.UI.Window;
import Burst.Engine.Source.Core.Component;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class GridLines extends Component {

    @Override
    public void editorUpdate(float dt) {
        Camera camera = Window.getScene().camera();
        Vector2f cameraPos = camera.position;
        Vector2f projectionSize = camera.getProjectionSize();

        float firstX = ((int)Math.floor(cameraPos.x / GridLines_Config.GRID_WIDTH)) * GridLines_Config.GRID_HEIGHT;
        float firstY = ((int)Math.floor(cameraPos.y / GridLines_Config.GRID_HEIGHT)) * GridLines_Config.GRID_HEIGHT;

        int numVtLines = (int)(projectionSize.x * camera.getZoom() / GridLines_Config.GRID_WIDTH) + 2;
        int numHzLines = (int)(projectionSize.y * camera.getZoom() / GridLines_Config.GRID_HEIGHT) + 2;

        float width = (int)(projectionSize.x * camera.getZoom()) + (5 * GridLines_Config.GRID_WIDTH);
        float height = (int)(projectionSize.y * camera.getZoom()) + (5 * GridLines_Config.GRID_HEIGHT);

        int maxLines = Math.max(numVtLines, numHzLines);
        Vector3f color = new Vector3f(0.2f, 0.2f, 0.2f);
        for (int i=0; i < maxLines; i++) {
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
