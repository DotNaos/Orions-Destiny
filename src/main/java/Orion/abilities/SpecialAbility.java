package Orion.abilities;

import Burst.Engine.Source.Core.Input.KeyListener;

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
