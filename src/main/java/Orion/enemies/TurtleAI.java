package Orion.enemies;

import Burst.Engine.Source.Core.Viewport;
import Burst.Engine.Source.Core.Actor;
import Burst.Engine.Source.Core.UI.Window;
import Burst.Engine.Source.Core.Component;
import Orion.abilities.Fireball;
import Burst.Engine.Source.Runtime.Animation.StateMachine;
import Burst.Engine.Source.Runtime.Actor.PlayerController;
import org.jbox2d.dynamics.contacts.Contact;
import org.joml.Vector2f;
import Burst.Engine.Source.Core.Physics.Physics2D;
import Burst.Engine.Source.Core.Physics.Components.Rigidbody2D;

public class TurtleAI extends Component {
    private transient boolean goingRight = false;
    private transient Rigidbody2D rb;
    private transient float walkSpeed = 0.6f;
    private transient Vector2f velocity = new Vector2f();
    private transient Vector2f acceleration = new Vector2f();
    private transient Vector2f terminalVelocity = new Vector2f(2.1f, 3.1f);
    private transient boolean onGround = false;
    private transient boolean isDead = false;
    private transient boolean isMoving = false;
    private transient StateMachine stateMachine;
    private float movingDebounce = 0.32f;

    @Override
    public void start() {
        this.stateMachine = this.actor.getComponent(StateMachine.class);
        this.rb = actor.getComponent(Rigidbody2D.class);
        this.acceleration.y = Window.getPhysics().getGravity().y * 0.7f;
    }

    @Override
    public void update(float dt) {
        movingDebounce -= dt;
        Viewport viewport = Window.getScene().getCamera();
        if (this.actor.transform.position.x >
                viewport.position.x + viewport.getProjectionSize().x * viewport.getZoom()) {
            return;
        }

        if (!isDead || isMoving) {
            if (goingRight) {
                actor.transform.scale.x = -0.25f;
                velocity.x = walkSpeed;
                acceleration.x = 0;
            } else {
                actor.transform.scale.x = 0.25f;
                velocity.x = -walkSpeed;
                acceleration.x = 0;
            }
        } else {
            velocity.x = 0;
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

        if (this.actor.transform.position.x <
                Window.getScene().getCamera().position.x - 0.5f) {// ||
                //this.gameObject.transform.position.y < 0.0f) {
            this.actor.destroy();
        }
    }

    public void checkOnGround() {
        float innerPlayerWidth = 0.25f * 0.7f;
        float yVal = -0.2f;
        onGround = Physics2D.checkOnGround(this.actor, innerPlayerWidth, yVal);
    }

    public void stomp() {
        this.isDead = true;
        this.isMoving = false;
        this.velocity.zero();
        this.rb.setVelocity(this.velocity);
        this.rb.setAngularVelocity(0.0f);
        this.rb.setGravityScale(0.0f);
        this.stateMachine.trigger("squashMe");
//        AssetManager.getSound("assets/sounds/bump.ogg").play();
    }

    @Override
    public void preSolve(Actor obj, Contact contact, Vector2f contactNormal) {
        GoombaAI goomba = obj.getComponent(GoombaAI.class);
        if (isDead && isMoving && goomba != null) {
            goomba.stomp();
            contact.setEnabled(false);
//            AssetManager.getSound("assets/sounds/kick.ogg").play();
        }

        PlayerController playerController = obj.getComponent(PlayerController.class);
        if (playerController != null) {
            if (!isDead && !playerController.isDead() &&
                    !playerController.isHurtInvincible() &&
                    contactNormal.y > 0.58f) {
                playerController.enemyBounce();
                stomp();
                walkSpeed *= 3.0f;
            } else if (movingDebounce < 0 && !playerController.isDead() &&
                    !playerController.isHurtInvincible() &&
                    (isMoving || !isDead) && contactNormal.y < 0.58f) {
                playerController.die();
                if (!playerController.isDead()) {
                    contact.setEnabled(false);
                }
            } else if (!playerController.isDead() && !playerController.isHurtInvincible()) {
                if (isDead && contactNormal.y > 0.58f) {
                    playerController.enemyBounce();
                    isMoving = !isMoving;
                    goingRight = contactNormal.x < 0;
                } else if (isDead && !isMoving) {
                    isMoving = true;
                    goingRight = contactNormal.x < 0;
                    movingDebounce = 0.32f;
                }
            } else if (!playerController.isDead() && playerController.isHurtInvincible()) {
                contact.setEnabled(false);
            }
        } else if (Math.abs(contactNormal.y) < 0.1f && !obj.isDead() && obj.getComponent(MushroomAI.class) == null) {
            goingRight = contactNormal.x < 0;
            if (isMoving && isDead) {
//                AssetManager.getSound("assets/sounds/bump.ogg").play();
            }
        }

        if (obj.getComponent(Fireball.class) != null) {
            if (!isDead) {
                walkSpeed *= 3.0f;
                stomp();
            } else {
                isMoving = !isMoving;
                goingRight = contactNormal.x < 0;
            }
            obj.getComponent(Fireball.class).disappear();
            contact.setEnabled(false);
        }
    }
}
