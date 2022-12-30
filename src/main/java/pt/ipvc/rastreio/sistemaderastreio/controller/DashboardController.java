package pt.ipvc.rastreio.sistemaderastreio.controller;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import pt.ipvc.rastreio.sistemaderastreio.Routes.routes;
import pt.ipvc.rastreio.sistemaderastreio.backend.user;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import static pt.ipvc.rastreio.sistemaderastreio.Data.data.*;
public class DashboardController implements Initializable {
    @FXML
    private Label name;
    @FXML
    private Label userName;
    @FXML
    private ImageView imageSwitchMy;
    @FXML
    private HBox Utilizadores;
    @FXML
    private Label ClientStat;
    @FXML
    private Label ProjectsStat;
    @FXML
    private Label taskStat;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        returnUserLogged();
        setVisibleUsers();
        setStats();
    }
    private void returnUserLogged(){
        System.out.println(userLogged());
        name.setText(Objects.requireNonNull(userLogged()).getName());
        userName.setText(Objects.requireNonNull(userLogged()).getUsername());
    }

    private void setVisibleUsers(){
        Utilizadores.setVisible(!Objects.requireNonNull(userLogged()).getTipoUser().equals(user.typeUser.userStd));
    }
    public void setStats(){
        ClientStat.setText(String.valueOf(users.size()));
        ProjectsStat.setText(String.valueOf(projects.size()));
        taskStat.setText(String.valueOf(tasks.size()));
    }
    @FXML
    void logout(MouseEvent event) throws IOException {
        UserController.setIdLog(0);
        routes.handleGeneric(event, "Login user", "appView.fxml");
    }
    @FXML
    void handleImage(MouseEvent event) throws IOException {
        routes.handleGeneric(event, "My settings", "mySettings.fxml");
    }
    public void switchCreateTask(MouseEvent event) throws IOException {
        routes.handleGeneric(event, "Create task", "taskEditView.fxml");
    }
    @FXML
    void handleProject(MouseEvent event) throws IOException {
        routes.handleGeneric(event, "List Project", "projectView.fxml");
    }
    @FXML
    public void handleInvite(MouseEvent event) throws IOException {
        routes.handleGeneric(event, "Create and view invites", "invitesView.fxml");
    }
    public void switchUser(MouseEvent event) throws IOException {
        routes.handleGeneric(event, "Create task", "userView.fxml");
    }
    @FXML
    public void handleReport(MouseEvent event) throws IOException {
        routes.handleGeneric(event, "Create report", "reportsView.fxml");
    }
}