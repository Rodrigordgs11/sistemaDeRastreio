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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pt.ipvc.rastreio.sistemaderastreio.App;
import pt.ipvc.rastreio.sistemaderastreio.Data.data;
import pt.ipvc.rastreio.sistemaderastreio.backend.*;
import pt.ipvc.rastreio.sistemaderastreio.utils.Alerts;
import pt.ipvc.rastreio.sistemaderastreio.utils.loginRegisterExceptions.isEmptyException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;
import static pt.ipvc.rastreio.sistemaderastreio.Data.data.*;
public class ProjectController implements Initializable {
    @FXML
    private Parent parent;
    @FXML
    private Scene scene;
    @FXML
    private Stage stage;
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
    @FXML
    public void SaveChanges(ActionEvent event){
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
    public void Validator() throws isEmptyException{
        if(CreateName.getText().isEmpty() || CreateClientName.getText().isEmpty() || CreatePrice.getText().isEmpty()) throw new isEmptyException("Text field is empty");
    }
    @FXML
    void ListAll(ActionEvent event) {
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
    void ListAllMine(ActionEvent event) {
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
    void ListAllShared(ActionEvent event) {
        container.getChildren().clear();
        for (Project p : projects) {
            //System.out.println(p.getSharedUsers());
            //System.out.println(userLogged().getId());
            if(p.getSharedUsers().contains(String.valueOf(userLogged().getId()))) {
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
        System.out.println(userLogged());
        name.setText(Objects.requireNonNull(userLogged()).getName());
        userName.setText(Objects.requireNonNull(userLogged()).getUsername());
    }
    private void setVisibleUsers(){
        Utilizadores.setVisible(!Objects.requireNonNull(userLogged()).getTipoUser().equals(user.typeUser.userStd));
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
    @FXML
    void handleDashboard(MouseEvent event) throws IOException {
        parent = FXMLLoader.load(Objects.requireNonNull(App.class.getResource("dashboardView.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
        stage.setTitle("Menu Inicial");
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
    public void CreateProject(ActionEvent event) throws IOException {
        parent = FXMLLoader.load(Objects.requireNonNull(App.class.getResource("createProject.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
        stage.setTitle("Create Project");
    }
}