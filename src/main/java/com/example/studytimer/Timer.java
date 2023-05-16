package com.example.studytimer;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class Timer extends Application {

    // Init variables
    Stage window;

    Label clock = new Label("00:00:00");

    static boolean timerIsRunning;
    boolean isPaused = false;

    private static final Duration INTERVAL = Duration.seconds(1);
    long elapsedTime = 0;
    long timeAtPause = 0;

    private long startTime;
    private Timeline timeline;

    public static void main(String[] args) {
        // Init
        timerIsRunning = false;
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {

        // String stylesheet = getClass().getResource("/style.css").toExternalForm();
        window = primaryStage;

        // Create Display
        BorderPane layout = new BorderPane();
        layout.getStyleClass().add("bg-1");

        // Label timer display
        clock.setFont(new Font("Arial", 26));
        clock.getStyleClass().add("bg-1");
        layout.setCenter(clock);

        // Play timer button
        Button play = new Button("Play");
        play.getStyleClass().add("bg-1");
        layout.setBottom(play);

        // If Play Button is clicked
        play.setOnAction(e -> {
            // Play
            if (!timerIsRunning) {
                play.setText("Pause");
                startTimer();
            }
            // Stop
            else {
                play.setText("Play");
                stopTimer();
            }
        });

        Scene scene = new Scene(layout, 500, 500);
        // scene.getStylesheets().add(stylesheet);

        window.setTitle("Study Timer");
        window.setScene(scene);
        window.show();

    }

    // Plays timer
    private void startTimer() {
        startTime = System.currentTimeMillis();
        timerIsRunning = true;

        timeline = new Timeline(new KeyFrame(INTERVAL, event -> {
            // Formula for elapsed time
            // Time elapsed since button press (current time - start time at button press) + previous time at pause, if any
            elapsedTime = (System.currentTimeMillis() - startTime) + timeAtPause;

            // Updates display text with elapsed time
            updateElapsedTime(elapsedTime);
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

    }

    // Pauses timer
    private void stopTimer() {
        if (timeline != null) {
            timeline.stop();
            timeline = null;
            timerIsRunning = false;
            isPaused = true;
            timeAtPause = elapsedTime;
        }
    }

    // Updates label text to display elapsed time
    private void updateElapsedTime(long elapsedTime) {
        long seconds = elapsedTime / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;

        seconds %= 60;
        minutes %= 60;

        String timeString = String.format("%02d:%02d:%02d", hours, minutes, seconds);

        Platform.runLater(() -> {
            // this.elapsedTime = elapsedTime;
            clock.setText(timeString);
        });
    }

}