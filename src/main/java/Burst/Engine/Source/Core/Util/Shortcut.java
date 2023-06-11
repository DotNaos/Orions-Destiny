package Burst.Engine.Source.Core.Util;

/**
 * @author Oliver Schuetz
 */
public class Shortcut {
  private int keyCount;

  public int first = -1;
  public int second = -1;
  public int third = -1;
  public int fourth = -1;

  public Shortcut(int first, int second) {
    this.first = first;
    this.second = second;
    this.keyCount = 2;

    if (this.first == -1) {
      keyCount--;
      assert false : "First key is not valid";
    }
    if (this.second == -1) {
      keyCount--;
      assert false : "Second key is not valid";
    }
  }

  public Shortcut(int first, int second, int third) {
    this.first = first;
    this.second = second;
    this.third = third;
    this.keyCount = 3;

    if (this.first == -1) {
      keyCount--;
      assert false : "First key is not valid";
    }
    if (this.second == -1) {
      keyCount--;
      assert false : "Second key is not valid";
    }
    if (this.third == -1) keyCount--;
  }

  public Shortcut(int first, int second, int third, int fourth) {
    this.first = first;
    this.second = second;
    this.third = third;
    this.fourth = fourth;
    this.keyCount = 4;

    if (this.first == -1) {
      keyCount--;
      assert false : "First key is not valid";
    }
    if (this.second == -1) {
      keyCount--;
      assert false : "Second key is not valid";
    }
    if (this.third == -1) keyCount--;
    if (this.fourth == -1) keyCount--;
  }

  public int getKeyCount() {
    return this.keyCount;
  }

  public int first() {
    return this.first;
  }

  public int second() {
    return this.second;
  }

  public Shortcut copy() {
    return new Shortcut(this.first, this.second, this.third, this.fourth);
  }

  public Shortcut set(Shortcut from) {
    this.first = from.first;
    this.second = from.second;
    this.third = from.third;
    this.fourth = from.fourth;
    this.keyCount = from.keyCount;
    return this;
  }

}
