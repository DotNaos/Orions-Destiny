package Orion.abilities;

public class LightAbilityOne {
    //Ultimate Ability die über die ganze map(sichtbaren bereich) geht?
    private float damage;
    private float knockback;
    private float animationLength;
    private boolean collision;

    public float getDamage(float damage){
        damage=10;
        return damage;
    }
    public float getKnockback(float knockback){
        knockback = 2.5f;
        return knockback;
    }
    public float getAnimationLength(float animationLength){
        animationLength=2;
        return animationLength;
    }
    public boolean getCollision(boolean collision){
        collision=false;
        return collision;
    }
}
