package pt.ipvc.rastreio.sistemaderastreio.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import pt.ipvc.rastreio.sistemaderastreio.Routes.routes;
import pt.ipvc.rastreio.sistemaderastreio.backend.user;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static pt.ipvc.rastreio.sistemaderastreio.Data.data.*;
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
    public int getId() {
        return id;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idUser.setVisible(false);
        //returnUserLogged();
    }
    public void setData(user User){
        idUser.setText(String.valueOf(User.getId()));
        NameUser.setText(User.getName());
        NumberPhoneUser.setText(String.valueOf(User.getNumberPhone()));
        TypeUserUser.setText(String.valueOf(User.getTipoUser()));
        UsernameUser.setText(User.getUsername());
    }
    @FXML
    public void remove(ActionEvent event){
        userItem();
        users.removeIf(u -> Integer.parseInt(idUser.getText()) == u.getId());
        saveUsers();
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
        routes.handleGeneric(event, "Edit user", "editUser.fxml");
    }
    public void handleDashboard(MouseEvent event) throws IOException {
        routes.handleGeneric(event, "Inicial menu", "dashboardView.fxml");
    }
}