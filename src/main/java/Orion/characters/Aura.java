package Orion.characters;

import Burst.Engine.Source.Core.Assets.AssetManager;
import Burst.Engine.Source.Core.Assets.Graphics.Texture;
import Orion.res.AssetConfig;

public class Aura extends PlayerCharacter {
    public static final transient Texture icon = AssetManager.getAssetFromType(AssetConfig.ICON_PLAYER,Texture.class);
    public Aura() {
        super();
        this.name = "Aura";
        // this.sprite = new Sprite("assets/textures/characters/aura.png");
        this.description = """
                        Aura is a charismatic and confident character,\s
                        radiating energy wherever she actores.\s
                        Her powers give her the ability to control and manipulate energy in all forms,\s
                        and she uses her skills to protect and defend those she cares about.
                        Despite her seemingly carefree nature, Aura is a formidable force, and her powers are not to be underestimated.\s
                """;
    }
}
