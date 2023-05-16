package com.example.studytimer;

import javafx.scene.control.Label;

import java.util.Timer;
import java.util.TimerTask;

public class Stopwatch {
    private static final long INTERVAL = 1000; // Time interval in milliseconds

    private long startTime;
    private Timer timer;
    private Label clock;

    public Stopwatch(Label clock) {
        this.clock = clock;
    }

    public void start() {
        startTime = System.currentTimeMillis();

        TimerTask task = new TimerTask() {
            public void run() {
                long elapsedTime = System.currentTimeMillis() - startTime;
                printElapsedTime(elapsedTime);
            }
        };

        timer = new Timer();
        timer.scheduleAtFixedRate(task, INTERVAL, INTERVAL);
    }

    public void stop() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    private void printElapsedTime(long elapsedTime) {
        long seconds = elapsedTime / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;

        seconds %= 60;
        minutes %= 60;

        clock.setText(hours + ":" + minutes + ":" + seconds);
        System.out.printf("%02d:%02d:%02d%n", hours, minutes, seconds);
    }

}