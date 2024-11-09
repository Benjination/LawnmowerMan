package LawnmowerMan;

public class State {
    private static int direction;
    // 0: off, 1: east, 2: west, 3: eastSouth, 4: westSouth
    private static int x,y;
    private static boolean paused;

    public State (){}


    //Getters
    public int getDirection(){return direction;}

    public static boolean hasSavedState() {return  x != 0 || y != 0;}

    public int getX(){return x;}

    public int getY(){return y;}

    public boolean getPaused(){return paused;}


    // Setters
    public void setDirection(int newDirection) {direction = newDirection;}

    public void setX(int newX){x = newX;}

    public void setY(int newY){y = newY;}

    public static void Pause(){paused = true;}

    public static void unPause(){paused = false;}
}
