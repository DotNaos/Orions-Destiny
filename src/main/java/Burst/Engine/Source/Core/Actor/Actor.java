package Burst.Engine.Source.Core.Actor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import Burst.Engine.Source.Core.Util.DebugMessage;
import Burst.Engine.Source.Core.Util.ImGuiValueManager;
import Burst.Engine.Source.Editor.Editor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import Burst.Engine.Source.Core.Component;
import Burst.Engine.Source.Core.Assets.AssetManager;
import Burst.Engine.Source.Core.Assets.Graphics.Sprite;
import Burst.Engine.Source.Core.Assets.Graphics.Texture;
import Burst.Engine.Source.Core.Physics.Components.Transform;
import Burst.Engine.Source.Core.Render.SpriteRenderer;
import Burst.Engine.Source.Core.Saving.ActorDeserializer;
import Burst.Engine.Source.Core.Saving.ComponentDeserializer;
import Burst.Engine.Source.Core.UI.Window;
import Burst.Engine.Source.Core.Util.Util;
import Orion.res.AssetConfig;
import imgui.ImGui;
import imgui.flag.ImGuiCol;
import imgui.flag.ImGuiStyleVar;
import imgui.flag.ImGuiTableColumnFlags;

/**
 * Represents an object in the game world that can have Components attached to
 * it.
 */
public class Actor implements ImGuiValueManager {
    public static final Texture icon = AssetManager.getAssetFromType(AssetConfig.ICON_ACTOR,Texture.class);

    /**
     * The ID of the actor.
     * This is set to -1 by default, and is set to a unique ID when the actor is
     * created.
     * This is used to identify the actor when saving and loading or when searching
     * for an actor.
     */
    private long ID = -1;
    /**
     * The name of the actor.
     * This is set to "New Actor" by default, and is set to the name of the actor
     * when the actor is created.
     * Besides being used to identify the actor, this is also used to display the
     * actor in the editor.
     */
    protected String name = "ACTOR";
    private List<ActorComponent> components = new ArrayList<>();

    /**
     * Whether this actor is serialized when saving and loading.
     */
    private boolean serializedActor = true;
    private transient Map<String, Object> initialValues = new HashMap<>();

    private transient List<String> ignoreFields = new ArrayList<>();
    private transient boolean isInitialized = false;

    //!====================================================================================================
    //!|=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=|
    //!|--------------------------------------[ Initialization ]-----------------------------------------|
    //!|=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=|
    //!====================================================================================================

    /**
     * Creates a new Actor instance with default values.
     * Automatically generates a unique ID and creates a new Transform component.
     */

    public Actor()
    {
        if (this.name.equals("ACTOR")) this.name = "Actor: " + (Window.getScene().getEditor().getActors().size() + 1);
        if (this.ID == -1) this.ID = Util.generateUniqueID();
    }

    /**
     * Initializes the object.
     * <p>
     * This method is called by the system when the object is first created, before
     * it is put into service.
     * It is recommended to override this method to perform any initialization such
     * as allocating resources or
     * initializing variables.
     * </p>
     * <p>
     * The default implementation of this method does nothing.
     * </p>
     * <p>
     * <b>Note:</b> This method should not be called directly by application code.
     * Instead, it should be called by the
     * system when the object is first created.
     * </p>
     *
     * @see #destroy()
     */
    protected void init() {
        System.out.println("Initializing Actor: " + name);

        // Get the transform component
        Transform transform = getComponent(Transform.class);
        if (transform == null) {
            // If the transform component does not exist, create it
            transform = new Transform();
            addComponent(transform);
        }


        // Adjust the actors size to match the sprite size
        SpriteRenderer spriteRenderer = getComponent(SpriteRenderer.class);
        if (spriteRenderer != null) {
            Sprite sprite = spriteRenderer.getSprite();
            if (sprite != null) {

                // set the Texture to the sprite
                if(sprite.getTexture() == null)
                {
                    Texture texture = AssetManager.getAssetFromType(sprite.getFilepath(), Texture.class);
                    sprite.setTexture(texture);
                }

                float width = sprite.getWidth();
                float height = sprite.getHeight();


                // If aspect ratio is not 1:1, adjust the size
                if (width != height)
                {
                    if(width > height)
                    {
                        transform.size.x *= sprite.getWidth() / sprite.getHeight();
                    }
                    else
                    {
                        transform.size.y *= sprite.getHeight() / sprite.getWidth();
                    }
                    DebugMessage.info("Adjusting size of '" + name + "' to: 'X = " + transform.size.x + "' and 'Y = " + transform.size.y + "' | Sprite resolution: 'X = " + width + "' 'Y = " + height + "'");

                }

                spriteRenderer.setDirty();
            }
            isInitialized = true;
        }

        ignoreFields.add("initialValues");
        ignoreFields.add("components");

        if (this.initialValues == null) {
            this.initialValues = new HashMap<>();
        }
        if (this.ignoreFields == null) {
            this.ignoreFields = new ArrayList<>();
        }
        try{
            getInitialValues(this, this.ignoreFields, this.initialValues);
        } catch (
                IllegalAccessException e) {
            e.printStackTrace();
        }

        Editor editor = Window.getScene().getEditor();

        // Add actor to the render list
        editor.getViewportRenderer().addActor(this);
        // Add actor to the physics list
        editor.getPhysics().add(this);

    }


    //!====================================================================================================
    //!|=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=|
    //!|--------------------------------------[ Updating ]------------------------------------------------|
    //!|=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=|
    //!====================================================================================================

    /**
     * Calls the update() method of all components attached to this actor.
     *
     * @param dt The time elapsed since the last frame in seconds.
     */
    public void update(float dt) {
        if(!isInitialized) {
            init();
            isInitialized = true;
        }
        for (Component component : components) {
                component.update(dt);
        }
    }


    //!====================================================================================================
    //!|=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=|
    //!|--------------------------------------[ Operations ]-----------------------------------------------|
    //!|=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=|
    //!====================================================================================================

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
     *
     * @return A new Actor instance that is a copy of this actor.
     */
    public Actor copy() {
        // TODO: come up with cleaner solution
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(ActorComponent.class, new ComponentDeserializer())
                .registerTypeAdapter(Actor.class, new ActorDeserializer())
                .enableComplexMapKeySerialization()
                .create();
        String objAsJson = gson.toJson(this);
        Actor obj = gson.fromJson(objAsJson, Actor.class);

        obj.ID = Util.generateUniqueID();

        SpriteRenderer sprite = obj.getComponent(SpriteRenderer.class);
        if (sprite != null && sprite.getTexture() != null) {
            sprite.setTexture(
                    (Texture) AssetManager.getAssetFromType(sprite.getTexture().getFilepath(), Texture.class));
        }

        return obj;
    }

    //!====================================================================================================
    //!|=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=|
    //!|--------------------------------------[ Component System ]----------------------------------------|
    //!|=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=|
    //!====================================================================================================

    /**
     * Adds a {@link Component} to this actor's list of components, generates an ID
     * for the component, and sets the component's actor to this actor.
     *
     * @param ac the component to add to this actor's list of components
     * @throws NullPointerException if the specified component is {@code null}
     */
    public ActorComponent addComponent(ActorComponent ac) throws NullPointerException {
        // Check if component is null
        if (ac == null) {
            throw new NullPointerException("Cannot add null component to actor.");
        }

        // Check if actor already has component
        if (hasComponent(ac.getClass())) {
            return getComponent(ac.getClass());
        }
        this.components.add(ac);
        ac.actor = this;

        return ac;
    }

    private boolean hasComponent(Class<? extends Component> aClass) {
        for (Component c : components) {
            if (c.getClass().equals(aClass)) {
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
    public <T extends Component> void removeComponent(Class<T> componentClass) throws NullPointerException {
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

    /**
     * Returns a List of all the Components stored within this object.
     *
     * @return a List of all the Components
     * @see Component
     * @see List
     */
    public List<ActorComponent> getAllComponents() {
        return this.components;
    }

    //!====================================================================================================
    //!|=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=|
    //!|----------------------------------------[ ImGui ]-------------------------------------------------|
    //!|=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=|
    //!====================================================================================================

    /**
     * Generates an imgui interface for each Component in the components list that
     * is contained in a collapsing header
     * identified by the component's simple class name.
     *
     * @throws NullPointerException if components is null or contains null elements.
     * @implNote Uses the ImGui.collapsingHeader method to create the collapsing
     *           header.
     * @implSpec The imgui method of each Component is called if and only if its
     *           associated header is expanded.
     * @see <a href="https://github.com/ocornut/imgui">ImGui</a>
     * @see Component#imgui()
     */
    public void imgui() {
        if(ImGui.beginTable("ActorFields", 2)){
            // The second column is max 2/3 of the size of the first column
            ImGui.tableSetupColumn("", ImGuiTableColumnFlags.WidthStretch, 0.5f);

            ImGui.pushStyleColor(ImGuiCol.Border, 1.0f, 1.0f, 1.0f, 0.2f);
            ImGui.pushStyleVar(ImGuiStyleVar.FrameBorderSize, 1.0f);
            ImGui.pushStyleVar(ImGuiStyleVar.ItemSpacing, 20f, 20f);

            ImGuiShowFields(this, this.ignoreFields, this.initialValues);

            ImGui.popStyleColor();
            ImGui.popStyleVar(2);
            ImGui.endTable();
        }


        for (Component c : components) {
            // If the Component's header is expanded, calls its imgui method
            if (ImGui.collapsingHeader(c.getClass().getSimpleName())) {
                if (ImGui.beginTable("ActorComponents", 2)) {
                    // The second column is max 2/3 of the size of the first column
                    ImGui.tableSetupColumn("", ImGuiTableColumnFlags.WidthStretch, 0.5f);

                    ImGui.pushStyleColor(ImGuiCol.Border, 1.0f, 1.0f, 1.0f, 0.2f);
                    ImGui.pushStyleVar(ImGuiStyleVar.FrameBorderSize, 1.0f);
                    ImGui.pushStyleVar(ImGuiStyleVar.ItemSpacing, 20f, 20f);
                    c.imgui();
                    ImGui.popStyleColor();
                    ImGui.popStyleVar(2);
                    ImGui.endTable();
                }
            }
        }


    }

    //!====================================================================================================
    //!|=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=|
    //!|----------------------------------------[ Getters ]------------------------------------------------|
    //!|=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=|
    //!|----------------------------------------[ Setters ]------------------------------------------------|
    //!|=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=|
    //!====================================================================================================

    /**
     * @return the unique identifier of the object.
     */
    public long getID() {
        if (this.ID == -1) {
            this.ID = Util.generateUniqueID();
        }
        return this.ID;
    }
    public Transform getTransform() {
        if (getComponent(Transform.class) == null) {
            addComponent(new Transform(this));
        }
        return getComponent(Transform.class);
    }

    public String getName() {
        return this.name;
    }


    public Actor setName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Adds a SpriteRenderer if not there 
     * and sets the sprite of the SpriteRenderer 
     * @param sprite the sprite to set
     */
    public Actor setSprite(Sprite sprite)
    {
        if (this.getComponent(SpriteRenderer.class) == null)
        {
            this.addComponent(new SpriteRenderer(this));
        }
        
        this.getComponent(SpriteRenderer.class).setSprite(sprite);

        return this;
    }

    public boolean isSerializedActor() {
        return this.serializedActor;
    }

    public Actor setNotSerializable() {
        this.serializedActor = false;
        return this;
    }




}
