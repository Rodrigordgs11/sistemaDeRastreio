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

public class UserItemController extends UserController implements Initializable{
    private static int id;
    @FXML
    private Label NameUser;
    @FXML
    private Label NumberPhoneUser;
    @FXML
    private Label TypeUserUser;
    @FXML
    private Label UsernameUser;
    @FXML
    private Label idUser;
    @FXML
    private Parent parent;
    @FXML
    private Stage stage;
    @FXML
    private Scene scene;
    public int getId() {
        return id;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idUser.setVisible(false);
    }
    public void setData(user User){
        idUser.setText(String.valueOf(User.getId()));
        NameUser.setText(User.getName());
        NumberPhoneUser.setText(String.valueOf(User.getNumberPhone()));
        TypeUserUser.setText(String.valueOf(User.getTipoUser()));
        UsernameUser.setText(User.getUsername());
    }
    @FXML
    public void edit(ActionEvent event) throws IOException {
        userItem();
        for (user u: users){
            if(Integer.parseInt(idUser.getText()) == u.getId()){
                id = u.getId();
                System.out.println(getId());
            }
        }
        parent = FXMLLoader.load(Objects.requireNonNull(App.class.getResource("editUser.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
        stage.setTitle("Menu Inicial");
    }
    @FXML
    public void editPass(ActionEvent event) {
        userItem();
        for (user u: users){
            if(Integer.parseInt(idUser.getText()) == u.getId()){

            }
        }
    }
    @FXML
    public void remove(ActionEvent event) {
        userItem();
        for (user u: users){
            if(Integer.parseInt(idUser.getText()) == u.getId()){
                //System.out.println(u.getId());
                //System.out.println(idUser.getText());
            }
        }
    }
    public void handleDashboard(MouseEvent event) throws IOException {
        parent = FXMLLoader.load(Objects.requireNonNull(App.class.getResource("dashboardView.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
        stage.setTitle("Menu Inicial");
    }
}
