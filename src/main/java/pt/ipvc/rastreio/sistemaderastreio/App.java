package pt.ipvc.rastreio.sistemaderastreio;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pt.ipvc.rastreio.sistemaderastreio.controller.UserController.*;

import java.io.IOException;
import java.util.Objects;

import static pt.ipvc.rastreio.sistemaderastreio.Data.data.loadUsers;

public class App extends Application {
    @Override
    public void start(Stage primaryStage){
        try {
            loadUsers();
            Parent parent = FXMLLoader.load(Objects.requireNonNull(App.class.getResource("appView.fxml")));
            Scene scene = new Scene(parent);
            primaryStage.setScene(scene);
            primaryStage.show();
            primaryStage.setTitle("Login and register");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
}