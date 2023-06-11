package Burst.Engine.Source.Editor.Gizmo;

import Burst.Engine.Source.Core.Assets.Graphics.Sprite;
import Burst.Engine.Source.Core.Input.MouseListener;

/**
 * @author GamesWithGabe
 */
public class ScaleGizmo extends Gizmo {
  public ScaleGizmo(Sprite scaleSprite) {
    super(scaleSprite);
  }

  @Override
  public void updateEditor(float dt) {
    super.updateEditor(dt);
    if (activeActor != null) {
      if (xAxisActive && !yAxisActive) {
        activeActor.getTransform().scale.x += MouseListener.getWorldDx();
      } else if (yAxisActive) {
        activeActor.getTransform().scale.y += MouseListener.getWorldDy();
      }
    }
  }
}
