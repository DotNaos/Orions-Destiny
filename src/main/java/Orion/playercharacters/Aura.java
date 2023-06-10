package Orion.playercharacters;

import Burst.Engine.Source.Core.Assets.AssetManager;
import Burst.Engine.Source.Core.Assets.Graphics.Texture;
import Orion.res.AssetConfig;

public class Aura extends PlayerCharacter {
    public Aura() {
        super();
        this.name = "Aura";
        // Energie und Gravitation
        this.description = """
                        Aura is a charismatic and confident character,\s
                        radiating energy wherever she actores.\s
                        Her powers give her the ability to control and manipulate energy in all forms,\s
                        and she uses her skills to protect and defend those she cares about.
                        Despite her seemingly carefree nature, Aura is a formidable force, and her powers are not to be underestimated.\s
                """;
        this.name ="Aura";
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
