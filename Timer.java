package LAWNMOWERMAN;

import java.util.Timer;
import java.util.TimerTask;

public class Timer {
    private Timer timer;
    private long startTime;
    private long elapsedTime;
    private boolean isRunning;

    public Timer() {
        timer = new Timer();
        elapsedTime = 0;
        isRunning = false;
    }

    public void timerStart() {
        if (!isRunning) {
            startTime = System.currentTimeMillis();
            isRunning = true;
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    if (isRunning) {
                        elapsedTime = System.currentTimeMillis() - startTime;
                    }
                }
            }, 0, 1000);
        }
    }

    public void timerStop() {
        if (isRunning) {
            timer.cancel();
            isRunning = false;
        }
    }

    public void timerPause() {
        if (isRunning) {
            timer.cancel();
            elapsedTime += System.currentTimeMillis() - startTime;
            isRunning = false;
        }
    }

    public long getElapsedTime() {
        return isRunning ? elapsedTime + (System.currentTimeMillis() - startTime) : elapsedTime;
    }

    public void reset() {
        timer.cancel();
        timer = new java.util.Timer();
        elapsedTime = 0;
        isRunning = false;
    }
}