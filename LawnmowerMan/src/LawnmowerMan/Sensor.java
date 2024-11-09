package LawnmowerMan;


public class Sensor
{
    public static boolean isEdgeReached(int x, int width) {
        return x < 0 || x >= width; 
    }
    
    public static boolean isBottomReached(int y, int height) {
        return y >= height; 
    }
}