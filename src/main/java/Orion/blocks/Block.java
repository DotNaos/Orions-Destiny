package Orion.blocks;

import Burst.Engine.Source.Core.Actor.Actor;
import Burst.Engine.Source.Core.Component;
import Burst.Engine.Source.Core.Actor.PlayerController;
import org.jbox2d.dynamics.contacts.Contact;
import org.joml.Vector2f;

public abstract class Block extends Component {
    private transient boolean bopGoingUp = true;
    private transient boolean doBopAnimation = false;
    private transient Vector2f bopStart;
    private transient Vector2f topBopLocation;
    private transient boolean active = true;

    public float bopSpeed = 0.4f;

    @Override
    public void start() {
        this.bopStart = new Vector2f(this.actor.transform.position);
        this.topBopLocation = new Vector2f(bopStart).add(0.0f, 0.02f);
    }

    @Override
    public void update(float dt) {
        if (doBopAnimation) {
            if (bopGoingUp) {
                if (this.actor.transform.position.y < topBopLocation.y) {
                    this.actor.transform.position.y += bopSpeed * dt;
                } else {
                    bopGoingUp = false;
                }
            } else {
                if (this.actor.transform.position.y > bopStart.y) {
                    this.actor.transform.position.y -= bopSpeed * dt;
                } else {
                    this.actor.transform.position.y = this.bopStart.y;
                    bopGoingUp = true;
                    doBopAnimation = false;
                }
            }
        }
    }

    @Override
    public void beginCollision(Actor obj, Contact contact, Vector2f contactNormal) {
        PlayerController playerController = obj.getComponent(PlayerController.class);
        if (active && playerController != null && contactNormal.y < -0.8f) {
            doBopAnimation = true;
//            AssetManager.getSound("assets/sounds/bump.ogg").play();
            playerHit(playerController);
        }
    }

    public void setInactive() { this.active = false; }

    abstract void playerHit(PlayerController playerController);
}
