package Orion.playercharacters;

import Burst.Engine.Source.Core.Assets.AssetManager;
import Burst.Engine.Source.Core.Assets.Graphics.SpriteSheet;
import Burst.Engine.Source.Core.Assets.Graphics.Texture;
import Burst.Engine.Source.Core.Render.SpriteRenderer;
import Orion.res.AssetConfig;

import java.util.Timer;

public class Helix extends PlayerCharacter {
    @Override
    public void init()
    {
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

        this.idleSprites = AssetManager.getAssetFromType(AssetConfig.Files.Images.SpriteSheets.HELIX_IDLE, SpriteSheet.class);
        this.walkSprites = AssetManager.getAssetFromType(AssetConfig.Files.Images.SpriteSheets.HELIX_WALK, SpriteSheet.class);
        this.runSprites = AssetManager.getAssetFromType(AssetConfig.Files.Images.SpriteSheets.HELIX_RUN, SpriteSheet.class);
        this.jumpSprites = AssetManager.getAssetFromType(AssetConfig.Files.Images.SpriteSheets.HELIX_JUMP, SpriteSheet.class);
        this.attackSprites =  AssetManager.getAssetFromType(AssetConfig.Files.Images.SpriteSheets.HELIX_ATTACK, SpriteSheet.class);
    }

    @Override
    public void imgui()
    {
        super.imgui();
    }
}
