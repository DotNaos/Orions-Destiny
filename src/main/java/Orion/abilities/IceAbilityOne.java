package Orion.abilities;

import Burst.Engine.Source.Core.Graphics.Input.KeyListener;
import Burst.Engine.Source.Core.Graphics.Input.MouseListener;

public class IceAbilityOne {

    private float damage;
    private float speedSlow;
    private float animationLength;
    private boolean collision;


    public float getDamage(){
        return this.damage;
    }

    public float getSpeedSlow() {
        return this.speedSlow;
    }


    public float getAnimationLength(){
        return this.animationLength;
    }

    public boolean isCollision() {
        return this.collision;
    }
}
