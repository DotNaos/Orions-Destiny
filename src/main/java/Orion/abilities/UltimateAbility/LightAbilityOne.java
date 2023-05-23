package Orion.abilities.UltimateAbility;

public class LightAbilityOne {
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
}
