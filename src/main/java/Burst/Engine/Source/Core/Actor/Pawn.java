package Burst.Engine.Source.Core.Actor;

import Burst.Engine.Source.Core.Assets.Graphics.Sprite;

public class Pawn extends Actor {
    public Pawn(String name, Sprite sprite) {
        super(name, sprite);
    }

    public Pawn(Sprite sprite) {
        this("Pawn", sprite);
    }

    public Pawn(String name) {
        this(name, null);
    }


}
