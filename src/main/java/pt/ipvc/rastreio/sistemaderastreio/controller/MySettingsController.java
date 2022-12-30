package pt.ipvc.rastreio.sistemaderastreio.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import pt.ipvc.rastreio.sistemaderastreio.Data.data;
import pt.ipvc.rastreio.sistemaderastreio.Routes.routes;
import pt.ipvc.rastreio.sistemaderastreio.backend.user;
import pt.ipvc.rastreio.sistemaderastreio.utils.Alerts;
import pt.ipvc.rastreio.sistemaderastreio.utils.loginRegisterExceptions.*;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import static pt.ipvc.rastreio.sistemaderastreio.Data.data.*;
public class MySettingsController implements Initializable {
    @FXML
    private PasswordField ConfirmPass;
    @FXML
    private TextField Name;
    @FXML
    private PasswordField Password;
    @FXML
    private TextField Phone;
    @FXML
    private TextField UserName;
    @FXML
    private PasswordField WorkHours;
    @FXML
    private Label name;
    @FXML
    private Label userName;
    @FXML
    private HBox Utilizadores;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        returnUserLogged();
        setVisibleUsers();
    }
    public void validator() throws isEmptyException, alreadyExistException, matchException {
        boolean existPhone = false;
        boolean exitUser = false;
        if(!Phone.getText().isEmpty()) for(user u: users) if((Integer.parseInt(Phone.getText())) == u.getNumberPhone()) existPhone = true;
        if(existPhone) throw new alreadyExistException("Phone already exists");
        if(!Phone.getText().isEmpty()) if(Phone.getText().length() != 9 ) throw new NumberFormatException("Phone field must have 9 numbers");
        if(!UserName.getText().isEmpty()) for(user u: users) if(UserName.getText().equals(u.getUsername())) exitUser = true;
        if(exitUser) throw new alreadyExistException("Username already exists");
        if(!(Password.getText().equals(ConfirmPass.getText()))) throw new matchException("Passwords aren't matching");
    }
    public void buttonSaveChanges(){
        try {
            validator();
            for (user u : users) {
                if (userLogged().getId() == u.getId()) {
                    if (!Name.getText().isEmpty()) u.setName(Name.getText());
                    if (!Phone.getText().isEmpty()) u.setNumberPhone(Integer.parseInt(Phone.getText()));
                    if (!Password.getText().isEmpty()) u.setPassword(Password.getText());
                    if (!UserName.getText().isEmpty()) u.setUsername(UserName.getText());
                    if (!WorkHours.getText().isEmpty()) u.setNumOfWork(Integer.parseInt(WorkHours.getText()));
                }
            }
            System.out.println(userLogged());
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
    private void setVisibleUsers(){
        Utilizadores.setVisible(Objects.requireNonNull(userLogged()).getTipoUser().equals(user.typeUser.admin) || Objects.requireNonNull(userLogged()).getTipoUser().equals(user.typeUser.userManager));
    }
    public void returnUserLogged(){
        name.setText(Objects.requireNonNull(userLogged()).getName());
        userName.setText(Objects.requireNonNull(userLogged()).getUsername());
    }
    public void handleDashboard(MouseEvent event) throws IOException {
        routes.handleGeneric(event, "Menu Inicial", "dashboardView.fxml");
    }
    @FXML
    void handleImage(MouseEvent event) throws IOException {
        routes.handleGeneric(event, "My settings", "mySettings.fxml");
    }
    public void switchCreateTask(MouseEvent event) throws IOException {
        routes.handleGeneric(event, "Create task", "taskEditView.fxml");
    }
    @FXML
    void handleProject(MouseEvent event) throws IOException {
        routes.handleGeneric(event, "List Project", "projectView.fxml");
    }
    @FXML
    public void handleInvite(MouseEvent event) throws IOException {
        routes.handleGeneric(event, "Create and view invites", "invitesView.fxml");
    }
    public void switchUser(MouseEvent event) throws IOException {
        routes.handleGeneric(event, "Create task", "userView.fxml");
    }
    @FXML
    public void handleReport(MouseEvent event) throws IOException {
        routes.handleGeneric(event, "Create report", "reportsView.fxml");
    }
}