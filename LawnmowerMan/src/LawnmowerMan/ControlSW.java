package LawnmowerMan;

import javax.swing.SwingUtilities;

public class ControlSW {
    private static Mower mower;

    public static void startButtonClicked(int height, int width, GUI gui) {
        if (mower == null) {
            mower = new Mower(gui);
        }

        if (State.hasSavedState()) {
            mower.resume();
            mower.startMowing(width, height);
        } else {
            mower.state = new State();
            mower.startMowing(width, height);
        }
    }

    public static void stopButtonClicked() {
        if (mower != null) {
            mower.pause();
        }
    }

    public static void resetButtonClicked(int length, int width, GUI gui) {
        if (mower != null) {
            mower.reset();
        }
        Lawn lawn = new Lawn(length, width);
        for (Square square : lawn.squares) {
            Square.Paint(square);
        }
        gui.Update(lawn);
        mower = new Mower(gui);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            int width = 20;
            int height = 20;
            @SuppressWarnings("unused")
            GUI gui = new GUI(width, height);
        });
    }
}
