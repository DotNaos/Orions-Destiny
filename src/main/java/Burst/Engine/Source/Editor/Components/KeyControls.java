package Burst.Engine.Source.Editor.Components;

import Burst.Engine.Config.Constants.GridLines_Config;
import Burst.Engine.Source.Core.Actor.Actor;
import Burst.Engine.Source.Game.Animation.StateMachine;
import Burst.Engine.Source.Core.Input.KeyListener;
import Burst.Engine.Source.Core.UI.Window;
import Burst.Engine.Source.Editor.EditorOption;
import Burst.Engine.Source.Editor.Panel.PropertiesPanel;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.GLFW.*;

public class KeyControls implements EditorOption {
    private float debounceTime = 0.2f;
    private float debounce = 0.0f;


    public void update(float dt) {
        debounce -= dt;

        PropertiesPanel propertiesPanel = Window.getScene().getPanel(PropertiesPanel.class);
        Actor activeActor = propertiesPanel.getActiveGameObject();
        List<Actor> activeActors = propertiesPanel.getActiveGameObjects();
        float multiplier = KeyListener.isKeyPressed(GLFW_KEY_LEFT_SHIFT) ? 0.1f : 1.0f;

        if (KeyListener.isKeyPressed(GLFW_KEY_LEFT_CONTROL) &&
                KeyListener.keyBeginPress(GLFW_KEY_D) && activeActor != null) {
            Actor newObj = activeActor.copy();
            assert Window.getScene().getGame() != null;
            Window.getScene().getGame().addActor(newObj);
            newObj.transform.position.add(GridLines_Config.SIZE, 0.0f, 0);
            propertiesPanel.setActiveGameObject(newObj);
            if (newObj.getComponent(StateMachine.class) != null) {
                newObj.getComponent(StateMachine.class).refreshTextures();
            }
        } else if (KeyListener.isKeyPressed(GLFW_KEY_LEFT_CONTROL) &&
                KeyListener.keyBeginPress(GLFW_KEY_D) && activeActors.size() > 1) {
            List<Actor> actors = new ArrayList<>(activeActors);
            propertiesPanel.clearSelected();
            for (Actor actor : actors) {
                Actor copy = actor.copy();
                assert Window.getScene().getGame() != null;
                Window.getScene().getGame().addActor(copy);
                propertiesPanel.addActiveGameObject(copy);
                if (copy.getComponent(StateMachine.class) != null) {
                    copy.getComponent(StateMachine.class).refreshTextures();
                }
            }
        } else if (KeyListener.keyBeginPress(GLFW_KEY_DELETE)) {
            for (Actor actor : activeActors) {
                actor.destroy();
            }
            propertiesPanel.clearSelected();
        } else if (KeyListener.isKeyPressed(GLFW_KEY_PAGE_DOWN) && debounce < 0) {
            debounce = debounceTime;
            for (Actor actor : activeActors) {
                actor.transform.zIndex--;
            }
        } else if (KeyListener.isKeyPressed(GLFW_KEY_PAGE_UP) && debounce < 0) {
            debounce = debounceTime;
            for (Actor actor : activeActors) {
                actor.transform.zIndex++;
            }
        } else if (KeyListener.isKeyPressed(GLFW_KEY_UP) && debounce < 0) {
            debounce = debounceTime;
            for (Actor actor : activeActors) {
                actor.transform.position.y += GridLines_Config.SIZE * multiplier;
            }
        } else if (KeyListener.isKeyPressed(GLFW_KEY_LEFT) && debounce < 0) {
            debounce = debounceTime;
            for (Actor actor : activeActors) {
                actor.transform.position.x -= GridLines_Config.SIZE * multiplier;
            }
        } else if (KeyListener.isKeyPressed(GLFW_KEY_RIGHT) && debounce < 0) {
            debounce = debounceTime;
            for (Actor actor : activeActors) {
                actor.transform.position.x += GridLines_Config.SIZE * multiplier;
            }
        } else if (KeyListener.isKeyPressed(GLFW_KEY_DOWN) && debounce < 0) {
            debounce = debounceTime;
            for (Actor actor : activeActors) {
                actor.transform.position.y -= GridLines_Config.SIZE * multiplier;
            }
        }
    }

    /**
     *
     */
    @Override
    public void imgui() {

    }
}
