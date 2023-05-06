package Orion.items;

import Burst.Engine.Source.Core.Actor;
import Burst.Engine.Source.Core.Component;
import Burst.Engine.Source.Runtime.Actor.PlayerController;
import org.jbox2d.dynamics.contacts.Contact;
import org.joml.Vector2f;

public class Coin extends Component {
    private Vector2f topY;
    private float coinSpeed = 1.4f;
    private transient boolean playAnim = false;

    @Override
    public void start() {
        topY = new Vector2f(this.actor.transform.position.y).add(0, 0.5f);
    }

    @Override
    public void update(float dt) {
        if (playAnim) {
            if (this.actor.transform.position.y < topY.y) {
                this.actor.transform.position.y += dt * coinSpeed;
                this.actor.transform.scale.x -= (0.5f * dt) % -1.0f;
            } else {
                actor.destroy();
            }
        }
    }

    @Override
    public void preSolve(Actor obj, Contact contact, Vector2f contactNormal) {
        if (obj.getComponent(PlayerController.class) != null) {
//            AssetManager.getSound("assets/sounds/coin.ogg").play();
            playAnim = true;
            contact.setEnabled(false);
        }
    }
}
