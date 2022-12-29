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
import pt.ipvc.rastreio.sistemaderastreio.backend.user;
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

    @FXML
    private HBox Utilizadores;

    @FXML
    private VBox container = new VBox();

    @FXML
    private HBox hBox;

    @FXML
    private TextField EndTimeSearch;

    @FXML
    private TextField StarteTimeSearch;

    @FXML
    private Button createTaskButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        returnUserLogged();
        setVisibleUsers();
    }

    @FXML
    void handleImage(MouseEvent event) throws IOException {
        parent = FXMLLoader.load(Objects.requireNonNull(App.class.getResource("mySettings.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
        stage.setTitle("My Settings");
    }

    public void handleDashboard(MouseEvent event) throws IOException {
        parent = FXMLLoader.load(Objects.requireNonNull(App.class.getResource("dashboardView.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
        stage.setTitle("Menu Inicial");
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
    public void handleProject(MouseEvent event) throws IOException {
        parent = FXMLLoader.load(Objects.requireNonNull(App.class.getResource("projectView.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
        stage.setTitle("List Project");
    }

    public void ListTasks() {
        for (Task t : tasks) {
            if (t.getidUser() == userLogged().getId()) {
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
                task.setEndTime(dateFormat);
                tasks.add(task);
                userLogged().getTasks().add(task);
                //System.out.println(userLogged().getTasks().get(0).getDescription());
            } else {
                task.setState(TaskState.EMCURSO);
                task.setStartTime(dateFormat);
                task.setEndTime(dateFormat);
                userLogged().getTasks().add(task);
                tasks.add(task);
                //System.out.println(userLogged().getTasks().toString());
            }
            data.saveTasks();
        } catch (isEmptyException e) {
            Alerts.showAlert("Empty field", "A field is empty", e.getMessage(), Alert.AlertType.WARNING);
        } catch (ParseException e) {
            Alerts.showAlert("Date format", "The Date format is incorrect(DD-MM-YY HH:MM:SS)", e.getMessage(), Alert.AlertType.WARNING);
        }
        createTaskButton.setText("Created!");
    }

    @FXML
    void CreateTask(ActionEvent event) throws IOException {
        parent = FXMLLoader.load(Objects.requireNonNull(App.class.getResource("taskView.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
        stage.setTitle("Create Task");
    }

    @FXML
    void ListALLEMCURSO(ActionEvent event) {
        correctDuration();
        StarteTimeSearch.setVisible(false);
        EndTimeSearch.setVisible(false);
        container.getChildren().clear();
        for (Task t : tasks) {
            if (t.getState().equals(TaskState.EMCURSO)) {
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
        StarteTimeSearch.setVisible(false);
        EndTimeSearch.setVisible(false);
        taskItem();
    }

    public void taskItem() {
        startTask();
        endTask();
        correctDuration();
        container.getChildren().clear();
        for (Task t : tasks) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(App.class.getResource("taskItem.fxml"));
            try {
                hBox = fxmlLoader.load();
                TaskItemController taskItemController = fxmlLoader.getController();
                taskItemController.setData(t);
                taskItemController.invisible();
                container.getChildren().add(hBox); //give id to hbox
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    void ListAllFINALIZADO(ActionEvent event) {
        correctDuration();
        container.getChildren().clear();
        StarteTimeSearch.setVisible(true);
        EndTimeSearch.setVisible(true);
        for (Task t : tasks) {
            if (t.getState().equals(TaskState.FINALIZADO)) {
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

    public void startTask() {
        Date date = new Date();
        for (Task t : tasks) {
            System.out.println(date);
            if (t.getStartTime().compareTo(date) <= 0) {
                t.setState(TaskState.EMCURSO);
            }
        }
    }

    public void endTask() {
        Date date = new Date();
        for (Task t : tasks) {
            if (t.getEndTime().compareTo(date) <= 0 && !(t.getStartTime().compareTo(t.getEndTime()) == 0 && !(t.getState() == TaskState.FINALIZADO))) {
                t.setState(TaskState.FINALIZADO);
            }
        }
    }

    @FXML
    void showText(ActionEvent event) throws ParseException, isEmptyException {
        try {
            validatorLOL();
            for (Task t : tasks) {
                if (t.getStartTime().after(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(StarteTimeSearch.getText()))
                        && t.getEndTime().before(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(EndTimeSearch.getText()))) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(App.class.getResource("taskItem.fxml"));
                    hBox = fxmlLoader.load();
                    TaskItemController taskItemController = fxmlLoader.getController();
                    taskItemController.setData(t);
                    container.getChildren().add(hBox); //give id to hbox
                }
            }
        } catch(IOException e){
            throw new RuntimeException(e);
        }catch(ParseException e){
            Alerts.showAlert("Date format", "The Date format is incorrect(DD-MM-YY HH:MM:SS)", e.getMessage(), Alert.AlertType.WARNING);
        }catch(isEmptyException e) {
            Alerts.showAlert("Empty field", "A field is empty", e.getMessage(), Alert.AlertType.WARNING);
         }
    }

    public void validatorLOL() throws isEmptyException, ParseException {
        Date startTime;
        Date endTime;
        if(StarteTimeSearch.getText().isEmpty() || EndTimeSearch.getText().isEmpty()) throw new isEmptyException("Field is empty");
        if(!StarteTimeSearch.getText().isEmpty()){
            startTime = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(StarteTimeSearch.getText());
        }
        if(!EndTimeSearch.getText().isEmpty()){
            endTime = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(EndTimeSearch.getText());
        }
    }

    public void correctDuration(){
        for (Task t: tasks){
            if (t.getState() == TaskState.EMCURSO){
                t.setDuration((new Date().getTime() - t.getStartTime().getTime()) / (1000*60)/60);
            }
             if (t.getState() == TaskState.FINALIZADO) {
                 t.setDuration((t.getEndTime().getTime() - t.getStartTime().getTime()) / (1000*60^2)/60);
             }
        }
    }
    private void returnUserLogged(){
        System.out.println(userLogged());
        name.setText(Objects.requireNonNull(userLogged()).getName());
        userName.setText(Objects.requireNonNull(userLogged()).getUsername());
    }
    private void setVisibleUsers(){
        if(!Objects.requireNonNull(userLogged()).getTipoUser().equals(user.typeUser.userStd))
            Utilizadores.setVisible(true);
        else
            Utilizadores.setVisible(false);
    }
    @FXML
    public void handleInvite(MouseEvent event) throws IOException {
        parent = FXMLLoader.load(Objects.requireNonNull(App.class.getResource("invitesView.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
        stage.setTitle("Create and view invites");
    }

    public void handleUser(MouseEvent event) throws IOException {
        parent = FXMLLoader.load(Objects.requireNonNull(App.class.getResource("userView.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
        stage.setTitle("Create task");
    }
}




