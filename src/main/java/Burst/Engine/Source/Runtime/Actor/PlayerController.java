package Burst.Engine.Source.Runtime.Actor;

import Burst.Engine.Source.Core.Actor;
import Burst.Engine.Source.Core.Graphics.Input.KeyListener;
import Burst.Engine.Source.Core.util.Prefabs;
import Burst.Engine.Source.Core.UI.Window;
import Burst.Engine.Source.Core.Component;
import Burst.Engine.Source.Runtime.Animation.StateMachine;
import Orion.blocks.Ground;
import Burst.Engine.Source.Core.Graphics.Render.SpriteRenderer;
import Orion.abilities.Fireball;
import org.jbox2d.dynamics.contacts.Contact;
import org.joml.Vector2f;
import org.joml.Vector4f;
import Burst.Engine.Source.Core.Physics.Physics2D;
import Burst.Engine.Source.Core.Physics.Components.PillboxCollider;
import Burst.Engine.Source.Core.Physics.Components.Rigidbody2D;
import Burst.Engine.Source.Core.Physics.Enums.BodyType;
import Burst.Engine.Source.Core.Scene.EditorSceneInitializer;
import Burst.Engine.Source.Core.Scene.GameInitializer;

import static org.lwjgl.glfw.GLFW.*;

public class PlayerController extends Component {

    public float walkSpeed = 1.9f;
    public float jumpBoost = 1.0f;
    public float jumpImpulse = 3.0f;
    public float slowDownForce = 0.05f;
    public Vector2f terminalVelocity = new Vector2f(2.1f, 3.1f);
    public transient boolean onGround = false;
    private transient float groundDebounce = 0.0f;
    private transient float groundDebounceTime = 0.1f;
    private transient Rigidbody2D rb;
    private transient StateMachine stateMachine;
    private transient float bigJumpBoostFactor = 1.05f;
    private transient float playerWidth = 0.25f;
    private transient int jumpTime = 0;
    private transient Vector2f acceleration = new Vector2f();
    private transient Vector2f velocity = new Vector2f();
    private transient boolean isDead = false;
    private transient int enemyBounce = 0;

    private transient float hurtInvincibilityTimeLeft = 0;
    private transient float hurtInvincibilityTime = 1.4f;
    private transient float deadMaxHeight = 0;
    private transient float deadMinHeight = 0;
    private transient boolean deadGoingUp = true;
    private transient float blinkTime = 0.0f;
    private transient SpriteRenderer spr;

    private transient boolean playWinAnimation = false;
    private transient float walkTime = 2.2f;

    @Override
    public void start() {
        this.spr = actor.getComponent(SpriteRenderer.class);
        this.rb = actor.getComponent(Rigidbody2D.class);
        this.stateMachine = actor.getComponent(StateMachine.class);
        this.rb.setGravityScale(0.0f);
    }

    @Override
    public void update(float dt) {


        if (KeyListener.isKeyPressed(GLFW_KEY_RIGHT) || KeyListener.isKeyPressed(GLFW_KEY_D)) {
            this.actor.transform.scale.x = playerWidth;
            this.acceleration.x = walkSpeed;

            if (this.velocity.x < 0) {
                this.stateMachine.trigger("switchDirection");
                this.velocity.x += slowDownForce;
            } else {
                this.stateMachine.trigger("startRunning");
            }
        } else if (KeyListener.isKeyPressed(GLFW_KEY_LEFT) || KeyListener.isKeyPressed(GLFW_KEY_A)) {
            this.actor.transform.scale.x = -playerWidth;
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

        if (KeyListener.keyBeginPress(GLFW_KEY_E)) {
            Vector2f position = new Vector2f(this.actor.transform.position)
                    .add(this.actor.transform.scale.x > 0
                    ? new Vector2f(0.26f, 0)
                    : new Vector2f(-0.26f, 0));
            Actor fireball = Prefabs.generateFireball(position);
            fireball.getComponent(Fireball.class).goingRight =
                    this.actor.transform.scale.x > 0;
            Window.getScene().getGame().addActor(fireball);
        }

        if (KeyListener.isKeyPressed(GLFW_KEY_SPACE) && (jumpTime > 0 || onGround || groundDebounce > 0)) {
            if ((onGround || groundDebounce > 0) && jumpTime == 0) {
//                AssetManager.getSound("assets/sounds/jump-small.ogg").play();
                jumpTime = 28;
                this.velocity.y = jumpImpulse;
            } else if (jumpTime > 0) {
                jumpTime--;
                this.velocity.y = ((jumpTime / 2.2f) * jumpBoost);
            } else {
                this.velocity.y = 0;
            }
            groundDebounce = 0;
        } else if (enemyBounce > 0) {
            enemyBounce--;
            this.velocity.y = ((enemyBounce / 2.2f) * jumpBoost);
        }else if (!onGround) {
            if (this.jumpTime > 0) {
                this.velocity.y *= 0.35f;
                this.jumpTime = 0;
            }
            groundDebounce -= dt;
            this.acceleration.y = Window.getPhysics().getGravity().y * 0.7f;
        } else {
            this.velocity.y = 0;
            this.acceleration.y = 0;
            groundDebounce = groundDebounceTime;
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
        this.actor.transform.position.add(amount);
        this.rb.setPosition(this.actor.transform.position);
    }

    public void setPosition(Vector2f newPos) {
        this.actor.transform.position.set(newPos);
        this.rb.setPosition(newPos);
    }



    @Override
    public void beginCollision(Actor collidingObject, Contact contact, Vector2f contactNormal) {
        if (isDead) return;

        if (collidingObject.getComponent(Ground.class) != null) {
            if (Math.abs(contactNormal.x) > 0.8f) {
                this.velocity.x = 0;
            } else if (contactNormal.y > 0.8f) {
                this.velocity.y = 0;
                this.acceleration.y = 0;
                this.jumpTime = 0;
            }
        }
    }

}
