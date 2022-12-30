package pt.ipvc.rastreio.sistemaderastreio.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import pt.ipvc.rastreio.sistemaderastreio.Data.data;
import pt.ipvc.rastreio.sistemaderastreio.Routes.routes;
import pt.ipvc.rastreio.sistemaderastreio.backend.Task;
import pt.ipvc.rastreio.sistemaderastreio.backend.TaskState;
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
    @FXML
    private Label description;
    @FXML
    private Label EndTime;
    @FXML
    private Label Price = new Label();
    @FXML
    private Label Project = new Label();
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

    @FXML
    private Button editTaskButton;

    @FXML
    private Button terminateTaskButton;
    @FXML
    private Button addButton;
    public int getId() {
        return id;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}
    public void invisible(){
        for (Task t: tasks) {
            if (Integer.parseInt(idTask.getText()) == t.getIdTask()) {
                if (t.getProjectName() == null) {
                    Project.setVisible(false);
                    Price.setVisible(false);
                }
                if (!t.getEndTime().equals(t.getStartTime())){
                    EndTime.setVisible(true);
                    termination.setVisible(false);
                }
                if (t.getState() == TaskState.PORINICIAR){
                    remover.setVisible(false);
                    termination.setVisible(false);
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
        Project.setText(task.getProjectName());
        Price.setText(String.valueOf(task.getPriceProject()));
        idTask.setText(String.valueOf(task.getIdTask()));
        idTask.setVisible(false);
        for (Task t: tasks) {
            if (Integer.parseInt(idTask.getText()) == t.getIdTask()) {
                if (!(t.getState() == TaskState.FINALIZADO)) {
                    EndTime.setVisible(false);
                }
            }
        }
    }
    @FXML
    public void edit(ActionEvent event) throws IOException {
        ListAllMethod();
        for (Task t: tasks){
            if(Integer.parseInt(idTask.getText()) == t.getIdTask()){
                id = t.getIdTask();
                System.out.println(getId());
            }
        }
        routes.handleGeneric(event, "Edit task", "editTask.fxml");
    }
    @FXML
    public void remove(ActionEvent event){
        ListAllMethod();
        tasks.removeIf(t -> Integer.parseInt(idTask.getText()) == t.getIdTask());
        saveTasks();
        remover.setVisible(false);
    }
    @FXML
    void terminate(ActionEvent event) throws IOException{
        ListAllMethod();
        for (Task t: tasks){
            if(Integer.parseInt(idTask.getText()) == t.getIdTask()){
                id = t.getIdTask();
                System.out.println(getId());
            }
        }
        routes.handleGeneric(event, "Terminate Task", "terminateTaskView.fxml");
        termination.setVisible(false);
    }
    public void handleDashboard(MouseEvent event) throws IOException {
        routes.handleGeneric(event, "Initial menu", "dashboardView.fxml");
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
        terminateTaskButton.setText("Terminated!!");
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
        editTaskButton.setText("Edited!");
        saveTasks();
    }
    public void validatorEdit() throws isEmptyException{
        if(EditDescription.getText().isEmpty()) throw new isEmptyException("The field is empty");
    }
    @FXML
    void addTaskAction(ActionEvent event) {
        for (Task t: tasks){
            if(Integer.parseInt(idTask.getText()) == t.getIdTask()){
                id = t.getIdTask();
                System.out.println(ProjectItemController.getId());
                t.setIdProject(ProjectItemController.getId());
            }
        }
        for(Task tk: tasks){
            System.out.println(tk);
        }
        saveTasks();
    }
}