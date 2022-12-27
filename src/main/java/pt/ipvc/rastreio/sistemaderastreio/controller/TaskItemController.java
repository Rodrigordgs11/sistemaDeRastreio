package pt.ipvc.rastreio.sistemaderastreio.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import pt.ipvc.rastreio.sistemaderastreio.App;
import pt.ipvc.rastreio.sistemaderastreio.Data.data;
import pt.ipvc.rastreio.sistemaderastreio.backend.Task;
import pt.ipvc.rastreio.sistemaderastreio.backend.TaskState;
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

public class TaskItemController extends TaskController implements Initializable{
    private static int id;
    private Stage stage;
    private Scene scene;
    private Parent parent;
    @FXML
    private Label description;
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
    private Label Duration;
    @FXML
    private Label idTask;
    @FXML
    private Button remover;
    @FXML
    private Button termination;
    @FXML
    private TextField EditDescription;

    public int getId() {
        return id;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}

    public void invisible(){
        idTask.setVisible(false);
        for (Task t: tasks) {
            if (Integer.parseInt(idTask.getText()) == t.getIdTask()) {
                if (t.getProjectName() == null) {
                    Project.setVisible(false);
                    Price.setVisible(false);
                }
                if (!(t.getState() == TaskState.FINALIZADO)) {
                    EndTime.setVisible(false);
                }
                if (!t.getEndTime().equals(t.getStartTime())){
                    EndTime.setVisible(true);
                    termination.setVisible(false);
                }
                if (t.getState() == TaskState.PORINICIAR){
                    remover.setVisible(false);
                    Duration.setVisible(false);
                }
                if ((t.getState() == TaskState.FINALIZADO)){
                    termination.setVisible(false);
                }
            }
        }
    }

    public void setData(Task task){
        Duration.setText(String.valueOf(task.getDuration()));
        description.setText(task.getDescription());
        StartTime.setText(String.valueOf(task.getStartTime()));
        EndTime.setText(String.valueOf(task.getEndTime()));
        State.setText(String.valueOf(task.getState()));
        idTask.setText(String.valueOf(task.getIdTask()));
        invisible();
    }
    @FXML
    public void edit(ActionEvent event) throws IOException {
        taskItem();
        for (Task t: tasks){
            if(Integer.parseInt(idTask.getText()) == t.getIdTask()){
                id = t.getIdTask();
                System.out.println(getId());
            }
        }
        parent = FXMLLoader.load(Objects.requireNonNull(App.class.getResource("editTask.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
        stage.setTitle("Menu Inicial");
    }
    @FXML
    public void remove(ActionEvent event){
        taskItem();
        tasks.removeIf(t -> Integer.parseInt(idTask.getText()) == t.getIdTask());
        saveTasks();
    }

    @FXML
    void terminate(ActionEvent event) throws IOException{
        taskItem();
        for (Task t: tasks){
            if(Integer.parseInt(idTask.getText()) == t.getIdTask()){
                id = t.getIdTask();
                System.out.println(getId());
            }
        }
        parent = FXMLLoader.load(Objects.requireNonNull(App.class.getResource("terminateTaskView.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
        stage.setTitle("Terminate Task");
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
    private TextField TerminateDate;
    @FXML
    public void terminateTask(ActionEvent event) throws ParseException {
        try {
            ValidatorTerminate();
        }catch (ParseException e){
            Alerts.showAlert("Date format", "The Date format is incorrect(DD-MM-YY HH:MM:SS)",e.getMessage(), Alert.AlertType.WARNING);
        }
        data.saveTasks();
    }

    public void ValidatorTerminate() throws ParseException{
        java.util.Date dateFormat = new Date();
        for (Task t: tasks) {
            if (t.getIdTask() == getId()) {
                if (!TerminateDate.getText().isEmpty()) {
                    dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(TerminateDate.getText());
                    t.setEndTime(dateFormat);
                } else {
                    t.setState(TaskState.FINALIZADO);
                    t.setEndTime(dateFormat);
                    Objects.requireNonNull(userLogged()).getTasks().add(t);
                }
            }
        }
    }
    @FXML
    void editTask(ActionEvent event) {
        try {
            validatorEdit();
            if(!EditDescription.getText().isEmpty()){
                for (Task t:tasks){
                    if(t.getIdTask() == getId()){
                        t.setDescription(EditDescription.getText());
                    }
                }
            }
        } catch (isEmptyException e) {
            Alerts.showAlert("Empty field", "A field is empty",e.getMessage(), Alert.AlertType.WARNING);
        }
        saveTasks();
    }

    public void validatorEdit() throws isEmptyException{
        if(EditDescription.getText().isEmpty()) throw new isEmptyException("The field is empty");
    }

    @FXML
    void addTaskAction(ActionEvent event) {

    }
}
