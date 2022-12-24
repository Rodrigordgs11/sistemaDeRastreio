package pt.ipvc.rastreio.sistemaderastreio.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import pt.ipvc.rastreio.sistemaderastreio.App;
import pt.ipvc.rastreio.sistemaderastreio.Data.data;
import pt.ipvc.rastreio.sistemaderastreio.backend.TaskState;
import pt.ipvc.rastreio.sistemaderastreio.backend.Task;
import pt.ipvc.rastreio.sistemaderastreio.utils.Alerts;
import pt.ipvc.rastreio.sistemaderastreio.utils.loginRegisterExceptions.isEmptyException;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import static pt.ipvc.rastreio.sistemaderastreio.Data.data.*;


import javafx.event.ActionEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class TaskController implements Initializable {
    @FXML
    private PasswordField ConfirmPass;
    @FXML
    private TextField Name;
    @FXML
    private PasswordField Password;
    @FXML
    private TextField Phone;
    @FXML
    private TextField UserName;
    @FXML
    private Label name;
    @FXML
    private Label userName;
    @FXML
    private Parent parent;
    @FXML
    private Scene scene;
    @FXML
    private Stage stage;
    @FXML
    private TextField Date;

    @FXML
    private TextField Description;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //returnUserLogged();
    }

    public void returnUserLogged(){
        name.setText(Objects.requireNonNull(userLogged()).getName());
        userName.setText(Objects.requireNonNull(userLogged()).getUsername());
    }
    public void handleDashboard(MouseEvent event) throws IOException {
        parent = FXMLLoader.load(Objects.requireNonNull(App.class.getResource("dashboardView.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
        stage.setTitle("Menu Inicial");
    }
    public void ListTasks(){
        for (Task t: tasks){
            if(t.getidUser() == userLogged().getId()){
                System.out.println(t);
            }
        }
    }
    public void validator() throws isEmptyException, ParseException {
        if (Description.getText().isEmpty()) throw new isEmptyException("Description field is empty");
    }

    public void createTask() throws isEmptyException {
        try {
            validator();
            Task task = new Task(Description.getText(), TaskState.PORINICIAR, Objects.requireNonNull(userLogged()).getId());
            Date dateFormat = new Date();
            if (!Date.getText().isEmpty()) {
                    dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(Date.getText());
                    task.setStartTime(dateFormat);
                    tasks.add(task);
                    userLogged().getTasks().add(task);
                    //System.out.println(userLogged().getTasks().get(0).getDescription());
            }else {
                task.setState(TaskState.EMCURSO);
                task.setStartTime(dateFormat);
                userLogged().getTasks().add(task);
                tasks.add(task);
                //System.out.println(userLogged().getTasks().toString());
            }
            data.saveTasks();
        }catch(isEmptyException e){
            Alerts.showAlert("Empty field", "A field is empty",e.getMessage(), Alert.AlertType.WARNING);
        } catch (ParseException e) {
            Alerts.showAlert("Date format", "The Date format is incorrect(DD-MM-YY HH:MM:SS)",e.getMessage(), Alert.AlertType.WARNING);
        }
    }

    @FXML
    private HBox Utilizadores;

    @FXML
    private VBox container = new VBox();

    @FXML
    private HBox hBox;

    @FXML
    void CreateTask(ActionEvent event) throws  IOException{
        parent = FXMLLoader.load(Objects.requireNonNull(App.class.getResource("taskView.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
        stage.setTitle("Create Task");
    }

    @FXML
    void ListALLEMCURSO(ActionEvent event) {
        for (Task t: tasks){
            if(t.getState().equals(TaskState.EMCURSO)) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(App.class.getResource("taskItem.fxml"));
                try {
                    hBox = fxmlLoader.load();
                    TaskItemController taskItemController = fxmlLoader.getController();
                    taskItemController.setData(t);
                    container.getChildren().add(hBox); //give id to hbox
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    @FXML
    public void ListAll(ActionEvent event) {
        taskItem();
    }

    public void taskItem() {
        for (Task t: tasks){
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(App.class.getResource("taskItem.fxml"));
            try {
                hBox = fxmlLoader.load();
                TaskItemController taskItemController = fxmlLoader.getController();
                taskItemController.setData(t);
                container.getChildren().add(hBox); //give id to hbox
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    void ListAllFINALIZADO(ActionEvent event) {
        for (Task t: tasks){
            if(t.getState().equals(TaskState.FINALIZADO)) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(App.class.getResource("taskItem.fxml"));
                try {
                    hBox = fxmlLoader.load();
                    TaskItemController taskItemController = fxmlLoader.getController();
                    taskItemController.setData(t);
                    container.getChildren().add(hBox); //give id to hbox
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }


    @FXML
    void handleImage(MouseEvent event) {

    }

    @FXML
    private Label EndTime;

    @FXML
    private Label Price;

    @FXML
    private Label Project;

    @FXML
    private Label StartTime;

    @FXML
    private Label State;

    @FXML
    private Label idUser;

    @FXML
    void edit(ActionEvent event) throws IOException {

    }

    @FXML
    void remove(ActionEvent event) {

    }

    @FXML
    void terminate(ActionEvent event) {

    }



}



