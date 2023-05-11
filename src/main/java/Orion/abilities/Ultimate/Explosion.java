package Orion.abilities.Ultimate;

import Orion.abilities.UltimateAbility;

public class Explosion extends UltimateAbility {
    private float spd = 1.0f;
    private float dmg; //Damage depends on distance to player and distance to enemy
    private float knockback;
    private float size = 1.0f;

    @Override
    public void init(){

    }
}

