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
    private static Map<String, Spritesheet> spritesheets = new HashMap<>();

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
            assetDir = assets.getDir();
            foundFiles = searchDirectory(assetDir, assets.getFileType());

            DebugMessage.header(assets.getName());


            // Add all assetPaths to the map
            for (String assetPath : foundFiles) {
                assets.getAssets().put(assetPath, new Texture(assetPath));
                // System.out.println(assetPath);
            }

            DebugMessage.loadSuccess("Loaded " + assets.getAssets().size() + " " + assets.getName());
        }

            DebugMessage.noDebug = false;
    }

    public static List<? extends Asset> getAllAssetsFromType(Class<? extends Asset> assetType) {

        if (assetType.equals(Spritesheet.class)) {
            return new ArrayList<>(spritesheets.values());
        } else {
            for (Assets assets : assetsList) {
                if (assets.getAssetType().equals(assetType)) {
                    return new ArrayList<>(assets.getAssets().values());
                }
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

        if (assetType.equals(Spritesheet.class)) {

            return assetType.cast(AssetManager.spritesheets.getOrDefault(filePath, null));

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
                    files.add(file.getAbsolutePath());
                    System.out.println(file.getAbsolutePath());
                }
            }
        }
    }

    public static List<String> printAllAssetsFromType(Class<? extends Asset> assetType) {
        List<String> keys;
        if (assetType.equals(Spritesheet.class)) {
            keys = new ArrayList<>(spritesheets.keySet());
        } else {
            DebugMessage.notFound("Did not found: " + assetType);
            return null;
        }

        for (String key : keys) {
            System.out.println(key);
        }
        return keys;
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
        List<Spritesheet> ListOfSpriteSheets = spritesheets.values().stream().toList();
        for (Spritesheet sheet : ListOfSpriteSheets) {
            if (sheet.getUsage() == usage) {
                spritesheetsWithUsage.add(sheet);
            }
        }

        return spritesheetsWithUsage;
    }
}
