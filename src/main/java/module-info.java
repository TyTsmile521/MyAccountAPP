module com.tytsmile.miniaccount {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.base;
    requires javafx.graphics;

    opens com.tytsmile.miniaccount to javafx.fxml;
    opens com.tytsmile.miniaccount.controller to javafx.fxml;
    opens com.tytsmile.miniaccount.databases.DAO to javafx.fxml;
    opens com.tytsmile.miniaccount.databases.entity to javafx.fxml;
    opens com.tytsmile.miniaccount.view to javafx.fxml;
    exports com.tytsmile.miniaccount;
}