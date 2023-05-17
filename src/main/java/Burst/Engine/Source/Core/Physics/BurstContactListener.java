package Burst.Engine.Source.Core.Physics;

import Burst.Engine.Source.Core.Actor.Actor;
import Burst.Engine.Source.Core.Component;
import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.collision.WorldManifold;
import org.jbox2d.dynamics.contacts.Contact;
import org.joml.Vector3f;

public class BurstContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Actor objA = (Actor) contact.getFixtureA().getUserData();
        Actor objB = (Actor) contact.getFixtureB().getUserData();
        WorldManifold worldManifold = new WorldManifold();
        contact.getWorldManifold(worldManifold);
        Vector3f aNormal = new Vector3f(worldManifold.normal.x, worldManifold.normal.y, 0);
        Vector3f bNormal = new Vector3f(aNormal).negate();

        for (Component c : objA.getAllComponents()) {
            c.beginCollision(objB, contact, aNormal);
        }

        for (Component c : objB.getAllComponents()) {
            c.beginCollision(objA, contact, bNormal);
        }
    }

    @Override
    public void endContact(Contact contact) {
        Actor objA = (Actor) contact.getFixtureA().getUserData();
        Actor objB = (Actor) contact.getFixtureB().getUserData();
        WorldManifold worldManifold = new WorldManifold();
        contact.getWorldManifold(worldManifold);
        Vector3f aNormal = new Vector3f(worldManifold.normal.x, worldManifold.normal.y, 0);
        Vector3f bNormal = new Vector3f(aNormal).negate();

        for (Component c : objA.getAllComponents()) {
            c.endCollision(objB, contact, aNormal);
        }

        for (Component c : objB.getAllComponents()) {
            c.endCollision(objA, contact, bNormal);
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold manifold) {
        Actor objA = (Actor) contact.getFixtureA().getUserData();
        Actor objB = (Actor) contact.getFixtureB().getUserData();
        WorldManifold worldManifold = new WorldManifold();
        contact.getWorldManifold(worldManifold);
        Vector3f aNormal = new Vector3f(worldManifold.normal.x, worldManifold.normal.y, 0);
        Vector3f bNormal = new Vector3f(aNormal).negate();

        for (Component c : objA.getAllComponents()) {
            c.preSolve(objB, contact, aNormal);
        }

        for (Component c : objB.getAllComponents()) {
            c.preSolve(objA, contact, bNormal);
        }
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse contactImpulse) {
        Actor objA = (Actor) contact.getFixtureA().getUserData();
        Actor objB = (Actor) contact.getFixtureB().getUserData();
        WorldManifold worldManifold = new WorldManifold();
        contact.getWorldManifold(worldManifold);
        Vector3f aNormal = new Vector3f(worldManifold.normal.x, worldManifold.normal.y, 0);
        Vector3f bNormal = new Vector3f(aNormal).negate();

        for (Component c : objA.getAllComponents()) {
            c.postSolve(objB, contact, aNormal);
        }

        for (Component c : objB.getAllComponents()) {
            c.postSolve(objA, contact, bNormal);
        }
    }
}
