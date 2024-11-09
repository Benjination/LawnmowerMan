package LawnmowerMan;

import java.awt.Color;

public class Square
{
    private Color color;
    public int x;
    public int y;

    public Square(int x, int y)
    {
        this.color = new Color (0, 100, 0);
        this.x = x;
        this.y = y;
    }

    public static void Paint(Square square)
    {
        Color darkGreen = new Color(0, 100, 0);
        square.setColor(darkGreen);
    }

    public static void Repaint(Square square)
    {
        Color lightGreen = new Color(144, 238, 144);
        square.setColor(lightGreen);
    }

    //getters
    public Color getColor(){return color;}
    //setters
    public void setColor(Color color){this.color = color;}
}