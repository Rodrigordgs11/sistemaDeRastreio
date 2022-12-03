package pt.ipvc.rastreio.sistemaderastreio.controller;
import pt.ipvc.rastreio.sistemaderastreio.backend.*;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import static pt.ipvc.rastreio.sistemaderastreio.backend.user.*;

public class UserController {
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

    public void buttonRegisterAction(){
        user newUser = new user(nameRegister.getText(), nameRegister.getText(), passwordRegister.getText(), Integer.parseInt(phoneRegister.getText()));
        users.add(newUser);
    }

    public void buttonLoginAction(){
        for (user u: users){
            if (u.getUsername().equals(userNameLogin.getText()) && u.getPassword().equals(passwordLogin.getText())){
                buttonLogin.setText("login com sucesso");
            }
        }
    }

}