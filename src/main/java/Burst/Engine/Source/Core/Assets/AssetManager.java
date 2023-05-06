package Burst.Engine.Source.Core.Assets;

import Burst.Engine.Config.ShaderConfig;
import Burst.Engine.Source.Core.Assets.Audio.Sound;
import Burst.Engine.Source.Core.Assets.Graphics.*;
import Burst.Engine.Source.Core.Component;
import Burst.Engine.Source.Core.util.DebugMessage;
import Orion.res.Assets;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AssetManager {
    private static Map<String, Spritesheet> spritesheets = new HashMap<>();
    private static Map<String, Sprite> sprites = new HashMap<>();
    private static Map<String, Texture> textures = new HashMap<>();

    private static Map<String, Shader> shaders = new HashMap<>();
    private static Map<String, Sound> sounds = new HashMap<>();


    private static Map<String, Font> fonts = new HashMap<>();
    private static Map<String, LevelMap> maps = new HashMap<>();
    private static Map<String, Background> backgrounds = new HashMap<>();
    private static Map<String, UI_Assets> UIs = new HashMap<>();

    public static void loadAllAssets() {
        System.out.println("Loading Assets from ...");


        // =================== Spritesheets ===================
        loadAllAssetOfType(Spritesheet.class);
        System.out.println("Loaded " + spritesheets.size() + " spritesheets");

        // ===================== Shader =======================
        loadAllAssetOfType(Shader.class);
        System.out.println("Loaded " + shaders.size() + " shaders");


        // =================== Sounds ===================
        loadAllAssetOfType(Sound.class);
        System.out.println("Loaded " + sounds.size() + " sounds");
        // set overworld sound to loop
        ((Sound) getAssetFromType(Sound.class, "assets/sounds/main-theme-overworld.ogg")).init(true);
        ((Sound) AssetManager.getAssetFromType(("assets/sounds/main-theme-overworld.ogg"), Sound.class)).play();

    }


    /**
     * Searches the directory (specified in Assets) for the assets and add them to the map of assets
     *
     * @param assetType The type of asset to load
     * @return The number of assets loaded
     */
    public static int loadAllAssetOfType(Class<? extends Asset> assetType) {
        int count = 0;
        String assetDir;


        if (assetType.equals(Spritesheet.class)) {
            assetDir = Assets.SPRITESHEETS;

            // search for all spritesheets in the directory
            String[] foundfiles = searchDirectory(assetDir, "png");

            // save the list of  in a variable
            List<Spritesheet> assetList = Assets.SPRITESHEETS_LIST;

            // Add all spritesheets to the map
            for (Spritesheet spritesheet : assetList) {
                spritesheets.put(spritesheet.getFilepath(), spritesheet);
                count++;
            }

            return count;
        } else if (assetType.equals(Sprite.class) || assetType.equals(Texture.class)) {
            DebugMessage.printError(assetType.toString() + "Are not Files to load");
            return count;
        } else if (assetType.equals(Shader.class)) {
            assetDir = ShaderConfig.SHADER_PATH;
            String[] foundfiles = searchDirectory(assetDir, "glsl");

            // Add all assetPaths to the map
            for (String assetPath : foundfiles) {
                shaders.put(assetPath, new Shader(assetPath));
                count++;
            }
            return count;
        } else if (assetType.equals(Sound.class)) {
            assetDir = Assets.SOUNDS;
            String[] foundfiles = searchDirectory(assetDir, "ogg");

            // Add all assetPaths to the map
            for (String assetPath : foundfiles) {
                sounds.put(assetPath, new Sound(assetPath));
                count++;
            }
            return count;
        } else if (assetType.equals(Font.class)) {
            assetDir = Assets.FONTS;
            String[] foundfiles = searchDirectory(assetDir, "ttf");

            // Add all assetPaths to the map
            for (String assetPath : foundfiles) {
                fonts.put(assetPath, new Font(assetPath));
                count++;
            }
            return count;
        } else if (assetType.equals(LevelMap.class)) {
            assetDir = Assets.MAPS;
            String[] foundfiles = searchDirectory(assetDir, "tmx");

            // Add all assetPaths to the map
            for (String assetPath : foundfiles) {
                maps.put(assetPath, new LevelMap(assetPath));
                count++;
            }
            return count;
        } else if (assetType.equals(Background.class)) {
            assetDir = Assets.BACKGROUNDS;
            String[] foundfiles = searchDirectory(assetDir, "png");

            // Add all assetPaths to the map
            for (String assetPath : foundfiles) {
                backgrounds.put(assetPath, new Background(assetPath));
                count++;
            }
            return count;
        } else if (assetType.equals(UI_Assets.class)) {
            assetDir = Assets.UI;
            String[] foundfiles = searchDirectory(assetDir, "png");

            // Add all assetPaths to the map
            for (String assetPath : foundfiles) {
                UIs.put(assetPath, new UI_Assets(assetPath));
                count++;
            }
            return count;
        }
        DebugMessage.printNotFound("Did not found: " + assetType.toString());
        return -1;

    }

    public static List<? extends Asset> getAllAssetsFromType(Class<? extends Asset> assetType) {

        if (assetType.equals(Spritesheet.class)) {
            return new ArrayList<>(spritesheets.values());
        } else if (assetType.equals(Sprite.class)) {
            return new ArrayList<>(sprites.values());
        } else if (assetType.equals(Texture.class)) {
            return new ArrayList<>(textures.values());
        } else if (assetType.equals(Shader.class)) {
            return new ArrayList<>(shaders.values());
        } else if (assetType.equals(Sound.class)) {
            return new ArrayList<>(sounds.values());
        } else if (assetType.equals(Font.class)) {
            return new ArrayList<>(fonts.values());
        } else if (assetType.equals(LevelMap.class)) {
            return new ArrayList<>(maps.values());
        } else if (assetType.equals(Background.class)) {
            return new ArrayList<>(backgrounds.values());
        } else if (assetType.equals(UI_Assets.class)) {
            return new ArrayList<>(UIs.values());
        }
        DebugMessage.printNotFound("Did not found: " + assetType.toString());
        return null;
    }

    public static <T extends Asset> T getAssetFromType(Class<T> assetType, String resourceName) {
        if (assetType.equals(Spritesheet.class)) {
            return (T) AssetManager.spritesheets.getOrDefault(resourceName, null);
        } else if (assetType.equals(Sprite.class)) {
            return (T) AssetManager.sprites.getOrDefault(resourceName, null);
        } else if (assetType.equals(Texture.class)) {
            if (AssetManager.textures.containsKey(resourceName)) {
                return (T) AssetManager.textures.get(resourceName);
            } else {
                // Create a new texture and add it to the map
                Texture texture = new Texture(resourceName);
                texture.init();
                AssetManager.textures.put(resourceName, texture);
                return (T) texture;
            }
        } else if (assetType.equals(Shader.class)) {
            if (AssetManager.shaders.containsKey(resourceName)) {
                return (T) AssetManager.shaders.get(resourceName);
            } else {
                // Create a new shader and add it to the map
                Shader shader = new Shader(resourceName);
                shader.compile();
                AssetManager.shaders.put(resourceName, shader);
                return (T) shader;
            }
        } else if (assetType.equals(Sound.class)) {
            return (T) AssetManager.sounds.getOrDefault(resourceName, null);
        } else if (assetType.equals(Font.class)) {
            DebugMessage.printNotFound(assetType.toString() + "IS NOT IMPLEMENTED YET");
            return (T) AssetManager.fonts.getOrDefault(resourceName, null);
        } else if (assetType.equals(LevelMap.class)) {
            DebugMessage.printNotFound(assetType.toString() + "IS NOT IMPLEMENTED YET");
            return (T) AssetManager.maps.getOrDefault(resourceName, null);
        } else if (assetType.equals(Background.class)) {
            DebugMessage.printNotFound(assetType.toString() + "IS NOT IMPLEMENTED YET");
            return (T) AssetManager.backgrounds.getOrDefault(resourceName, null);
        } else if (assetType.equals(UI_Assets.class)) {
            DebugMessage.printNotFound(assetType.toString() + "IS NOT IMPLEMENTED YET");
            return (T) AssetManager.UIs.getOrDefault(resourceName, null);
        }
        DebugMessage.printNotFound("Did not found: " + assetType.toString());
        return null;
    }

    public static <T extends Asset> T getAssetFromType(String resourceName, Class<T> assetType) {
        return getAssetFromType(assetType, resourceName);
    }

    public static String[] searchDirectory(String relativePath, String fileType) {
        // search for files in the given directory
        String[] files = new File(relativePath).list((dir, name) -> name.toLowerCase().endsWith("." + fileType));
        for (int i = 0; i < files.length; i++) {
            files[i] = relativePath + "/" + files[i];
            System.out.println(files[i]);
        }
        return files;
    }

    public static List<String> printAllAssetsFromType(Class<? extends Asset> assetType) {
        List<String> keys = new ArrayList<>();
        if (assetType.equals(Spritesheet.class)) {
            keys = new ArrayList<>(spritesheets.keySet());
        } else if (assetType.equals(Sprite.class)) {
            keys = new ArrayList<>(sprites.keySet());
        } else if (assetType.equals(Texture.class)) {
            keys = new ArrayList<>(textures.keySet());
        } else if (assetType.equals(Shader.class)) {
            keys = new ArrayList<>(shaders.keySet());
        } else if (assetType.equals(Sound.class)) {
            keys = new ArrayList<>(sounds.keySet());
        } else if (assetType.equals(Font.class)) {
            keys = new ArrayList<>(fonts.keySet());
        } else if (assetType.equals(LevelMap.class)) {
            keys = new ArrayList<>(maps.keySet());
        } else if (assetType.equals(Background.class)) {
            keys = new ArrayList<>(backgrounds.keySet());
        } else if (assetType.equals(UI_Assets.class)) {
            keys = new ArrayList<>(UIs.keySet());
        } else {
            DebugMessage.printNotFound("Did not found: " + assetType.toString());
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
        try {
            path = URLDecoder.decode(path, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            if (e.equals(UnsupportedEncodingException.class)) {
                System.out.println("UnsupportedEncodingException");
            } else {
                System.out.println("Could not decode path");
            }
        }
        return path;
    }
}
