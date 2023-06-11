package Orion.playercharacters;

import Burst.Engine.Source.Core.Actor.Pawn;
import Burst.Engine.Source.Core.Actor.PlayerController;
import Burst.Engine.Source.Core.Assets.AssetManager;
import Burst.Engine.Source.Core.Assets.Graphics.SpriteSheet;
import Burst.Engine.Source.Core.Assets.Graphics.Texture;
import Orion.res.AssetConfig;

public abstract class PlayerCharacter extends Pawn {

    protected SpriteSheet idleSprites;
    protected SpriteSheet walkSprites;
    protected SpriteSheet runSprites;
    protected SpriteSheet jumpSprites;
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
        this.icon = AssetManager.getAssetFromType(AssetConfig.Files.Images.Icons.PLAYER,Texture.class);
        this.addComponent(new PlayerController(this));
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

    @Override
    public void imgui()
    {
        super.imgui(this);
    }
}
