package util;

import components.Spritesheet;
import Burst.Sound;
import renderer.Shader;
import renderer.Texture;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class AssetManager {
    private static Map<String, Shader> shaders = new HashMap<>();
    private static Map<String, Texture> textures = new HashMap<>();
    private static Map<String, Spritesheet> spritesheets = new HashMap<>();
    private static Map<String, Sound> sounds = new HashMap<>();




    public static void loadAllResources()
    {
        // Add all spritesheets
//        String[] spritesheets = searchDirectory(AssetHolder.SPRITESHEETS, "png");
//        for (String spritesheet : spritesheets) {
//            addSpritesheet(spritesheet, new Spritesheet(getTexture(spritesheet), 16, 16, 16, 0));
//        }

        // ===================== Shader =======================
            getShader(AssetHolder.SHADER_DEFAULT);

        // =================== Spritesheets ===================
            addSpritesheet(AssetHolder.BLOCKS,AssetHolder.BLOCKS_SPRITESHEET);

            addSpritesheet(AssetHolder.PLAYER, AssetHolder.PLAYER_SPRITESHEET);

            addSpritesheet(AssetHolder.BIG_PLAYER, AssetHolder.BIG_PLAYER_SPRITESHEET);

            addSpritesheet(AssetHolder.TURTLE, AssetHolder.TURTLE_SPRITESHEET);


            addSpritesheet(AssetHolder.PIPES, AssetHolder.PIPES_SPRITESHEET);


            addSpritesheet(AssetHolder.ITEMS, AssetHolder.ITEMS_SPRITESHEET);


            addSpritesheet(AssetHolder.GIZMOS, AssetHolder.GIZMOS_SPRITESHEET);




        // =================== Sounds ===================
                //        AssetManager.addSound("assets/sounds/main-theme-overworld.ogg", true);
                //        AssetManager.addSound("assets/sounds/flagpole.ogg", false);
                //        AssetManager.addSound("assets/sounds/break_block.ogg", false);
                //        AssetManager.addSound("assets/sounds/bump.ogg", false);
                //        AssetManager.addSound("assets/sounds/coin.ogg", false);
                //        AssetManager.addSound("assets/sounds/gameover.ogg", false);
                //        AssetManager.addSound("assets/sounds/jump-small.ogg", false);
                //        AssetManager.addSound("assets/sounds/mario_die.ogg", false);
                //        AssetManager.addSound("assets/sounds/pipe.ogg", false);
                //        AssetManager.addSound("assets/sounds/powerup.ogg", false);
                //        AssetManager.addSound("assets/sounds/powerup_appears.ogg", false);
                //        AssetManager.addSound("assets/sounds/stage_clear.ogg", false);
                //        AssetManager.addSound("assets/sounds/stomp.ogg", false);
                //        AssetManager.addSound("assets/sounds/kick.ogg", false);
                //        AssetManager.addSound("assets/sounds/invincible.ogg", false);
                //
                //        AssetManager.getSound(("assets/sounds/main-theme-overworld.ogg")).play();

    }



    /**
     *
     * @param relativePath the path in the project directory
     * @param fileType the file type to search for eg. "png"
     * @return the count of found files
     */

    public static String[] searchDirectory(String relativePath, String fileType)
    {
        // search for files in the given directory
        String[] files = new File(relativePath).list((dir, name) -> name.toLowerCase().endsWith("." + fileType));
        for (int i = 0; i < files.length; i++) {
            files[i] = relativePath + "/" + files[i];
            System.out.println(files[i]);
        }
        return files;
    }


    /**
     * @param resourceName the path in the project directory
     * @return Shader if already there, otherwise a new Shader is created and returned
     */
    public static Shader getShader(String resourceName) {
        File file = new File(resourceName);
        if (AssetManager.shaders.containsKey(file.getAbsolutePath())) {
            return AssetManager.shaders.get(file.getAbsolutePath());
        } else {
            Shader shader = new Shader(resourceName);
            shader.compile();
            AssetManager.shaders.put(file.getAbsolutePath(), shader);
            return shader;
        }
    }


    // Generate Javadoc for this method

    /**
     * @param resourceName the path in the project directory
     * @return Texture if already there, otherwise a new Texture is created and returned
     */
    public static Texture getTexture(String resourceName) {
        File file = new File(resourceName);
        if (AssetManager.textures.containsKey(file.getAbsolutePath())) {
            return AssetManager.textures.get(file.getAbsolutePath());
        } else {
            Texture texture = new Texture();
            texture.init(resourceName);
            AssetManager.textures.put(file.getAbsolutePath(), texture);
            return texture;
        }
    }

    /**
     * @param resourceName the path in the project directory
     * @param spritesheet the Spritesheet to be added
     */
    public static void addSpritesheet(String resourceName, Spritesheet spritesheet) {
        File file = new File(resourceName);
        if (!AssetManager.spritesheets.containsKey(file.getAbsolutePath())) {
            AssetManager.spritesheets.put(file.getAbsolutePath(), spritesheet);
        }
    }

    /**
     * @param resourceName the path in the project directory
     * @return Spritesheet if already there, otherwise null
     */
    public static Spritesheet getSpritesheet(String resourceName) {
        File file = new File(resourceName);
        if (!AssetManager.spritesheets.containsKey(file.getAbsolutePath())) {
            assert false : "Error: Tried to access spritesheet '" + resourceName + "' and it has not been added to asset pool.";
        }
        return AssetManager.spritesheets.getOrDefault(file.getAbsolutePath(), null);
    }

    /**
     * @return Collection of all Sounds
     */
    public static Collection<Sound> getAllSounds() {
        return sounds.values();
    }

    /**
     * @param soundFile the path in the project directory
     * @return Sound if already there, otherwise null
     * @throws Exception if sound file not added
     */
    public static Sound getSound(String soundFile) {
        File file = new File(soundFile);
        if (sounds.containsKey(file.getAbsolutePath())) {
            return sounds.get(file.getAbsolutePath());
        } else {
            assert false : "Sound file not added '" + soundFile + "'";
        }

        return null;
    }

    /**
     * @param soundFile the path in the project directory
     * @param loops whether the sound should loop
     * @return Sound if already there, otherwise a new Sound is created and returned
     */
    public static Sound addSound(String soundFile, boolean loops) {
        File file = new File(soundFile);
        if (sounds.containsKey(file.getAbsolutePath())) {
            return sounds.get(file.getAbsolutePath());
        } else {
            Sound sound = new Sound(file.getAbsolutePath(), loops);
            AssetManager.sounds.put(file.getAbsolutePath(), sound);
            return sound;
        }
    }
}
