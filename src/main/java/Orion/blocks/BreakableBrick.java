package Orion.blocks;

import Burst.Engine.Source.Runtime.Actor.PlayerController;

public class BreakableBrick extends Block {

    @Override
    void playerHit(PlayerController playerController) {
        if (!playerController.isSmall()) {
//            AssetManager.getSound("assets/sounds/break_block.ogg").play();
            gameObject.destroy();
        }
    }
}
