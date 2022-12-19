package pt.ipvc.rastreio.sistemaderastreio.controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pt.ipvc.rastreio.sistemaderastreio.App;
import pt.ipvc.rastreio.sistemaderastreio.backend.*;

import javafx.fxml.FXML;
import pt.ipvc.rastreio.sistemaderastreio.utils.*;
import pt.ipvc.rastreio.sistemaderastreio.utils.loginRegisterExceptions.*;

import java.io.IOException;
import java.util.Objects;

import static pt.ipvc.rastreio.sistemaderastreio.Data.data.*;

public class UserController{
    @FXML
    private TextField userNameLogin;
    @FXML
    private PasswordField passwordLogin;
    @FXML
    private TextField userNameRegister;
    @FXML
    private PasswordField passwordRegister;
    @FXML
    private PasswordField confirmPasswordRegister;
    @FXML
    private TextField nameRegister;
    @FXML
    private TextField phoneRegister;
    @FXML
    private Button buttonLogin;
    @FXML
    private Button buttonRegister;
    @FXML
    private Scene scene;
    @FXML
    private Stage stage;
    @FXML
    private Parent parent;
    private boolean nExist = false;
    public void switchScene(ActionEvent event) throws IOException {
        buttonLoginAction();
        if(nExist){
            parent = FXMLLoader.load(Objects.requireNonNull(App.class.getResource("dashboardView.fxml")));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(parent);
            stage.setScene(scene);
            stage.show();
        }
    }
    public void validator() throws alreadyExistException, matchException, isEmptyException {
        boolean exist = false;
        boolean existPhone = false;
        if(nameRegister.getText().isEmpty() || userNameRegister.getText().isEmpty() || passwordRegister.getText().isEmpty() || phoneRegister.getText().isEmpty() || passwordRegister.getText().isEmpty() || confirmPasswordRegister.getText().isEmpty()) throw new isEmptyException("Text field is empty");
        for(user u : users) if (userNameRegister.getText().equals(u.getUsername())) exist = true;
        if(exist) throw new alreadyExistException("Username already used");
        for(user u : users) if (Integer.parseInt(phoneRegister.getText()) == u.getNumberPhone()) existPhone = true;
        if(existPhone) throw new alreadyExistException("Phone already exists");
        if(phoneRegister.getText().length() != 9) throw new NumberFormatException("Phone field must have 9 numbers");
        if(!(passwordRegister.getText().equals(confirmPasswordRegister.getText()))) throw new matchException("Passwords aren't matching");
    }
    public void buttonRegisterAction(){
        try {
            validator();
            user newUser = new user(nameRegister.getText(), nameRegister.getText(), passwordRegister.getText(), Integer.parseInt(phoneRegister.getText()), 0, user.typeUser.userStd);
            users.add(newUser);
            users.get(0).tipoUser = user.typeUser.admin;
            buttonRegister.setText("Registado with sucess..");
        }catch(NumberFormatException e){
            Alerts.showAlert("Phone error", "Integer field with letters or Incorrect number of numbers", e.getMessage(), Alert.AlertType.ERROR);
        }catch(isEmptyException e){
            Alerts.showAlert("Empty field", "A field is empty",e.getMessage(), Alert.AlertType.WARNING);
        }catch(alreadyExistException e){
            Alerts.showAlert("User already exists", "User already exists or phone number exists, change for another",e.getMessage(), Alert.AlertType.ERROR);
        }catch(matchException e){
            Alerts.showAlert("Passswords", "The passwords must be equal",e.getMessage(), Alert.AlertType.ERROR);
        }
        saveUsers();
    }

    public void buttonLoginAction(){
        try {
            if (userNameLogin.getText().isEmpty() || passwordLogin.getText().isEmpty()) throw new isEmptyException("Text field is empty");
            for (user u : users)
                if (u.getUsername().equals(userNameLogin.getText()) && u.getPassword().equals(passwordLogin.getText())){
                    buttonLogin.setText("Login with sucess..");
                    nExist = true;
                }
            if(!nExist) throw new matchException("Username or password invalid");
        }catch(isEmptyException e){
            Alerts.showAlert("Empty field", "A field is empty",e.getMessage(), Alert.AlertType.ERROR);
        }catch (matchException e){
            Alerts.showAlert("Invalid username or password", "You must put valid username or password",e.getMessage(), Alert.AlertType.ERROR);
        }
    }
}