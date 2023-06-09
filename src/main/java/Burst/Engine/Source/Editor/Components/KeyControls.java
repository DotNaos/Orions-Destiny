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

import java.util.ArrayList;
import java.util.List;

public class KeyControls extends Component {
  protected float debounceTime = 0.2f;
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

    float multiplier = KeyListener.isKeyPressed(HotKeys.get().Modifier_EditorSlow) ? 0.1f : 1.0f;


    if (duplicateActor(propertiesPanel, activeActors, activeActor)) ;
    else if (duplicateActors(propertiesPanel, activeActors, activeActor)) ;
    else if (deleteActors(propertiesPanel, activeActors, activeActor)) ;
    else if (moveZIndex(propertiesPanel, activeActors, activeActor)) ;
    else if (move(propertiesPanel, activeActors, activeActor, multiplier)) ;

  }


  private boolean duplicateActor(PropertiesPanel propertiesPanel, List<Actor> activeActors, Actor activeActor) {
    if (KeyListener.isKeyPressed(HotKeys.get().Shortcut_EditorDuplicate.first) && KeyListener.keyBeginPress(HotKeys.get().Shortcut_EditorDuplicate.second) && activeActor != null) {
      Actor newActor = activeActor.copy();

      if (Window.getScene().getEditor() == null) return false;

      newActor.getTransform().position.add(GridLines_Config.SIZE, 0.0f);

      Window.getScene().getEditor().addActor(newActor);
      propertiesPanel.setActiveActor(newActor);

      if (newActor.getComponent(StateMachine.class) != null) {
        newActor.getComponent(StateMachine.class).refreshTextures();
      }
      return true;
    }
    return false;
  }

  private boolean duplicateActors(PropertiesPanel propertiesPanel, List<Actor> activeActors, Actor activeActor) {
    if (KeyListener.isKeyPressed(HotKeys.get().Shortcut_EditorDuplicate.first) && KeyListener.keyBeginPress(HotKeys.get().Shortcut_EditorDuplicate.second) && activeActors.size() > 1) {
      List<Actor> actors = new ArrayList<>(activeActors);
      propertiesPanel.clearSelected();

      for (Actor actor : actors) {
        Actor copy = actor.copy();
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
    if (KeyListener.keyBeginPress(HotKeys.get().EditorDelete) && activeActor != null) {
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
      debounce = debounceTime;
      for (Actor actor : activeActors) {
        actor.getTransform().zIndex = actor.getTransform().zIndex - 1;
      }

      return true;
    } else if (KeyListener.isKeyPressed(HotKeys.get().EditorMoveZToFront) && debounce < 0) {
      debounce = debounceTime;
      for (Actor actor : activeActors) {
        actor.getTransform().zIndex = actor.getTransform().zIndex + 1;
      }
      return true;
    }
    return false;
  }

  private boolean move(PropertiesPanel propertiesPanel, List<Actor> activeActors, Actor activeActor, float multiplier) {
    if (KeyListener.isKeyPressed(HotKeys.get().EditorMoveUp) && debounce < 0) {
      debounce = debounceTime;
      for (Actor actor : activeActors) {
        actor.getTransform().position.y += GridLines_Config.SIZE * multiplier;
      }
        return true;
    } else if (KeyListener.isKeyPressed(HotKeys.get().EditorMoveLeft) && debounce < 0) {
      debounce = debounceTime;
      for (Actor actor : activeActors) {
        actor.getTransform().position.x -= GridLines_Config.SIZE * multiplier;
      }
        return true;
    } else if (KeyListener.isKeyPressed(HotKeys.get().EditorMoveRight) && debounce < 0) {
      debounce = debounceTime;
      for (Actor actor : activeActors) {
        actor.getTransform().position.x += GridLines_Config.SIZE * multiplier;
      }
        return true;
    } else if (KeyListener.isKeyPressed(HotKeys.get().EditorMoveDown) && debounce < 0) {
      debounce = debounceTime;
      for (Actor actor : activeActors) {
        actor.getTransform().position.y -= GridLines_Config.SIZE * multiplier;
      }
        return true;
    }
    return false;
  }
}

