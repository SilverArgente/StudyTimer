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

    // Visual component variables
    Scene scene;
    Label clock = new Label("00:00:00");
    Button stopwatchBtn, timerBtn, play, reset;
    StackPane timerDisplay;
    HBox topBar, bottomBar;
    BorderPane layout;

    // Timer functionality variables
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

        // Adding CSS file doesn't work for some reason, time to hard code it
        // scene.getStylesheets().add("/src/css/style.css");
        Parent root = FXMLLoader.load(getClass().getResource("study_timer.fxml"));

        // Final display to add to scene
        layout = new BorderPane();

        // Set stopwatch as default mode
        initializeStopwatchScene();

        // Switch between stopwatch and timer
        stopwatchBtn.setOnAction(e -> {
            initializeStopwatchScene();
        });

        timerBtn.setOnAction(e -> {
            initializeTimerScene();
        });

        // Apply final layout to scene, display scene to end user
        scene = new Scene(layout, 500, 500);
        
        primaryStage.setTitle("Study Timer");
        primaryStage.setMinHeight(300);
        primaryStage.setMinWidth(300);
        primaryStage.setMaxHeight(500);
        primaryStage.setMaxWidth(500);
        primaryStage.setScene(scene);
        primaryStage.show();

    }


    private void initializeStopwatchScene() {
        // Stopwatch/Timer Buttons layout component
        topBar = new HBox();
        topBar.setPadding(new Insets(15, 12, 15, 12));
        topBar.setSpacing(30);

        // Stopwatch Mode Button
        stopwatchBtn = new Button("Stopwatch");
        stopwatchBtn.getStyleClass().add("bg-1");

        // Timer Mode Button
        timerBtn = new Button("Timer");
        timerBtn.getStyleClass().add("bg-1");

        topBar.getChildren().addAll(stopwatchBtn, timerBtn);
        topBar.setAlignment(Pos.TOP_CENTER);

        // Timer Display layout component
        timerDisplay = new StackPane();
        clock.setFont(new Font("Arial", 26));
        clock.getStyleClass().add("bg-1");
        timerDisplay.getChildren().addAll(clock);

        // Start / Pause / Reset Button layout component
        bottomBar = new HBox();
        bottomBar.setPadding(new Insets(15, 12, 15, 12));
        bottomBar.setSpacing(30);
        bottomBar.setAlignment(Pos.TOP_CENTER);
        bottomBar.setStyle("-fx-background-color: #909090;");

        // Play / Pause button
        play = new Button("Play");
        play.getStyleClass().add("bg-2");

        // Reset button
        reset = new Button("Reset");
        reset.getStyleClass().add("bg-1");

        layout.setTop(topBar);
        layout.setCenter(timerDisplay);
        layout.setBottom(bottomBar);

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

        // Switch between stopwatch and timer
        stopwatchBtn.setOnAction(e -> {
            initializeStopwatchScene();
        });

        timerBtn.setOnAction(e -> {
            initializeTimerScene();
        });

        // Add both buttons to bottom bar layout component
        bottomBar.getChildren().addAll(play, reset);

        // Set theme to all components
        setStopwatchModeTheme();
    }

    private void initializeTimerScene() {
        // Stopwatch/Timer Buttons layout component
        topBar = new HBox();
        topBar.setPadding(new Insets(15, 12, 15, 12));
        topBar.setSpacing(30);

        // Stopwatch Mode Button
        stopwatchBtn = new Button("Stopwatch");
        stopwatchBtn.getStyleClass().add("bg-1");

        // Timer Mode Button
        timerBtn = new Button("Timer");
        timerBtn.getStyleClass().add("bg-1");

        topBar.getChildren().addAll(stopwatchBtn, timerBtn);
        topBar.setAlignment(Pos.TOP_CENTER);

        // Timer Display layout component
        timerDisplay = new StackPane();
        clock.setFont(new Font("Arial", 26));
        clock.getStyleClass().add("bg-1");
        timerDisplay.getChildren().addAll(clock);

        // Start / Pause / Reset Button layout component
        bottomBar = new HBox();
        bottomBar.setPadding(new Insets(15, 12, 15, 12));
        bottomBar.setSpacing(30);
        bottomBar.setAlignment(Pos.TOP_CENTER);
        bottomBar.setStyle("-fx-background-color: #909090;");

        // Play / Pause button
        play = new Button("Play");
        play.getStyleClass().add("bg-2");

        // Reset button
        reset = new Button("Reset");
        reset.getStyleClass().add("bg-1");

        layout.setTop(topBar);
        layout.setCenter(timerDisplay);
        layout.setBottom(bottomBar);

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
            play.setText("Pla");
            stopTimer();
            elapsedTime = 0;
            timeAtPause = 0;
            clock.setText("00:00:00");
        });

        // Switch between stopwatch and timer
        stopwatchBtn.setOnAction(e -> {
            initializeStopwatchScene();
        });

        timerBtn.setOnAction(e -> {
            initializeTimerScene();
        });

        // Add both buttons to bottom bar layout component
        bottomBar.getChildren().addAll(play, reset);

        setTimerModeTheme();
    }

    public void setStopwatchModeTheme() {
        stopwatchBtn.setStyle(
                "-fx-background-color: #2A7BA3;" +
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

        layout.setStyle("-fx-background-color: #192428");
        topBar.setStyle("-fx-background-color: #414C50");
        bottomBar.setStyle("-fx-background-color: #414C50");

        reset.setVisible(true);

        clock.setStyle("-fx-text-fill: white");

    }

    public void setTimerModeTheme () {
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
                "-fx-background-color: #2A7BA3;" +
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

        layout.setStyle("-fx-background-color: #192428");
        topBar.setStyle("-fx-background-color: #414C50");
        bottomBar.setStyle("-fx-background-color: #414C50");

        reset.setVisible(false);

        clock.setStyle("-fx-text-fill: white");
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