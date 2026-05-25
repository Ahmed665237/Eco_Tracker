module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.example.demo to javafx.fxml;
    exports com.example.demo;

    opens com.example.myscreen to javafx.fxml;
    exports com.example.myscreen;

    opens org.example to javafx.fxml;
    exports org.example;
}
