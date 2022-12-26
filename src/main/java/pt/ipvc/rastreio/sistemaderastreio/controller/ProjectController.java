package pt.ipvc.rastreio.sistemaderastreio.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pt.ipvc.rastreio.sistemaderastreio.App;
import pt.ipvc.rastreio.sistemaderastreio.Data.data;
import pt.ipvc.rastreio.sistemaderastreio.backend.*;
import pt.ipvc.rastreio.sistemaderastreio.utils.Alerts;
import pt.ipvc.rastreio.sistemaderastreio.utils.loginRegisterExceptions.alreadyExistException;
import pt.ipvc.rastreio.sistemaderastreio.utils.loginRegisterExceptions.isEmptyException;
import pt.ipvc.rastreio.sistemaderastreio.utils.loginRegisterExceptions.matchException;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import static pt.ipvc.rastreio.sistemaderastreio.Data.data.*;

public class ProjectController {

    @FXML
    private Parent parent;
    @FXML
    private Scene scene;
    @FXML
    private Stage stage;
    @FXML
    private HBox Utilizadores;

    @FXML
    private VBox container;

    @FXML
    private Label name;

    @FXML
    private Label userName;

    @FXML
    private TextField CreateClientName;

    @FXML
    private TextField CreateName;

    @FXML
    private TextField CreatePrice;


    @FXML
    void handleDashboard(MouseEvent event) throws IOException {
        parent = FXMLLoader.load(Objects.requireNonNull(App.class.getResource("dashboardView.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
        stage.setTitle("Menu Inicial");
    }

    @FXML
    public void handleProject(MouseEvent event) throws IOException {
        parent = FXMLLoader.load(Objects.requireNonNull(App.class.getResource("projectView.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
        stage.setTitle("List Project");
    }
    @FXML
    void handleTask(MouseEvent event) throws IOException {
        parent = FXMLLoader.load(Objects.requireNonNull(App.class.getResource("taskEditView.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
        stage.setTitle("List Tasks");
    }

    @FXML
    public void CreateProject(ActionEvent event) throws IOException {
        parent = FXMLLoader.load(Objects.requireNonNull(App.class.getResource("createProject.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
        stage.setTitle("Create Project");
    }

    @FXML
    public void SaveChanges(ActionEvent event){
        try {
            Validator();
            Project project = new Project(CreateName.getText(), CreateClientName.getText(), Float.valueOf(CreatePrice.getText()));
            projects.add(project);
        }catch (NumberFormatException e){
            Alerts.showAlert("Price Per Hour", "Float field with letters",e.getMessage(), Alert.AlertType.ERROR);
        }catch(isEmptyException e) {
            Alerts.showAlert("Empty field", "A field is empty", e.getMessage(), Alert.AlertType.WARNING);
        }
        data.saveProjects();
    }

    public void Validator() throws isEmptyException{
        boolean exist = false;
        boolean existPhone = false;
        if(CreateName.getText().isEmpty() || CreateClientName.getText().isEmpty() || CreatePrice.getText().isEmpty()) throw new isEmptyException("Text field is empty");
    }


    @FXML
    void ListAll(ActionEvent event) {

    }

    @FXML
    void ListAllMine(ActionEvent event) {

    }

    @FXML
    void ListAllShared(ActionEvent event) {

    }





    @FXML
    void handleImage(MouseEvent event) {

    }

}


