package Orion.playercharacters;

public class Apex extends PlayerCharacter {
  public Apex() {
    super();
    // Stealth shadow ninja
    this.description = """ 
                A stealthy shadow ninja that can control the shadows. \s
                Slashing his enemies empowered with dark magic. \s
                Sometimes casting a shadow clone to confuse his enemies. \s
            """;
    this.name = "Apex";
    this.HP = 0;
    this.DEF = 0;
    this.ATK = 0;
    this.SPD = 0;
    this.STAMINA = 0;
    this.LVL = 0;
    this.EXP = 0;

  }

  @Override
  public void imgui() {
    super.imgui();
  }
}
