package pt.ipvc.rastreio.sistemaderastreio.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import pt.ipvc.rastreio.sistemaderastreio.Data.data;
import pt.ipvc.rastreio.sistemaderastreio.backend.user;
import pt.ipvc.rastreio.sistemaderastreio.utils.Alerts;
import pt.ipvc.rastreio.sistemaderastreio.utils.loginRegisterExceptions.*;

import static pt.ipvc.rastreio.sistemaderastreio.Data.data.*;

public class MySettingsController {
    @FXML
    private TextField ConfirmPass;
    @FXML
    private TextField Name;
    @FXML
    private TextField Password;
    @FXML
    private TextField Phone;
    @FXML
    private TextField UserName;
    @FXML
    private Label name;
    @FXML
    private Label userName;
    public void validator() throws isEmptyException, alreadyExistException, matchException {
        boolean existPhone = false;
        boolean exitUser = false;
        boolean exitPass = false;
        if(!Phone.getText().isEmpty()) for(user u: users) if((Integer.parseInt(Phone.getText())) == u.getNumberPhone()) existPhone = true;
        if(existPhone) throw new alreadyExistException("Phone already exists");
        if(!Phone.getText().isEmpty()) if(Phone.getText().length() != 9 ) throw new alreadyExistException("Phone field must have 9 numbers");
        if(!UserName.getText().isEmpty()) for(user u: users) if(UserName.getText().equals(u.getUsername())) exitUser = true;
        if(exitUser) throw new alreadyExistException("Username already exists");
        if(!Password.getText().isEmpty()) for (user u: users) if(Password.getText().equals(u.getPassword())) exitPass = true;
        if(exitPass) throw new alreadyExistException("Password already exists");
        if(!(Password.getText().equals(ConfirmPass.getText()))) throw new matchException("Passwords aren't matching");
    }

    public void buttonSaveChanges() throws matchException, alreadyExistException, isEmptyException {
        try {
            validator();
            for (user u: users){
                if(userLogged().getId() == u.getId()){
                    if(!Name.getText().isEmpty()) u.setName(Name.getText());
                    if(!Phone.getText().isEmpty()) u.setNumberPhone(Integer.parseInt(Phone.getText()));
                    if(!Password.getText().isEmpty()) u.setPassword(Password.getText());
                    if(!UserName.getText().isEmpty()) u.setUsername(UserName.getText());
                }
            }
            System.out.println(userLogged());
        }catch(isEmptyException e){
            Alerts.showAlert("Empty field", "A field is empty",e.getMessage(), Alert.AlertType.WARNING);
        }catch(alreadyExistException e){
            Alerts.showAlert("Already exists", "User already exists or phone number exists or passWord exists, change for another",e.getMessage(), Alert.AlertType.ERROR);
        }catch(matchException e){
            Alerts.showAlert("Passswords", "The passwords must be equal",e.getMessage(), Alert.AlertType.ERROR);
        }
        data.saveUsers();
    }
}