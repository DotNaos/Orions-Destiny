package Burst.Engine.Source.Core.Actor;


import Burst.Engine.Source.Core.Assets.Graphics.Sprite;
import Burst.Engine.Source.Core.Assets.Graphics.Texture;
import Burst.Engine.Source.Core.Component;
import Burst.Engine.Source.Core.Physics.Components.Transform;
import Burst.Engine.Source.Core.Saving.ComponentDeserializer;
import Burst.Engine.Source.Core.Saving.ActorDeserializer;
import Burst.Engine.Source.Core.Graphics.Render.SpriteRenderer;
import Burst.Engine.Source.Core.UI.Window;
import Burst.Engine.Source.Core.Util.Util;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import imgui.ImGui;
import Burst.Engine.Source.Core.Assets.AssetManager;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.List;
/**
 * Represents an object in the game world that can have Components attached to it.
 */
public class Actor {
    /**
     * The ID of the actor.
     * This is set to -1 by default, and is set to a unique ID when the actor is created.
     * This is used to identify the actor when saving and loading or when searching for an actor.
     */
    private long ID = -1;

    /**
     * The name of the actor.
     * This is set to  "New Actor" by default, and is set to the name of the actor when the actor is created.
     * Besides being used to identify the actor, this is also used to display the actor in the editor.
     */
    public String name = "New Actor";
    private List<Component> components;
    /**
     * The Transform component attached to this actor.
     * This is automatically added when the actor is created.
     */
    public transient Transform transform;

    SpriteRenderer spriteRenderer;

    /**
     * Whether this actor is serialized when saving and loading.
     */
    public boolean serializedActor = true;


    //====================================================================================================
    // |=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=|
    // |--------------------------------------[ Initialization ]-----------------------------------------|
    // |=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=|
    //====================================================================================================

    /**
     * Creates a new Actor instance with default values.
     * Automatically generates a unique ID and creates a new Transform component.
     */
    public Actor() {
        this.name = name;
        this.components = new ArrayList<>();
        this.transform = new Transform(this);
        this.ID = Util.generateUniqueID();
    }

    public Actor(Sprite sprite, float sizeX, float sizeY)
    {
        this("Actor: " + (Window.getScene().getGame().getActors().size() + 1));
        this.spriteRenderer = new SpriteRenderer(this);
        this.spriteRenderer.setSprite(sprite);
        this.components.add(this.spriteRenderer);
        this.transform.size = new Vector2f(sizeX, sizeY);
    }

    /**
     * Creates a new Actor instance with a given name.
     * Automatically generates a unique ID and creates a new Transform component.
     * @param name The name to give to the actor.
     */
    public Actor(String name) {
        this();
        this.name = name;
    }

    /**
     * Creates a new Actor instance with a given name and position.
     * Automatically generates a unique ID and creates a new Transform component.
     * @param name The name to give to the actor.
     * @param x The x position of the actor.
     * @param y The y position of the actor.
     */
    public Actor(String name, float x, float y) {
        this(name);
        this.transform.position.set(x, y);
    }

    /**
     * Creates a new Actor instance with a given name, position, and rotation.
     * Automatically generates a unique ID and creates a new Transform component.
     * @param name The name to give to the actor.
     * @param x The x position of the actor.
     * @param y The y position of the actor.
     * @param rotation The rotation of the actor.
     */

    public Actor(String name, float x, float y, float rotation) {
        this(name, x, y);
        this.transform.rotation = rotation;
    }

    /**
     * Creates a new Actor instance with a given name, position, rotation, and scale.
     * Automatically generates a unique ID and creates a new Transform component.
     * @param name The name to give to the actor.
     * @param x The x position of the actor.
     * @param y The y position of the actor.
     * @param rotation The rotation of the actor.
     * @param scaleX The x scale of the actor.
     * @param scaleY The y scale of the actor.
     */

    public Actor(String name, float x, float y, float rotation, float scaleX, float scaleY) {
        this(name, x, y, rotation);
        this.transform.scale.set(scaleX, scaleY);
    }

    /**
     * Creates a new Actor instance with a given name, transform
     * Automatically generates a unique ID and creates a new Transform component.
     * @param name The name to give to the actor.
     * @param transform The transform to give to the actor.
     */
    public Actor(String name, Transform transform) {
        this(name);
        this.transform.copy(transform);
    }


    /**
     Initializes the object.
     <p>
     This method is called by the system when the object is first created, before it is put into service.
     It is recommended to override this method to perform any initialization such as allocating resources or
     initializing variables.
     </p>
     <p>
     The default implementation of this method does nothing.
     </p>
     <p>
     <b>Note:</b> This method should not be called directly by application code. Instead, it should be called by the
     system when the object is first created.
     </p>
     @see #destroy()
     @see #start()
     */
    public void init() {}

    public void start() {}

    //====================================================================================================
    // |=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=|
    // |--------------------------------------[ Updating ]------------------------------------------------|
    // |=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=|
    //====================================================================================================

    /**
     * Calls the update() method of all components attached to this actor.
     * @param dt The time elapsed since the last frame in seconds.
     */
    public void update(float dt) {
        for (Component component : components) {
            component.update(dt);
        }
    }

    /**
     * Calls the updateEditor() method of all components attached to this actor.
     * @param dt The time elapsed since the last frame in seconds.
     */
    public void updateEditor(float dt) {
        for (Component component : components) {
            component.updateEditor(dt);
        }
    }

    //====================================================================================================
    // |=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=|
    // |--------------------------------------[ Operations ]---------------------------------------------|
    // |=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=|
    //====================================================================================================

    /**
     * Calls the destroy() method of all components attached to this actor.
     */
    public void destroy() {
        for (Component component : components) {
            component.destroy();
        }
    }

    /**
     * Creates a copy of this actor.
     * @return A new Actor instance that is a copy of this actor.
     */
    public Actor copy() {
        // TODO: come up with cleaner solution
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Component.class, new ComponentDeserializer())
                .registerTypeAdapter(Actor.class, new ActorDeserializer())
                .enableComplexMapKeySerialization()
                .create();
        String objAsJson = gson.toJson(this);
        Actor obj = gson.fromJson(objAsJson, Actor.class);

        obj.ID = Util.generateUniqueID();
        for (Component c : obj.getAllComponents()) {
            c.generateId();
        }

        SpriteRenderer sprite = obj.getComponent(SpriteRenderer.class);
        if (sprite != null && sprite.getTexture() != null) {
            sprite.setTexture((Texture) AssetManager.getAssetFromType(sprite.getTexture().getFilepath(), Texture.class));
        }

        return obj;
    }


    //====================================================================================================
    // |=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=|
    // |--------------------------------------[ Component System ]---------------------------------------|
    // |=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=|
    //====================================================================================================

    /**

     Adds a {@link Component} to this actor's list of components, generates an ID for the component, and sets the component's actor to this actor.

     @param c the component to add to this actor's list of components

     @throws NullPointerException if the specified component is {@code null}
     */
    public void addComponent(Component c) throws NullPointerException
    {
        c.generateId();
        this.components.add(c);
        c.actor = this;

        c.start();
    }


    /**
     Removes the first occurrence of a {@link Component} of the specified {@link Class} from the internal list of components.
     @param <T> the type of the component to remove
     @param componentClass the {@link Class} object representing the type of the component to remove
     @throws NullPointerException if componentClass is null
     @see Component
     @see Class
     @see List
     @since 1.0
     */
    public <T extends Component> void removeComponent(Class<T> componentClass) throws NullPointerException
    {
        for (int i=0; i < components.size(); i++) {
            Component c = components.get(i);
            if (componentClass.isAssignableFrom(c.getClass())) {
                components.remove(i);
                return;
            }
        }
    }


    /**
     Returns the first component of the specified {@code componentClass} found in the {@code components} list, or {@code null} if none is found.
     @param <T> the type of the component
     @param componentClass the class of the component to be retrieved
     @return the first component of the specified {@code componentClass} found in the {@code components} list, or {@code null} if none is found
     @throws ClassCastException if the component cannot be cast to the specified class
     @see Class#isAssignableFrom(Class)
     @see Class#cast(Object)
     */
    public <T extends Component> T getComponent(Class<T> componentClass) throws ClassCastException
    {
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

    /**
     Returns a List of all the Components stored within this object.
     @return a List of all the Components
     @see Component
     @see List
     */
    public List<Component> getAllComponents() {
        return this.components;
    }

    //====================================================================================================
    // |=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=|
    // |----------------------------------------[ ImGui ]------------------------------------------------|
    // |=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=|
    //====================================================================================================

    /**
     Generates an imgui interface for each Component in the components list that is contained in a collapsing header
     identified by the component's simple class name.
     @implNote Uses the ImGui.collapsingHeader method to create the collapsing header.
     @implSpec The imgui method of each Component is called if and only if its associated header is expanded.
     @see <a href="https://github.com/ocornut/imgui">ImGui</a>
     @see Component#imgui()
     @throws NullPointerException if components is null or contains null elements.
     */
    public void imgui() {
        // Iterates through each Component in the components list
        for (Component c : components) {
            // If the Component's header is expanded, calls its imgui method
//            if (ImGui.collapsingHeader(c.getClass().getSimpleName())) c.imgui();
        }
    }

    //====================================================================================================
    // |=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=|
    // |----------------------------------------[ Getters ]---------------------------------------------|
    // |=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=|
    // |----------------------------------------[ Setters ]---------------------------------------------|
    // |=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=|
    //====================================================================================================

    /**
     @return the unique identifier of the object.
     */
    public long getID() {
        return this.ID;
    }
}
