package Orion.abilities.Special;

import Burst.Engine.Source.Core.Input.KeyListener;
import Orion.abilities.Ability;
import Orion.characters.PlayerCharacter;

public class SpecialAbility extends Ability {




    /**
     * kjlkslghsglh
     */
    @Override
    public void activate() {
        if(!KeyListener.isKeyPressed(getActivationInput(69))) return;        //kommt dort eine 1 rein?
    }

    /**
     *
     */
    @Override
    public void init() {

    }

    public int getActivationInput(int activationInput){

        return activationInput;
    }
}
