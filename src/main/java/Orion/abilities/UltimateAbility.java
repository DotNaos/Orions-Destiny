package Orion.abilities;

import Orion.abilities.Ultimate.EnergyAbility;
import org.joml.Vector2f;

import java.util.Vector;

import static Orion.abilities.Ultimate.EnergyAbility.actualDmg;

public class UltimateAbility extends Ability{

    protected Vector2f castingPosition = new Vector2f();
    protected float enemyDefence;
    protected float enemyDistance;
    protected float bulletDistanceEnemy;


    public float dmg = actualDmg;
    public float heal = EnergyAbility.heal;
    public float area;
    public float knockback;
    public float slowDown;
    public float staminaCost;
    public float traveltime;
    public float duration;
    public float cooldown;

    /**
     *
     */
    @Override
    public void activate() {

    }

    /**
     *
     */
    @Override
    public void init() {

    }

    public float variablen(){
        float playerPosX = .getXPos;
        float playerPosY = .getYPos;

        float enemyPosX = .getXPos;
        float enemyPosY = .getYPos;

        float bulletPosX = .getXPos;        //Detonation Position of the Bullet
        float bulletPosY = .getYPos;

        float enemyDefence = .get;

        float a;
        float b;
        float c;
        float d;

        castingPosition.x = playerPosX;
        castingPosition.y = playerPosY;

        a = playerPosX - enemyPosX;
        b = playerPosY - enemyPosY;

        enemyDistance = (float) Math.sqrt((a*a) + (b*b));

        c = bulletPosX - enemyPosX;
        d = bulletPosY - enemyPosY;

        bulletDistanceEnemy = (float) Math.sqrt((c*c) + (d*d));
    }
}
