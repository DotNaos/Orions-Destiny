package Orion.blocks;

import Burst.Engine.Source.Core.Actor.Actor;
import Burst.Engine.Source.Core.Actor.ActorComponent;
import Burst.Engine.Source.Core.Actor.PlayerController;
import Burst.Engine.Source.Core.Component;
import org.jbox2d.dynamics.contacts.Contact;
import org.joml.Vector3f;

public abstract class Block extends ActorComponent {
    public float bopSpeed = 0.4f;
    private transient boolean bopactoringUp = true;
    private transient boolean doBopAnimation = false;
    private transient Vector3f bopStart;
    private transient Vector3f topBopLocation;
    private transient boolean active = true;

    public Block(Actor actor) {
        super(actor);
    }

    @Override
    public void start() {
        this.bopStart = new Vector3f(this.actor.transform.position);
        this.topBopLocation = new Vector3f(bopStart).add(0.0f, 0.02f, 0);
    }

    @Override
    public void update(float dt) {
        if (doBopAnimation) {
            if (bopactoringUp) {
                if (this.actor.transform.position.y < topBopLocation.y) {
                    this.actor.transform.position.y += bopSpeed * dt;
                } else {
                    bopactoringUp = false;
                }
            } else {
                if (this.actor.transform.position.y > bopStart.y) {
                    this.actor.transform.position.y -= bopSpeed * dt;
                } else {
                    this.actor.transform.position.y = this.bopStart.y;
                    bopactoringUp = true;
                    doBopAnimation = false;
                }
            }
        }
    }

    @Override
    public void beginCollision(Actor obj, Contact contact, Vector3f contactNormal) {
        PlayerController playerController = obj.getComponent(PlayerController.class);
        if (active && playerController != null && contactNormal.y < -0.8f) {
            doBopAnimation = true;
//            AssetManager.getSound("assets/sounds/bump.ogg").play();
            playerHit(playerController);
        }
    }

    public void setInactive() {
        this.active = false;
    }

    abstract void playerHit(PlayerController playerController);
}
