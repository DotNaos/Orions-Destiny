package Orion.blocks;

import Burst.Engine.Source.Core.Actor.Actor;
import Burst.Engine.Source.Core.Actor.ActorComponent;
import Burst.Engine.Source.Core.Actor.PlayerController;
import Burst.Engine.Source.Core.Assets.Graphics.Sprite;
import Burst.Engine.Source.Core.Component;
import org.jbox2d.dynamics.contacts.Contact;
import org.joml.Vector3f;

public class Block extends Actor {

    public Block(Sprite sprite) {
        super(sprite);
    }

}
