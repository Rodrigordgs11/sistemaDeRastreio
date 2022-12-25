package pt.ipvc.rastreio.sistemaderastreio.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import pt.ipvc.rastreio.sistemaderastreio.App;
import pt.ipvc.rastreio.sistemaderastreio.backend.Task;
import pt.ipvc.rastreio.sistemaderastreio.backend.TaskState;
import pt.ipvc.rastreio.sistemaderastreio.backend.user;

import java.io.IOException;
import java.net.URL;
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
    private Label idTask;

    @FXML
    private Button remove;
    @FXML
    void terminate(ActionEvent event) {

    }
    public int getId() {
        return id;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idTask.setVisible(true);
    }

    public void invisible(){
        for (Task t: tasks) {
            if (Integer.parseInt(idTask.getText()) == t.getIdTask()) {
                if (t.getProjectName() == null) {
                    Project.setVisible(false);
                    Price.setVisible(false);
                }
                if (!(t.getState() == TaskState.FINALIZADO)) {
                    EndTime.setVisible(false);
                }
                if (t.getState() == TaskState.PORINICIAR){
                    remove.setVisible(false);
                }
            }
        }
    }

    public void setData(Task task){
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
    public void handleDashboard(MouseEvent event) throws IOException {
        parent = FXMLLoader.load(Objects.requireNonNull(App.class.getResource("dashboardView.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
        stage.setTitle("Menu Inicial");
    }
}
