package Orion.abilities;


import Orion.playercharacters.PlayerCharacter;

public abstract class Ability {
  protected String name;
  protected PlayerCharacter playerCharacter;
  protected String description;
  protected int cooldown;
  protected int duration;
  private int activationInput;


  public Ability() {

  }

  public abstract void activate();

  public abstract void init();
}
