package Orion.abilities.Special;

import org.joml.Vector2f;

import static org.joml.Math.sin;

/**
 * @author Dominik Abler
 */
public class FireAbility extends SpecialAbility {
  // creates a fire vortex around enemies and damages them
  // causes damage over time
  // the vortex will be able to destroy enemies
  // affected enemies will suffer increased damage
  // affected enemies carry a mark that they are affected by the fire vortex

  private Vector2f position;
  private Vector2f direction;

  // Constructor
  public FireAbility() {

    position = new Vector2f(0, 0);

    direction = new Vector2f(200, 100);
  }

  // shoot the fire vortex

  /**
   *
   */
  public void shootFireVortex() {
    // Get the start and end points of the fire vortex
    Vector2f start = new Vector2f();
    Vector2f end = new Vector2f();

    // Get the direction of the fire vortex
    Vector2f direction = new Vector2f();

    // Get the player's position
    Vector2f playerPosition = new Vector2f();

    // Get the player's rotation
    float playerRotation = 0;

    // Set the start point of the fire vortex


    // Draw the fire vortex
    // use debugdraw for drawing

    // Check if the fire vortex hits an enemy
    // If it does, destroy the enemy


    // Get the enemies

    // Loop through the enemies


  }


  public void update(float dt) {
    // draw the fire vortex
    shootFireVortex();
    // update the direction of the fire vortex

    direction.add(sin(dt), 0.1f * dt);
  }

  /**
   *
   */
  @Override
  public void activate() {

  }
}
