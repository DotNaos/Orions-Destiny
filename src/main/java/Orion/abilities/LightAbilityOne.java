package Orion.abilities;

public class LightAbilityOne {
    private float damage;
    private float knockback;
    private float travellingSpeed;
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

}
