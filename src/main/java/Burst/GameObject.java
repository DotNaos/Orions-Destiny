package Burst;

import components.Component;

import java.util.ArrayList;
import java.util.List;

public class GameObject {
    private static int ID_COUNTER = 0;
    private int uid = -1;
    private String name;
    private List<Component> components;
    public Transform transform;
    private int zIndex;
    private boolean doSerialization = true;

    public GameObject(String name, Transform transform, int zIndex) {
        this.name = name;
        this.components = new ArrayList<>();
        this.transform = transform;
        this.zIndex = zIndex;

        this.uid = ID_COUNTER++;
    }

    public <T extends Component> T getComponent(Class<T> componentClass) {
        for (Component c : components) {
            if (componentClass.isAssignableFrom(c.getClass())) {
                try {
                    return componentClass.cast(c);
                } catch (ClassCastException e) {
                    e.printStackTrace();
                    assert false : "Error: Could not cast component: '" + c.getClass().getName() + "' to '" + componentClass.getName() + "'";
                }
            }
        }

        return null;
    }

    public <T extends Component> void removeComponent (Class<T> componentClass) {
        for (int i = 0; i < components.size(); i++) {
            Component c = components.get(i);
            if (componentClass.isAssignableFrom(c.getClass())) {
                components.remove(i);
                return;
            }
        }
    }

    public void addComponent (Component c) {
        // check if component already exists, if so overwrite it
        for (int i = 0; i < components.size(); i++) {
            Component existingComponent = components.get(i);
            if (existingComponent.getClass().equals(c.getClass())) {
                components.set(i, c);
                c.gameObject = this;
                return;
            }
        }

        c.generateUID();
        this.components.add(c);
        c.gameObject = this;
    }

    // TODO: Maybe optimize this?
    public void update(float dt) {
        for (int i = 0; i < components.size(); i++) {
            components.get(i).update(dt);
        }
    }

    public void start()
    {
        for (int i = 0; i < components.size(); i++) {
            components.get(i).start();
        }
    }
    public void imgui() {
        for (Component component : components) {
            component.imgui();
        }
    }

    public int zIndex() {
        return this.zIndex;
    }

    public static void init(int maxId) {
        ID_COUNTER = maxId;
    }

    public int getUID() {
        return this.uid;
    }

    public List<Component> getAllComponents() {
        return this.components;
    }

    public void setNoSerialize() {
        this.doSerialization = false;
    }

    public boolean doSerialization() {
        return this.doSerialization;
    }
}
