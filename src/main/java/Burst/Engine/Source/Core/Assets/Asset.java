package Burst.Engine.Source.Core.Assets;


import Burst.Engine.Source.Core.util.Prefabs;

/**
 * Every asset in the engine is a subclass of this class.
 *
 */
public class Asset {
    protected String filepath;
    protected long id;


    public Asset(String filepath) {
        this.filepath = filepath;
        this.id = Prefabs.generateHashID(filepath);
        init();
    }

    protected void init() {}

    public String getFilepath() {
        return this.filepath;
    }

    public long getID() {
        return this.id;
    }


}
