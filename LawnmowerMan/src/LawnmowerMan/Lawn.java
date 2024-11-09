package LawnmowerMan;

import java.awt.Color;
import java.util.ArrayList;

public class Lawn
{
    public ArrayList<Square> squares;
    private int width, height;

    public Lawn(int width, int height)
    {
        this.width = width;
        this.height = height;
        squares = new ArrayList<Square>();
        setSquares();
    }

    private void setSquares()
    {
        for (int y = 0; y < height; y++) 
        {
            for (int x = 0; x < width; x++) 
            {
                Square square = new Square(x, y);
                squares.add(square);
            }
        }
    }
}