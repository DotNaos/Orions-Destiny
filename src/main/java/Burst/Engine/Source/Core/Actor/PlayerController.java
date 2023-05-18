package Burst.Engine.Source.Core.Actor;

import Burst.Engine.Source.Core.Component;
import Burst.Engine.Source.Game.Animation.StateMachine;
import Burst.Engine.Source.Core.Input.KeyListener;
import Burst.Engine.Source.Core.Physics.Components.Rigidbody2D;
import Orion.blocks.Ground;
import org.jbox2d.dynamics.contacts.Contact;
import org.joml.Vector3f;

import static org.lwjgl.glfw.GLFW.*;

public class PlayerController extends Component {

    public float walkSpeed = 1.9f;
    public float jumpBoost = 1.0f;
    public float jumpImpulse = 3.0f;
    public float slowDownForce = 0.05f;
    public Vector3f terminalVelocity = new Vector3f(2.1f, 3.1f ,0);
    public transient boolean onGround = false;
    private transient Rigidbody2D rb;
    private transient StateMachine stateMachine;
    private transient float playerWidth = 0.25f;
    private transient Vector3f acceleration = new Vector3f();
    private transient Vector3f velocity = new Vector3f();
    private transient boolean isDead = false;

    public PlayerController(Actor actor) {
        super(actor);
    }

    @Override
    public void start() {
    }

    @Override
    public void update(float dt) {
        if (KeyListener.isKeyPressed(GLFW_KEY_RIGHT) || KeyListener.isKeyPressed(GLFW_KEY_D)) {
            this.actor.transform.size.x = playerWidth;
            this.acceleration.x = walkSpeed;

            if (this.velocity.x < 0) {
                this.stateMachine.trigger("switchDirection");
                this.velocity.x += slowDownForce;
            } else {
                this.stateMachine.trigger("startRunning");
            }
        } else if (KeyListener.isKeyPressed(GLFW_KEY_LEFT) || KeyListener.isKeyPressed(GLFW_KEY_A)) {
            this.actor.transform.size.x = -playerWidth;
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

    public void move(Vector3f amount) {
        this.actor.transform.position.add(amount);
        this.rb.setPosition(this.actor.transform.position);
    }

    public void setPosition(Vector3f newPos) {
        this.actor.transform.position.set(newPos);
        this.rb.setPosition(newPos);
    }


    @Override
    public void beginCollision(Actor collidinactorbject, Contact contact, Vector3f contactNormal) {
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
