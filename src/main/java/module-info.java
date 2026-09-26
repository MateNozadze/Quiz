module com.example.loggame {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.logging.log4j;
    requires java.sql;
    requires com.h2database;

    opens com.example.loggame to javafx.fxml;
    exports com.example.loggame;
}
