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
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import pt.ipvc.rastreio.sistemaderastreio.App;
import pt.ipvc.rastreio.sistemaderastreio.backend.Project;
import pt.ipvc.rastreio.sistemaderastreio.backend.Task;
import pt.ipvc.rastreio.sistemaderastreio.utils.Alerts;
import pt.ipvc.rastreio.sistemaderastreio.utils.loginRegisterExceptions.isEmptyException;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import static pt.ipvc.rastreio.sistemaderastreio.Data.data.*;

public class ProjectItemController extends ProjectController implements Initializable {

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
    @FXML
    private Label ProjectName = new Label();

    @FXML
    private Button editButton;

    public static int getId() {
        return id;
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        for (Project p: projects) {
            if (p.getIdProject() == getId()) {
                setNameProject(p);
            }
        }
    }
    public void invisible(){idProject.setVisible(false);
        for (Project p: projects){
            if (Integer.parseInt(idProject.getText()) == p.getIdProject()) {
                if (!p.getOwner().equals(userLogged().getUsername())) {
                    remove.setVisible(false);
                    editButton.setVisible(false);
                }
            }
        }
    }
    public void setNameProject(Project p){
        ProjectName.setText("Project " + p.getName());
    }
    public void setData(Project p){
        Name.setText(p.getName());
        ClientName.setText(p.getClientName());
        Price.setText(String.valueOf(p.getPricePerHour()));
        TaskNumber.setText(String.valueOf(p.getTasks().size()));
        TimeSpent.setText(String.valueOf(getTotalDuration(p)));
        idProject.setText(String.valueOf(p.getIdProject()));
        if (p.getOwner().equals(userLogged().getUsername())) {
            Owner.setText("Me");
        } else{
            Owner.setText(p.getOwner());
        }
        invisible();
    }
    public long getTotalDuration(Project p){
        long total = 0;
        for (int i = 0; i < p.getTasks().size(); i++){
            total += p.getTasks().get(i).getDuration();
        }
        return total;
    }
    @FXML
    void ListTasks(ActionEvent event) throws IOException {
        projectItem();
        for (Project p: projects){
            if(Integer.parseInt(idProject.getText()) == p.getIdProject()){
                id = p.getIdProject();
                System.out.println(getId());
            }
        }
        parent = FXMLLoader.load(Objects.requireNonNull(App.class.getResource("projectTasks.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
        stage.setTitle("Menu Inicial");
    }

    public void projectTaskList () {
        for (Project p : projects) {
            System.out.println(p.getTasks());
            if (p.getIdProject() == getId()) {
                for (Task t : tasks) {
                    System.out.println(getId());
                    if (t.getIdProject() == getId()) {
                        FXMLLoader fxmlLoader = new FXMLLoader();
                        fxmlLoader.setLocation(App.class.getResource("taskItem.fxml"));
                        try {
                            hBox = fxmlLoader.load();
                            TaskItemController taskItemController = fxmlLoader.getController();
                            taskItemController.setData(t);
                            taskItemController.invisible();
                            setNameProject(p);
                            container.getChildren().add(hBox);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        }
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
        remove.setVisible(false);
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

    @FXML
    void AddTask(ActionEvent event) {
        container.getChildren().clear();
        for (Task t : tasks) {
            if(t.getIdProject() == 0){
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(App.class.getResource("taskItemAddProject.fxml"));
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

    public void addTaskToProject(){
        for (Project p : projects){
            for (Task t: tasks){
                if (t.getIdProject() == p.getIdProject()){
                    p.getTasks().add(t);
                }
            }

        }
    }
}
