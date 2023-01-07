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
import javafx.scene.layout.VBox;
import pt.ipvc.rastreio.sistemaderastreio.App;
import pt.ipvc.rastreio.sistemaderastreio.Data.data;
import pt.ipvc.rastreio.sistemaderastreio.Routes.routes;
import pt.ipvc.rastreio.sistemaderastreio.backend.*;
import pt.ipvc.rastreio.sistemaderastreio.utils.Alerts;
import pt.ipvc.rastreio.sistemaderastreio.utils.loginRegisterExceptions.isEmptyException;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import static pt.ipvc.rastreio.sistemaderastreio.Data.data.*;
public class ProjectController implements Initializable {
    @FXML
    private HBox Utilizadores;
    @FXML
    protected VBox container = new VBox();
    @FXML
    private Label name;
    @FXML
    private Label userName;
    @FXML
    private TextField CreateClientName;
    @FXML
    private TextField CreateName;
    @FXML
    private TextField CreatePrice;
    @FXML
    private Button saveChangesButton;
    protected HBox hBox = new HBox();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        returnUserLogged();
        setVisibleUsers();
    }
    public void Validator() throws isEmptyException{
        if(CreateName.getText().isEmpty() || CreateClientName.getText().isEmpty() || CreatePrice.getText().isEmpty()) throw new isEmptyException("Text field is empty");
    }
    @FXML
    public void SaveChanges(){
        try {
            Validator();
                Project project = new Project(CreateName.getText(), CreateClientName.getText(), Float.parseFloat(CreatePrice.getText()), Objects.requireNonNull(userLogged()).getUsername(), null);
            projects.add(project);
            saveChangesButton.setText("Saved!");
        }catch (NumberFormatException e){
            Alerts.showAlert("Price Per Hour", "Float field with letters",e.getMessage(), Alert.AlertType.ERROR);
        }catch(isEmptyException e) {
            Alerts.showAlert("Empty field", "A field is empty", e.getMessage(), Alert.AlertType.WARNING);
        }
        data.saveProjects();
    }
    @FXML
    void ListAll() {
        if(!(Objects.requireNonNull(userLogged()).tipoUser == user.typeUser.userStd)){
            projectItem();
        }else{
            listAllUserStd();
        }
    }
    public void projectItem(){
        container.getChildren().clear();
        for (Project p : projects) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(App.class.getResource("projectItem.fxml"));
            try {
                hBox = fxmlLoader.load();
                ProjectItemController projectItemController = fxmlLoader.getController();
                projectItemController.setData(p);
                container.getChildren().add(hBox);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void listAllUserStd(){
        container.getChildren().clear();
        for (Project p : projects) {
            if(p.getSharedUsers().contains(String.valueOf(Objects.requireNonNull(userLogged()).getId()))
               || p.getOwner().equals(Objects.requireNonNull(userLogged()).getUsername())){
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(App.class.getResource("projectItem.fxml"));
                try {
                    hBox = fxmlLoader.load();
                    ProjectItemController projectItemController = fxmlLoader.getController();
                    projectItemController.setData(p);
                    container.getChildren().add(hBox);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
    @FXML
    void ListAllMine() {
        container.getChildren().clear();
        for (Project p : projects) {
            if (p.getOwner().equals(Objects.requireNonNull(userLogged()).getUsername())) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(App.class.getResource("projectItem.fxml"));
                try {
                    hBox = fxmlLoader.load();
                    ProjectItemController projectItemController = fxmlLoader.getController();
                    projectItemController.setData(p);
                    container.getChildren().add(hBox);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
    @FXML
    void ListAllShared() {
        container.getChildren().clear();
        for (Project p : projects) {
            if(p.getSharedUsers().contains(String.valueOf(Objects.requireNonNull(userLogged()).getId()))) {
                System.out.println("LOLAS" + p);
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(App.class.getResource("projectItem.fxml"));
                try {
                    hBox = fxmlLoader.load();
                    ProjectItemController projectItemController = fxmlLoader.getController();
                    projectItemController.setData(p);
                    container.getChildren().add(hBox);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
    private void returnUserLogged(){
        name.setText(Objects.requireNonNull(userLogged()).getName());
        userName.setText(Objects.requireNonNull(userLogged()).getUsername());
    }
    private void setVisibleUsers(){
        Utilizadores.setVisible(!Objects.requireNonNull(userLogged()).getTipoUser().equals(user.typeUser.userStd));
    }
    @FXML
    void handleImage(MouseEvent event) throws IOException {
        routes.handleGeneric(event, "My Settings", "mySettings.fxml");
    }
    @FXML
    public void handleInvite(MouseEvent event) throws IOException {
        routes.handleGeneric(event, "Create and view invites", "invitesView.fxml");
    }
    public void handleUser(MouseEvent event) throws IOException {
        routes.handleGeneric(event, "Create task", "userView.fxml");
    }
    @FXML
    void handleDashboard(MouseEvent event) throws IOException {
        routes.handleGeneric(event, "Menu Inicial", "dashboardView.fxml");
    }
    @FXML
    public void handleProject(MouseEvent event) throws IOException {
        routes.handleGeneric(event, "List Project", "projectView.fxml");
    }
    @FXML
    void handleTask(MouseEvent event) throws IOException {
        routes.handleGeneric(event, "List Tasks", "taskEditView.fxml");
    }
    @FXML
    public void CreateProject(ActionEvent event) throws IOException {
        routes.handleGeneric(event, "Create Project", "createProject.fxml");
    }
    @FXML
    public void handleReport(MouseEvent event) throws IOException {
        routes.handleGeneric(event, "Create report", "reportsView.fxml");
    }
}