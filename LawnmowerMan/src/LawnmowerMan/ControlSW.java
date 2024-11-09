package LawnmowerMan;


import javax.swing.SwingUtilities;

public class ControlSW
{
    private static GUI gui;
    
    public static void startButtonClicked(int height, int width, GUI gui)
    {
        Mower mower = new Mower(gui);

        if(State.hasSavedState())
        {
            State.unPause();
            Mower.PowerOn(mower, mower.state.getX(), mower.state.getY());  
        }
        else
        {
            mower.state = new State();  
            Mower.PowerOn(mower, width, height);  
        } 
    }
    

    public void stopButtonClicked()
    {
        Mower.Pause();
    }

    public static void resetButtonClicked(int length, int width, GUI gui)
{
    Mower.Pause();
    Lawn lawn = new Lawn(length, width);
    for(Square square : lawn.squares)
    {
        Square.Paint(square);
    }
    gui.Update(lawn);
}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            int width = 20;  
            int height = 20;
            gui = new GUI(width, height);
        });
    }

}
