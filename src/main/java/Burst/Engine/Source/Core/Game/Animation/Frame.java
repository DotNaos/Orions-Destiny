package Burst.Engine.Source.Core.Game.Animation;

import Burst.Engine.Source.Core.Assets.Graphics.Sprite;

class Frame {
    public Sprite sprite;
    public float frameTime;

    public Frame() {

    }

    public Frame(Sprite sprite, float frameTime) {
        this.sprite = sprite;
        this.frameTime = frameTime;
    }
}
