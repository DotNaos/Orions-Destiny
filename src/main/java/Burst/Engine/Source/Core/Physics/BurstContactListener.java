package Burst.Engine.Source.Core.Physics;

import Burst.Engine.Source.Core.Actor.Actor;
import Burst.Engine.Source.Core.Actor.ActorComponent;
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

        for (ActorComponent ac : objA.getAllComponents()) {
            ac.beginCollision(objB, contact, aNormal);
        }

        for (ActorComponent ac : objB.getAllComponents()) {
            ac.beginCollision(objA, contact, bNormal);
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

        for (ActorComponent ac : objA.getAllComponents()) {
            ac.endCollision(objB, contact, aNormal);
        }

        for (ActorComponent ac : objB.getAllComponents()) {
            ac.endCollision(objA, contact, bNormal);
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

        for (ActorComponent ac : objA.getAllComponents()) {
            ac.preSolve(objB, contact, aNormal);
        }

        for (ActorComponent ac : objB.getAllComponents()) {
            ac.preSolve(objA, contact, bNormal);
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

        for (ActorComponent ac : objA.getAllComponents()) {
            ac.postSolve(objB, contact, aNormal);
        }

        for (ActorComponent ac : objB.getAllComponents()) {
            ac.postSolve(objA, contact, bNormal);
        }
    }
}
