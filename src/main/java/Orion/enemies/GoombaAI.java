package Orion.enemies;

import Burst.Engine.Source.Core.Viewport;
import Burst.Engine.Source.Core.Actor;
import Burst.Engine.Source.Core.UI.Window;
import Burst.Engine.Source.Core.Component;
import Burst.Engine.Source.Runtime.Animation.StateMachine;
import Burst.Engine.Source.Runtime.Actor.PlayerController;
import Orion.abilities.Fireball;
import org.jbox2d.dynamics.contacts.Contact;
import org.joml.Vector2f;
import Burst.Engine.Source.Core.Physics.Physics2D;
import Burst.Engine.Source.Core.Physics.Components.Rigidbody2D;

public class GoombaAI extends Component {

    private transient boolean goingRight = false;
    private transient Rigidbody2D rb;
    private transient float walkSpeed = 0.6f;
    private transient Vector2f velocity = new Vector2f();
    private transient Vector2f acceleration = new Vector2f();
    private transient Vector2f terminalVelocity = new Vector2f();
    private transient boolean onGround = false;
    private transient boolean isDead = false;
    private transient float timeToKill = 0.5f;
    private transient StateMachine stateMachine;

    @Override
    public void start() {
        this.stateMachine = actor.getComponent(StateMachine.class);
        this.rb = actor.getComponent(Rigidbody2D.class);
        this.acceleration.y = Window.getPhysics().getGravity().y * 0.7f;
    }

    @Override
    public void update(float dt) {
        Viewport viewport = Window.getScene().getCamera();
        if (this.actor.transform.position.x >
                viewport.position.x + viewport.getProjectionSize().x * viewport.getZoom()) {
            return;
        }

        if (isDead) {
            timeToKill -= dt;
            if (timeToKill <= 0) {
                this.actor.destroy();
            }
            this.rb.setVelocity(new Vector2f());
            return;
        }

        if (goingRight) {
            velocity.x = walkSpeed;
        } else {
            velocity.x = -walkSpeed;
        }

        checkOnGround();
        if (onGround) {
            this.acceleration.y = 0;
            this.velocity.y = 0;
        } else {
            this.acceleration.y = Window.getPhysics().getGravity().y * 0.7f;
        }

        this.velocity.y += this.acceleration.y * dt;
        this.velocity.y = Math.max(Math.min(this.velocity.y, this.terminalVelocity.y), -terminalVelocity.y);
        this.rb.setVelocity(velocity);
        if (this.actor.transform.position.x < Window.getScene().getCamera().position.x - 0.5f) {
            this.actor.destroy();
        }
    }

    public void checkOnGround() {
        float innerPlayerWidth = 0.25f * 0.7f;
        float yVal = -0.14f;
        onGround = Physics2D.checkOnGround(this.actor, innerPlayerWidth, yVal);
    }

    @Override
    public void preSolve(Actor obj, Contact contact, Vector2f contactNormal) {
        if (isDead) {
            return;
        }

        PlayerController playerController = obj.getComponent(PlayerController.class);
        if (playerController != null) {
            if (!playerController.isDead() && !playerController.isHurtInvincible() &&
                    contactNormal.y > 0.58f) {
                playerController.enemyBounce();
                stomp();
            } else if (!playerController.isDead() && !playerController.isInvincible()) {
                playerController.die();
                if (!playerController.isDead()) {
                    contact.setEnabled(false);
                }
            } else if (!playerController.isDead() && playerController.isInvincible()) {
                contact.setEnabled(false);
            }
        } else if (Math.abs(contactNormal.y) < 0.1f) {
            goingRight = contactNormal.x < 0;
        }

        if (obj.getComponent(Fireball.class) != null) {
            stomp();
            obj.getComponent(Fireball.class).disappear();
        }
    }

    public void stomp() {
        stomp(true);
    }

    public void stomp(boolean playSound) {
        this.isDead = true;
        this.velocity.zero();
        this.rb.setVelocity(new Vector2f());
        this.rb.setAngularVelocity(0.0f);
        this.rb.setGravityScale(0.0f);
        this.stateMachine.trigger("squashMe");
        this.rb.setIsSensor();
        if (playSound) {
//            AssetManager.getSound("assets/sounds/bump.ogg").play();
        }
    }
}
