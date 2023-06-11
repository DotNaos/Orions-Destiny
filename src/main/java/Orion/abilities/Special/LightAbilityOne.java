package Orion.abilities.Special;

import Orion.abilities.Ability;
import Orion.abilities.Special.SpecialAbility;
/**
 * @author Dominik Abler
 */
public class LightAbilityOne extends SpecialAbility {
    //Ultimate Ability die über die ganze map(sichtbaren bereich) geht?
    private float damage;
    private float knockback;
    private float animationLength;
    private boolean collision;

    public float getDamage() {
        return this.damage;
    }

    public float getKnockback() {
        return this.knockback;
    }

    public float getAnimationLength() {
        return this.animationLength;
    }

    public boolean getCollision() {
        return this.collision;
    }

    /**
     *
     */
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
