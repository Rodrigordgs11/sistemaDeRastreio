package pt.ipvc.rastreio.sistemaderastreio.Routes;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import pt.ipvc.rastreio.sistemaderastreio.App;

import java.io.IOException;
import java.util.Objects;

public class routes {
    private static Stage stage;
    private static Scene scene;
    private static Parent parent;

    public static void handleGeneric(MouseEvent event, String title, String sceneName) throws IOException {
        parent = FXMLLoader.load(Objects.requireNonNull(App.class.getResource(sceneName)));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
        stage.setTitle(title);
    }
    public static void handleGeneric(ActionEvent event, String title, String sceneName) throws IOException {
        parent = FXMLLoader.load(Objects.requireNonNull(App.class.getResource(sceneName)));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
        stage.setTitle(title);
    }
}
