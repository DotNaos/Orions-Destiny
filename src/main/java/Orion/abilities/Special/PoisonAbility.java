package Orion.abilities.Special;

import Orion.abilities.Ability;
/**
 * @author Dominik Abler
 */
public class PoisonAbility extends Ability {
  public float animationLength;
  public int range;
  private float damage;
  private float duration;
  private float dmgOverTime;

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
}

