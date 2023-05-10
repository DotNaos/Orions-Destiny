package Orion.abilities.Ultimate;

import Orion.abilities.UltimateAbility;

public class Explosion extends UltimateAbility {

    private float spd = 1.0f;

    private float dmg = enemyDistance*10; //Damage depends on distance to player and distance to enemy

    private float knockback = enemyDistance;

    private float size = 1.0f;

    }
}
