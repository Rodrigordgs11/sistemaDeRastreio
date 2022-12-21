package pt.ipvc.rastreio.sistemaderastreio.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import pt.ipvc.rastreio.sistemaderastreio.App;
import pt.ipvc.rastreio.sistemaderastreio.Data.data;
import pt.ipvc.rastreio.sistemaderastreio.backend.user;
import pt.ipvc.rastreio.sistemaderastreio.utils.Alerts;
import pt.ipvc.rastreio.sistemaderastreio.utils.loginRegisterExceptions.*;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

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
    private Label name;
    @FXML
    private Label userName;
    @FXML
    private Parent parent;
    @FXML
    private Scene scene;
    @FXML
    private Stage stage;
    @FXML
    private HBox Utilizadores;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        returnUserLogged();
        setVisibleUsers();
    }
    public void returnUserLogged(){
        name.setText(Objects.requireNonNull(userLogged()).getName());
        userName.setText(Objects.requireNonNull(userLogged()).getUsername());
    }
    public void handleDashboard(MouseEvent event) throws InterruptedException, IOException {
        TimeUnit.SECONDS.sleep(1);
        parent = FXMLLoader.load(Objects.requireNonNull(App.class.getResource("dashboardView.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
        stage.setTitle("Menu Inicial");
    }
    public void validator() throws isEmptyException, alreadyExistException, matchException {
        boolean existPhone = false;
        boolean exitUser = false;
        boolean exitPass = false;
        if(!Phone.getText().isEmpty()) for(user u: users) if((Integer.parseInt(Phone.getText())) == u.getNumberPhone()) existPhone = true;
        if(existPhone) throw new alreadyExistException("Phone already exists");
        if(!Phone.getText().isEmpty()) if(Phone.getText().length() != 9 ) throw new NumberFormatException("Phone field must have 9 numbers");
        if(!UserName.getText().isEmpty()) for(user u: users) if(UserName.getText().equals(u.getUsername())) exitUser = true;
        if(exitUser) throw new alreadyExistException("Username already exists");
        if(!(Password.getText().equals(ConfirmPass.getText()))) throw new matchException("Passwords aren't matching");
    }

    public void buttonSaveChanges() throws matchException, alreadyExistException, isEmptyException {
        try {
            validator();
            for (user u : users) {
                if (userLogged().getId() == u.getId()) {
                    if (!Name.getText().isEmpty()) u.setName(Name.getText());
                    if (!Phone.getText().isEmpty()) u.setNumberPhone(Integer.parseInt(Phone.getText()));
                    if (!Password.getText().isEmpty()) u.setPassword(Password.getText());
                    if (!UserName.getText().isEmpty()) u.setUsername(UserName.getText());
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
        if(Objects.requireNonNull(userLogged()).getTipoUser().equals(user.typeUser.admin) || Objects.requireNonNull(userLogged()).getTipoUser().equals(user.typeUser.userManager))
            Utilizadores.setVisible(true);
        else
            Utilizadores.setVisible(false);
    }

}