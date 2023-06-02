package Orion.characters;

import Burst.Engine.Source.Core.Assets.AssetManager;
import Burst.Engine.Source.Core.Assets.Graphics.Texture;
import Orion.res.AssetConfig;

public class Helix extends PlayerCharacter {
    public static final transient Texture icon = AssetManager.getAssetFromType(AssetConfig.ICON_PLAYER,Texture.class);
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
    }
}
