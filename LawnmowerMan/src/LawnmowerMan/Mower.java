package LawnmowerMan;

import java.awt.Color;

import javax.swing.SwingUtilities;

public class Mower
{
    // 0: off, 1: east, 2: west, 3: eastSouth, 4: westSouth
    Square location; 
    int power;
    Cutter cutter;
    State state;

    private GUI gui;
        
    public Mower(GUI gui)
    {
        this.gui = gui;
        this.cutter = new Cutter(gui);
        this.location = new Square(0, 0);
        this.state = new State();
        
    }

    public static void PowerOn(Mower mower, int width, int height) {
        // Create a new thread for the mowing logic
        new Thread(() -> {
            mower.power = 1;
            mower.state.setDirection(1); // Start heading east
            mower.state.setX(0);
            mower.state.setY(0);
    
            while (!Sensor.isBottomReached(mower.state.getY(), height)) {
                switch (mower.state.getDirection()) {
                    case 1: // Heading east
                        while (!Sensor.isEdgeReached(mower.state.getX(), width)) {
                            updateMowerAndGUI(mower);
                            mower.state.setX(mower.state.getX() + 1);
                            mower.location = new Square(mower.state.getX(), mower.state.getY());
                        }
                        mower.state.setX(mower.state.getX() - 1);
                        mower.location = new Square(mower.state.getX(), mower.state.getY());
                        mower.state.setDirection(2); // Change direction to south
                        break;
    
                    case 2: // East side Heading south
                        if (!Sensor.isBottomReached(mower.state.getY(), height)) {
                            mower.state.setY(mower.state.getY() + 1);
                            mower.location = new Square(mower.state.getX(), mower.state.getY());
                            updateMowerAndGUI(mower);
                            mower.state.setX(mower.state.getX() - 1);
                            mower.location = new Square(mower.state.getX(), mower.state.getY());
                        }
                        mower.state.setDirection(3); // Change direction to west
                        break;
    
                    case 3: // Heading west
                        while (!Sensor.isEdgeReached(mower.state.getX(), width)) {
                            updateMowerAndGUI(mower);
                            mower.state.setX(mower.state.getX() - 1);
                            mower.location = new Square(mower.state.getX(), mower.state.getY());
                        }
                        mower.state.setDirection(4); // Change direction to south
                        break;
    
                    case 4: // Heading south again
                        if (!Sensor.isBottomReached(mower.state.getY(), height)) {
                            mower.state.setY(mower.state.getY() + 1);
                            mower.location = new Square(mower.state.getX(), mower.state.getY());
                            updateMowerAndGUI(mower);
                            mower.state.setX(mower.state.getX() + 1);
                            mower.location = new Square(mower.state.getX(), mower.state.getY());
                        }
                        mower.state.setDirection(1); // Change direction to east
                        break;
    
                    default:
                        System.out.println("How on Earth did I get here?");
                }
            }
            // Final update after completing the lawn
            mower.state.setX(mower.state.getX() + 1);
            mower.location = new Square(mower.state.getX(), mower.state.getY());
            updateMowerAndGUI(mower);
        }).start();
    }
    
    private static void updateMowerAndGUI(Mower mower) {
        try {
            Thread.sleep(10); // Wait for 100ms between cuts
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    
        SwingUtilities.invokeLater(() -> {
            Cutter.Cut(mower, mower.gui);
            mower.gui.updateSquare(mower.location);
            mower.gui.repaint();
        });
    
        // Add a small delay to allow GUI to repaint
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void Pause()
    {
        //mower.state.Pause();
    }
}