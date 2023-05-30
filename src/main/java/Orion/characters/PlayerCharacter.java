package Orion.characters;

import Burst.Engine.Source.Core.Actor.Pawn;
import Burst.Engine.Source.Core.Assets.Graphics.Sprite;

public abstract class PlayerCharacter extends Pawn {
    protected String description;
    protected int HP;
    protected int DEF;
    protected int ATK;
    protected int SPD;
    protected int STAMINA;
    protected int LVL;
    protected int EXP;

    public PlayerCharacter() {
        super();
    }

    public static <T extends PlayerCharacter> T getNewPlayerCharacter(int PlayerID) {
        // Create a new instance of the specified type and return it

        switch (PlayerID)
        {
            case 2 -> {
                return (T) new Aura();
            }
            case 3 -> {
                return (T) new Genesis();
            }
            case 4 -> {
                return (T) new Helix();
            }
            case 5 -> {
                return (T) new Solaris();
            }
            default -> {
                // Default character
                return (T) new Apex();
            }
        }
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
