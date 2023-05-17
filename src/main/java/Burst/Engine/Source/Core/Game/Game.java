package Burst.Engine.Source.Core.Game;

import Burst.Engine.Source.Core.Actor.Actor;
import Burst.Engine.Source.Core.Assets.Graphics.Background;
import Burst.Engine.Source.Core.Assets.Graphics.Sprite;
import Burst.Engine.Source.Core.Assets.Graphics.Texture;
import Burst.Engine.Source.Core.Component;
import Burst.Engine.Source.Core.Graphics.Input.MouseListener;
import Burst.Engine.Source.Core.Physics.Physics2D;
import Burst.Engine.Source.Core.Saving.ActorDeserializer;
import Burst.Engine.Source.Core.Saving.ComponentDeserializer;
import Burst.Engine.Source.Core.Scene.Scene;
import Burst.Engine.Source.Core.Util.DebugMessage;
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


public class Game {
    protected List<Actor> actors;
    protected List<Actor> actorsToAdd;
    protected Physics2D physics2D;
    protected Scene scene;
    protected Background background;

    public Game(Scene scene) {
        this.scene = scene;
    }

    public void init() {
        this.physics2D = new Physics2D();
        this.actors = new ArrayList<>();
        this.actorsToAdd = new ArrayList<>();

        System.out.println("\n" + scene.getOpenScene());
        scene.addPanel(new ViewportPanel());

        loadLevel();
        scene.getSceneInitializer().loadResources(this);
        start();

        // Show a debug image
        Texture tex = new Texture("assets/images/debug/blendImage1.png");
//        tex.init();
        Sprite sprite = new Sprite();
        sprite.setTexture(tex);


        this.scene.getViewportRenderer().add(new Actor(sprite, 10, 10));
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
        if (!actor.isSerializedActor()) return;
        saveLevel();
    }

    //====================================================================================================
    // |=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=|
    // |--------------------------------------[ Saving and Loading ]-------------------------------------|
    // |=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=|
    //====================================================================================================
    private Gson gsonBuilder() {
        return new GsonBuilder().setPrettyPrinting().registerTypeAdapter(Component.class, new ComponentDeserializer()).registerTypeAdapter(Actor.class, new ActorDeserializer()).enableComplexMapKeySerialization().create();
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
