package Orion.characters;

import Burst.Engine.Source.Core.Assets.AssetManager;
import Burst.Engine.Source.Core.Assets.Graphics.Sprite;
import Burst.Engine.Source.Core.Assets.Graphics.Texture;
import Orion.res.AssetConfig;

public class Apex extends PlayerCharacter {
    public static final transient Texture icon = AssetManager.getAssetFromType(AssetConfig.ICON_PLAYER,Texture.class);
    public Apex() {
        super();
        this.name = "Apex";
        // this.sprite = new Sprite("assets/textures/characters/apex.png");
        this.description = """
                        Apex is a mysterious and elusive character who is feared by many.\s
                        He possesses a powerful mastery over the shadows, and is able to bend them to his will.\s
                        His abilities make him nearly impossible to detect,\s
                        and he can disappear into the shadows at a moment's notice.\s
                """;
    }
}
