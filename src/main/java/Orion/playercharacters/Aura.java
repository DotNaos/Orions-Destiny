package Orion.playercharacters;

public class Aura extends PlayerCharacter {
  public Aura() {
    super();
    this.name = "Aura";
    // Energie und Gravitation
    this.description = """
            An ancient golem older than the world itself. \s
            He is made of pure energy and can control gravity. \s
            Throwing shards at his enemies empowered with all sorts of energies. \s
            """;
    this.name = "Aura";
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
    super.imgui(this);
  }
}
