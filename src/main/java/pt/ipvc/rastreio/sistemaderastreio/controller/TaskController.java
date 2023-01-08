package pt.ipvc.rastreio.sistemaderastreio.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import pt.ipvc.rastreio.sistemaderastreio.App;
import pt.ipvc.rastreio.sistemaderastreio.Data.data;
import pt.ipvc.rastreio.sistemaderastreio.Routes.routes;
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
import static pt.ipvc.rastreio.sistemaderastreio.Data.data.*;
import static pt.ipvc.rastreio.sistemaderastreio.controller.ProjectItemController.addTaskToProject;

import javafx.event.ActionEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
public class TaskController implements Initializable {
    @FXML
    private Label name;
    @FXML
    private Label userName;
    @FXML
    private TextField Date;
    @FXML
    private TextField Description;
    @FXML
    private HBox Utilizadores;
    @FXML
    private Button search;
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
        addTaskToProject();
    }
    public void validator() throws isEmptyException, ParseException {
        if (Description.getText().isEmpty()) throw new isEmptyException("Description field is empty");
    }
    public void createTask() {
        try {
            validator();
            Task task = new Task(Description.getText(), TaskState.PORINICIAR, Objects.requireNonNull(userLogged()).getId());
            Date dateFormat = new Date();
            if (!Date.getText().isEmpty()) {
                dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(Date.getText());
                task.setStartTime(dateFormat);
                task.setEndTime(dateFormat);
                tasks.add(task);
                Objects.requireNonNull(userLogged()).getTasks().add(task);
            } else {
                task.setState(TaskState.EMCURSO);
                task.setStartTime(dateFormat);
                task.setEndTime(dateFormat);
                Objects.requireNonNull(userLogged()).getTasks().add(task);
                tasks.add(task);
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
    public void ListAll(ActionEvent event) {
        EndTimeSearch.setVisible(false);
        StarteTimeSearch.setVisible(false);
        search.setVisible(false);
        ListAllMethod();
    }
    @FXML
    public void ListALLEMCURSO(ActionEvent event) {
        StarteTimeSearch.setVisible(false);
        EndTimeSearch.setVisible(false);
        search.setVisible(false);
        startTask();
        endTask();
        correctDuration();
        container.getChildren().clear();
        for (Task t : tasks) {
            if (!(Objects.requireNonNull(userLogged()).tipoUser == user.typeUser.userStd)
                    && t.getState().equals(TaskState.EMCURSO)) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(App.class.getResource("taskItem.fxml"));
                try {
                    hBox = fxmlLoader.load();
                    TaskItemController taskItemController = fxmlLoader.getController();
                    taskItemController.setData(t);
                    taskItemController.invisible();
                    container.getChildren().add(hBox);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                if (t.getState().equals(TaskState.EMCURSO) && t.getidUser() == Objects.requireNonNull(userLogged()).getId()) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(App.class.getResource("taskItem.fxml"));
                    try {
                        hBox = fxmlLoader.load();
                        TaskItemController taskItemController = fxmlLoader.getController();
                        taskItemController.setData(t);
                        taskItemController.invisible();
                        container.getChildren().add(hBox);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }
    public void ListAllMethod() {
        startTask();
        endTask();
        correctDuration();
        container.getChildren().clear();
        for (Task t : tasks) {
            if (!(Objects.requireNonNull(userLogged()).tipoUser == user.typeUser.userStd)) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(App.class.getResource("taskItem.fxml"));
                try {
                    hBox = fxmlLoader.load();
                    TaskItemController taskItemController = fxmlLoader.getController();
                    taskItemController.setData(t);
                    taskItemController.invisible();
                    container.getChildren().add(hBox);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                if (t.getidUser() == Objects.requireNonNull(userLogged()).getId()) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(App.class.getResource("taskItem.fxml"));
                    try {
                        hBox = fxmlLoader.load();
                        TaskItemController taskItemController = fxmlLoader.getController();
                        taskItemController.setData(t);
                        taskItemController.invisible();
                        container.getChildren().add(hBox);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }
    @FXML
    void ListAllFINALIZADO(ActionEvent event) {
        correctDuration();
        container.getChildren().clear();
        StarteTimeSearch.setVisible(true);
        EndTimeSearch.setVisible(true);
        search.setVisible(true);
        for (Task t : tasks) {
            if (!(Objects.requireNonNull(userLogged()).tipoUser == user.typeUser.userStd)) {
                if (t.getState().equals(TaskState.FINALIZADO)) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(App.class.getResource("taskItem.fxml"));
                    try {
                        hBox = fxmlLoader.load();
                        TaskItemController taskItemController = fxmlLoader.getController();
                        taskItemController.setData(t);
                        taskItemController.invisible();
                        container.getChildren().add(hBox);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            } else {
                if (t.getState().equals(TaskState.FINALIZADO) && t.getidUser() == Objects.requireNonNull(userLogged()).getId()) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(App.class.getResource("taskItem.fxml"));
                    try {
                        hBox = fxmlLoader.load();
                        TaskItemController taskItemController = fxmlLoader.getController();
                        taskItemController.setData(t);
                        container.getChildren().add(hBox);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }
    public void startTask() {
        Date date = new Date();
        for (Task t : tasks) {
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
    void showText(ActionEvent event) {
        container.getChildren().clear();
        try {
            validatorDate();
            for (Task t : tasks) {
                if (t.getStartTime().after(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(StarteTimeSearch.getText()))
                        && t.getEndTime().before(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(EndTimeSearch.getText()))) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(App.class.getResource("taskItem.fxml"));
                    hBox = fxmlLoader.load();
                    TaskItemController taskItemController = fxmlLoader.getController();
                    taskItemController.setData(t);
                    container.getChildren().add(hBox);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            Alerts.showAlert("Date format", "The Date format is incorrect(DD-MM-YY HH:MM:SS)", e.getMessage(), Alert.AlertType.WARNING);
        } catch (isEmptyException e) {
            Alerts.showAlert("Empty field", "A field is empty", e.getMessage(), Alert.AlertType.WARNING);
        }
    }
    public void validatorDate() throws isEmptyException, ParseException {
        Date startTime;
        Date endTime;
        if (StarteTimeSearch.getText().isEmpty() || EndTimeSearch.getText().isEmpty())
            throw new isEmptyException("Field is empty");
        if (!StarteTimeSearch.getText().isEmpty()) {
            startTime = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(StarteTimeSearch.getText());
        }
        if (!EndTimeSearch.getText().isEmpty()) {
            endTime = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(EndTimeSearch.getText());
        }
    }
    public static void correctDuration() {
        for (Task t : tasks) {
            if (t.getState() == TaskState.EMCURSO) {
                t.setDuration((new Date().getTime() - t.getStartTime().getTime()) / (1000 * 60) / 60);
            }
            if (t.getState() == TaskState.FINALIZADO) {
                t.setDuration((t.getEndTime().getTime() - t.getStartTime().getTime()) / (1000 * 60 ^ 2) / 60);
            }
        }
    }
    private void returnUserLogged() {
        name.setText(Objects.requireNonNull(userLogged()).getName());
        userName.setText(Objects.requireNonNull(userLogged()).getUsername());
    }
    private void setVisibleUsers() {
        Utilizadores.setVisible(!Objects.requireNonNull(userLogged()).getTipoUser().equals(user.typeUser.userStd));
    }
    @FXML
    public void handleInvite(MouseEvent event) throws IOException {
        routes.handleGeneric(event, "Create and view invites", "invitesView.fxml");
    }
    public void handleUser(MouseEvent event) throws IOException {
        routes.handleGeneric(event, "Create task", "userView.fxml");
    }
    @FXML
    void handleImage(MouseEvent event) throws IOException {
        routes.handleGeneric(event, "My Settings", "mySettings.fxml");
    }
    public void handleDashboard(MouseEvent event) throws IOException {
        routes.handleGeneric(event, "Inicial menu", "dashboardView.fxml");
    }
    @FXML
    void handleTask(MouseEvent event) throws IOException {
        routes.handleGeneric(event, "List Tasks", "taskEditView.fxml");
    }
    @FXML
    public void handleProject(MouseEvent event) throws IOException {
        routes.handleGeneric(event, "List Project", "projectView.fxml");
    }
    @FXML
    void CreateTask(ActionEvent event) throws IOException {
        routes.handleGeneric(event, "Create Task", "taskView.fxml");
    }
    @FXML
    public void handleReport(MouseEvent event) throws IOException {
        routes.handleGeneric(event, "Create report", "reportsView.fxml");
    }
    public void switchUser(MouseEvent event) throws IOException {
        routes.handleGeneric(event, "Create task", "userView.fxml");
    }
}