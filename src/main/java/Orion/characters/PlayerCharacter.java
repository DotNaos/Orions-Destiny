package Orion.characters;

import java.lang.reflect.InvocationTargetException;

import Burst.Engine.Source.Core.Actor.Pawn;
import Burst.Engine.Source.Core.Assets.AssetManager;
import Burst.Engine.Source.Core.Assets.Graphics.Sprite;
import Burst.Engine.Source.Core.Assets.Graphics.Texture;
import Orion.res.AssetConfig;

public abstract class PlayerCharacter extends Pawn {
    public static final transient Texture icon = AssetManager.getAssetFromType(AssetConfig.ICON_PLAYER,Texture.class);
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

    public static <T extends PlayerCharacter> T getNewPlayerCharacter(Class<T> type) throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        // Create a new instance of the specified type and return it
        return type.getDeclaredConstructor().newInstance();
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
