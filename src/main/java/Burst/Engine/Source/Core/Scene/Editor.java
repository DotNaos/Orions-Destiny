package Burst.Engine.Source.Core.Scene;

import Burst.Engine.Source.Core.Actor;
import Burst.Engine.Source.Core.UI.Window;
import Burst.Engine.Source.Runtime.Game;

public class Editor extends Game {
    public Editor(Scene scene) {
        super(scene);
    }

    public void init()
    {

    }

    @Override
    public void update(float dt) {
        Window.getScene().getCamera().adjustProjection();

        for (int i = 0; i < actors.size(); i++) {
            Actor go = actors.get(i);
            go.updateEditor(dt);

            if (go.isDead()) {
                actors.remove(i);
                Window.getScene().getViewportRenderer().destroyGameObject(go);
                this.physics2D.destroyGameObject(go);
                i--;
            }
        }

        for (Actor go : pendingObjects) {
            actors.add(go);
            go.start();
            Window.getScene().getViewportRenderer().add(go);
            this.physics2D.add(go);
        }
        pendingObjects.clear();
    }
}
