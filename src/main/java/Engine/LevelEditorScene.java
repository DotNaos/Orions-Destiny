package Engine;

import java.awt.event.KeyEvent;

public class LevelEditorScene extends Scene{
    public boolean changingScene = false;
    public float timeToChangeScene = 2.0f;

    public LevelEditorScene()
    {
        System.out.print("LevelEditorScene");
    }

    @Override
    public void update(float dt) {

        System.out.println("" + Math.round(1.0f / dt) + " FPS");

        if (!changingScene && KeyListener.isKeyPressed((KeyEvent.VK_SPACE)))
        {
            changingScene = true;
        }

        if (changingScene && timeToChangeScene > 0)
        {
            timeToChangeScene -= dt;
            Window.get().r = dt * 5.0f;
            Window.get().g = dt * 5.0f;
            Window.get().b = dt * 5.0f;

        }
        else if (changingScene)
        {
            Window.changeScene(1);
        }
    }


}
