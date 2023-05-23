package Orion.abilities;


import Orion.characters.PlayerCharacter;

public abstract class Ability {
    protected String name;
    protected PlayerCharacter playerCharacter;
    protected String description;
    protected int cooldown;
    protected int duration;

    public Ability (PlayerCharacter playerCharacter){
        this.playerCharacter = playerCharacter;
    }


    private int activationInput;

    public abstract void activate();

    public abstract void init ();
}
