package Orion.abilities.UltimateAbility;

import Orion.abilities.Ability;

public class PoisonAbility extends Ability {
    private float damage;
    private float duration;
    private float dmgOverTime;
    public float animationLength;
    public int range;


    public float getDamage() {
        damage = 15;
        return damage;
    }

    public float getDuration() {
        duration = 5;
        return duration;
    }

    public float getDmgOverTime() {
        dmgOverTime = 5;
        return dmgOverTime;
    }

    public float getAnimationLength() {
        animationLength = 1;
        return animationLength;
    }

    public int getRange() {
        range = 4;
        return range;
    }

    /**
     *
     */
    @Override
    public void activate() {

    }
}

