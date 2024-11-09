package LawnmowerMan;

public class Cutter
{
    private Square currentSquare;


    public static void Cut(Mower mower, GUI gui)
    {
        Square.Repaint(mower.location);
        gui.updateSquare(mower.location);
    }

    //getters
    public Square getSquare(){return currentSquare;}

    //setters
    public void setSquare(Square newLocation){this.currentSquare = newLocation;}
}

