package Orion.abilities;

public class FireAbilityOne {
    private float damage;
    public float burnDamage;
    public float burnLength;
    private float travellingSpeed;
    private float animationLength;
    private boolean collision;

    public float getDamage(float damage){
        damage=35;
        return damage;
    }

    public float getTravellingSpeed(float travellingSpeed) {
        travellingSpeed= 3;
        return travellingSpeed;
    }
    public float getBurnLength(float burnLength){
        burnLength=4.5f;
        return burnLength;
    }

    public float getBurnDamage(float burnDamage) {
        burnDamage = 3;
        return burnDamage;
    }

    public float getAnimationLength(float animationLength){
        animationLength=2;
        return animationLength;
    }

    public boolean isCollision(boolean collision) {
        collision = false;
        return collision;
    }
}
