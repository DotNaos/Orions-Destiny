package Orion.characters;

public abstract class PlayerCharacter {
    protected String name;
    protected String description;
    protected int HP;
    protected int DEF;
    protected int ATK;
    protected int SPD;
    protected int STAMINA;
    protected int LVL;
    protected int EXP;

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public int getHP() {
        return this.HP;
    }

    public int getDEF() {
        return this.DEF;
    }

    public int getATK() {
        return this.ATK;
    }

    public int getSPD() {
        return this.SPD;
    }

    public int getSTAMINA() {
        return this.STAMINA;
    }

    public int getLVL() {
        return this.LVL;
    }

    public int getEXP() {
        return this.EXP;
    }
}