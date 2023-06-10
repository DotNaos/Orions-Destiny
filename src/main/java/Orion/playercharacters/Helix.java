package Orion.playercharacters;

import Burst.Engine.Source.Core.Assets.AssetManager;
import Burst.Engine.Source.Core.Assets.Graphics.Texture;
import Orion.res.AssetConfig;

public class Helix extends PlayerCharacter {
    public Helix() {
        super();
        this.name = "Helix";
        // this.sprite = new Sprite("assets/textures/characters/helix.png");
        this.description = """
                        Helix is a wise and enigmatic character, possessing a mastery over the flow of time itself.\s
                        His abilities allow him to manipulate time in various ways, such as slowing it down, speeding it up,\s
                        or even traveling through it. He can also shift between different dimensions and realities,\s
                        making him a valuable ally in the fight against otherworldly threats. Despite his seemingly detached nature,\s
                        Helix is a fierce and dedicated warrior, and his powers make him a formidable opponent in battle.\s
                        In addition, his regenerative abilities make him difficult to defeat,\s
                        as he can quickly heal from even the most severe injuries.\s
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
