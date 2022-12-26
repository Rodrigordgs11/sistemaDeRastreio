package pt.ipvc.rastreio.sistemaderastreio.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import pt.ipvc.rastreio.sistemaderastreio.backend.Project;
import pt.ipvc.rastreio.sistemaderastreio.backend.Task;

public class ProjectItemController extends ProjectController{
    @FXML
    private Label ClientName;

    @FXML
    private Label Name;

    @FXML
    private Label Owner;

    @FXML
    private Label Price;

    @FXML
    private Label TaskNumber;

    @FXML
    private Label TimeSpent;

    @FXML
    private Label idTask;

    @FXML
    private Button lis;

    @FXML
    private Button remove;

    public void setData(Project p){
        Name.setText(p.getName());
        ClientName.setText(p.getClientName());
        Price.setText(String.valueOf(p.getPricePerHour()));
        TaskNumber.setText(String.valueOf(p.getTasks().size()));
        TimeSpent.setText(String.valueOf(getTotalDuration(p)));
    }

    public long getTotalDuration(Project p){
        long total = 0;
        for (int i = 0; i < p.getTasks().size(); i++){
            total += p.getTasks().get(i).getDuration();
        }
        return total;
    }

    @FXML
    void ListTasks(ActionEvent event) {

    }

    @FXML
    void edit(ActionEvent event) {

    }

    @FXML
    void remove(ActionEvent event) {

    }
}
