package Burst.Engine.Source.Core.Actor;

import org.jbox2d.dynamics.contacts.Contact;
import org.joml.Vector2f;

import Burst.Engine.Config.HotKeys;
import Burst.Engine.Source.Core.Input.KeyListener;
import Burst.Engine.Source.Core.Physics.Components.Rigidbody2D;
import Burst.Engine.Source.Game.Animation.StateMachine;
import Orion.blocks.Ground;

public class PlayerController extends ActorComponent {

    public float walkSpeed = 1.9f;
    public float jumpBoost = 1.0f;
    public float jumpImpulse = 3.0f;
    public float slowDownForce = 0.05f;
    public Vector2f terminalVelocity = new Vector2f(2.1f, 3.1f);
    public transient boolean onGround = false;
    private transient Rigidbody2D rb;
    private transient StateMachine stateMachine;
    private transient float playerWidth = 0.25f;
    private transient Vector2f acceleration = new Vector2f();
    private transient Vector2f velocity = new Vector2f();
    private transient boolean isDead = false;

    public PlayerController(Actor actor) {
        super(actor);
    }

    @Override
    public void start() {
    }

    @Override
    public void update(float dt) {
        if (KeyListener.isKeyPressed(HotKeys.get().PlayerMoveRight)) {
            this.actor.getTransform().size.x = playerWidth;
            this.acceleration.x = walkSpeed;

            if (this.velocity.x < 0) {
                this.stateMachine.trigger("switchDirection");
                this.velocity.x += slowDownForce;
            } else {
                this.stateMachine.trigger("startRunning");
            }
        } else if (KeyListener.isKeyPressed(HotKeys.get().PlayerMoveLeft)) {
            this.actor.getTransform().size.x = -playerWidth;
            this.acceleration.x = -walkSpeed;

            if (this.velocity.x > 0) {
                this.stateMachine.trigger("switchDirection");
                this.velocity.x -= slowDownForce;
            } else {
                this.stateMachine.trigger("startRunning");
            }
        } else {
            this.acceleration.x = 0;
            if (this.velocity.x > 0) {
                this.velocity.x = Math.max(0, this.velocity.x - slowDownForce);
            } else if (this.velocity.x < 0) {
                this.velocity.x = Math.min(0, this.velocity.x + slowDownForce);
            }

            if (this.velocity.x == 0) {
                this.stateMachine.trigger("stopRunning");
            }
        }

        this.velocity.x += this.acceleration.x * dt;
        this.velocity.y += this.acceleration.y * dt;
        this.velocity.x = Math.max(Math.min(this.velocity.x, this.terminalVelocity.x), -this.terminalVelocity.x);
        this.velocity.y = Math.max(Math.min(this.velocity.y, this.terminalVelocity.y), -this.terminalVelocity.y);
        this.rb.setVelocity(this.velocity);
        this.rb.setAngularVelocity(0);

        if (!onGround) {
            stateMachine.trigger("jump");
        } else {
            stateMachine.trigger("stopJumping");
        }
    }

    public void move(Vector2f amount) {
        this.actor.getTransform().position.add(amount);
        this.rb.setPosition(this.actor.getTransform().position);
    }

    public void setPosition(Vector2f newPos) {
        this.actor.getTransform().position.set(newPos);
        this.rb.setPosition(newPos);
    }


    @Override
    public void beginCollision(Actor collidinactorbject, Contact contact, Vector2f contactNormal) {
        if (isDead) return;

        if (collidinactorbject.getComponent(Ground.class) != null) {
            if (Math.abs(contactNormal.x) > 0.8f) {
                this.velocity.x = 0;
            } else if (contactNormal.y > 0.8f) {
                this.velocity.y = 0;
                this.acceleration.y = 0;
            }
        }
    }

}
