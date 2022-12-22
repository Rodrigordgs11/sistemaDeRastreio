package pt.ipvc.rastreio.sistemaderastreio.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import pt.ipvc.rastreio.sistemaderastreio.backend.user;

import java.net.URL;
import java.util.ResourceBundle;

import static pt.ipvc.rastreio.sistemaderastreio.Data.data.users;

public class UserItemController extends  UserController implements Initializable{
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
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idUser.setVisible(true);
    }
    public void setData(user User){
        idUser.setText(String.valueOf(User.getId()));
        NameUser.setText(User.getName());
        NumberPhoneUser.setText(String.valueOf(User.getNumberPhone()));
        TypeUserUser.setText(String.valueOf(User.getTipoUser()));
        UsernameUser.setText(User.getUsername());

    }
    @FXML
    void edit(ActionEvent event) {
        userItem();
        for (user u: users){
            if(Integer.parseInt(idUser.getText()) == u.getId()){
                System.out.println(u.getId());
                System.out.println(idUser.getText());
            }
        }
    }
    public int returnId(){
        int id = 0;
        for (user u: users){
            if(Integer.parseInt(idUser.getText()) == u.getId()){
                System.out.println(u.getId());
                System.out.println(idUser.getText());
                id = u.getId();
            }
        }
        return id;
    }
    @FXML
    void editPass(ActionEvent event) {
        userItem();
        for (user u: users){
            if(Integer.parseInt(idUser.getText()) == u.getId()){
                System.out.println(u.getId());
                System.out.println(idUser.getText());
            }
        }
    }
    @FXML
    void remove(ActionEvent event) {
        userItem();
        for (user u: users){
            if(Integer.parseInt(idUser.getText()) == u.getId()){
                System.out.println(u.getId());
                System.out.println(idUser.getText());

            }
        }
    }
}
