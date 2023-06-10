package Burst.Engine.Source.Editor.Components;

import Burst.Engine.Config.Constants.GridLines_Config;
import Burst.Engine.Config.HotKeys;
import Burst.Engine.Source.Core.Actor.Actor;
import Burst.Engine.Source.Core.Component;
import Burst.Engine.Source.Core.Input.KeyListener;
import Burst.Engine.Source.Core.UI.Window;
import Burst.Engine.Source.Editor.Editor;
import Burst.Engine.Source.Editor.Panel.PropertiesPanel;
import Burst.Engine.Source.Game.Animation.StateMachine;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class KeyControls extends Component {
  protected float inputDelay = 0.2f * 0.75f;
  protected transient float debounce = 0.0f;


  public void update(float dt) {
    super.update(dt);
    if (debounce > -1000.0f) {
      debounce -= dt;
    } else {
      debounce = -1.0f;
    }

    PropertiesPanel propertiesPanel = Window.getScene().getPanel(PropertiesPanel.class);
    Actor activeActor = propertiesPanel.getActiveActor();
    List<Actor> activeActors = propertiesPanel.getActiveActors();

    if (duplicateActors(propertiesPanel, activeActors, activeActor)) ;
    else if (deleteActors(propertiesPanel, activeActors, activeActor)) ;
    else if (moveZIndex(propertiesPanel, activeActors, activeActor)) ;
    else if (move(propertiesPanel, activeActors, activeActor)) ;

  }


  private boolean duplicateActors(PropertiesPanel propertiesPanel, List<Actor> activeActors, Actor activeActor) {
    if (KeyListener.isKeyPressed(HotKeys.get().Shortcut_EditorDuplicate.first) && KeyListener.isKeyPressed(HotKeys.get().Shortcut_EditorDuplicate.second) && activeActors.size() >= 1 && debounce < 0) {
      debounce = inputDelay;
      List<Actor> actors = new ArrayList<>(activeActors);
      propertiesPanel.clearSelected();



      // calculate the offset
      float offset = max(actors).x - min(actors).x + GridLines_Config.SIZE;

      for (Actor actor : actors) {
        Actor copy = actor.copy();
        copy.getTransform().position.x += offset;

        assert Window.getScene().getEditor() != null;
        Window.getScene().getEditor().addActor(copy);
        propertiesPanel.addActiveActor(copy);
        if (copy.getComponent(StateMachine.class) != null) {
          copy.getComponent(StateMachine.class).refreshTextures();
        }

      }

      return true;

    }

    return false;

  }

  private boolean deleteActors(PropertiesPanel propertiesPanel, List<Actor> activeActors, Actor activeActor) {
    if (KeyListener.isKeyPressed(HotKeys.get().EditorDelete) && activeActor != null && debounce < 0) {
      debounce = inputDelay;
      for (Actor actor : activeActors) {
        Window.getScene().getEditor().removeActor(actor);
      }
      propertiesPanel.clearSelected();
      Editor editor = Window.getScene().getEditor();
      if (editor.getActors().size() > 0) {
        propertiesPanel.addActiveActor(editor.getActors().get(editor.getActors().size() - 1));
      }
      return true;
    }
    return false;

  }

  private boolean moveZIndex(PropertiesPanel propertiesPanel, List<Actor> activeActors, Actor activeActor) {
    if (KeyListener.isKeyPressed(HotKeys.get().EditorMoveZToBack) && debounce < 0) {
      debounce = inputDelay;
      for (Actor actor : activeActors) {
        actor.getTransform().zIndex = actor.getTransform().zIndex - 1;
      }

      return true;
    } else if (KeyListener.isKeyPressed(HotKeys.get().EditorMoveZToFront) && debounce < 0) {
      debounce = inputDelay;
      for (Actor actor : activeActors) {
        actor.getTransform().zIndex = actor.getTransform().zIndex + 1;
      }
      return true;
    }
    return false;
  }

  private boolean move(PropertiesPanel propertiesPanel, List<Actor> activeActors, Actor activeActor) {
    Vector2f multiplier = new Vector2f(1.0f, 1.0f);
    boolean moveSlow = KeyListener.isKeyPressed(HotKeys.get().Modifier_EditorSlow);
    boolean moveFast = KeyListener.isKeyPressed(HotKeys.get().Modifier_EditorFast);

    if (moveSlow && moveFast) {
      multiplier = new Vector2f(0.5f, 0.5f);
    } else if (moveSlow) {
      multiplier = new Vector2f(0.1f, 0.1f);
    } else if (moveFast) {
      Vector2f offset = new Vector2f(
          max(activeActors).x - min(activeActors).x,
          max(activeActors).y - min(activeActors).y
      );
      offset.add(1.0f, 1.0f);
        multiplier = new Vector2f(
            offset.x,
            offset.y
        );
    }


    if (KeyListener.isKeyPressed(HotKeys.get().EditorMoveUp) && debounce < 0) {
      debounce = inputDelay;
      for (Actor actor : activeActors) {
        actor.getTransform().position.y += GridLines_Config.SIZE * multiplier.y;
      }
        return true;
    } else if (KeyListener.isKeyPressed(HotKeys.get().EditorMoveLeft) && debounce < 0) {
      debounce = inputDelay;
      for (Actor actor : activeActors) {
        actor.getTransform().position.x -= GridLines_Config.SIZE * multiplier.x;
      }
        return true;
    } else if (KeyListener.isKeyPressed(HotKeys.get().EditorMoveRight) && debounce < 0) {
      debounce = inputDelay;
      for (Actor actor : activeActors) {
        actor.getTransform().position.x += GridLines_Config.SIZE * multiplier.x;
      }
        return true;
    } else if (KeyListener.isKeyPressed(HotKeys.get().EditorMoveDown) && debounce < 0) {
      debounce = inputDelay;
      for (Actor actor : activeActors) {
        actor.getTransform().position.y -= GridLines_Config.SIZE * multiplier.y;
      }
        return true;
    }
    return false;
  }

  public Vector2f min(List<Actor> actors)
  {
    Vector2f min = new Vector2f(actors.get(0).getTransform().position.x, actors.get(0).getTransform().position.y);
    for (Actor actor : actors) {
      min.x = Math.min(min.x , actor.getTransform().position.x);
      min.y = Math.min(min.y , actor.getTransform().position.y);
    }
    return min;
  }

    public Vector2f max(List<Actor> actors)
    {
        Vector2f max = new Vector2f(actors.get(0).getTransform().position.x, actors.get(0).getTransform().position.y);
        for (Actor actor : actors) {
        max.x = Math.max(max.x , actor.getTransform().position.x);
        max.y = Math.max(max.y , actor.getTransform().position.y);
        }
        return max;
    }
}

