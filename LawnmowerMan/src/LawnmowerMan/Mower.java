package LawnmowerMan;

import javax.swing.SwingUtilities;

public class Mower
{
    // 0: off, 1: east, 2: west, 3: eastSouth, 4: westSouth
    Square location; 
    int power;
    Cutter cutter;
    State state;
    private volatile boolean isPaused = false;
    private volatile boolean isReset = false;
    private Thread mowingThread;

    private GUI gui;
        
    public Mower(GUI gui)
    {
        this.gui = gui;
        this.cutter = new Cutter();
        this.location = new Square(0, 0);
        this.state = new State();
        
    }
    public void startMowing(int width, int height) {
        if (mowingThread != null && mowingThread.isAlive()) {
            resume();
            return;
        }

        isReset = false;
        mowingThread = new Thread(() -> powerOn(width, height));
        mowingThread.start();
    }

    private void powerOn(int width, int height) {
        power = 1;
        if (!isReset) {
            state.setDirection(1); // Start heading east
            state.setX(0);
            state.setY(0);
        }

        while (!Sensor.isBottomReached(state.getY(), height) && !isReset) {
            if (isPaused) {
                synchronized (this) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                }
            }
            if (isReset) break;

            switch (state.getDirection()) {
                case 1: // Heading east
                    while (!Sensor.isEdgeReached(state.getX(), width) && !isPaused && !isReset) {
                        updateMowerAndGUI();
                        state.setX(state.getX() + 1);
                        location = new Square(state.getX(), state.getY());
                    }
                    if (!isPaused && !isReset) {
                        state.setX(state.getX() - 1);
                        location = new Square(state.getX(), state.getY());
                        state.setDirection(2); // Change direction to south
                    }
                    break;

                case 2: // East side Heading south
                    if (!Sensor.isBottomReached(state.getY(), height) && !isPaused && !isReset) {
                        state.setY(state.getY() + 1);
                        location = new Square(state.getX(), state.getY());
                        updateMowerAndGUI();
                        state.setX(state.getX() - 1);
                        location = new Square(state.getX(), state.getY());
                        state.setDirection(3); // Change direction to west
                    }
                    break;

                case 3: // Heading west
                    while (!Sensor.isEdgeReached(state.getX(), width) && !isPaused && !isReset) {
                        updateMowerAndGUI();
                        state.setX(state.getX() - 1);
                        location = new Square(state.getX(), state.getY());
                    }
                    if (!isPaused && !isReset) {
                        state.setDirection(4); // Change direction to south
                    }
                    break;

                case 4: // Heading south again
                    if (!Sensor.isBottomReached(state.getY(), height) && !isPaused && !isReset) {
                        state.setY(state.getY() + 1);
                        location = new Square(state.getX(), state.getY());
                        updateMowerAndGUI();
                        state.setX(state.getX() + 1);
                        location = new Square(state.getX(), state.getY());
                        state.setDirection(1); // Change direction to east
                    }
                    break;

                default:
                    System.out.println("How on Earth did I get here?");
            }
        }
        if (!isReset) {
            // Final update after completing the lawn
            state.setX(state.getX() + 1);
            location = new Square(state.getX(), state.getY());
            updateMowerAndGUI();
        }
    }

    public void pause() {
        this.isPaused = true;
    }

    public void resume() {
        this.isPaused = false;
        synchronized (this) {
            this.notifyAll();
        }
    }

    public void reset() {
        pause();
        isReset = true;
        if (mowingThread != null) {
            mowingThread.interrupt();
        }
        state = new State();
        location = new Square(0, 0);
        isPaused = false;
        isReset = false;
    }

    private void updateMowerAndGUI() {
        try {
            Thread.sleep(10); // Wait for 10ms between cuts
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        SwingUtilities.invokeLater(() -> {
            Cutter.Cut(this, gui);
            gui.updateSquare(location);
            gui.repaint();
        });

        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}