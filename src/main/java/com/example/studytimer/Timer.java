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

    private static final Duration INTERVAL = Duration.seconds(1);
    long elapsedTime;

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
            if (e.getSource() == play && !timerIsRunning) {
                play.setText("Stop");
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

    // Plays timer, keeps track of time
    private void startTimer() {
        startTime = System.currentTimeMillis();

        timeline = new Timeline(new KeyFrame(INTERVAL, event -> {
            elapsedTime = System.currentTimeMillis() - startTime;
            updateElapsedTime(elapsedTime);
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        timerIsRunning = true;
    }

    private void stopTimer() {
        if (timeline != null) {
            timeline.stop();
            timeline = null;
            timerIsRunning = false;
        }
    }

    private void updateElapsedTime(long elapsedTime) {
        long seconds = elapsedTime / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;

        seconds %= 60;
        minutes %= 60;

        String timeString = String.format("%02d:%02d:%02d", hours, minutes, seconds);

        Platform.runLater(() -> {
            String savedTimerText = timeString;
            clock.setText(timeString);
        });
    }

}