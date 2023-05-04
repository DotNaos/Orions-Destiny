package Burst.Engine.Source.Core.UI;

import imgui.ImGui;
import org.joml.Vector2f;

import java.awt.*;

public class Menu {

    private float buttonSpacing = 20;

    private Vector2f alignment;

    public Menu(Vector2f alignment)
    {
        this.alignment = alignment;
    }


    public Menu()
    {
        this.alignment = new Vector2f((float) 100 / 50, (float) 100 / 50);
    }

    public void ButtonMenu(int columns, int rows, float buttonWidth, float buttonHeight, float buttonSpacing, float marginX, float marginY)
    {
        ButtonMenu(columns, rows, buttonWidth, buttonHeight, buttonSpacing, marginX, marginY, this.alignment);
    }

    public void ButtonMenu(int columns, int rows, float buttonWidth, float buttonHeight, float buttonSpacing)
    {
        ButtonMenu(columns, rows, buttonWidth, buttonHeight, buttonSpacing, 0, 0, this.alignment);
    }

    public void ButtonMenu(int columns, int rows, float buttonWidth, float buttonHeight)
    {
        ButtonMenu(columns, rows, buttonWidth, buttonHeight, buttonSpacing, 0, 0, this.alignment);
    }
    public void ButtonMenu(int columns, int rows, float buttonWidth, float buttonHeight, float buttonSpacing, float marginX, float marginY, Vector2f alignment)
    {
        float currentX = (Window.getWidth() - ((buttonWidth * columns) - (buttonSpacing * (columns-1)))) / alignment.x + marginX;
        float currentY = (Window.getHeight() - ((buttonHeight * rows) - (buttonSpacing * (rows-1)))) / alignment.y + marginY;

        for (int i = 0; i < columns; i++)
        {
            for (int j = 0; j < rows; j++) {
                if (ImGui.button("Button" + i + j, buttonWidth, buttonHeight))
                {
                    System.out.println("Button" + i + j + " pressed");
                }
                ImGui.sameLine();
                currentY += buttonHeight + buttonSpacing;
            }
            currentX += buttonWidth + buttonSpacing;
            currentY = (Window.getHeight() - ((buttonHeight * rows) - (buttonSpacing * (rows-1)))) / alignment.y + marginY;
        }
    }



}
