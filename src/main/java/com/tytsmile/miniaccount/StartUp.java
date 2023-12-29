package com.tytsmile.miniaccount;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class StartUp extends Application {
    private static Stage mainstage;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        StartUp.mainstage = stage;
        stage.setTitle("MINI记账");
        changeView("view/Welcome.fxml");
        //stage.setHeight(494);
        //stage.setWidth(800);
        stage.setResizable(false);
        stage.show();
    }
    public static void changeView(String fxml)  {
        Parent root = null;
        try {
            root = FXMLLoader.load(StartUp.class.getResource(fxml));
            mainstage.setScene(new Scene(root));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static Stage getMainstage() {
        return mainstage;
    }

    public static void setMainstage(Stage mainstage) {
        StartUp.mainstage = mainstage;
    }
}