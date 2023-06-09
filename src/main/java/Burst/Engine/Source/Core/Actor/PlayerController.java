package Burst.Engine.Source.Core.Actor;

import Burst.Engine.Config.Config;
import Burst.Engine.Config.Config.HotKeys;
import Burst.Engine.Source.Core.Input.KeyListener;
import Burst.Engine.Source.Core.Physics.Components.Rigidbody2D;
import Burst.Engine.Source.Game.Animation.StateMachine;
import Orion.blocks.Block;
import Orion.blocks.Ground;
import org.jbox2d.dynamics.contacts.Contact;
import org.joml.Vector2f;

import java.util.List;

/**
 * @author GamesWithGabe
 * A component that controls the player character in the game.
 * This component handles player movement, input, and physics interactions.
 */
public class PlayerController extends ActorComponent {

  public float walkSpeed = 1.9f;
  public float jumpBoost = 1.0f;
  public float jumpImpulse = 3.0f;
  public float slowDownForce = 0.05f;
  public Vector2f terminalVelocity = new Vector2f(2.1f, 3.1f);
  public transient boolean onGround = false;
  private transient Rigidbody2D rb;
  private transient StateMachine stateMachine;
  private transient Vector2f acceleration = new Vector2f();
  private transient Vector2f velocity = new Vector2f();
  private boolean isDead = false;

  public PlayerController() {
    super();
  }

  @Override
  public void init() {
    super.init();
    this.acceleration = new Vector2f();
    this.velocity = new Vector2f();

    if (this.actor.getComponent(Rigidbody2D.class) == null) {
      this.actor.addComponent(new Rigidbody2D());
      this.rb = this.actor.getComponent(Rigidbody2D.class);
    }

    if (this.actor.getComponent(StateMachine.class) != null) {
      this.stateMachine = this.actor.getComponent(StateMachine.class);
    }
  }

  @Override
  public void updateEditor(float dt) {
    super.updateEditor(dt);
  }

  @Override
  public void update(float dt) {
    super.update(dt);
    try {
      List<Integer> pressedKeys = KeyListener.getPressedKeys();
      if (pressedKeys.contains(Config.get(HotKeys.class).PlayerJump)) {
          if (onGround) {
          this.velocity.y = jumpImpulse;
          this.acceleration.y = jumpBoost;
          }
        System.out.println("Jumping");
      }
      if (moveRight());
      else if (moveLeft());
      else idle();

      this.velocity.x += this.acceleration.x * dt;
      this.velocity.y += this.acceleration.y * dt;
      this.velocity.x = Math.max(Math.min(this.velocity.x, this.terminalVelocity.x), -this.terminalVelocity.x);
      this.velocity.y = Math.max(Math.min(this.velocity.y, this.terminalVelocity.y), -this.terminalVelocity.y);
      this.rb.setVelocity(this.velocity);
      this.rb.setAngularVelocity(0);

      if (!onGround) {
        stateMachine.trigger("jump");
      } else {
//        stateMachine.trigger("stopJumping");
      }
    } catch (NullPointerException e) {
      init();
    }
  }

  private boolean moveRight() {
    if (KeyListener.isKeyPressed(Config.get(HotKeys.class).PlayerMoveRight)) {
      // When player is moving right, flip the sprite
      this.actor.getTransform().size.x = Math.abs(this.actor.getTransform().size.x);
      this.acceleration.x = walkSpeed;

      this.stateMachine.trigger("run");

      if (this.velocity.x < 0) {
        this.velocity.x += slowDownForce;
      }

      return true;
    }
    return false;
  }

  private boolean moveLeft()
  {
    if (KeyListener.isKeyPressed(Config.get(HotKeys.class).PlayerMoveLeft)) {
      // When player is moving left, flip the sprite
      this.actor.getTransform().size.x = -Math.abs(this.actor.getTransform().size.x);
      this.acceleration.x = -walkSpeed;

      this.stateMachine.trigger("run");

      if (this.velocity.x > 0) {
//          this.stateMachine.trigger("switchDirection");
        this.velocity.x -= slowDownForce;
      }
        return true;
    }
    return false;
  }

  private void idle()
  {
    this.acceleration.x = 0;
    if (this.velocity.x > 0) {
      this.velocity.x = Math.max(0, this.velocity.x - slowDownForce);
    } else if (this.velocity.x < 0) {
      this.velocity.x = Math.min(0, this.velocity.x + slowDownForce);
    }

    if (this.velocity.x == 0) {
      this.stateMachine.trigger("idle");
    }
  }

  public void jump() {
      if (onGround) {
      this.velocity.y = jumpImpulse;
      this.acceleration.y = -jumpBoost;
      }
  }

  public void move(Vector2f amount) {
    this.actor.getTransform().getPosition().add(amount);
    this.rb.setPosition(this.actor.getTransform().getPosition());
  }

  public void setPosition(Vector2f newPos) {
    this.actor.getTransform().getPosition().set(newPos);
    this.rb.setPosition(newPos);
  }


  @Override
  public void beginCollision(Actor collidinactorbject, Contact contact, Vector2f contactNormal) {
    if (isDead) return;

    if (collidinactorbject instanceof Block) {
      if (Math.abs(contactNormal.x) > 0.8f) {
        this.velocity.x = 0;
      } else if (contactNormal.y > 0.8f) {
        this.velocity.y = 0;
        this.acceleration.y = 0;
      }
    }
  }

}
