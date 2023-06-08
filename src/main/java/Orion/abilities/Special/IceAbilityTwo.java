package Orion.abilities.Special;

public class IceAbilityTwo {
    private float damage;
    private float speedSlow;
    private float travellingSpeed;
    private float animationLength;
    private boolean collision;

    public float getDamage(float damage) {
        damage = 35;
        return damage;
    }

    public float getSpeedSlow() {
        return this.speedSlow;
    }

    public float getTravellingSpeed() {
        return this.travellingSpeed;
    }

    public float getAnimationLength() {
        return this.animationLength;
    }

    public boolean isCollision() {
        return this.collision;
    }
}
