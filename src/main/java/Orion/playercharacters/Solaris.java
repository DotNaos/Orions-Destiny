package Orion.playercharacters;

public class Solaris extends PlayerCharacter {
  public Solaris() {
    super();

    this.name = "Solaris";
    // Hellbeast
    this.description = """
            A beast from the depths of hell, Solaris is a powerful and fearsome creature.\s
            He can shoot fireballs from his mouth, and expel flames from his body.\s
            """;
    this.name = "Solaris";
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
