package Orion.blocks;

import Burst.Engine.Source.Core.Actor;
import Burst.Engine.Source.Core.util.Prefabs;
import Burst.Engine.Source.Core.UI.Window;
import Burst.Engine.Source.Runtime.Animation.StateMachine;
import Burst.Engine.Source.Runtime.Actor.PlayerController;

public class QuestionBlock extends Block {
    private enum BlockType {
        Coin,
        Powerup,
        Invincibility
    }

    public BlockType blockType = BlockType.Coin;

    @Override
    void playerHit(PlayerController playerController) {
        switch(blockType) {
            case Coin:
                doCoin(playerController);
                break;
            case Powerup:
                doPowerup(playerController);
                break;
            case Invincibility:
                doInvincibility(playerController);
                break;
        }

        StateMachine stateMachine = actor.getComponent(StateMachine.class);
        if (stateMachine != null) {
            stateMachine.trigger("setInactive");
            this.setInactive();
        }
    }

    private void doInvincibility(PlayerController playerController) {
    }

    private void doPowerup(PlayerController playerController) {
        if (playerController.isSmall()) {
            spawnMushroom();
        } else {
            spawnFlower();
        }
    }

    private void doCoin(PlayerController playerController) {
        Actor coin = Prefabs.generateBlockCoin();
        coin.transform.position.set(this.actor.transform.position);
        coin.transform.position.y += 0.25f;

        Window.getScene().getGame().addActor(coin);
    }

    private void spawnMushroom() {
        Actor mushroom = Prefabs.generateMushroom();
        mushroom.transform.position.set(actor.transform.position);
        mushroom.transform.position.y += 0.25f;

        Window.getScene().getGame().addActor(mushroom);
    }

    private void spawnFlower() {
        Actor flower = Prefabs.generateFlower();
        flower.transform.position.set(actor.transform.position);
        flower.transform.position.y += 0.25f;
        
        Window.getScene().getGame().addActor(flower);
    }
}
