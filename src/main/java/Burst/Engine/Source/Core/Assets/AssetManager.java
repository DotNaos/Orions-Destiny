package Burst.Engine.Source.Core.Assets;

import Burst.Engine.Config.ShaderConfig;
import Burst.Engine.Source.Core.Assets.Audio.Sound;
import Burst.Engine.Source.Core.Assets.Graphics.*;
import Burst.Engine.Source.Core.Component;
import Burst.Engine.Source.Core.util.DebugMessage;
import Orion.res.Assets;

import java.io.File;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;


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
        DebugMessage.noDebug = true;
        DebugMessage.header("Loading Assets");
        System.out.println("Loading Assets from: " + Assets.class.getProtectionDomain().getCodeSource().getLocation().getPath() + "\n");

        // =================== Spritesheets ===================
        DebugMessage.header("Spritesheets");
        loadAllAssetOfType(Spritesheet.class);
        DebugMessage.loadSuccess("Loaded " + spritesheets.size() + " spritesheets");

        // ===================== Shader =======================
        DebugMessage.header("Shaders");
        loadAllAssetOfType(Shader.class);
        DebugMessage.loadSuccess("Loaded " + shaders.size() + " shaders");


        // =================== Sounds ===================
        DebugMessage.header("Sounds");
        loadAllAssetOfType(Sound.class);
        DebugMessage.loadSuccess("Loaded " + sounds.size() + " sounds");

        DebugMessage.noDebug = false;
        // set overworld sound to loop
//        getAssetFromType(Sound.class, "assets/sounds/main-theme-overworld.ogg").init(true);
//        AssetManager.getAssetFromType(("assets/sounds/main-theme-overworld.ogg"), Sound.class).play();

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

            // save the list of  in a variable
            List<Spritesheet> assetList = Assets.SPRITESHEETS_LIST;

            // Add all Spritesheets to the map
            for (Spritesheet spritesheet : assetList) {
                spritesheets.put(spritesheet.getFilepath(), spritesheet);
                count++;
            }

            return count;
        } else if (assetType.equals(Sprite.class) || assetType.equals(Texture.class)) {
            DebugMessage.error(assetType.toString() + "Are not Files to load");
            return count;
        } else if (assetType.equals(Shader.class)) {
            assetDir = ShaderConfig.SHADER_PATH;
            String[] foundFiles = searchDirectory(assetDir, "glsl");
            
            // Add all assetPaths to the map
            for (String assetPath : foundFiles) {
                shaders.put(assetPath, new Shader(assetPath));
                count++;
            }
            return count;
        } else if (assetType.equals(Sound.class)) {
            assetDir = Assets.SOUNDS;
            String[] foundFiles = searchDirectory(assetDir, "ogg");

            // Add all assetPaths to the map
            for (String assetPath : foundFiles) {
                sounds.put(assetPath, new Sound(assetPath));
                count++;
            }
            return count;
        } else if (assetType.equals(Font.class)) {
            assetDir = Assets.FONTS;
            String[] foundFiles = searchDirectory(assetDir, "ttf");

            // Add all assetPaths to the map
            for (String assetPath : foundFiles) {
                fonts.put(assetPath, new Font(assetPath));
                count++;
            }
            return count;
        } else if (assetType.equals(LevelMap.class)) {
            assetDir = Assets.MAPS;
            String[] foundFiles = searchDirectory(assetDir, "tmx");

            // Add all assetPaths to the map
            for (String assetPath : foundFiles) {
                maps.put(assetPath, new LevelMap(assetPath));
                count++;
            }
            return count;
        } else if (assetType.equals(Background.class)) {
            assetDir = Assets.BACKGROUNDS;
            String[] foundFiles = searchDirectory(assetDir, "png");

            // Add all assetPaths to the map
            for (String assetPath : foundFiles) {
                backgrounds.put(assetPath, new Background(assetPath));
                count++;
            }
            return count;
        } else if (assetType.equals(UI_Assets.class)) {
            assetDir = Assets.UI;
            String[] foundFiles = searchDirectory(assetDir, "png");

            // Add all assetPaths to the map
            for (String assetPath : foundFiles) {
                UIs.put(assetPath, new UI_Assets(assetPath));
                count++;
            }
            return count;
        }
        DebugMessage.notFound("Did not found: " + assetType);
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
        DebugMessage.notFound("Did not found: " + assetType.toString());
        return null;
    }

    public static <T extends Asset> T getAssetFromType(Class<T> assetType, String resourceName) {
        System.out.println("Loading ressource: " + resourceName);
        File file = new File(resourceName);
        if (!file.exists()) {
            DebugMessage.notFound("Did not found: " + resourceName);
            return null;
        }
        else {
            System.out.print("Found ressource: ");
            resourceName = file.getAbsolutePath();
        }


        if (assetType.equals(Spritesheet.class)) {
            
            return assetType.cast(AssetManager.spritesheets.getOrDefault(resourceName, null));
            
        } else if (assetType.equals(Sprite.class)) {
            
            return assetType.cast(AssetManager.sprites.getOrDefault(resourceName, null));
            
        } else if (assetType.equals(Texture.class)) {
            if (AssetManager.textures.containsKey(resourceName)) {
                
                return assetType.cast(AssetManager.textures.get(resourceName));
                
            } else {
                // Create a new texture and add it to the map


                Texture texture = new Texture(resourceName);
                AssetManager.textures.put(resourceName, texture);
                
                return assetType.cast(texture);
                
            }
        } else if (assetType.equals(Shader.class)) {
            if (AssetManager.shaders.containsKey(resourceName)) {
                
                return assetType.cast(AssetManager.shaders.get(resourceName));
                
            } else {
                // Create a new shader and add it to the map
                Shader shader = new Shader(resourceName);
                shader.compile();
                AssetManager.shaders.put(resourceName, shader);
                return assetType.cast(shader);
            }
        } else if (assetType.equals(Sound.class)) {
            return assetType.cast( AssetManager.sounds.getOrDefault(resourceName, null));
        } else if (assetType.equals(Font.class)) {
            DebugMessage.notFound(assetType + "IS NOT IMPLEMENTED YET");
            return assetType.cast( AssetManager.fonts.getOrDefault(resourceName, null));
        } else if (assetType.equals(LevelMap.class)) {
            DebugMessage.notFound(assetType + "IS NOT IMPLEMENTED YET");
            return assetType.cast( AssetManager.maps.getOrDefault(resourceName, null));
        } else if (assetType.equals(Background.class)) {
            DebugMessage.notFound(assetType + "IS NOT IMPLEMENTED YET");
            return assetType.cast( AssetManager.backgrounds.getOrDefault(resourceName, null));
        } else if (assetType.equals(UI_Assets.class)) {
            DebugMessage.notFound(assetType + "IS NOT IMPLEMENTED YET");
            return assetType.cast( AssetManager.UIs.getOrDefault(resourceName, null));
        }
        DebugMessage.notFound("Did not found: " + assetType);
        return null;
    }

    public static <T extends Asset> T getAssetFromType(String resourceName, Class<T> assetType) {
        return getAssetFromType(assetType, resourceName);
    }

    public static String[] searchDirectory(String relativePath, String fileType) {
        // search for files in the given directory
        String[] files = new File(relativePath).list((dir, name) -> name.toLowerCase().endsWith("." + fileType));
        for (int i = 0; i < Objects.requireNonNull(files).length; i++) {
            files[i] = relativePath + "/" + files[i];
            System.out.println(files[i]);
        }
        return files;
    }

    public static List<String> printAllAssetsFromType(Class<? extends Asset> assetType) {
        List<String> keys;
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
}
