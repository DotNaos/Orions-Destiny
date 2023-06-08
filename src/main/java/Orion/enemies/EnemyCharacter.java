package Orion.enemies;

import Burst.Engine.Source.Core.Actor.Pawn;

public abstract class EnemyCharacter extends Pawn {
    protected int HP;
    protected int DEF;
    protected int ATK;
    protected int SPD;
    protected int LVL;
    public EnemyCharacter(String name) {
        super(name);
    }

    public int getHP() {
        return this.HP;
    }
    public int getDEF() {
        return this.HP;
    }
    public int getATK() {
        return this.HP;
    }
    public int getSPD() {
        return this.HP;
    }
    public int getLVL() {
        return this.HP;
    }

}
