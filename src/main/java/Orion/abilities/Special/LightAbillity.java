package Orion.abilities.Special;

import Orion.abilities.Ability;
import org.joml.Vector2f;
import org.joml.Vector3f;


public class LightAbillity extends Ability {

  // Use vectors for the light beam
  Vector2f start;
  Vector2f end;
  Vector2f direction;

  // Constructor

  public LightAbillity(String name, String description, int cost) {

  }

  // shoot the light beam
  public void shootLightBeam() {
    // Get the start and end points of the light beam
    Vector2f start = new Vector2f();
    Vector2f end = new Vector2f();

    // Get the direction of the light beam
    Vector2f direction = new Vector2f();

    // Get the player's position
    Vector2f playerPosition = new Vector2f();

    // Get the player's rotation
    float playerRotation = 0;


    // Set the start point of the light beam
    start.x = playerPosition.x;
    start.y = playerPosition.y;

    // Set the direction of the light beam
    direction.x = (float) Math.cos(playerRotation);
    direction.y = (float) Math.sin(playerRotation);

    // Set the end point of the light beam
    end.x = start.x + direction.x * 1000;
    end.y = start.y + direction.y * 1000;

    // Draw the light beam
    // use debugdraw for drawing

    // Check if the light beam hits an enemy
    // If it does, destroy the enemy

    // Get the enemies

    // Loop through the enemies


  }

  public void setLightColor(Vector3f vector3f) {
  }

  /**
   *
   */
  @Override
  public void activate() {

  }

  /**
   *
   */
  @Override
  public void init() {

  }
}
