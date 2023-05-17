package Orion.abilities;

import Burst.Engine.Source.Core.Graphics.Input.KeyListener;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_E;

public class SpecialAbility extends Ability{



    /**
     * kjlkslghsglh
     */
    @Override
    public void activate() {
        if(!KeyListener.isKeyPressed(getActivationInput(69))) return;        //kommt dort eine 1 rein?
    }

    public int getActivationInput(int activationInput){

        return activationInput;
    }
}
