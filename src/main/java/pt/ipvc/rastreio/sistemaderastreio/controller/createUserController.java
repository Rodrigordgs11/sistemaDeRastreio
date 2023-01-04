package pt.ipvc.rastreio.sistemaderastreio.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import pt.ipvc.rastreio.sistemaderastreio.Data.data;
import pt.ipvc.rastreio.sistemaderastreio.Routes.routes;
import pt.ipvc.rastreio.sistemaderastreio.backend.user;
import pt.ipvc.rastreio.sistemaderastreio.backend.userStd;
import pt.ipvc.rastreio.sistemaderastreio.utils.Alerts;
import pt.ipvc.rastreio.sistemaderastreio.utils.loginRegisterExceptions.alreadyExistException;
import pt.ipvc.rastreio.sistemaderastreio.utils.loginRegisterExceptions.isEmptyException;
import pt.ipvc.rastreio.sistemaderastreio.utils.loginRegisterExceptions.matchException;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import static pt.ipvc.rastreio.sistemaderastreio.Data.data.userLogged;
import static pt.ipvc.rastreio.sistemaderastreio.Data.data.users;
public class createUserController extends UserController implements Initializable {
    @FXML
    private PasswordField ConfirmPass;
    @FXML
    private TextField Name;
    @FXML
    private PasswordField Password;
    @FXML
    private TextField Phone;
    @FXML
    private TextField TypeUser;
    @FXML
    private TextField UserName;
    @FXML
    private HBox Utilizadores;
    @FXML
    private Label name;
    @FXML
    private Label userName;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        returnUserLogged();
        setVisibleUsers();
    }
    @FXML
    void SaveChanges(ActionEvent event) {
        try {
            Validator();
            user newUser = new userStd(Name.getText(), UserName.getText(), Password.getText(), Integer.parseInt(Phone.getText()), 0, user.typeUser.valueOf(TypeUser.getText()));
            users.add(newUser);
        }catch (NumberFormatException e){
            Alerts.showAlert("Phone number", "Integer field with letters or Incorrect number of numbers",e.getMessage(), Alert.AlertType.ERROR);
        }catch(isEmptyException e){
            Alerts.showAlert("Empty field", "A field is empty",e.getMessage(), Alert.AlertType.WARNING);
        }catch(alreadyExistException e){
            Alerts.showAlert("Already exists", "User already exists or phone number exists, change for another",e.getMessage(), Alert.AlertType.ERROR);
        }catch(matchException e){
            Alerts.showAlert("Passswords", "The passwords must be equal",e.getMessage(), Alert.AlertType.ERROR);
        }
        data.saveUsers();
    }
    public void Validator() throws isEmptyException, alreadyExistException, matchException {
        boolean exist = false;
        boolean existPhone = false;
        if(Name.getText().isEmpty() || UserName.getText().isEmpty() || Password.getText().isEmpty() || ConfirmPass.getText().isEmpty() || Phone.getText().isEmpty() || TypeUser.getText().isEmpty()) throw new isEmptyException("Text field is empty");
        for(user u : users) if (UserName.getText().equals(u.getUsername())) exist = true;
        if(exist) throw new alreadyExistException("Username already used");
        for(user u : users) if (Integer.parseInt(Phone.getText()) == u.getNumberPhone()) existPhone = true;
        if(existPhone) throw new alreadyExistException("Phone already exists");
        if(Phone.getText().length() != 9) throw new NumberFormatException("Phone field must have 9 numbers");
        if(!(Password.getText().equals(ConfirmPass.getText()))) throw new matchException("Passwords aren't matching");
        if(!(TypeUser.getText().toLowerCase().equals("usermanager") || TypeUser.getText().toLowerCase().equals("admin") ||
           TypeUser.getText().toLowerCase().equals("userstd"))) throw new matchException("This type user does not exist");
    }
    private void returnUserLogged(){
        System.out.println(userLogged());
        name.setText(Objects.requireNonNull(userLogged()).getName());
        userName.setText(Objects.requireNonNull(userLogged()).getUsername());
    }
    private void setVisibleUsers(){
        Utilizadores.setVisible(!Objects.requireNonNull(userLogged()).getTipoUser().equals(user.typeUser.userStd));
    }
    public void handleDashboard(MouseEvent event) throws IOException {
        routes.handleGeneric(event, "Menu Inicial", "dashboardView.fxml");
    }
    public void handleImage(MouseEvent event) throws IOException {
        routes.handleGeneric(event, "My Settings", "mySettings.fxml");
    }
    @FXML
    public void handleProject(MouseEvent event) throws IOException {
        routes.handleGeneric(event, "List Project", "projectView.fxml");
    }
    @FXML
    public void handleInvite(MouseEvent event) throws IOException {
        routes.handleGeneric(event, "Create and view invites", "invitesView.fxml");
    }
    @FXML
    public void handleReport(MouseEvent event) throws IOException {
        routes.handleGeneric(event, "Create report", "reportsView.fxml");
    }
    @FXML
    public void handleTask(MouseEvent event) throws IOException {
        routes.handleGeneric(event, "Create task", "taskEditView.fxml");
    }
}