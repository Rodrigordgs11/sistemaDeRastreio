package pt.ipvc.rastreio.sistemaderastreio.controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import pt.ipvc.rastreio.sistemaderastreio.backend.Invite;
import pt.ipvc.rastreio.sistemaderastreio.backend.InviteState;
import pt.ipvc.rastreio.sistemaderastreio.backend.Project;

import static pt.ipvc.rastreio.sistemaderastreio.Data.data.*;

public class InviteItemController extends InviteController{
    private static int id;
    @FXML
    private Label description;
    @FXML
    private Label Project;
    @FXML
    private Label receiver;
    @FXML
    private Label Sender;
    @FXML
    private Label idUser;
    @FXML
    private Label State;

    public static int getId() {
        return id;
    }

    public static void setId(int id) {
        InviteItemController.id = id;
    }

    public void setData(Invite i){
        receiver.setText(String.valueOf(i.getReceiver()));
        Sender.setText(String.valueOf(i.getSender()));
        State.setText(String.valueOf(i.getInviteState()));
        description.setText(i.getDescription());
        Project.setText(String.valueOf(i.getIdProject()));
        idUser.setText(String.valueOf(i.getId()));
        idUser.setVisible(true);
    }
    @FXML
    void Accept(ActionEvent event) {
        for(Invite i: invites){
            if(Integer.parseInt(idUser.getText()) == i.getId()){
                i.setInviteState(InviteState.ACCEPT);
            }
        }
        saveInvites();
    }
    @FXML
    void recused(ActionEvent event) {
        for(Invite i: invites){
            if(Integer.parseInt(idUser.getText()) == i.getId()){
                i.setInviteState(InviteState.RECUSE);
            }
        }
        saveInvites();
    }
}