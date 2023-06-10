package Orion.playercharacters;

import Burst.Engine.Source.Core.Assets.AssetManager;
import Burst.Engine.Source.Core.Assets.Graphics.Texture;
import Orion.res.AssetConfig;

public class Genesis extends PlayerCharacter {
    public Genesis() {
        super();
        this.name = "Genesis";
        // this.sprite = new Sprite("assets/textures/characters/genesis.png");
        // Natur
        this.description = """
                    Genesis is a wise and nurturing character, connected to the forces of nature in a profound way.\s
                    He has the ability to control and manipulate the natural world,\s
                    and his healing powers allow him to care for and restore those in need.\s
                    Despite his peaceful nature, Genesis is not to be underestimated,\s
                    as his connection to the forces of nature gives him immense power.\s
                """;
        this.name ="Genesis";
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
