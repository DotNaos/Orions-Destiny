package Burst.Engine.Source.Core.Actor;

import Burst.Engine.Source.Core.Assets.AssetManager;
import Burst.Engine.Source.Core.Assets.Graphics.Sprite;
import Burst.Engine.Source.Core.Component;
import Burst.Engine.Source.Core.Physics.Components.Transform;
import Burst.Engine.Source.Core.Render.SpriteRenderer;
import Burst.Engine.Source.Core.Saving.ActorDeserializer;
import Burst.Engine.Source.Core.Saving.ComponentDeserializer;
import Burst.Engine.Source.Core.UI.Window;
import Burst.Engine.Source.Core.Util.ImGuiValueManager;
import Burst.Engine.Source.Core.Util.Util;
import Burst.Engine.Source.Game.Game;
import Orion.res.AssetConfig;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import imgui.ImGui;
import imgui.flag.ImGuiCol;
import imgui.flag.ImGuiStyleVar;
import imgui.flag.ImGuiTableColumnFlags;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Oliver Schuetz
 * Represents an object in the game world that can have Components attached to
 * it.
 */
public class Actor implements ImGuiValueManager {
  public Sprite icon = AssetManager.getAssetFromType(AssetConfig.Files.Images.Icons.ACTOR, Sprite.class);

  /**
   * The name of the actor.
   * This is set to "New Actor" by default, and is set to the name of the actor
   * when the actor is created.
   * Besides being used to identify the actor, this is also used to display the
   * actor in the editor.
   */
  protected String name = "ACTOR";

  protected String type = this.getClass().getCanonicalName();
  /**
   * The ID of the actor.
   * This is set to -1 by default, and is set to a unique ID when the actor is
   * created.
   * This is used to identify the actor when saving and loading or when searching
   * for an actor.
   */
  private long ID = -1;
  private List<ActorComponent> components = new ArrayList<>();

  /**
   * Whether this actor is serialized when saving and loading.
   */
  private boolean serializedActor = true;
  /**
   * If this flag is set to true, the actors size the size will adjust to the Texture aspect ratio.
   *
   * @see #adjustSizeToSprite()
   */
  private boolean adjustSizeToTexture = false;

  /**
   * Values of all fields at the time of initialization.
   */
  private transient Map<String, Object> initialValues = new HashMap<>();


  /**
   * Fields not shown in imgui.
   */
  private transient List<String> ignoreFields = new ArrayList<>();


  /**
   * Flag indicating whether the actor is initialized.
   * This is set to false by default, and is set to true when the actor is
   * initialized.
   * This makes sure that the actor is always initialized before it is used.
   */
  private transient boolean isInitialized = false;

  /**
   * Indicating can select the actor by clicking on it.
   */
  private boolean pickable = true;

  //!====================================================================================================
  //!|=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=|
  //!|--------------------------------------[ Initialization ]-----------------------------------------|
  //!|=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=|
  //!====================================================================================================

  /**
   * Creates a new Actor instance with default values.
   * Automatically generates a unique ID and creates a new Transform component.
   */

  public Actor() {
    if (this.name.equals("ACTOR")) this.name = "Actor " + this.ID;
  }

  public Actor(int id) {
    this.ID = id;
    this.serializedActor = false;
    this.name = "TEMPORARY ACTOR";
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
    if (this.ID == -1) this.ID = Util.generateUniqueID();

    if (!hasComponent(Transform.class)) addComponent(new Transform());

    if (!hasComponent(SpriteRenderer.class)) addComponent(new SpriteRenderer().setSprite(this.icon));

    if (this.initialValues == null) this.initialValues = new HashMap<>();
    this.ignoreFields = new ArrayList<>();

    ignoreFields.add("initialValues");
    ignoreFields.add("components");

    getInitialValues(this, this.ignoreFields, this.initialValues);

    Game game = Window.getScene().getGame();

    // Add actor to the render list
    game.getViewportRenderer().addActor(this);

    // Add actor to the physics list
    game.getPhysics().add(this);

    isInitialized = true;
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
  public void updateEditor(float dt) {
    if (!isInitialized) {
      init();
    }
    if (adjustSizeToTexture) {
      adjustSizeToSprite();
    }

    for (Component component : components) {
      component.updateEditor(dt);
    }
  }

  public void update(float dt) {
    if (!isInitialized) {
      init();
    }

    if (adjustSizeToTexture) {
      adjustSizeToSprite();

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
    Window.getScene().getGame().removeActor(this);
    for (Component component : components) {
      component.destroy();
    }
    this.components = new ArrayList<>();
    this.initialValues = new HashMap<>();
    this.ignoreFields = new ArrayList<>();
    this.isInitialized = false;
  }

  /**
   * Creates a copy of this actor.
   *
   * @return A new Actor instance that is a copy of this actor.
   */
  public Actor copy() {        // TODO: come up with cleaner solution
    Gson gson = new GsonBuilder().registerTypeAdapter(ActorComponent.class, new ComponentDeserializer()).registerTypeAdapter(Actor.class, new ActorDeserializer()).enableComplexMapKeySerialization().create();
    String objAsJson = gson.toJson(this);
    Object copy = gson.fromJson(objAsJson, Actor.class).generateName();

    return (Actor) copy;
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
  public Actor addComponent(ActorComponent ac) throws NullPointerException {
    // Check if component is null
    if (ac == null) {
      throw new NullPointerException("Cannot add null component to actor.");
    }

    // Check if actor already has component
    if (hasComponent(ac.getClass())) {
      System.err.println("Actor already has component of type " + ac.getClass().getSimpleName());
      return this;
    }

    this.components.add(ac);
    ac.actor = this;
    ac.init();


    return this;
  }

  /**
   * @param aClass the {@link Class} object representing the type of component to
   * @return
   */
  public boolean hasComponent(Class<? extends Component> aClass) {
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
   * the {@code components} list, or {@code null} if none is found
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
   * header.
   * @implSpec The imgui method of each Component is called if and only if its
   * associated header is expanded.
   * @see <a href="https://github.com/ocornut/imgui">ImGui</a>
   * @see Component#imgui()
   */
  public void imgui(Object o) {
    if (ImGui.beginTable("ActorFields", 2)) {
      // The second column is max 2/3 of the size of the first column
      ImGui.tableSetupColumn("", ImGuiTableColumnFlags.WidthStretch, 0.5f);

      ImGui.pushStyleColor(ImGuiCol.Border, 1.0f, 1.0f, 1.0f, 0.2f);
      ImGui.pushStyleVar(ImGuiStyleVar.FrameBorderSize, 1.0f);
      ImGui.pushStyleVar(ImGuiStyleVar.ItemSpacing, 20f, 20f);

      ImGuiShowFields(o, this.ignoreFields, this.initialValues);

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

  public void imgui() {
    imgui(this);
  }

  //!====================================================================================================
  //!|=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=|
  //!|----------------------------------------[ Getters ]------------------------------------------------|
  //!|=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=|
  //!|----------------------------------------[ Setters ]------------------------------------------------|
  //!|=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=|
  //!====================================================================================================

  /**
   * If the object does not have a unique identifier, one is generated.
   *
   * @return the unique identifier of the object.
   */
  public long getID() {
    if (this.ID == -1) {
      this.ID = Util.generateUniqueID();
    }
    return this.ID;
  }

  /**
   * If the object does not have a {@link Transform} component, one is added.
   *
   * @return the {@link Transform} component of the object.
   */
  public Transform getTransform() {
    if (getComponent(Transform.class) == null) {
      addComponent(new Transform());
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
   * Generates a name for the object based on its class name and count of objects
   *
   * @return the {@link SpriteRenderer} component of the object.
   */
  private Actor generateName() {
    this.name = this.getClass().getSimpleName() + " " + (Window.getScene().getGame().getActors().size() + 1);
    return this;
  }

  /**
   * Adds a SpriteRenderer if not there
   * and sets the sprite of the SpriteRenderer
   *
   * @param sprite the sprite to set
   */
  public Actor setSprite(Sprite sprite) {
    if (this.getComponent(SpriteRenderer.class) == null) {
      this.addComponent(new SpriteRenderer());
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


  /**
   * Sets the Actor dirty.
   * Dirty Textures are redrawn on the next frame.
   *
   * @implNote If the Actor has a {@link SpriteRenderer} component, its
   * {@link SpriteRenderer#setDirty()} method is called.
   * @see SpriteRenderer#setDirty()
   */
  public void setDirty() {
    SpriteRenderer sr = getComponent(SpriteRenderer.class);
    if (sr != null) {
      sr.setDirty();
    }
  }



  public void adjustSizeToSprite() {
    adjustSizeToSprite(false);
  }

  private void adjustSizeToSprite(boolean onStart) {
    Transform transform = getTransform();
    SpriteRenderer spriteRenderer = getComponent(SpriteRenderer.class);

    if (transform == null || spriteRenderer == null) return;

    Sprite sprite = spriteRenderer.getSprite();

    if (sprite == null) return;


    float width = sprite.getWidth();
    float height = sprite.getHeight();

    float spriteAspectRatio = width / height;
    float actorAspectRatio = transform.size.x / transform.size.y;

    if (onStart) {
      if (spriteAspectRatio != 1) {
        transform.size.y = transform.size.x / spriteAspectRatio;
      } else {
        transform.size.x = transform.size.y * spriteAspectRatio;
      }

    } else {
      if (spriteAspectRatio > actorAspectRatio) {
        transform.size.x = transform.size.y * spriteAspectRatio;
      } else {
        transform.size.y = transform.size.x / spriteAspectRatio;
      }
    }


    spriteRenderer.setDirty();
    adjustSizeToTexture = false;
  }

  public boolean isPickable() {
    return pickable;
  }

  public boolean isNotPickable() {
    return !pickable;
  }

  public Actor setPickable(boolean pickable) {
    this.pickable = pickable;
    return this;
  }

  /**
   * IMPORTANT: Call before init()
   *
   * @param fieldName Ignore this field when generating the imgui interface
   */
  public void addIgnoreField(String fieldName) {
    this.ignoreFields.add(fieldName);
  }

  public void setSerializable(boolean b) {
    this.serializedActor = b;
  }

  public void setSerializable() {
    this.serializedActor = true;
  }

  public boolean shouldAdjustToSize() {
    return this.adjustSizeToTexture;
  }

  public void setNotAdjustToSize() {
    this.adjustSizeToTexture = false;
  }

  public Sprite getIcon() {
    return this.icon;
  }

  public void genID() {
    this.ID = Util.generateUniqueID();
  }
}
