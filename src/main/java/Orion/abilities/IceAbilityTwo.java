package Orion.abilities;

public class IceAbilityTwo {
    private float damage;
    private float speedSlow;
    private float travellingSpeed;
    private float animationLength;
    private boolean collision;
    public float getDamage(float damage){
        damage=35;
        return damage;
    }

    public float getSpeedSlow(float speedSlow) {
        speedSlow = 0.25f;
        return speedSlow;
    }

    public float getTravellingSpeed(float travellingSpeed) {
        travellingSpeed= 3;
        return travellingSpeed;
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
