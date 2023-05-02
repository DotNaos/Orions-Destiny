package components;

import Burst.GameObject;
import org.jbox2d.dynamics.contacts.Contact;
import org.joml.Vector2f;
import physics2d.components.Rigidbody2D;
import util.AssetManager;

public class Flower extends Component {
    private transient Rigidbody2D rb;

    @Override
    public void start() {
        this.rb = gameObject.getComponent(Rigidbody2D.class);
        AssetManager.getSound("assets/sounds/powerup_appears.ogg").play();
        this.rb.setIsSensor();
    }

    @Override
    public void beginCollision(GameObject obj, Contact contact, Vector2f contactNormal) {
        PlayerController playerController = obj.getComponent(PlayerController.class);
        if (playerController != null) {
            playerController.powerup();
            this.gameObject.destroy();
        }
    }
}
