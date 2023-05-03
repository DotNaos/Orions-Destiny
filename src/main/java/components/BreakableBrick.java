package components;

import util.AssetManager;

public class BreakableBrick extends Block {

    @Override
    void playerHit(PlayerController playerController) {
        if (!playerController.isSmall()) {
//            AssetManager.getSound("assets/sounds/break_block.ogg").play();
            gameObject.destroy();
        }
    }
}
