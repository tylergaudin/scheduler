module gaudin.scheduler {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;


    opens gaudin.scheduler.appmain to javafx.fxml;
    exports gaudin.scheduler.appmain;
    exports gaudin.scheduler.DAO;
    opens gaudin.scheduler.DAO to javafx.fxml;
    exports gaudin.scheduler.model;
    opens gaudin.scheduler.model to javafx.fxml;
    exports gaudin.scheduler.utilities;
    opens gaudin.scheduler.utilities to javafx.fxml;
    exports gaudin.scheduler.controller;
    opens gaudin.scheduler.controller to javafx.fxml;
}