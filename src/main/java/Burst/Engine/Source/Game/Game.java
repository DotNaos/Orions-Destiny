package Burst.Engine.Source.Game;

import Burst.Engine.Source.Core.Actor.Actor;
import Burst.Engine.Source.Core.Actor.ActorComponent;
import Burst.Engine.Source.Core.Assets.AssetManager;
import Burst.Engine.Source.Core.Assets.Graphics.Sprite;
import Burst.Engine.Source.Core.Assets.Graphics.Texture;
import Burst.Engine.Source.Core.Component;
import Burst.Engine.Source.Core.Physics.Physics2D;
import Burst.Engine.Source.Core.Render.SpriteRenderer;
import Burst.Engine.Source.Core.Saving.ActorDeserializer;
import Burst.Engine.Source.Core.Saving.ComponentDeserializer;
import Burst.Engine.Source.Core.Scene.Scene;
import Burst.Engine.Source.Core.Util.DebugMessage;
import Burst.Engine.Source.Editor.Panel.ViewportPanel;
import Orion.res.AssetConfig;
import Orion.res.Assets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class Game {
    protected List<Actor> actors;
    protected List<Actor> actorsToAdd;
    protected List<Component> components;
    protected Physics2D physics2D;
    protected Scene scene;

    public Game(Scene scene) {
        this.scene = scene;
    }

    public void init() {
        this.physics2D = new Physics2D();
        this.actors = new ArrayList<>();
        this.actorsToAdd = new ArrayList<>();
        this.components = new ArrayList<>();

        DebugMessage.info("Game initializing...");
        DebugMessage.info("Opening Scene: " + scene.getOpenScene());

        DebugMessage.header("Game Panels");
        scene.addPanel(new ViewportPanel());

        scene.getSceneInitializer().loadResources(this);

        loadLevel();

     //? Debug code
//         this.addActor(new Actor().setSprite(new Sprite().setTexture(AssetManager.getAssetFromType(AssetConfig.ICON_PLAYER, Texture.class))));
//         this.addActor(new Actor().setSprite(new Sprite().setTexture(new Texture("Assets/images/spritesheets/pipes.png"))));
     //? End debug code

        for (Actor actor : actors) {
            actor.init();
        }
        start();
    }

    //! ====================================================================================================
    //! |=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=|
    //! |--------------------------------------[ Game Loop ]----------------------------------------------|
    //! |=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=|
    //! ====================================================================================================

    public void start() {
        for (Actor actor : actors) {
            scene.getViewportRenderer().add(actor);
            this.physics2D.add(actor);
        }
    }


    public void destroy() {
        for (Actor actor : actors) {
            actor.destroy();
        }
    }

    public void update(float dt) {
        for (Component c : components) {
            c.update(dt);
        }

        scene.getViewport().adjustProjection();
        this.physics2D.update(dt);

        for (Actor actor : actors) {
            actor.update(dt);
        }
    }


    public void addActor(Actor actor) {
        actors.add(actor);

        // Add the actor to the physics engine
        this.physics2D.add(actor);

        // Add the actor to the viewport renderer, if it has a sprite
        if (actor.getComponent(SpriteRenderer.class) != null) {
            scene.getViewportRenderer().add(actor);
        }

        // Save the level if the actor is a serialized actor
        if (!actor.isSerializedActor()) return;
        saveLevel();
    }

    //! ====================================================================================================
    //! |=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=|
    //! |--------------------------------------[ Component System ]---------------------------------------|
    //! |=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=|
    //! ====================================================================================================

    /**
     * Adds a {@link Component} to the list of components in the game
     *
     * @param c the component to add
     * @throws NullPointerException if the specified component is {@code null}
     */
    protected void addComponent(Component c) throws NullPointerException {
        // Check if component is null
        if (c == null) {
            throw new NullPointerException("Cannot add null component to actor.");
        }

        // Check if actor already has component
        if (hasComponent(c.getClass())) {
            return;
        }
        c.generateId();
        this.components.add(c);

        c.start();
    }

    protected boolean hasComponent(Class<? extends Component> cClass) {
        for (Component c : components) {
            if (c.getClass().equals(cClass)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Removes the first occurrence of a {@link Component} of the specified
     * {@link Class} from the internal list of components.
     *
     * @param <T>            the type of the component to remove
     * @param componentClass the {@link Class} object representing the type of the
     *                       component to remove
     * @throws NullPointerException if componentClass is null
     * @see Component
     * @see Class
     * @see List
     * @since 1.0
     */
    protected <T extends Component> void removeComponent(Class<T> componentClass) throws NullPointerException {
        for (int i = 0; i < components.size(); i++) {
            Component c = components.get(i);
            if (componentClass.isAssignableFrom(c.getClass())) {
                components.remove(i);
                return;
            }
        }
    }

    /**
     * Returns the first component of the specified {@code componentClass} found in
     * the {@code components} list, or {@code null} if none is found.
     *
     * @param <T>            the type of the component
     * @param componentClass the class of the component to be retrieved
     * @return the first component of the specified {@code componentClass} found in
     *         the {@code components} list, or {@code null} if none is found
     * @throws ClassCastException if the component cannot be cast to the specified
     *                            class
     * @see Class#isAssignableFrom(Class)
     * @see Class#cast(Object)
     */
    public <T extends Component> T getComponent(Class<T> componentClass) throws ClassCastException {
        for (Component c : components) {
            if (componentClass.isAssignableFrom(c.getClass())) {
                try {
                    return componentClass.cast(c);
                } catch (ClassCastException e) {
                    e.printStackTrace();
                    assert false : "Error: Casting component.";
                }
            }
        }

        return null;
    }


    //! ====================================================================================================
    //! |=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=|
    //! |--------------------------------------[ Saving and Loading ]-------------------------------------|
    //! |=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=|
    //! ====================================================================================================
    private Gson gsonBuilder() {
        return new GsonBuilder()
        .setPrettyPrinting()
        .registerTypeAdapter(ActorComponent.class, new ComponentDeserializer())
        .registerTypeAdapter(Actor.class, new ActorDeserializer())
        .enableComplexMapKeySerialization()
        .create();
    }

    public void saveLevel() {
        try {
            FileWriter writer = new FileWriter(".\\levels\\level.json");
            List<Actor> actorsToSerialize = new ArrayList<>();
            for (Actor actor : this.actors) {
                if (actor.isSerializedActor()) {
                    actorsToSerialize.add(actor);
                }
            }
            writer.write(gsonBuilder().toJson(actorsToSerialize));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadLevel() {
        String inFile = "";
        try {
            inFile = new String(Files.readAllBytes(Paths.get(".\\levels\\level.json")));
        } catch (IOException e) {
            if (e instanceof NoSuchFileException) {
                DebugMessage.notFound("Level Not Found");
            } else {
                DebugMessage.error("Error Loading Level");
            }
        }

        if (!inFile.equals("")) {
            Actor[] actors = gsonBuilder().fromJson(inFile, Actor[].class);
    
            for (Actor actor : actors) {
                addActor(actor);
            }
        }
    }





    //! ====================================================================================================
    //! |=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=|
    //! |--------------------------------------[ Getters and Setters ]------------------------------------|
    //! |=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=|
    //! ====================================================================================================
    public Physics2D getPhysics() {
        return this.physics2D;
    }

    public <T extends Component> Actor getActorWith(Class<T> ActorClass) {
        for (Actor actor : actors) {
            if (actor.getComponent(ActorClass) != null) {
                return actor;
            }
        }

        return null;
    }

    public List<Actor> getactors() {
        return this.actors;
    }

    public Actor getActor(int ActorId) {
        Optional<Actor> result = this.actors.stream().filter(Actor -> Actor.getID() == ActorId).findFirst();
        return result.orElse(null);
    }

    public Actor getActor(String ActorName) {
        Optional<Actor> result = this.actors.stream().filter(Actor -> Actor.getName().equals(ActorName)).findFirst();
        return result.orElse(null);
    }

    public List<Actor> getActors() {
        return actors;
    }

    //! ====================================================================================================
    //! |=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=|
    //! |--------------------------------------[ ImGui ]--------------------------------------------------|
    //! |=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=|
    //! ====================================================================================================

    public void imgui(){
        // TODO: MAYBE ADD FUNCTIONALITY HERE
    }
}
