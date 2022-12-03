package pt.ipvc.rastreio.sistemaderastreio.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
public class Alerts {
    public static void showAlert(String title, String header, String content, AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.show();
        //how to use it
        //Alerts.showAlert("title", "header", e.getMessage(), Alert.AlertType.ERROR);
    }
}

