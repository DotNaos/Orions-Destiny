package Orion.playercharacters;

import Burst.Engine.Source.Core.Assets.AssetManager;
import Burst.Engine.Source.Core.Assets.Graphics.Texture;
import Orion.res.AssetConfig;

public class Solaris extends PlayerCharacter {
    public Solaris() {
        super();
        this.name = "Solaris";
        this.description = """
                       Solaris is a bright and energetic character, radiating heat and light wherever he actores.\s
                       His powers give him the ability to control fire,\s
                       and he uses his skills to bring warmth and light to the world around him.\s
                       Despite his warm nature, Solaris is a force to be reckoned with, and he is not to be trifled with.\s
                """;
        this.name ="Solaris";
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
        super.imgui(this);
    }
}
