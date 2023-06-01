package Burst.Engine.Source.Core.Assets;

import Burst.Engine.Config.Constants.Font_Config;
import Burst.Engine.Config.Shader_Config;
import Burst.Engine.Source.Core.Assets.Audio.Sound;
import Burst.Engine.Source.Core.Assets.Graphics.*;
import Burst.Engine.Source.Core.Component;
import Burst.Engine.Source.Core.Util.DebugMessage;
import Orion.res.AssetHolder;
import Orion.res.Assets;

import java.io.File;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class AssetManager {

    private static List<Assets> assetsList = new ArrayList<>();

    public static void loadAllAssets() {
        DebugMessage.noDebug = true;
        DebugMessage.header("Loading Assets");

        String assetDir = AssetHolder.ASSETS;
        List<String> foundFiles;  

        assetsList.add(new Assets(Texture.class, AssetHolder.TEXTURES, "png"));
        assetsList.add(new Assets(Spritesheet.class, AssetHolder.SPRITESHEETS, "png"));
        assetsList.add(new Assets( Shader.class, Shader_Config.PATH, "glsl"));
        assetsList.add(new Assets( Sound.class, AssetHolder.SOUNDS, "ogg"));
        assetsList.add(new Assets( Font.class, Font_Config.PATH, "ttf"));
        assetsList.add(new Assets( LevelMap.class, AssetHolder.MAPS, "tmx"));


        for (Assets assets : assetsList)
        {
            if (assets.getAssetType().isAssignableFrom(Spritesheet.class)) continue;

            assetDir = assets.getDir();
            foundFiles = searchDirectory(assetDir, assets.getFileType());

            DebugMessage.header(assets.getName());


            // Add all assetPaths to the map
            for (String assetPath : foundFiles) {
                // add the relative path to the asset
                assetPath = assetPath.replace("\\", "/");
                
                assets.getAssets().put(assetPath, createNewAsset(assetPath, assets.getAssetType())); 
                
            } 

            DebugMessage.loadSuccess("Loaded " + assets.getAssets().size() + " " + assets.getName());
        }

            DebugMessage.noDebug = false;
    }

    private static Asset createNewAsset(String assetPath, Class<? extends Asset> assetType) {
        if (assetType.isAssignableFrom(Texture.class)) {
            return new Texture(assetPath);
        } else if (assetType.isAssignableFrom(Spritesheet.class)) {
            return new Spritesheet(assetPath);
        } else if (assetType.isAssignableFrom(Shader.class)) {
            return new Shader(assetPath);
        } else if (assetType.isAssignableFrom(Sound.class)) {
            return new Sound(assetPath);
        } else if (assetType.isAssignableFrom(Font.class)) {
            return new Font(assetPath);
        } else if (assetType.isAssignableFrom(LevelMap.class)) {
            return new LevelMap(assetPath);
        } else {
            DebugMessage.error("Could not create asset: " + assetType.toString());
            return null;
        }

    }

    public static List<? extends Asset> getAllAssetsFromType(Class<? extends Asset> assetType) {


        for (Assets assets : assetsList) {
            if (assets.getAssetType().equals(assetType)) {
                return new ArrayList<>(assets.getAssets().values());
            }
        }

        DebugMessage.notFound("Did not found: " + assetType.toString());
        return null;
    }

    public static <T extends Asset> T getAssetFromType(Class<T> assetType, String filePath) {
        File file = new File(filePath);

        if (!file.exists()) {
            DebugMessage.notFound("Did not found: " + filePath);
            return null;
        }


        for (Assets assets : assetsList) {
            if (assets.getAssetType().equals(assetType)) {
                return (T) assets.getAssets().get(filePath);
            }
        }


        DebugMessage.notFound("Did not found: " + assetType);
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
                    System.out.println(file.getPath());
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
            System.out.println(key);
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
    public static List<Spritesheet> getSpriteSheets(SpriteSheetUsage usage) {
        List<Spritesheet> spritesheetsWithUsage = new ArrayList<>();
        List<Spritesheet> ListOfSpriteSheets = (List<Spritesheet>) getAllAssetsFromType(Spritesheet.class);
        for (Spritesheet sheet : ListOfSpriteSheets) {
            if (sheet.getUsage() == usage) {
                spritesheetsWithUsage.add(sheet);
            }
        }

        return spritesheetsWithUsage;
    }
}
