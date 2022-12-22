package pt.ipvc.rastreio.sistemaderastreio.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import pt.ipvc.rastreio.sistemaderastreio.backend.user;

import java.net.URL;
import java.util.ResourceBundle;

public class UserItemController implements Initializable {
    @FXML
    private Label NameUser;

    @FXML
    private Label NumberPhoneUser;

    @FXML
    private Label TypeUserUser;

    @FXML
    private Label UsernameUser;
    @FXML
    private ImageView Edit;
    @FXML
    private ImageView Pass;
    @FXML
    private ImageView Remove;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void setData(user User){
        NameUser.setText(User.getName());
        NumberPhoneUser.setText(String.valueOf(User.getNumberPhone()));
        TypeUserUser.setText(String.valueOf(User.getTipoUser()));
        UsernameUser.setText(User.getUsername());
    }
    @FXML
    void edit(ActionEvent event) {
        
    }
    @FXML
    void editPass(ActionEvent event) {

    }
    @FXML
    void remove(ActionEvent event) {

    }


}
