module com.example.loggame {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.jfoenix;
    requires org.controlsfx.controls;
    requires org.apache.logging.log4j;
    requires java.logging;
    requires java.sql;

    opens com.example.loggame to javafx.fxml;
    exports com.example.loggame;
}
