package com.tytsmile.miniaccount.util;

import com.tytsmile.miniaccount.StartUp;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class openNewWindowUtil {

    public static void openNewWindow(String fxml) {
        Stage primaryStage = StartUp.getMainstage(); // 获取主窗口的Stage
        Stage temstage = new Stage();
        temstage.setWidth(400);
        temstage.setHeight(247);
        temstage.setTitle("消息");
        temstage.initModality(Modality.APPLICATION_MODAL); // 模态对话框，阻止用户与其他窗口交互

        Parent root = null;
        try {
            root = FXMLLoader.load(StartUp.class.getResource(fxml));
            temstage.setScene(new Scene(root));

            // 计算新窗口的位置以在主窗口中间显示
            if (primaryStage != null) {
                // 获取主窗口的位置
                double parentX = primaryStage.getX();
                double parentY = primaryStage.getY();
                // 计算新窗口的位置以在主窗口中间显示
                double newX = parentX + (primaryStage.getWidth() - temstage.getWidth()) / 2;
                double newY = parentY + (primaryStage.getHeight() - temstage.getHeight()) / 2;

                temstage.setX(newX);
                temstage.setY(newY);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        temstage.showAndWait();
    }
}
