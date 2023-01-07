package pt.ipvc.rastreio.sistemaderastreio.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import pt.ipvc.rastreio.sistemaderastreio.Routes.routes;
import pt.ipvc.rastreio.sistemaderastreio.backend.user;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
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
    @FXML
    private Button removeUser;
    @FXML
    private Button editUser;
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
        if(userLogged() != null && Objects.requireNonNull(userLogged()).getTipoUser() == user.typeUser.userManager) {
            for (user u : users) {
                if (u.getTipoUser() != user.typeUser.userStd) {
                    removeUser.setVisible(false);
                    editUser.setVisible(false);
                }
            }
        }
    }
    @FXML
    public void remove(){
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
    @FXML
    public void handleInvite(MouseEvent event) throws IOException {
        routes.handleGeneric(event, "Create and view invites", "invitesView.fxml");
    }
    @FXML
    public void handleProject(MouseEvent event) throws IOException {
        routes.handleGeneric(event, "List Project", "projectView.fxml");
    }

    @FXML
    public void handleReport(MouseEvent event) throws IOException {
        routes.handleGeneric(event, "Create report", "reportsView.fxml");
    }
}