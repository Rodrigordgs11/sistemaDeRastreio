package pt.ipvc.rastreio.sistemaderastreio.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import pt.ipvc.rastreio.sistemaderastreio.App;
import pt.ipvc.rastreio.sistemaderastreio.Data.data;
import pt.ipvc.rastreio.sistemaderastreio.Routes.routes;
import pt.ipvc.rastreio.sistemaderastreio.backend.Invite;
import pt.ipvc.rastreio.sistemaderastreio.backend.InviteState;
import pt.ipvc.rastreio.sistemaderastreio.backend.Project;
import pt.ipvc.rastreio.sistemaderastreio.backend.user;
import pt.ipvc.rastreio.sistemaderastreio.utils.Alerts;
import pt.ipvc.rastreio.sistemaderastreio.utils.loginRegisterExceptions.alreadyExistException;
import pt.ipvc.rastreio.sistemaderastreio.utils.loginRegisterExceptions.isEmptyException;
import pt.ipvc.rastreio.sistemaderastreio.utils.loginRegisterExceptions.matchException;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import static pt.ipvc.rastreio.sistemaderastreio.Data.data.*;
public class InviteController implements Initializable {
    @FXML
    private HBox Utilizadores = new HBox();
    @FXML
    private VBox container = new VBox();
    @FXML
    private Label name = new Label();
    @FXML
    private Label userName = new Label();
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
    public void Validator() throws isEmptyException, alreadyExistException, matchException {
        boolean itsOwner = false;
        boolean existUser = false;
        boolean existProject = false;
        if(Receiver.getText().isEmpty() || Description.getText().isEmpty() || idProject.getText().isEmpty()) throw new isEmptyException("Text field is empty");
        for (user u: users) if(Receiver.getText().equals(u.getUsername())) existUser = true;
        for (Project p: projects) if(Objects.requireNonNull(userLogged()).getUsername().equals(p.getOwner()) && p.getIdProject() == Integer.parseInt(idProject.getText()))  itsOwner = true;
        if(!itsOwner) throw new matchException("The project isn't yours");
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
        }catch (NumberFormatException e){
            Alerts.showAlert("idProject", "Integer field with letters",e.getMessage(), Alert.AlertType.ERROR);
        }catch (alreadyExistException e){
            Alerts.showAlert("User or project does not exists", "Introduce another project or user",e.getMessage(), Alert.AlertType.ERROR);
        }catch(isEmptyException e) {
            Alerts.showAlert("Empty field", "A field is empty", e.getMessage(), Alert.AlertType.WARNING);
        }catch (matchException e){
            Alerts.showAlert("idProject field", "You must be the owner of this project", e.getMessage(), Alert.AlertType.ERROR);
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
    private void returnUserLogged(){
        name.setText(Objects.requireNonNull(userLogged()).getName());
        userName.setText(Objects.requireNonNull(userLogged()).getUsername());
    }
    private void setVisibleUsers(){
        Utilizadores.setVisible(!Objects.requireNonNull(userLogged()).getTipoUser().equals(user.typeUser.userStd));
    }
    @FXML
    void CreateInvite(ActionEvent event) throws IOException {
        routes.handleGeneric(event, "Create invite", "createinvite.fxml");
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
    void handleProject(MouseEvent event) throws IOException {
        routes.handleGeneric(event, "List Project", "projectView.fxml");
    }
    @FXML
    void handleTask(MouseEvent event) throws IOException {
        routes.handleGeneric(event, "List Tasks", "taskEditView.fxml");
    }
    @FXML
    public void handleReport(MouseEvent event) throws IOException {
        routes.handleGeneric(event, "Create report", "reportsView.fxml");
    }
    @FXML
    public void handleInvite(MouseEvent event) throws IOException {
        routes.handleGeneric(event, "Create and view invites", "invitesView.fxml");
    }
}