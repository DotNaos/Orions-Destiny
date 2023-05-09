package Burst.Engine.Source.Editor;

import Burst.Engine.Source.Core.Assets.Graphics.Spritesheet;

import java.util.ArrayList;
import java.util.List;

public class ContentDrawer {
    private List<Spritesheet> spritesheets = new ArrayList<>();

    public ContentDrawer() {

    }

    public void addSpritesheet(Spritesheet spritesheet) {
        spritesheets.add(spritesheet);
    }

    public void removeSpritesheet(Spritesheet spritesheet) {
        for (int i = 0; i < spritesheets.size(); i++)
        {
            if (spritesheets.get(i).getID() == spritesheet.getID()) {
                spritesheets.remove(i);
                return;
            }
        }
    }



}
