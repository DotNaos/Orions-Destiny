package Orion.abilities.Special;

import Orion.abilities.Ability;

public class IceAbilityOne extends Ability{

    private float damage;
    private float speedSlow;
    private float animationLength;
    private boolean collision;


    public float getDamage() {
        return this.damage;
    }

    public float getSpeedSlow() {
        return this.speedSlow;
    }


    public float getAnimationLength() {
        return this.animationLength;
    }

    public boolean isCollision() {
        return this.collision;
    }

        @Override
        public void activate() {

        }

    /**
     *
     */
    @Override
    public void init() {

    }
}
