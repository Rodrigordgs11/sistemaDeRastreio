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
import pt.ipvc.rastreio.sistemaderastreio.backend.Invite;
import pt.ipvc.rastreio.sistemaderastreio.backend.InviteState;
import pt.ipvc.rastreio.sistemaderastreio.backend.Project;
import pt.ipvc.rastreio.sistemaderastreio.backend.user;
import pt.ipvc.rastreio.sistemaderastreio.utils.Alerts;
import pt.ipvc.rastreio.sistemaderastreio.utils.loginRegisterExceptions.alreadyExistException;
import pt.ipvc.rastreio.sistemaderastreio.utils.loginRegisterExceptions.isEmptyException;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import static pt.ipvc.rastreio.sistemaderastreio.Data.data.*;

public class InviteController implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent parent;
    @FXML
    private HBox Utilizadores;
    @FXML
    private VBox container = new VBox();
    @FXML
    private Label name;
    @FXML
    private Label userName;
    private HBox hBox;
    @FXML
    private TextField Description;
    @FXML
    private TextField Receiver;
    @FXML
    private TextField idProject;
    @FXML
    private Button saveChangesButton;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        returnUserLogged();
        setVisibleUsers();
    }
    public void Validator() throws isEmptyException, alreadyExistException {
        boolean existUser = false;
        boolean existProject = false;
        if(Receiver.getText().isEmpty() || Description.getText().isEmpty() || idProject.getText().isEmpty()) throw new isEmptyException("Text field is empty");
        for (user u: users) if(Receiver.getText().equals(u.getUsername())) existUser = true;
        if(!existUser) throw new alreadyExistException("User don't exists");
        for (Project p: projects) if(Integer.parseInt(idProject.getText()) == p.getIdProject()) existProject = true;
        if(!existProject) throw new alreadyExistException("Project don't exists");
    }
    @FXML
    void SaveChanges(ActionEvent event) {
        try {
            Validator();
            Invite invite = new Invite(Objects.requireNonNull(userLogged()).getUsername(), Receiver.getText(), Description.getText(), Integer.parseInt(idProject.getText()), InviteState.WITHOUTANSWER);
            invites.add(invite);
            saveChangesButton.setText("Saved!");
        }catch (alreadyExistException e){
            Alerts.showAlert("User or project does not exists", "Introduce another project or user",e.getMessage(), Alert.AlertType.ERROR);
        }catch(isEmptyException e) {
            Alerts.showAlert("Empty field", "A field is empty", e.getMessage(), Alert.AlertType.WARNING);
        }
        data.saveInvites();
    }
    public void listReceiver(){
        container.getChildren().clear();
        for (Invite i : invites) {
            if (i.getReceiver().equals(Objects.requireNonNull(userLogged()).getUsername())) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(App.class.getResource("inviteItem.fxml"));
                try {
                    hBox = fxmlLoader.load();
                    InviteItemController inviteItemControllerr = fxmlLoader.getController();
                    inviteItemControllerr.setData(i);
                    container.getChildren().add(hBox);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
    public void listSender(){
        container.getChildren().clear();
        for (Invite i : invites) {
            if (i.getSender().equals(Objects.requireNonNull(userLogged()).getUsername())) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(App.class.getResource("inviteItem.fxml"));
                try {
                    hBox = fxmlLoader.load();
                    InviteItemController inviteItemController = fxmlLoader.getController();
                    inviteItemController.setData(i);
                    container.getChildren().add(hBox);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
    @FXML
    void CreateInvite(ActionEvent event) throws IOException {
        parent = FXMLLoader.load(Objects.requireNonNull(App.class.getResource("createinvite.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
        stage.setTitle("Create invite");
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
    void handleImage(MouseEvent event) throws IOException {
        parent = FXMLLoader.load(Objects.requireNonNull(App.class.getResource("mySettings.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
        stage.setTitle("My Settings");
    }
    @FXML
    void handleProject(MouseEvent event) throws IOException {
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
    private void returnUserLogged(){
        System.out.println(userLogged());
        name.setText(Objects.requireNonNull(userLogged()).getName());
        userName.setText(Objects.requireNonNull(userLogged()).getUsername());
    }

    private void setVisibleUsers(){
        if(!Objects.requireNonNull(userLogged()).getTipoUser().equals(user.typeUser.userStd))
            Utilizadores.setVisible(true);
        else
            Utilizadores.setVisible(false);
    }

}
