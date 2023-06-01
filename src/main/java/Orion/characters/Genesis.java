package Orion.characters;

import Burst.Engine.Source.Core.Assets.AssetManager;
import Burst.Engine.Source.Core.Assets.Graphics.Texture;
import Orion.res.AssetConfig;

public class Genesis extends PlayerCharacter {
    public static final transient Texture icon = AssetManager.getAssetFromType(AssetConfig.ICON_PLAYER,Texture.class).flippedTexture();
    public Genesis() {
        super();
        this.name = "Genesis";
        // this.sprite = new Sprite("assets/textures/characters/genesis.png");
        this.description = """
                    Genesis is a wise and nurturing character, connected to the forces of nature in a profound way.\s
                    He has the ability to control and manipulate the natural world,\s
                    and his healing powers allow him to care for and restore those in need.\s
                    Despite his peaceful nature, Genesis is not to be underestimated,\s
                    as his connection to the forces of nature gives him immense power.\s
                """;
    }
}
