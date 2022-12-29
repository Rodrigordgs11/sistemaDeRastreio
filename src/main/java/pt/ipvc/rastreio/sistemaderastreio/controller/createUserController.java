package pt.ipvc.rastreio.sistemaderastreio.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import pt.ipvc.rastreio.sistemaderastreio.App;
import pt.ipvc.rastreio.sistemaderastreio.Data.data;
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
    private Scene scene;
    @FXML
    private Stage stage;
    @FXML
    private Parent parent;
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
    @FXML
    void SaveChanges(ActionEvent event) {
        try {
            Validator();
            user newUser = new userStd(Name.getText(), UserName.getText(), Password.getText(), Integer.parseInt(Phone.getText()), 0,  user.typeUser.userStd);
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
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        returnUserLogged();
        setVisibleUsers();
    }
    public void Validator() throws isEmptyException, alreadyExistException, matchException {
        boolean exist = false;
        boolean existPhone = false;
        if(Name.getText().isEmpty() || UserName.getText().isEmpty() || Password.getText().isEmpty() || ConfirmPass.getText().isEmpty() || Phone.getText().isEmpty() /*TypeUser.getText().isEmpty()*/) throw new isEmptyException("Text field is empty");
        for(user u : users) if (UserName.getText().equals(u.getUsername())) exist = true;
        if(exist) throw new alreadyExistException("Username already used");
        for(user u : users) if (Integer.parseInt(Phone.getText()) == u.getNumberPhone()) existPhone = true;
        if(existPhone) throw new alreadyExistException("Phone already exists");
        if(Phone.getText().length() != 9) throw new NumberFormatException("Phone field must have 9 numbers");
        if(!(Password.getText().equals(ConfirmPass.getText()))) throw new matchException("Passwords aren't matching");
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
        parent = FXMLLoader.load(Objects.requireNonNull(App.class.getResource("dashboardView.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
        stage.setTitle("Menu Inicial");
    }
    public void handleImage(MouseEvent event) throws IOException {
        parent = FXMLLoader.load(Objects.requireNonNull(App.class.getResource("mySettings.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
        stage.setTitle("My Settings");
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
    public void handleInvite(MouseEvent event) throws IOException {
        parent = FXMLLoader.load(Objects.requireNonNull(App.class.getResource("invitesView.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
        stage.setTitle("Create and view invites");
    }
}
