package Burst.Engine.Source.Core.Assets;

import Burst.Engine.Config.Constants.Font_Config;
import Burst.Engine.Config.Shader_Config;
import Burst.Engine.Source.Core.Assets.Audio.Sound;
import Burst.Engine.Source.Core.Assets.Graphics.*;
import Burst.Engine.Source.Core.Component;
import Burst.Engine.Source.Core.Util.DebugMessage;
import Orion.res.AssetConfig;
import Orion.res.Assets;

import java.io.File;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static Orion.res.AssetConfig.Directories.*;
import static Orion.res.AssetConfig.Files.Images.SpriteSheets.MAPS;

/**
 * @author Oliver Schuetz
 */
public class AssetManager {
  public static final boolean showLoadingAssets = false;
  private static final List<Assets> assetsList = new ArrayList<>();

  public static void loadAllAssets() {
    DebugMessage.noDebug = false;

    DebugMessage.info("Loading Assets");

    String assetDir = AssetConfig.Directories.ASSETS;
    List<String> foundFiles;

    assetsList.add(new Assets(Texture.class, IMAGES, "png"));
    assetsList.add(new Assets(SpriteSheet.class, SPRITESHEETS, "png"));
    assetsList.add(new Assets(Shader.class, Shader_Config.PATH, "glsl"));
    assetsList.add(new Assets(Sound.class, SOUNDS, "ogg"));
    assetsList.add(new Assets(Font.class, Font_Config.PATH, "ttf"));
    assetsList.add(new Assets(LevelMap.class, MAPS, "tmx"));


    for (Assets assets : assetsList) {
      assetDir = assets.getDir();
      foundFiles = searchDirectory(assetDir, assets.getFileType());

      DebugMessage.header(assets.getName());


      // Add all assetPaths to the map
      for (String assetPath : foundFiles) {
        // add the relative path to the asset
        assetPath = assetPath.replace("\\", "/");

        Asset asset = createNewAsset(assetPath, assets.getAssetType());


        // If the asset is a spritesheet, set the config to the matching filepath of the config
        if (asset instanceof SpriteSheet) {
          for (SpriteSheetConfig config : AssetConfig.SPRITESHEETS_CONFIG) {
            if (config.filePath.equals(assetPath)) {
              ((SpriteSheet) asset).setConfig(config);
              asset.build();
              assets.getAssets().put(assetPath, asset);
              if (showLoadingAssets) {
                System.out.println("ADDING " + assets.getName().toUpperCase() + " : " + assetPath);
                System.out.println("\\--------->" + " CONFIG : " + config.filePath + "\n");
              }
            }
          }
        } else {

          if (showLoadingAssets) {
            System.out.println("ADDING " + assets.getName().toUpperCase() + " : " + assetPath);
          }

          asset.build();
          assets.getAssets().put(assetPath, asset);
        }
      }
      DebugMessage.loadSuccess("Loaded " + assets.getAssets().size() + " " + assets.getName());
    }

    DebugMessage.noDebug = false;
  }

  private static Asset createNewAsset(String assetPath, Class<? extends Asset> assetType) {
    if (assetType.isAssignableFrom(Texture.class)) {
      return new Texture(assetPath);
    } else if (assetType.isAssignableFrom(SpriteSheet.class)) {
      return new SpriteSheet(assetPath);
    } else if (assetType.isAssignableFrom(Shader.class)) {
      return new Shader(assetPath);
   } else if (assetType.isAssignableFrom(Sound.class)) {
      return new Sound(assetPath);
    } else if (assetType.isAssignableFrom(Font.class)) {
      return new Font(assetPath);
    } else if (assetType.isAssignableFrom(LevelMap.class)) {
      return new LevelMap(assetPath);
    } else {
      DebugMessage.error("Could not create asset: " + assetType);
      return null;
    }

  }

  public static List<? extends Asset> getAllAssetsFromType(Class<? extends Asset> assetType) {
    for (Assets assets : assetsList) {
      if (assets.getAssetType().equals(assetType)) {
        return new ArrayList<>(assets.getAssets().values());
      }
    }

    DebugMessage.notFound("Did not found any Asset from Type: " + assetType.toString());
    return null;
  }

  public static <T extends Asset> T getAssetFromType(Class<T> assetType, String filePath) {
    File file = new File(filePath);

    if (!file.exists()) {
      if (filePath.equals("Generated")) {
        DebugMessage.info("Generated " + assetType.getName());
        return null;
      }
      DebugMessage.notFound("Did not found Asset for filepath: " + filePath);
      return null;
    }


    for (Assets assets : assetsList) {
      if (assets.getAssetType().equals(assetType)) {
        return (T) assets.getAssets().get(filePath);
      }
    }

    for (Assets assets : assetsList) {
      System.out.println("AssetType:" + assets.getAssetType());
    }


    DebugMessage.notFound("Did not found the Asset of Type: " + assetType);
    return null;
  }

  public static <T extends Asset> T getAssetFromType(String filePath, Class<T> assetType) {
    return getAssetFromType(assetType, filePath);
  }

  public static List<String> searchDirectory(String relativePath, String fileType) {
    List<String> files = new ArrayList<>();
    File directory = new File(relativePath);
    if (directory.exists() && directory.isDirectory()) {
      searchDirectoryHelper(directory, fileType, files);
    }
    return files;
  }

  private static void searchDirectoryHelper(File directory, String fileType, List<String> files) {
    File[] fileList = directory.listFiles();
    if (fileList != null) {
      for (File file : fileList) {
        if (file.isDirectory()) {
          searchDirectoryHelper(file, fileType, files);
        } else if (file.getName().toLowerCase().endsWith("." + fileType)) {
          files.add(file.getPath());

          //! Prints all found Assets
//                    System.out.println(file.getPath());
        }
      }
    }
  }

  public static void printAllAssetsFromType(Class<? extends Asset> assetType) {
    List<String> keys = new ArrayList<>();
    for (Assets assets : assetsList) {
      if (assets.getAssetType().equals(assetType)) {
        keys = new ArrayList<>(assets.getAssets().keySet());
        break;
      }
    }
    for (String key : keys) {
      System.out.println("Key: " + key);
    }


  }

  public static String getFilePath(Component component) {
    // get the path of the component file
    String path = component.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
    path = URLDecoder.decode(path, StandardCharsets.UTF_8);
    return path;
  }

  /**
   * @param usage Specifies where in the contentdrawer it is displayed; Also adds
   *              some Funktionality
   * @return All Spritesheets in the Map of spritesheets that match the usage
   */
  public static List<SpriteSheet> getSpriteSheets(SpriteSheetUsage usage) {
    List<SpriteSheet> spritesheetsWithUsage = new ArrayList<>();
    List<SpriteSheet> listOfSpriteSheets = (List<SpriteSheet>) getAllAssetsFromType(SpriteSheet.class);
    for (SpriteSheet sheet : listOfSpriteSheets) {
      if (sheet.getUsage() == usage) {
        spritesheetsWithUsage.add(sheet);
      }
    }

    return spritesheetsWithUsage;
  }
}
