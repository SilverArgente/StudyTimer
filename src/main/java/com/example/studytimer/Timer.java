package com.example.studytimer;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
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
        window = primaryStage;

        // Set stage title
        window.setTitle("TimeSavvy");

        // Timer Button
        timerButton = new Button();
        timerButton.setText("Timer");
        timerButton.setTranslateX(0);
        timerButton.setTranslateY(50);

        // Create Presets Button
        createPresetsButton = new Button();
        createPresetsButton.setText("Create Preset");
        createPresetsButton.setTranslateX(-200);
        createPresetsButton.setTranslateY(-230);

        // Create Time Count Label
        clock = new Label("0:00");
        clock.setFont(new Font("Arial", 24));
        clock.setTranslateX(0);
        clock.setTranslateY(-50);


        // presetBtn.setOnAction(e -> window.setScene(presetScene));

        // Layout
//        VBox layout1 = new VBox(20);
//        layout1.getChildren().addAll(clock, presetBtn);
//        timerScene = new Scene(layout1, 500, 500);

        // Button functionality
        timerButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.out.println("button was clicked! thanks bucky java!");
            }
        });



        // Create scene, add button to scene
        StackPane layout = new StackPane();

        Scene scene = new Scene(layout, 500, 500);

        layout.getChildren().addAll(timerButton, createPresetsButton, clock);

        scene.setFill(Color.BLACK);

        // Set scene to stage, show stage
        window.setScene(scene);
        window.show();

    }

}