package LawnmowerMan;

import java.awt.Color;
import java.util.ArrayList;

public class Lawn
{
    public ArrayList<Square> squares;
    public int width, height;

    public Lawn(int width, int height) {
        this.width = width;
        this.height = height;
        initializeLawn(width, height);
    }

    public Lawn(){}

    public void initializeLawn(int width, int height) {
        this.width = width;
        this.height = height;
        Color color = new Color(10,93,0);
        squares = new ArrayList<>();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                squares.add(new Square(x, y, color));
            }
        }
    }

    
}