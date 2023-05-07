package Burst.Engine.Source.Core.Game;

import Burst.Engine.Source.Core.Actor.Actor;
import Burst.Engine.Source.Core.Component;
import Burst.Engine.Source.Core.Graphics.Input.MouseListener;
import Burst.Engine.Source.Core.Physics.Physics2D;
import Burst.Engine.Source.Core.Saving.ComponentDeserializer;
import Burst.Engine.Source.Core.Saving.GameObjectDeserializer;
import Burst.Engine.Source.Core.Scene.Scene;
import Burst.Engine.Source.Core.util.DebugMessage;
import Burst.Engine.Source.Editor.Panel.ViewportPanel;
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


public class Game{
    protected List<Actor> actors;
    protected List<Actor> pendingObjects;
    protected Physics2D physics2D;
    protected Scene scene;

    public Game(Scene scene) {
        this.scene = scene;
    }

    public void init()
    {
        this.physics2D = new Physics2D();
        this.actors = new ArrayList<>();
        this.pendingObjects = new ArrayList<>();


        System.out.println("\n" + scene.getOpenScene());
        scene.addPanel(new ViewportPanel());

        loadLevel();

        scene.getSceneInitializer().loadResources(this);
        start();
    }

    //====================================================================================================
    // Scene States
    //====================================================================================================
    
    public void start() {
        for (Actor go : actors) {
            go.start();
            scene.getViewportRenderer().add(go);
            this.physics2D.add(go);
        }
    }


    public void destroy() {
        for (Actor go : actors) {
            go.destroy();
        }
    }
    
    public void update(float dt) {
        scene.getCamera().adjustProjection();
        this.physics2D.update(dt);

        for (int i = 0; i < actors.size(); i++) {
            Actor go = actors.get(i);
            go.update(dt);

            if (go.isDead()) {
                actors.remove(i);
                scene.getViewportRenderer().destroyGameObject(go);
                this.physics2D.destroyGameObject(go);
                i--;
            }
        }

        for (Actor go : pendingObjects) {
            actors.add(go);
            go.start();
            scene.getViewportRenderer().add(go);
            this.physics2D.add(go);
        }
        pendingObjects.clear();
    }




    public void addActor(Actor go) {
        if (scene.isPaused()) {
            actors.add(go);
        } else {
            pendingObjects.add(go);
        }
    }

    
    public Actor spawnActor(String name) {
        return new Actor(name);
    }

    //====================================================================================================
    // Serialization
    //====================================================================================================
    
    public void saveLevel() {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(Component.class, new ComponentDeserializer())
                .registerTypeAdapter(Actor.class, new GameObjectDeserializer())
                .enableComplexMapKeySerialization()
                .create();

        try {
            FileWriter writer = new FileWriter("level.json");
            List<Actor> objsToSerialize = new ArrayList<>();
            for (Actor obj : this.actors) {
                if (obj.doSerialization()) {
                    objsToSerialize.add(obj);
                }
            }
            writer.write(gson.toJson(objsToSerialize));
            writer.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void loadLevel() {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(Component.class, new ComponentDeserializer())
                .registerTypeAdapter(Actor.class, new GameObjectDeserializer())
                .enableComplexMapKeySerialization()
                .create();

        String inFile = "";
        try {
            inFile = new String(Files.readAllBytes(Paths.get("level.json")));
        } catch (IOException e)
        {
            if (e instanceof NoSuchFileException) {
                DebugMessage.notFound("Level Not Found");
            } else {
                DebugMessage.error("Error Loading Level");
            }
        }

        if (!inFile.equals("")) {
            long maxGoId = -1;
            long maxCompId = -1;
            Actor[] objs = gson.fromJson(inFile, Actor[].class);
            for (int i=0; i < objs.length; i++) {
                addActor(objs[i]);

                for (Component c : objs[i].getAllComponents()) {
                    if (c.getUid() > maxCompId) {
                        maxCompId = c.getUid();
                    }
                }
                if (objs[i].getUid() > maxGoId) {
                    maxGoId = objs[i].getUid();
                }
            }

            maxGoId++;
            maxCompId++;
            Actor.init((int) maxGoId);
            Component.init((int) maxCompId);
        }
    }
    
    
    //====================================================================================================
    // All Mouse and Keyboard callbacks
    //====================================================================================================

    /**
     * @param window
     * @param button
     * @param action
     * @param mods
     */

    public void mouseButtonCallback(long window, int button, int action, int mods) {
        if (!scene.getPanel(ViewportPanel.class).getWantCaptureMouse()) return;

        MouseListener.mouseButtonCallback(window, button, action, mods);
    }


    public void mousePositionCallback(long window, double xpos, double ypos) {
        if (scene.getPanel(ViewportPanel.class).getWantCaptureMouse()) return;

        MouseListener.clear();

    }


    //====================================================================================================
    // All getters and setters
    //====================================================================================================
    public Physics2D getPhysics() {
        return this.physics2D;
    }

    public <T extends Component> Actor getActorWith(Class<T> gameObjectClass) {
        for (Actor go : actors) {
            if (go.getComponent(gameObjectClass) != null) {
                return go;
            }
        }

        return null;
    }

    public List<Actor> getGameObjects() {
        return this.actors;
    }

    public Actor getGameObject(int gameObjectId) {
        Optional<Actor> result = this.actors.stream()
                .filter(gameObject -> gameObject.getUid() == gameObjectId)
                .findFirst();
        return result.orElse(null);
    }

    public Actor getGameObject(String gameObjectName) {
        Optional<Actor> result = this.actors.stream()
                .filter(gameObject -> gameObject.name.equals(gameObjectName))
                .findFirst();
        return result.orElse(null);
    }

    public List<Actor> getActors() {
        return actors;
    }

    public void imgui() {
        for (Actor go : actors) {
            go.imgui();
        }
    }
}