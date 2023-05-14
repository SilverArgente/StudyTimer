module com.example.studytimer {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.studytimer to javafx.fxml;
    exports com.example.studytimer;
}