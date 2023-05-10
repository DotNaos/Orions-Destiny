package Burst.Engine.Source.Core.Game;

import Burst.Engine.Source.Core.Actor.Actor;
import Burst.Engine.Source.Core.Assets.Graphics.Background;
import Burst.Engine.Source.Core.Component;
import Burst.Engine.Source.Core.Graphics.Input.MouseListener;
import Burst.Engine.Source.Core.Physics.Physics2D;
import Burst.Engine.Source.Core.Saving.ActorDeserializer;
import Burst.Engine.Source.Core.Saving.ComponentDeserializer;

import Burst.Engine.Source.Core.Scene.Scene;
import Burst.Engine.Source.Core.util.DebugMessage;
import Burst.Engine.Source.Editor.Panel.ViewportPanel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.swing.*;
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
    protected List<Actor> actorsToAdd;
    protected Physics2D physics2D;
    protected Scene scene;
    protected Background background;

    public Game(Scene scene) {
        this.scene = scene;
    }

    public void init()
    {
        this.physics2D = new Physics2D();
        this.actors = new ArrayList<>();
        this.actorsToAdd = new ArrayList<>();

        System.out.println("\n" + scene.getOpenScene());
        scene.addPanel(new ViewportPanel());

        loadLevel();
        scene.getSceneInitializer().loadResources(this);
        start();
    }

    //====================================================================================================
    // |=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=|
    // |--------------------------------------[ Game Loop ]----------------------------------------------|
    // |=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=|
    //====================================================================================================
    
    public void start() {
        for (Actor actor : actors) {
            actor.start();
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
        scene.getViewport().adjustProjection();
        this.physics2D.update(dt);

        for (Actor actor : actors) {
            actor.update(dt);
            scene.getViewportRenderer().render();
        }
    }




    public void addActor(Actor actor) {
            actors.add(actor);
    }

    
    public Actor spawnActor(String name) {
        return new Actor(name);
    }


    //====================================================================================================
    // |=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=|
    // |--------------------------------------[ Saving and Loading ]-------------------------------------|
    // |=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=|
    //====================================================================================================
    
    public void saveLevel() {

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(Component.class, new ComponentDeserializer())
                .registerTypeAdapter(Actor.class, new ActorDeserializer())
                .enableComplexMapKeySerialization()
                .create();

        try {
            FileWriter writer = new FileWriter("level.json");
            List<Actor> objsToSerialize = new ArrayList<>();
            for (Actor obj : this.actors) {
                if (obj.serializedActor) {
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
                .registerTypeAdapter(Actor.class, new ActorDeserializer())
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
            Actor[] objs = gson.fromJson(inFile, Actor[].class);
            for (Actor obj : objs) {
                addActor(obj);
            }
        }
    }
    
    
    //====================================================================================================
    // |=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=|
    // |--------------------------------------[ Input Callbacks ]----------------------------------------|
    // |=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=|
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
    // |=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=|
    // |--------------------------------------[ Getters and Setters ]------------------------------------|
    // |=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=|
    //====================================================================================================
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
        Optional<Actor> result = this.actors.stream()
                .filter(Actor -> Actor.getID() == ActorId)
                .findFirst();
        return result.orElse(null);
    }

    public Actor getActor(String ActorName) {
        Optional<Actor> result = this.actors.stream()
                .filter(Actor -> Actor.name.equals(ActorName))
                .findFirst();
        return result.orElse(null);
    }

    public List<Actor> getActors() {
        return actors;
    }

    //====================================================================================================
    // |=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=|
    // |--------------------------------------[ ImGui ]--------------------------------------------------|
    // |=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=|
    //====================================================================================================

    public void imgui() {
        for (Actor actor : actors) {
            actor.imgui();
        }
    }
}
