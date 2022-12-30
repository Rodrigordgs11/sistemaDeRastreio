package pt.ipvc.rastreio.sistemaderastreio.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import pt.ipvc.rastreio.sistemaderastreio.App;
import pt.ipvc.rastreio.sistemaderastreio.Routes.routes;
import pt.ipvc.rastreio.sistemaderastreio.backend.Project;
import pt.ipvc.rastreio.sistemaderastreio.backend.Task;
import pt.ipvc.rastreio.sistemaderastreio.backend.TaskState;
import pt.ipvc.rastreio.sistemaderastreio.utils.Alerts;
import pt.ipvc.rastreio.sistemaderastreio.utils.loginRegisterExceptions.isEmptyException;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import static pt.ipvc.rastreio.sistemaderastreio.Data.data.*;
import static pt.ipvc.rastreio.sistemaderastreio.controller.TaskController.correctDuration;

public class ProjectItemController extends ProjectController implements Initializable {
    private static int id;
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
        addTaskToProject();
        projectTaskList();
    }
    public void ValidatorEdit() throws isEmptyException{
        if(EditName.getText().isEmpty() || EditClientName.getText().isEmpty() || EditPrice.getText().isEmpty()) throw new isEmptyException("Text field is empty");
    }
    public void invisible(){idProject.setVisible(false);
        for (Project p: projects){
            if (Integer.parseInt(idProject.getText()) == p.getIdProject()) {
                if (!p.getOwner().equals(Objects.requireNonNull(userLogged()).getUsername())) {
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
        if (p.getOwner().equals(Objects.requireNonNull(userLogged()).getUsername())) {
            Owner.setText("Me");
        } else{
            Owner.setText(p.getOwner());
        }
        invisible();
    }
    public long getTotalDuration(Project p){
        long total = 0;
        for (Task t: p.getTasks()){
            total += t.getDuration();
        }
        System.out.println(total);
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
        routes.handleGeneric(event, "Menu Inicial", "projectTasks.fxml");
    }
    public void projectTaskList () {
        correctDuration();
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
        routes.handleGeneric(event, "Edit projects", "editProject.fxml");
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
    @FXML
    void remove(ActionEvent event) {
        projectItem();
        projects.removeIf(p -> Integer.parseInt(idProject.getText()) == p.getIdProject());
        saveProjects();
        remove.setVisible(false);
    }
    @FXML
    void AddTask(ActionEvent event) {
        container.getChildren().clear();
        correctDuration();
        for (Task t : tasks) {
            if(t.getIdProject() == 0 && t.getState() == TaskState.PORINICIAR){
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
    public static void addTaskToProject(){
        correctDuration();
        for (Project p : projects){
            for (Task t: tasks){
                if (t.getIdProject() == p.getIdProject() && !p.getTasks().contains(t)){
                    p.getTasks().add(t);
                    t.setPriceProject(p.getPricePerHour());
                    t.setProjectName(p.getName());
                }
            }

        }
    }
    @FXML
    void handleDashboard(MouseEvent event) throws IOException {
        routes.handleGeneric(event, "Menu Inicial", "dashboardView.fxml");
    }
    @FXML
    void handleImage(MouseEvent event) throws IOException {
        routes.handleGeneric(event, "My settings", "mySettings.fxml");
    }
    @FXML
    void handleTask(MouseEvent event) throws IOException {
        routes.handleGeneric(event, "Create task", "taskEditView.fxml");
    }
    @FXML
    void handleInvites(MouseEvent event) throws IOException {
        routes.handleGeneric(event, "Create and view invites", "invitesView.fxml");
    }
}