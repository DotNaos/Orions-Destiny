package Burst.Engine.Source.Core.Assets;


import Burst.Engine.Source.Core.Util.Util;

/**
 * Every asset in the engine is a subclass of this class.
 */
public abstract class Asset {
    protected String filepath;
    protected long ID;

    /**
     * Creates an asset with the given filepath.
     *
     * @param filepath The filepath of the asset.
     */

    public Asset(String filepath) {
        this.filepath = filepath;
        this.ID = Util.generateHashID(filepath);
        init();
    }

    protected void init() {
    }

    public String getFilepath() {
        return this.filepath;
    }

    public long getID() {
        return this.ID;
    }

    public abstract Asset build();
}
