package Orion.abilities.Ultimate;

import Orion.abilities.UltimateAbility;

public class EnergyAbility extends UltimateAbility {

    private float bulletSize = 0;
    private float dmg; //Damage depends on distance to player and distance to enemy
    private float dmgArea = 10;
    public static float actualDmg;
    private float knockback = 0;
    private float slowDown = 0;
    public static float heal = 0;
    private float healArea = 0;
    private float travelTime = 0;
    private float staminaCost = 20;
    private int duration = 10;
    private int cooldown = 50;
    String animation;

    public float damage () {
        dmgArea = (enemyDistance*bulletDistanceEnemy);
        actualDmg = dmgArea+dmg;
        return actualDmg;
    }
}