package Orion.res;

import Burst.Engine.Source.Core.Assets.Asset;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Oliver Schuetz
 */
public class Assets {
  private String dir = AssetConfig.Directories.ROOT;
  private String fileType;
  private Class<? extends Asset> assetType = Asset.class;

  private Map<String, Asset> assets = new HashMap<>();

  public String getDir() {
    return dir;
  }

  public String getFileType() {
    return fileType;
  }

  public Map<String, Asset> getAssets() {
    return assets;
  }

  public String getName() {
    // The name of the ClassType T
    return assetType.getSimpleName();
  }

  public Class<? extends Asset> getAssetType() {
    return assetType;
  }

  public Assets(Class<? extends Asset> assetType, String dir, String fileType) {
    this.assetType = assetType;
    this.dir = dir;
    this.fileType = fileType;
  }
}
