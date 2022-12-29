package pt.ipvc.rastreio.sistemaderastreio.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import pt.ipvc.rastreio.sistemaderastreio.App;
import pt.ipvc.rastreio.sistemaderastreio.Data.data;
import pt.ipvc.rastreio.sistemaderastreio.backend.user;
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

public class editUserController extends UserItemController implements Initializable {
    @FXML
    private Label userName;
    @FXML
    private Label name;
    @FXML
    private HBox Utilizadores;
    @FXML
    private Parent parent;
    @FXML
    private Stage stage;
    @FXML
    private Scene scene;
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
    private Button saveChngesButton;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        returnUserLogged();
        setVisibleUsers();
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
    public void validator() throws isEmptyException, alreadyExistException, matchException {
        boolean existPhone = false;
        boolean exitUser = false;
        if(!Phone.getText().isEmpty()) for(user u: users) if((Integer.parseInt(Phone.getText())) == u.getNumberPhone()) existPhone = true;
        if(existPhone) throw new alreadyExistException("Phone already exists");
        if(!Phone.getText().isEmpty()) if(Phone.getText().length() != 9 ) throw new NumberFormatException("Phone field must have 9 numbers");
        if(!UserName.getText().isEmpty()) for(user u: users) if(UserName.getText().equals(u.getUsername())) exitUser = true;
        if(exitUser) throw new alreadyExistException("Username already exists");
        if(!(Password.getText().equals(ConfirmPass.getText()))) throw new matchException("Passwords aren't matching");
        //if(!TypeUser.getText().isEmpty()) if(TypeUser.getText().equals(user.typeUser.userStd))
    }

    public void SaveChanges() throws matchException, alreadyExistException, isEmptyException {
        try {
            validator();
            userItem();
            for (user u : users) {
                if (getId() == u.getId()) {
                    if (!Name.getText().isEmpty()) u.setName(Name.getText());
                    if (!Phone.getText().isEmpty()) u.setNumberPhone(Integer.parseInt(Phone.getText()));
                    if (!Password.getText().isEmpty()) u.setPassword(Password.getText());
                    if (!UserName.getText().isEmpty()) u.setUsername(UserName.getText());
                }
            }
        }catch (NumberFormatException e){
            Alerts.showAlert("Phone number", "Integer field with letters or Incorrect number of numbers",e.getMessage(), Alert.AlertType.ERROR);
        }catch(isEmptyException e){
            Alerts.showAlert("Empty field", "A field is empty",e.getMessage(), Alert.AlertType.WARNING);
        }catch(alreadyExistException e){
            Alerts.showAlert("Already exists", "User already exists or phone number exists, change for another",e.getMessage(), Alert.AlertType.ERROR);
        }catch(matchException e){
            Alerts.showAlert("Passswords", "The passwords must be equal",e.getMessage(), Alert.AlertType.ERROR);
        }
        saveChngesButton.setText("Saved!");
        data.saveUsers();
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
