package Burst.Engine.Source.Core.Graphics.Input.Components;

import Burst.Engine.Source.Core.Component;
import Burst.Engine.Source.Runtime.Animation.StateMachine;
import Burst.Engine.Source.Core.Graphics.Input.KeyListener;
import Burst.Engine.Source.Editor.Panel.PropertiesPanel;
import Burst.Engine.Source.Core.Actor;
import Burst.Engine.Source.Core.UI.Window;
import Burst.Engine.Config.Constants.GridLines_Config;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.GLFW.*;

public class KeyControls extends Component {
    private float debounceTime = 0.2f;
    private float debounce = 0.0f;

    @Override
    public void updateEditor(float dt) {
        debounce -= dt;

        PropertiesPanel propertiesPanel = Window.getScene().getPanel(PropertiesPanel.class);
        Actor activeActor = propertiesPanel.getActiveGameObject();
        List<Actor> activeActors = propertiesPanel.getActiveGameObjects();
        float multiplier = KeyListener.isKeyPressed(GLFW_KEY_LEFT_SHIFT) ? 0.1f : 1.0f;

        if (KeyListener.isKeyPressed(GLFW_KEY_LEFT_CONTROL) &&
                KeyListener.keyBeginPress(GLFW_KEY_D) && activeActor != null) {
            Actor newObj = activeActor.copy();
            assert Window.getGameScene() != null;
            Window.getGameScene().addActor(newObj);
            newObj.transform.position.add(GridLines_Config.GRID_WIDTH, 0.0f);
            propertiesPanel.setActiveGameObject(newObj);
            if (newObj.getComponent(StateMachine.class) != null) {
                newObj.getComponent(StateMachine.class).refreshTextures();
            }
        } else if (KeyListener.isKeyPressed(GLFW_KEY_LEFT_CONTROL) &&
                KeyListener.keyBeginPress(GLFW_KEY_D) && activeActors.size() > 1) {
            List<Actor> actors = new ArrayList<>(activeActors);
            propertiesPanel.clearSelected();
            for (Actor go : actors) {
                Actor copy = go.copy();
                assert Window.getGameScene() != null;
                Window.getGameScene().addActor(copy);
                propertiesPanel.addActiveGameObject(copy);
                if (copy.getComponent(StateMachine.class) != null) {
                    copy.getComponent(StateMachine.class).refreshTextures();
                }
            }
        } else if (KeyListener.keyBeginPress(GLFW_KEY_DELETE)) {
            for (Actor go : activeActors) {
                go.destroy();
            }
            propertiesPanel.clearSelected();
        } else if (KeyListener.isKeyPressed(GLFW_KEY_PAGE_DOWN) && debounce < 0) {
            debounce = debounceTime;
            for (Actor go : activeActors) {
                go.transform.zIndex--;
            }
        } else if (KeyListener.isKeyPressed(GLFW_KEY_PAGE_UP) && debounce < 0) {
            debounce = debounceTime;
            for (Actor go : activeActors) {
                go.transform.zIndex++;
            }
        } else if (KeyListener.isKeyPressed(GLFW_KEY_UP) && debounce < 0) {
            debounce = debounceTime;
            for (Actor go : activeActors) {
                go.transform.position.y += GridLines_Config.GRID_HEIGHT * multiplier;
            }
        } else if (KeyListener.isKeyPressed(GLFW_KEY_LEFT) && debounce < 0) {
            debounce = debounceTime;
            for (Actor go : activeActors) {
                go.transform.position.x -= GridLines_Config.GRID_HEIGHT * multiplier;
            }
        } else if (KeyListener.isKeyPressed(GLFW_KEY_RIGHT) && debounce < 0) {
            debounce = debounceTime;
            for (Actor go : activeActors) {
                go.transform.position.x += GridLines_Config.GRID_HEIGHT * multiplier;
            }
        } else if (KeyListener.isKeyPressed(GLFW_KEY_DOWN) && debounce < 0) {
            debounce = debounceTime;
            for (Actor go : activeActors) {
                go.transform.position.y -= GridLines_Config.GRID_HEIGHT * multiplier;
            }
        }
    }
}
