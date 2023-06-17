package Burst.Engine.Source.Game.Animation;

import Burst.Engine.Source.Core.Actor.ActorComponent;
import Burst.Engine.Source.Core.Assets.Graphics.Sprite;
import Burst.Engine.Source.Core.Render.SpriteRenderer;
import Burst.Engine.Source.Core.Util.DebugMessage;

import java.util.*;

/**
 * @author GamesWithGabe
 */
public class StateMachine extends ActorComponent {
  private transient Map<String, AnimationState> states;
  private transient AnimationState currentState;
  private transient AnimationState entryState;

  public StateMachine() {
    super();
  }

  @Override
  public void init()
  {
    super.init();
  }

  public StateMachine addState(AnimationState state) {
    return addState(state, false);
  }

  public StateMachine addState(AnimationState state, boolean isEntry) {
    if (this.states == null) {
      this.states = new HashMap<>();
    }
    if (isEntry || this.entryState == null) {
      this.entryState = state;
    }
    state.stateMachine = this;
    this.states.put(state.getName(), state);
    return this;
  }

  @Override
  public void update(float dt) {
      super.update(dt);
      if (this.states == null || this.states.isEmpty()) {
        DebugMessage.info("No states in state machine");
        return;
      }
      if (this.entryState == null) {
        this.entryState = this.states.values().stream().toList().get(0);
      }
      if (this.currentState == null) {
        this.currentState = this.entryState;
      }

      this.currentState.update(dt);
      this.render();
  }

  @Override
  public void updateEditor(float dt)
  {
    super.updateEditor(dt);
    this.update(dt);
  }

  public void trigger(String state)
  {
    if (this.states == null || this.states.isEmpty()) {
      DebugMessage.info("No states in state machine");
      return;
    }

    if (this.states.containsKey(state)) {
      if (this.currentState != null && this.currentState != this.states.get(state)) {
        this.currentState.reset();
      }
      this.currentState = this.states.get(state);
    } else {
      DebugMessage.error("State does not exist: " + state);
    }

  }

  public void toEntry()
  {
    if (this.states == null || this.states.isEmpty()) {
      DebugMessage.info("No states in state machine");
      return;
    }

    this.currentState = this.entryState;
  }

  public void render()
  {
    // get the SpriteRenderer of the actor
    SpriteRenderer spriteRenderer = this.actor.getComponent(SpriteRenderer.class);
    if (spriteRenderer == null) {
      DebugMessage.error("No SpriteRenderer attached to actor");
      return;
    }

    // get the current Frame
    Sprite currentAnimation = this.currentState.getFrame();

    // set the SpriteRenderer's sprite to the current frame
    spriteRenderer.setSprite(currentAnimation);
  }

  public void refreshSprite()
  {
    // Set the SpriteRenderer's sprite to the entry state's current frame
    SpriteRenderer spriteRenderer = this.actor.getComponent(SpriteRenderer.class);
    if (spriteRenderer == null) {
      DebugMessage.error("No SpriteRenderer attached to actor");
      return;
    }

    spriteRenderer.setSprite(this.entryState.getFrame(1));
  }
}
