package Burst.Engine.Source.Core.Assets;


/**
 * Every asset in the engine is a subclass of this class.
 *
 */
public class Asset {
    protected String filepath;

    public Asset(String filepath) {
        this.filepath = "";
    }

    public String getFilepath() {
        return this.filepath;
    }



}
