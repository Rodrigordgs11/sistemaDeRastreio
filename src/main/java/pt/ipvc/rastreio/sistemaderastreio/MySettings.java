package pt.ipvc.rastreio.sistemaderastreio;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pt.ipvc.rastreio.sistemaderastreio.Data.data;

import java.io.IOException;
import java.util.Objects;
public class MySettings extends Application {
    @Override
    public void start(Stage primaryStage){
        try {
            Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("mySettings.fxml")));
            Scene scene = new Scene(parent);
            primaryStage.setScene(scene);
            primaryStage.show();
            primaryStage.setTitle("My settings");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
