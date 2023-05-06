package Burst.Engine.Source.Core;


import Burst.Engine.Source.Core.Assets.Graphics.Texture;
import Burst.Engine.Source.Core.Physics.Components.Transform;
import Burst.Engine.Source.Core.Saving.ComponentDeserializer;
import Burst.Engine.Source.Core.Saving.GameObjectDeserializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import Burst.Engine.Source.Core.Graphics.Render.SpriteRenderer;
import imgui.ImGui;
import Burst.Engine.Source.Core.Assets.AssetManager;

import java.util.ArrayList;
import java.util.List;

public class Actor {
    private long ID = -1;
    private static int ActorCounter = 0;

    public String name;
    private List<Component> components;
    public  transient Transform transform;
    private boolean doSerialization = true;
    private boolean isDead = false;

    public Actor(String name) {
        this.name = name;
        this.components = new ArrayList<>();

        this.ID = ActorCounter++;
    }

    public <T extends Component> T getComponent(Class<T> componentClass) {
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

    public <T extends Component> void removeComponent(Class<T> componentClass) {
        for (int i=0; i < components.size(); i++) {
            Component c = components.get(i);
            if (componentClass.isAssignableFrom(c.getClass())) {
                components.remove(i);
                return;
            }
        }
    }

    public void addComponent(Component c) {
        c.generateId();
        this.components.add(c);
        c.actor = this;
    }

    public void update(float dt) {
        for (int i=0; i < components.size(); i++) {
            components.get(i).update(dt);
        }
    }

    public void updateEditor(float dt) {
        for (int i=0; i < components.size(); i++) {
            components.get(i).updateEditor(dt);
        }
    }

    public void start() {
        for (int i=0; i < components.size(); i++) {
            components.get(i).start();
        }
    }

    public void imgui() {
        for (Component c : components) {
            if (ImGui.collapsingHeader(c.getClass().getSimpleName()))
                c.imgui();
        }
    }

    public void destroy() {
        this.isDead = true;
        for (int i=0; i < components.size(); i++) {
            components.get(i).destroy();
        }
    }

    public Actor copy() {
        // TODO: come up with cleaner solution
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Component.class, new ComponentDeserializer())
                .registerTypeAdapter(Actor.class, new GameObjectDeserializer())
                .enableComplexMapKeySerialization()
                .create();
        String objAsJson = gson.toJson(this);
        Actor obj = gson.fromJson(objAsJson, Actor.class);

        obj.generateUid();
        for (Component c : obj.getAllComponents()) {
            c.generateId();
        }

        SpriteRenderer sprite = obj.getComponent(SpriteRenderer.class);
        if (sprite != null && sprite.getTexture() != null) {
            sprite.setTexture((Texture) AssetManager.getAssetFromType(sprite.getTexture().getFilepath(), Texture.class));
        }

        return obj;
    }

    public boolean isDead() {
        return this.isDead;
    }

    public static void init(int maxId) {
        ActorCounter = maxId;
    }

    public long getUid() {
        return this.ID;
    }

    public List<Component> getAllComponents() {
        return this.components;
    }

    public void setNoSerialize() {
        this.doSerialization = false;
    }

    public void generateUid() {
        this.ID = ActorCounter++;
    }

    public boolean doSerialization() {
        return this.doSerialization;
    }

}
