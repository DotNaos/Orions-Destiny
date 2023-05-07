package Orion.abilities;



public abstract class Ability {
    protected String name;
    protected String description;
    protected int cooldown;
    protected int duration;
    private int activationInput;

    public abstract void activate();


}
