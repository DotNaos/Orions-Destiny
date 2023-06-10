package Orion.playercharacters;

import Burst.Engine.Source.Core.Assets.AssetManager;
import Burst.Engine.Source.Core.Assets.Graphics.Texture;
import Orion.res.AssetConfig;

public class Helix extends PlayerCharacter {
    public Helix() {
        super();
        this.name = "Helix";
        // this.sprite = new Sprite("assets/textures/characters/helix.png");
        // Necromancer
        this.description = """
                    The bringer of death, Helix is a powerful necromancer. \s
                    He can summon the dead to fight for him. \s
                    Sometimes extracting the life out of his enemies. \s
                    
                """;
        this.name ="Helix";
        this.HP = 0;
        this.DEF = 0;
        this.ATK = 0;
        this.SPD = 0;
        this.STAMINA = 0;
        this.LVL = 0;
        this.EXP = 0;
    }

    @Override
    public void imgui()
    {
        super.imgui();
    }
}
