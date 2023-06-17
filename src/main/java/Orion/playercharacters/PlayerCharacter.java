package Orion.playercharacters;

import Burst.Engine.Source.Core.Actor.Pawn;
import Burst.Engine.Source.Core.Actor.PlayerController;
import Burst.Engine.Source.Core.Assets.AssetManager;
import Burst.Engine.Source.Core.Assets.Graphics.Sprite;
import Burst.Engine.Source.Core.Assets.Graphics.SpriteSheet;
import Burst.Engine.Source.Core.Assets.Graphics.Texture;
import Burst.Engine.Source.Core.Render.SpriteRenderer;
import Burst.Engine.Source.Game.Animation.StateMachine;
import Orion.res.AssetConfig;
/**
 * @author Oliver Schuetz
 */
public abstract class PlayerCharacter extends Pawn {
    protected transient int spriteIndex = 0;
    protected transient SpriteSheet sprites;
    protected transient SpriteSheet idleSprites;
    protected transient SpriteSheet runSprites;
    protected transient SpriteSheet jumpSprites;
    protected transient SpriteSheet attackSprites;
    protected String description;
    protected int HP;
    protected int DEF;

    protected int ATK;
    protected int SPD;
    protected int STAMINA;
    protected int LVL;
    protected int EXP;

    /**
     * !Use init() to initialize values!
     * !NO CONSTRUCTOR!
     */
    public PlayerCharacter() {
        super();
        this.icon = AssetManager.getAssetFromType(AssetConfig.Files.Images.Icons.PLAYER, Sprite.class);
    }

    public void init()
    {
        super.init();
        if (!this.hasComponent(StateMachine.class)) {
            this.addComponent(new StateMachine());
        }
        if(!this.hasComponent(PlayerController.class))
        {
            this.addComponent(new PlayerController());
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

    @Override
    public void imgui()
    {
        super.imgui(this);
    }
}
