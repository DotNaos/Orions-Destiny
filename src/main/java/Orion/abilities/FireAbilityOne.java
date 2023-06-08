package Orion.abilities;

import Orion.abilities.Special.SpecialAbility;

public class FireAbilityOne extends SpecialAbility {

  private float damage;
  private float burnDamage;
  private float burnLength;
  private float travellingSpeed;
  private float animationLength;
  private boolean collision;


  public float getDamage() {
    return this.damage;
  }

  public float getTravellingSpeed() {
    return this.travellingSpeed;
  }

  public float getBurnLength() {
    return this.burnLength;
  }

  public float getBurnDamage() {
    return this.burnDamage;
  }

  public float getAnimationLength() {
    return this.animationLength;
  }

  public boolean isCollision() {
    return this.collision;
  }
}
