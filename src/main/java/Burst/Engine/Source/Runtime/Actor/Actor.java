package Burst.Engine.Source.Runtime.Actor;

public abstract class Actor {
    protected int ID = -1;
    public abstract void init();

    public abstract void update(float dt);

    public abstract void render();

    public abstract void destroy();

    public int getID() {
        return this.ID;
    }
}
