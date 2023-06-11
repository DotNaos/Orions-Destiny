package Burst.Engine.Source.Game;

import Burst.Engine.Config.ImGuiStyleConfig;
import Burst.Engine.Source.Core.Actor.Actor;
import Burst.Engine.Source.Core.Actor.ActorComponent;
import Burst.Engine.Source.Core.Actor.PlayerController;
import Burst.Engine.Source.Core.Component;
import Burst.Engine.Source.Core.Physics.Physics2D;
import Burst.Engine.Source.Core.Render.PickingTexture;
import Burst.Engine.Source.Core.Render.ViewportRenderer;
import Burst.Engine.Source.Core.Saving.ActorDeserializer;
import Burst.Engine.Source.Core.Saving.ComponentDeserializer;
import Burst.Engine.Source.Core.Scene.Scene;
import Burst.Engine.Source.Core.Scene.SceneType;
import Burst.Engine.Source.Core.UI.Window;
import Burst.Engine.Source.Core.Util.DebugMessage;
import Burst.Engine.Source.Editor.Components.GridLines;
import Burst.Engine.Source.Editor.Components.KeyControls;
import Burst.Engine.Source.Editor.Components.MouseControls;
import Burst.Engine.Source.Editor.ContentDrawer;
import Burst.Engine.Source.Editor.EditorCamera;
import Burst.Engine.Source.Editor.Panel.OutlinerPanel;
import Burst.Engine.Source.Editor.Panel.PropertiesPanel;
import Burst.Engine.Source.Editor.Panel.ViewportPanel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import imgui.ImGui;
import imgui.flag.ImGuiCol;
import imgui.flag.ImGuiStyleVar;
import imgui.flag.ImGuiTableColumnFlags;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.*;


public class Game {
  protected List<Actor> actors = new ArrayList<>();
  protected List<Actor> actorsToAdd = new ArrayList<>();
  protected List<Component> components = new ArrayList<>();
  protected Physics2D physics2D = new Physics2D();
  protected Scene scene;
  private ViewportRenderer viewportRenderer = new ViewportRenderer();
  protected transient boolean isInitialized = false;


  // Timer for saving
  private Timer timer = new Timer();

  public Game(Scene scene) {
    this.scene = scene;
  }

  public void init() {
    if (isInitialized) return;
    DebugMessage.info("Game initializing...");
    DebugMessage.info("Opening Scene: " + scene.getOpenScene());

    DebugMessage.header("Game Panels");
    scene.addPanel(new ViewportPanel());

    loadLevel();

    timer.scheduleAtFixedRate(new java.util.TimerTask() {
      @Override
      public void run() {
        saveLevel();
      }
    }, 1000, 1000);


    DebugMessage.info("Game initialized!");
    if (inGame()) {
      isInitialized = true;
      return;
    }

    DebugMessage.info("Editor initializing...");
    // Variables
    this.components.add(Window.get().getPickingTexture());
    this.components.add(new EditorCamera(this.scene.getViewport()));

    // Panels
    DebugMessage.header("Editor Panels");
    scene.addPanel(new ContentDrawer());
    scene.addPanel(new PropertiesPanel(getComponent(PickingTexture.class)));
    scene.addPanel(new OutlinerPanel());

    // Level Editor Stuff
    this.components.add(new MouseControls());
    this.components.add(new KeyControls());
    this.components.add(new GridLines());

    DebugMessage.info("Editor initialized!");


    isInitialized = true;
  }

  private boolean inGame() {
    return scene.getOpenScene() == SceneType.GAME;
  }
  private boolean inEditor() {
    return scene.getOpenScene() == SceneType.EDITOR;
  }



  //! ====================================================================================================
  //! |=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=|
  //! |--------------------------------------[ Game Loop ]----------------------------------------------|
  //! |=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=|
  //! ====================================================================================================

  public void destroy() {
    for (Actor actor : actors) {
      actor.destroy();
    }
  }

  public void update(float dt) {
    init();

    scene.getViewport().adjustProjection();

    this.physics2D.update(dt);

    for (Actor actor : actors) {
      actor.update(dt);
    }

    for (Component c : components) {
      c.update(dt);
    }

    // set the viewport position to the position of the first actor with a player controller

    for ( Actor actor : actors ) {
      if (actor.getComponent(PlayerController.class) != null) {
        scene.getViewport().position.x = actor.getTransform().position.x;
        scene.getViewport().position.y = actor.getTransform().position.y;
        break;
      }
    }



  }

  public void updateEditor(float dt) {
    init();


    scene.getViewport().adjustProjection();

    for (Actor actor : actors) {
      actor.updateEditor(dt);
    }

    for (Component c : components) {
      c.updateEditor(dt);
    }
  }



  public void addActor(Actor actor) {
    actors.add(actor);

    // Add the actor to the physics engine
    this.physics2D.add(actor);
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
    this.components.add(c);

    c.init();
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


  //! ====================================================================================================
  //! |=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=|
  //! |--------------------------------------[ Saving and Loading ]-------------------------------------|
  //! |=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=|
  //! ====================================================================================================
  private Gson gsonBuilder() {
    return new GsonBuilder().setPrettyPrinting().registerTypeAdapter(ActorComponent.class, new ComponentDeserializer()).registerTypeAdapter(Actor.class, new ActorDeserializer()).enableComplexMapKeySerialization().create();
  }

  public void saveLevel() {
    // return if a dialog is open
    if (Window.isDialogOpen()) {
      System.out.println("Dialog open, not saving level");
      return;

    } else if (!inEditor() ) {
      System.out.println("Not in editor, not saving level");
      return;
    }
//    System.out.println("Saving level...");

    try {
      FileWriter writer = new FileWriter(".\\levels\\level.json");
      List<Actor> actorsToSerialize = new ArrayList<>();
      for (Actor actor : this.actors) {
        if (actor.isSerializedActor()) {
          actorsToSerialize.add(actor);
//                    System.out.println("Added actor to serialize " + actor.getName());
        }
      }
      writer.write(gsonBuilder().toJson(actorsToSerialize));
      writer.close();
    } catch (Exception e) {
        DebugMessage.error("Error Saving Level");
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

      this.actors.addAll(Arrays.asList(actors));
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

  public void imgui() {

    if (inGame()) return;

    // Start the tab bar
    ImGui.begin("Editor");
    if (ImGui.beginTabBar("EditorTabs")) {
      if (ImGui.beginTabItem("Style")) {
        if (ImGui.beginTable("EditorStyle", 2)) {

          // The second column is max 2/3 of the size of the first column
          ImGui.tableSetupColumn("", ImGuiTableColumnFlags.WidthStretch, 0.5f);

          ImGui.pushStyleColor(ImGuiCol.Border, 1.0f, 1.0f, 1.0f, 0.2f);
          ImGui.pushStyleVar(ImGuiStyleVar.FrameBorderSize, 1.0f);
          ImGui.pushStyleVar(ImGuiStyleVar.ItemSpacing, 20f, 20f);

          ImGuiStyleConfig.get().imgui();

          ImGui.popStyleColor();
          ImGui.popStyleVar(2);


          ImGui.endTable();
          ImGui.endTabItem();
        }
      }

      // Components imgui
      if (ImGui.beginTabItem("Components")) {

        for (Component component : components) {
          if (ImGui.collapsingHeader(component.getClass().getSimpleName())) {
            if (ImGui.beginTable("EditorComponents", 2)) {
              // The second column is max 2/3 of the size of the first column
              ImGui.tableSetupColumn("", ImGuiTableColumnFlags.WidthStretch, 0.5f);

              ImGui.pushStyleColor(ImGuiCol.Border, 1.0f, 1.0f, 1.0f, 0.2f);
              ImGui.pushStyleVar(ImGuiStyleVar.FrameBorderSize, 1.0f);
              ImGui.pushStyleVar(ImGuiStyleVar.ItemSpacing, 20f, 20f);

              component.imgui();

              ImGui.popStyleColor();
              ImGui.popStyleVar(2);

              ImGui.endTable();
            }
          }
        }
        ImGui.endTabItem();
      }


      ImGui.endTabBar();
    }
    ImGui.end();
  }

  public void render() {
    this.viewportRenderer.render();
  }

  public void removeActor(Actor actor) {
    viewportRenderer.destroyActor(actor);
    this.actors.remove(actor);
  }

  public ViewportRenderer getViewportRenderer() {
    return this.viewportRenderer;
  }
}
