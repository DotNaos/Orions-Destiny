package Orion.abilities;

/**
 * @author Dominik Abler
 */
public class PoisonAbility {
  private float damage;
  private float duration;
  private float dmgOverTime;
  public float animationLength;
  public int range;


  public float getDamage() {
    damage = 15;
    return damage;
  }

  public float getDuration() {
    duration = 5;
    return duration;
  }

  public float getDmgOverTime() {
    dmgOverTime = 5;
    return dmgOverTime;
  }

  public float getAnimationLength() {
    animationLength = 1;
    return animationLength;
  }

  public int getRange() {
    range = 4;
    return range;
  }
}

