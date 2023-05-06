package Orion.blocks;

import Burst.Engine.Source.Core.Component;
import org.joml.Vector2f;

public class BlockCoin extends Component {
    private Vector2f topY;
    private float coinSpeed = 1.4f;

     @Override
    public void start() {
         topY = new Vector2f(this.actor.transform.position.y).add(0, 0.5f);
//         AssetManager.getSound("assets/sounds/coin.ogg").play();
     }

     @Override
    public void update(float dt) {
         if (this.actor.transform.position.y < topY.y) {
             this.actor.transform.position.y += dt * coinSpeed;
             this.actor.transform.scale.x -= (0.5f * dt) % -1.0f;
         } else {
             actor.destroy();
         }
     }
}
