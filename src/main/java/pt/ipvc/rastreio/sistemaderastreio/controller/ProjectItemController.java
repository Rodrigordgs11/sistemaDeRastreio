package pt.ipvc.rastreio.sistemaderastreio.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import pt.ipvc.rastreio.sistemaderastreio.App;
import pt.ipvc.rastreio.sistemaderastreio.backend.Project;
import pt.ipvc.rastreio.sistemaderastreio.backend.Task;
import pt.ipvc.rastreio.sistemaderastreio.utils.Alerts;
import pt.ipvc.rastreio.sistemaderastreio.utils.loginRegisterExceptions.isEmptyException;

import java.io.IOException;
import java.util.Objects;

import static pt.ipvc.rastreio.sistemaderastreio.Data.data.*;

public class ProjectItemController extends ProjectController{

    private static int id;

    private Stage stage;
    private Scene scene;
    private Parent parent;
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
    private Label idProject;

    @FXML
    private Button lis;

    @FXML
    private Button remove;
    @FXML
    private TextField EditClientName;

    @FXML
    private TextField EditName;

    @FXML
    private TextField EditPrice;

    @FXML
    private HBox Utilizadores;

    @FXML
    private Button editChangesButton;

    @FXML
    private Label name;

    @FXML
    private Label userName;

    public int getId() {
        return id;
    }

    public void invisible(){idProject.setVisible(false);}

    public void setData(Project p){
        Name.setText(p.getName());
        ClientName.setText(p.getClientName());
        Price.setText(String.valueOf(p.getPricePerHour()));
        TaskNumber.setText(String.valueOf(p.getTasks().size()));
        TimeSpent.setText(String.valueOf(getTotalDuration(p)));
        idProject.setText(String.valueOf(p.getIdProject()));
        Owner.setText(p.getOwner());
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
    void edit(ActionEvent event) throws IOException {
        projectItem();
        for (Project p: projects){
            if(Integer.parseInt(idProject.getText()) == p.getIdProject()){
                id = p.getIdProject();
                System.out.println(getId());
            }
        }
        parent = FXMLLoader.load(Objects.requireNonNull(App.class.getResource("editProject.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
        stage.setTitle("Menu Inicial");
    }

    @FXML
    void EditProject(ActionEvent event) {
        try {
            ValidatorEdit();
            if(!EditName.getText().isEmpty()){
                for (Project p: projects){
                    if(p.getIdProject() == getId()){
                        p.setName(EditName.getText());
                        p.setClientName(EditClientName.getText());
                        p.setPricePerHour(Float.parseFloat(EditPrice.getText()));
                    }
                }
            }
            editChangesButton.setText("Edited!");
        } catch (isEmptyException e) {
            Alerts.showAlert("Empty field", "A field is empty", e.getMessage(), Alert.AlertType.WARNING);
        } catch (NumberFormatException e){
            Alerts.showAlert("Price Per Hour", "Float field with letters", e.getMessage(), Alert.AlertType.ERROR);
        }
        saveProjects();
    }

    public void ValidatorEdit() throws isEmptyException{
        if(EditName.getText().isEmpty() || EditClientName.getText().isEmpty() || EditPrice.getText().isEmpty()) throw new isEmptyException("Text field is empty");
    }

    @FXML
    void remove(ActionEvent event) {
        projectItem();
        projects.removeIf(p -> Integer.parseInt(idProject.getText()) == p.getIdProject());
        saveProjects();
    }



    @FXML
    void handleDashboard(MouseEvent event) {

    }

    @FXML
    void handleImage(MouseEvent event) {

    }

    @FXML
    void handleTask(MouseEvent event) {

    }
}
