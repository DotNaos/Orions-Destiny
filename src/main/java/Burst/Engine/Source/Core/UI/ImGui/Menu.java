package Burst.Engine.Source.Core.UI.ImGui;

import Burst.Engine.Source.Core.UI.Window;
import imgui.ImGui;
import org.joml.Vector2f;

/**
 * @author Oliver Schuetz
 */
public class Menu {
  private float currentX;
  private float currentY;
  private Vector2f itemSize;
  private int columns = 2;

  private int rows = 2;


  private float spacing;

  private Vector2f alignment;

  private Vector2f margin;

  public Menu(int columns, int rows, Vector2f itemSize) {
    new Menu(columns, rows, itemSize, new Vector2f(100 / 50, 100 / 50));
  }

  public Menu(int columns, int rows, Vector2f itemSize, Vector2f alignment) {
    new Menu(columns, rows, itemSize, alignment, 20);
  }

  public Menu(int columns, int rows, Vector2f itemSize, Vector2f alignment, float spacing) {
    new Menu(columns, rows, itemSize, alignment, spacing, new Vector2f(0));
  }

  public Menu(int columns, int rows, Vector2f itemSize, Vector2f alignment, float spacing, Vector2f margin) {
    this.columns = columns;
    this.rows = rows;
    this.spacing = spacing;
    this.itemSize = new Vector2f(itemSize);
    this.margin = new Vector2f(margin);
    this.alignment = new Vector2f(alignment);

    float spacingSumX = this.spacing * (this.columns - 1);
    float spacingSumY = this.spacing * (this.rows - 1);
    float itemSumX = this.itemSize.x * this.columns;
    float itemSumY = this.itemSize.y * this.rows;

    currentX = (Window.getWidth() - itemSumX - spacingSumX) / this.alignment.x + this.margin.x;
    currentY = (Window.getHeight() - itemSumY - spacingSumY) / this.alignment.y + this.margin.y;

    ImGui.setCursorPosY(currentY);
    ImGui.setCursorPosX(currentX);
  }

  public void nextRow() {
    float spacingSumX = this.spacing * (this.columns - 1);
    float itemSumX = this.itemSize.x * this.columns;
    currentY -= -itemSize.y - spacing;
    currentX = (Window.getWidth() - itemSumX - spacingSumX) / this.alignment.x + this.margin.x;
    ImGui.setCursorPosX(currentX);
    ImGui.setCursorPosY(currentY);
  }

  public void nextColumn() {
    currentX -= -itemSize.x - spacing;
    ImGui.sameLine();
    ImGui.setCursorPosX(currentX);
  }
}
