package Burst.Engine.Source.Editor.Gizmo;

import Burst.Engine.Source.Core.Actor.Actor;
import Burst.Engine.Source.Core.Actor.ActorComponent;
import Burst.Engine.Source.Core.Assets.Graphics.Sprite;
import Burst.Engine.Source.Core.Component;
import Burst.Engine.Source.Core.Input.MouseListener;
import Burst.Engine.Source.Core.Physics.Components.Transform;
import Burst.Engine.Source.Core.Render.SpriteRenderer;
import Burst.Engine.Source.Core.UI.Window;
import Burst.Engine.Source.Editor.NonPickable;
import Burst.Engine.Source.Editor.Panel.PropertiesPanel;
import org.joml.Vector2f;
import org.joml.Vector2f;
import org.joml.Vector4f;

import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LEFT;

public class Gizmo extends ActorComponent {
    protected Actor activeActor = null;
    protected boolean xAxisActive = false;
    protected boolean yAxisActive = false;
    private Vector4f xAxisColor = new Vector4f(1, 0, 0, 1);
    private Vector4f xAxisColorHover = new Vector4f(0.5f, 0, 0, 1);
    private Vector4f yAxisColor = new Vector4f(0, 1, 0, 1);
    private Vector4f yAxisColorHover = new Vector4f(0, 0.5f, 0, 1);
    private Actor xAxisObject;
    private Actor yAxisObject;
    private SpriteRenderer xAxisSprite;
    private SpriteRenderer yAxisSprite;
    private Vector2f xAxisOffset = new Vector2f(24f / 80f, -6f / 80f);
    private Vector2f yAxisOffset = new Vector2f(-7f / 80f, 21f / 80f);
    private float gizmoWidth = 16f / 80f;
    private float gizmoHeight = 48f / 80f;
    private boolean using = false;

    private PropertiesPanel propertiesPanel;


    public Gizmo(Sprite arrowSprite) {
        super(null);
        this.xAxisObject = new Actor();
        this.xAxisObject.setSprite(arrowSprite);
        this.xAxisObject.transform = new Transform(new Vector2f(0), new Vector2f(gizmoWidth, gizmoHeight), 90);
        this.xAxisObject.transform.zIndex = 100;

        this.yAxisObject = new Actor();
        this.yAxisObject.setSprite(arrowSprite);
        this.yAxisObject.transform = new Transform(new Vector2f(0), new Vector2f(gizmoWidth, gizmoHeight), 180);
        this.yAxisObject.transform.zIndex = 100;

        this.xAxisSprite = this.xAxisObject.getComponent(SpriteRenderer.class);
        this.yAxisSprite = this.yAxisObject.getComponent(SpriteRenderer.class);

        this.xAxisObject.addComponent(new NonPickable());
        this.yAxisObject.addComponent(new NonPickable());

        this.propertiesPanel = Window.getScene().getPanel(PropertiesPanel.class);

        Window.getScene().getGame().addActor(this.xAxisObject);
        Window.getScene().getGame().addActor(this.yAxisObject);
    }

    @Override
    public void start() {
        this.xAxisObject.transform.zIndex = 100;
        this.yAxisObject.transform.zIndex = 100;
        this.xAxisObject.setNotSerializable();
        this.yAxisObject.setNotSerializable();
    }

    @Override
    public void update(float dt) {
        if (using) {
            this.setInactive();
        }
        this.xAxisObject.getComponent(SpriteRenderer.class).setColor(new Vector4f(0, 0, 0, 0));
        this.yAxisObject.getComponent(SpriteRenderer.class).setColor(new Vector4f(0, 0, 0, 0));
    }

    @Override
    public void updateEditor(float dt) {
        if (!using) return;

        this.activeActor = propertiesPanel.getActiveGameObject();
        if (this.activeActor != null) {
            this.setActive();
        } else {
            this.setInactive();
            return;
        }

        boolean xAxisHot = checkXHoverState();
        boolean yAxisHot = checkYHoverState();

        if ((xAxisHot || xAxisActive) && MouseListener.isDragging() && MouseListener.mouseButtonDown(GLFW_MOUSE_BUTTON_LEFT)) {
            xAxisActive = true;
            yAxisActive = false;
        } else if ((yAxisHot || yAxisActive) && MouseListener.isDragging() && MouseListener.mouseButtonDown(GLFW_MOUSE_BUTTON_LEFT)) {
            yAxisActive = true;
            xAxisActive = false;
        } else {
            xAxisActive = false;
            yAxisActive = false;
        }

        if (this.activeActor != null) {
            this.xAxisObject.transform.position.set(this.activeActor.transform.position);
            this.yAxisObject.transform.position.set(this.activeActor.transform.position);
            this.xAxisObject.transform.position.add(this.xAxisOffset);
            this.yAxisObject.transform.position.add(this.yAxisOffset);
        }
    }

    private void setActive() {
        this.xAxisSprite.setColor(xAxisColor);
        this.yAxisSprite.setColor(yAxisColor);
    }

    private void setInactive() {
        this.activeActor = null;
        this.xAxisSprite.setColor(new Vector4f(0, 0, 0, 0));
        this.yAxisSprite.setColor(new Vector4f(0, 0, 0, 0));
    }

    private boolean checkXHoverState() {
        Vector2f mousePos = new Vector2f(MouseListener.getWorldX(), MouseListener.getWorldY());
        if (mousePos.x <= xAxisObject.transform.position.x + (gizmoHeight / 2.0f) && mousePos.x >= xAxisObject.transform.position.x - (gizmoWidth / 2.0f) && mousePos.y >= xAxisObject.transform.position.y - (gizmoHeight / 2.0f) && mousePos.y <= xAxisObject.transform.position.y + (gizmoWidth / 2.0f)) {
            xAxisSprite.setColor(xAxisColorHover);
            return true;
        }

        xAxisSprite.setColor(xAxisColor);
        return false;
    }

    private boolean checkYHoverState() {
        Vector2f mousePos = new Vector2f(MouseListener.getWorldX(), MouseListener.getWorldY());
        if (mousePos.x <= yAxisObject.transform.position.x + (gizmoWidth / 2.0f) && mousePos.x >= yAxisObject.transform.position.x - (gizmoWidth / 2.0f) && mousePos.y <= yAxisObject.transform.position.y + (gizmoHeight / 2.0f) && mousePos.y >= yAxisObject.transform.position.y - (gizmoHeight / 2.0f)) {
            yAxisSprite.setColor(yAxisColorHover);
            return true;
        }

        yAxisSprite.setColor(yAxisColor);
        return false;
    }

    public void setUsing() {
        this.using = true;
    }

    public void setNotUsing() {
        this.using = false;
        this.setInactive();
    }
}
