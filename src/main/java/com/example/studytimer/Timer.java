package com.example.studytimer;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
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

        Parent root = FXMLLoader.load(getClass().getResource("study_timer.fxml"));

        // String stylesheet = getClass().getResource("/style.css").toExternalForm();
        window = primaryStage;

        // Stopwatch/Timer Buttons layout component
        HBox topBar = new HBox();
        topBar.setPadding(new Insets(15, 12, 15, 12));
        topBar.setSpacing(30);

        // Stopwatch Mode Button
        Button stopwatchBtn = new Button("Stopwatch");
        stopwatchBtn.getStyleClass().add("bg-1");

        // Timer Mode Button
        Button timerBtn = new Button("Timer");
        timerBtn.getStyleClass().add("bg-1");

        topBar.getChildren().addAll(stopwatchBtn, timerBtn);
        topBar.setAlignment(Pos.TOP_CENTER);

        // Timer Display layout component
        StackPane timerDisplay = new StackPane();
        clock.setFont(new Font("Arial", 26));
        clock.getStyleClass().add("bg-1");
        timerDisplay.getChildren().addAll(clock);

        // Start / Pause / Reset Button layout component
        HBox bottomBar = new HBox();
        bottomBar.setPadding(new Insets(15, 12, 15, 12));
        bottomBar.setSpacing(30);
        bottomBar.setAlignment(Pos.TOP_CENTER);
        bottomBar.setStyle("-fx-background-color: #909090;");



        // Play / Pause button
        Button play = new Button("Play");
        play.getStyleClass().add("bg-2");

        // Reset button
        Button reset = new Button("Reset");
        reset.getStyleClass().add("bg-1");


        // Add both buttons to bottom bar layout component
        bottomBar.getChildren().addAll(play, reset);

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

        // If reset button is clicked
        reset.setOnAction(e -> {
            play.setText("Play");
            stopTimer();
            elapsedTime = 0;
            timeAtPause = 0;
            clock.setText("00:00:00");
        });


        // Embed all layout components into one layout to establish scnee
        BorderPane layout = new BorderPane();
        layout.setTop(topBar);
        layout.setCenter(timerDisplay);
        layout.setBottom(bottomBar);


        // scene.getStylesheets().add("style.css")

        // Adding CSS file doesn't work for some reason, time to hard code it
        // scene.getStylesheets().add("/src/css/style.css");

        // CSS Stylings
        layout.setStyle("-fx-background-color: #192428");
        topBar.setStyle("-fx-background-color: #414C50");
        bottomBar.setStyle("-fx-background-color: #414C50");

        stopwatchBtn.setStyle(
        "-fx-background-color: #39ACE7;" +
        "-fx-border: none;" +
        "-fx-text-color: white;" +
        "-fx-padding: 10px 24px;" +
        "-fx-text-align: center;" +
        "-fx-text-decoration: none;" +
        "-fx-display: inline-block;" +
        "-fx-font-size: 16px;" +
        "-fx-margin: 4px 2px;" +
        "-fx-cursor: pointer"
        );

        timerBtn.setStyle(
                "-fx-background-color: #39ACE7;" +
                        "-fx-border: none;" +
                        "-fx-text-color: white;" +
                        "-fx-padding: 10px 24px;" +
                        "-fx-text-align: center;" +
                        "-fx-text-decoration: none;" +
                        "-fx-display: inline-block;" +
                        "-fx-font-size: 16px;" +
                        "-fx-margin: 4px 2px;" +
                        "-fx-cursor: pointer"
        );

        play.setStyle(
                "-fx-background-color: #39ACE7;" +
                        "-fx-border: none;" +
                        "-fx-text-color: white;" +
                        "-fx-padding: 10px 24px;" +
                        "-fx-text-align: center;" +
                        "-fx-text-decoration: none;" +
                        "-fx-display: inline-block;" +
                        "-fx-font-size: 16px;" +
                        "-fx-margin: 4px 2px;" +
                        "-fx-cursor: pointer"
        );

        reset.setStyle(
                "-fx-background-color: #39ACE7;" +
                        "-fx-border: none;" +
                        "-fx-text-color: white;" +
                        "-fx-padding: 10px 24px;" +
                        "-fx-text-align: center;" +
                        "-fx-text-decoration: none;" +
                        "-fx-display: inline-block;" +
                        "-fx-font-size: 16px;" +
                        "-fx-margin: 4px 2px;" +
                        "-fx-cursor: pointer"
        );

        clock.setStyle("-fx-text-fill: white");

        Scene scene = new Scene(layout, 500, 500);

        window.setTitle("Study Timer");
        window.setMinHeight(300);
        window.setMinWidth(300);
        window.setMaxHeight(500);
        window.setMaxWidth(500);
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