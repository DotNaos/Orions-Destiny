package Orion.abilities;

import org.joml.Vector2f;

public class UltimateAbility extends Ability {

    protected Vector2f castingPosition = new Vector2f();
    protected float playerDistance;
    protected float enemyDistance;
    protected float bulletDistanceEnemy;

    protected float bulletSize;
    protected float dmg;
    protected float dmgArea;
    protected float heal;
//    protected float healArea;
    protected float knockback;
    protected float slowDown;
    protected float staminaCost;
    protected float traveltime;
    protected float duration;
    protected float cooldown;
    protected String animation;

    /**
     *
     */
    @Override
    public void activate() {
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

        enemyDistance = (float) Math.sqrt((a * a) + (b * b));

        c = bulletPosX - enemyPosX;
        d = bulletPosY - enemyPosY;

        bulletDistanceEnemy = (float) Math.sqrt((c * c) + (d * d));
    }

    /**
     *
     */
    @Override
    public void init() {

    }
}
