package Orion.characters;

import Burst.Engine.Source.Core.Assets.AssetManager;
import Burst.Engine.Source.Core.Assets.Graphics.Texture;
import Orion.res.AssetConfig;

public class Solaris extends PlayerCharacter {
    public static final transient Texture icon = AssetManager.getAssetFromType(AssetConfig.ICON_PLAYER,Texture.class);
    public Solaris() {
        super();
        this.name = "Solaris";
        // this.sprite = new Sprite("assets/textures/characters/solaris.png");
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
}
