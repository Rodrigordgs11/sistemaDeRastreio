package pt.ipvc.rastreio.sistemaderastreio.controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import pt.ipvc.rastreio.sistemaderastreio.backend.Invite;
import pt.ipvc.rastreio.sistemaderastreio.backend.InviteState;
import pt.ipvc.rastreio.sistemaderastreio.backend.Project;
import pt.ipvc.rastreio.sistemaderastreio.backend.user;

import java.util.Objects;

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
    @FXML
    private Button acceptButton;
    @FXML
    private Button recuseButton;

    public static int getId() {
        return id;
    }
    public static void setId(int id) {
        InviteItemController.id = id;
    }
    public void invisible(){
        for (Invite i: invites){
            if (Integer.parseInt(idUser.getText()) == i.getId()) {
                if (i.getSender().equals(userLogged().getUsername()) || !i.getInviteState().equals(InviteState.WITHOUTANSWER)) {
                    acceptButton.setVisible(false);
                    recuseButton.setVisible(false);
                }
            }
        }
    }
    public void setData(Invite i){
        receiver.setText(String.valueOf(i.getReceiver()));
        Sender.setText(String.valueOf(i.getSender()));
        State.setText(String.valueOf(i.getInviteState()));
        description.setText(i.getDescription());
        Project.setText(String.valueOf(i.getIdProject()));
        idUser.setText(String.valueOf(i.getId()));
        idUser.setVisible(false);
        invisible();
    }
    @FXML
    void Accept(ActionEvent event) {
        for(Invite i: invites) {
            if (Integer.parseInt(idUser.getText()) == i.getId()) {
                i.setInviteState(InviteState.ACCEPTED);
                acceptButton.setVisible(false);
                for (Project p: projects){
                    if(i.getIdProject() == p.getIdProject()){
                        for (user u : users) {
                            if (u.getName().equals(i.getReceiver())) {
                                p.getSharedUsers().add(u.getId());
                            }
                        }
                    }
                }
            }
        }
        saveInvites();
    }
    @FXML
    void recused(ActionEvent event) {
        for(Invite i: invites){
            if(Integer.parseInt(idUser.getText()) == i.getId()){
                i.setInviteState(InviteState.RECUSED);
            }
        }
        saveInvites();
    }
    @FXML
    void remove(ActionEvent event) {
        for (Project p : projects) {
            for (Invite i : invites){
                if (i.getIdProject() == p.getIdProject() && i.getInviteState() == InviteState.ACCEPTED) {
                    i.setInviteState(InviteState.REMOVED);
                }
                for (user u : users){
                    if(i.getReceiver().equals(u.getUsername())){
                        System.out.println(i);
                        System.out.println(u);
                        p.getSharedUsers().remove(String.valueOf(u.getId()));
                        System.out.println(p);
                    }
                }
            }
        }
        saveProjects();
        saveInvites();
    }
}