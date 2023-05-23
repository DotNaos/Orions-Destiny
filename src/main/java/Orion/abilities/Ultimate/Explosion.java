package Orion.abilities.Ultimate;

import Orion.abilities.UltimateAbility;

public class Explosion extends UltimateAbility {


    private float bulletSize;
    private float dmg; //Damage depends on distance to player and distance to enemy
    private float dmgArea;
    private float actualDmg;
    private float knockback;
    private float slowDown;
    private float heal;
    private float healArea;
    private float travelTime;
    private float staminaCost;
    private int duration;
    private int cooldown;
    String animation;


    @Override
    public void init(){

    }

    public float damage () {
        dmgArea = (enemyDistance*bulletDistanceEnemy);
        actualDmg = dmgArea+dmg;
        return actualDmg;
    }
}

