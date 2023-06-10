package Orion.playercharacters;

public class Genesis extends PlayerCharacter {
  public Genesis() {
    super();
    this.name = "Genesis";
    // this.sprite = new Sprite("assets/textures/characters/genesis.png");
    this.description = """
            An old whise man that lived for centuries. He controls dark magic. \s
            But don't be fooled by his age, he is still a strong fighter. \s
            """;
    this.name = "Genesis";
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
