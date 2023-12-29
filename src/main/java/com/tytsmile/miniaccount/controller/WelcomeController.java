package com.tytsmile.miniaccount.controller;

import com.tytsmile.miniaccount.StartUp;
import javafx.fxml.FXML;

public class WelcomeController {

    @FXML
    public void initialize(){

    }

    @FXML
    public void dologin(){

        StartUp.changeView("view/OverviewPage.fxml");

    }
}
