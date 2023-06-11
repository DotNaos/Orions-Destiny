package Burst.Engine.Source.Core.Assets.Graphics;

import Burst.Engine.Source.Core.Assets.Asset;

/**
 * @author Oliver Schuetz
 */
public class LevelMap extends Asset {
    public LevelMap(String filepath) {
        super(filepath);
    }

    @Override
    public Asset build() {
        return this;
    }
}
