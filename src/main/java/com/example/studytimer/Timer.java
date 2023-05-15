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

public class Timer extends Application {

    // Init variables
    Stage window;
    Scene timerScene, presetScene;
    Button timerButton, createPresetsButton;
    ;
    Label clock;
    static int time;

    static boolean timerIsRunning;

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
        Label clock = new Label("0:00");
        clock.getStyleClass().add("bg-1");
        layout.setCenter(clock);

        // Play timer button
        Button play = new Button("Play");
        play.getStyleClass().add("bg-1");
        layout.setBottom(play);
        play.setOnAction(e -> {
            if (e.getSource() == play && !timerIsRunning) {
                timerIsRunning = true;
                checkTimerState();
            }
            else {
                timerIsRunning = false;
                checkTimerState();
            }
        });

        Scene scene = new Scene(layout, 500, 500);
        // scene.getStylesheets().add(stylesheet);

        window.setTitle("TimeSavvy");
        window.setScene(scene);
        window.show();

    }

    // Checks timer state on each click of play button
    public void checkTimerState() {
        if (timerIsRunning) {
            // Play timer
            System.out.println("Timer is running!");
        }
        else {
            // Stop timer
            System.out.println("Timer has been stopped!");
        }
    }

}