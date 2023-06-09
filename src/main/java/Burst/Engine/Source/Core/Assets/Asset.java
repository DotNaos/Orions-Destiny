package Burst.Engine.Source.Core.Assets;


import Burst.Engine.Source.Core.Util.Util;

import java.io.File;

/**
 * Every asset in the engine is a subclass of this class.
 */
public abstract class Asset {
    protected String filepath;
    protected long ID;

    protected boolean isDirty;

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

    public boolean setFilepath(String filepath)
    {
        // check if the filepath is in the assets folder
        // if not, print it
        File file = new File(filepath);
        if (!file.exists()) {
            System.out.println("Filepath does not exist: " + filepath);
            return false;
        }

        this.filepath = filepath;
        return true;
    }

    public long getID() {
        return this.ID;
    }

    public boolean isDirty() {
        return this.isDirty;
    }

    public void setClean() {
        this.isDirty = false;
    }

    public abstract Asset build();
}
