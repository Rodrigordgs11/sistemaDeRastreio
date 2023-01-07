package pt.ipvc.rastreio.sistemaderastreio.controller;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import pt.ipvc.rastreio.sistemaderastreio.App;
import pt.ipvc.rastreio.sistemaderastreio.Routes.routes;
import pt.ipvc.rastreio.sistemaderastreio.backend.*;
import javafx.fxml.FXML;
import pt.ipvc.rastreio.sistemaderastreio.utils.*;
import pt.ipvc.rastreio.sistemaderastreio.utils.loginRegisterExceptions.*;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import static pt.ipvc.rastreio.sistemaderastreio.Data.data.*;
public class UserController implements Initializable {
    private boolean nExist = false;
    private static int idLog;
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
    private Label name;
    @FXML
    private Label userName;
    @FXML
    private HBox Utilizadores;
    @FXML
    private VBox container = new VBox();
    public HBox hBox;
    public static int getIdLog() {
        return idLog;
    }

    public static void setIdLog(int idLog) {
        UserController.idLog = idLog;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userItem();
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
            user newUser = new userStd(nameRegister.getText(), userNameRegister.getText(), passwordRegister.getText(), Integer.parseInt(phoneRegister.getText()), 0,  user.typeUser.userStd);
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
                    idLog = u.getId();
                }
            if(!nExist) throw new matchException("Username or password invalid");
        }catch(isEmptyException e){
            Alerts.showAlert("Empty field", "A field is empty",e.getMessage(), Alert.AlertType.ERROR);
        }catch (matchException e){
            Alerts.showAlert("Invalid username or password", "You must put valid username or password",e.getMessage(), Alert.AlertType.ERROR);
        }
    }
    public void userItem(){
        for (user u: users){
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(App.class.getResource("userItem.fxml"));
            try {
                hBox = fxmlLoader.load();
                UserItemController userItemController = fxmlLoader.getController();
                userItemController.setData(u);
                container.getChildren().add(hBox);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    private void returnUserLogged(){
        name.setText(Objects.requireNonNull(userLogged()).getName());
        userName.setText(Objects.requireNonNull(userLogged()).getUsername());
    }
    private void setVisibleUsers(){
        Utilizadores.setVisible(Objects.requireNonNull(userLogged()).getTipoUser().equals(user.typeUser.admin) || Objects.requireNonNull(userLogged()).getTipoUser().equals(user.typeUser.userManager));
    }
    @FXML
    public void CreateUser(ActionEvent event) throws IOException {
        routes.handleGeneric(event, "Create User", "createUser.fxml");
    }
    public void handleDashboard(MouseEvent event) throws IOException {
        routes.handleGeneric(event, "Initial menu", "dashboardView.fxml");
    }
    public void switchScene(ActionEvent event) throws IOException{
        buttonLoginAction();
        if(nExist){
            routes.handleGeneric(event, "Initial menu", "dashboardView.fxml");
        }
    }
    public void handleImage(MouseEvent event) throws IOException {
        routes.handleGeneric(event, "My settings", "mySettings.fxml");
    }
}