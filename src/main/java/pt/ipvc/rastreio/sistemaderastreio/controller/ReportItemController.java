package pt.ipvc.rastreio.sistemaderastreio.controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import pt.ipvc.rastreio.sistemaderastreio.App;
import pt.ipvc.rastreio.sistemaderastreio.Routes.routes;
import pt.ipvc.rastreio.sistemaderastreio.backend.*;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.ResourceBundle;


import static pt.ipvc.rastreio.sistemaderastreio.Data.data.*;

public class ReportItemController extends ReportController implements Initializable {
    private static int id;
    @FXML
    private Label idDate;
    @FXML
    private Label Date;
    @FXML
    private Label Hours;

    @FXML
    private Label name = new Label();

    @FXML
    private Label userName = new Label();

    @FXML
    private Label TotalPrice;
    @FXML
    private HBox Utilizadores = new HBox();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ListTask(new ActionEvent());
        returnUserLogged();
        setVisibleUsers();
    }

    @FXML
    public void ListTasks(ActionEvent event) throws IOException {
        id = Integer.parseInt(idDate.getText());
        routes.handleGeneric(event, "Reports", "reportTasks.fxml");
    }

    public void ListTask(ActionEvent event) {
        container.getChildren().clear();
        for (Task t : tasks) {
            LocalDateTime Time = t.getStartTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            int day = Integer.parseInt(Time.format(DateTimeFormatter.ofPattern("dd")));
            if(day == getId()) {
                for (int i = getDayPicked(); i <= getSumPicked(); i++) {
                    if((Time.format(DateTimeFormatter.ofPattern("-MM-yyyy")).equals(getDateSplited())))
                    if (getClicouButton() == 1) {
                        if (String.valueOf(t.getIdProject()).equals(getProject()) && t.getidUser() == Objects.requireNonNull(userLogged()).getId()) {
                            if (getId() == i && t.getState() == TaskState.FINALIZADO) {
                                FXMLLoader fxmlLoader = new FXMLLoader();
                                fxmlLoader.setLocation(App.class.getResource("taskItem.fxml"));
                                try {
                                    HBox hBox = fxmlLoader.load();
                                    TaskItemController taskItemController = fxmlLoader.getController();
                                    taskItemController.setData(t);
                                    taskItemController.invisible();
                                    container.getChildren().add(hBox);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }
                    } else {
                        if (getId() == i && t.getState() == TaskState.FINALIZADO) {
                            FXMLLoader fxmlLoader = new FXMLLoader();
                            fxmlLoader.setLocation(App.class.getResource("taskItem.fxml"));
                            try {
                                HBox hBox = fxmlLoader.load();
                                TaskItemController taskItemController = fxmlLoader.getController();
                                taskItemController.setData(t);
                                taskItemController.invisible();
                                container.getChildren().add(hBox);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                }
            }
        }
    }
    public static int getId() {
        return id;
    }
    public static void setId(int id) {
        ReportItemController.id = id;
    }
    public void setData(int i){
        Date.setText(String.valueOf(i) /*+ getDateSplited()*/);
        Hours.setText(String.valueOf(getTotalHoursDay(i)));
        TotalPrice.setText(String.valueOf(getTotalPriceDay(i)));
        idDate.setText(String.valueOf(i));
        if (Objects.requireNonNull(userLogged()).getNumOfWork() < getTotalHoursDay(i)){
            Hours.setStyle("-fx-font-weight: bold");
        }
    }

    private void returnUserLogged(){
        name.setText(Objects.requireNonNull(userLogged()).getName());
        userName.setText(Objects.requireNonNull(userLogged()).getUsername());
    }
    private void setVisibleUsers(){
        Utilizadores.setVisible(!Objects.requireNonNull(userLogged()).getTipoUser().equals(user.typeUser.userStd));
    }
    @FXML
    void handleDashboard(MouseEvent event) throws IOException {
        routes.handleGeneric(event, "Menu Inicial", "dashboardView.fxml");
    }
    @FXML
    void handleImage(MouseEvent event) throws IOException {
        routes.handleGeneric(event, "My settings", "mySettings.fxml");
    }
    @FXML
    void handleTask(MouseEvent event) throws IOException {
        routes.handleGeneric(event, "Create task", "taskEditView.fxml");
    }
    @FXML
    void handleInvites(MouseEvent event) throws IOException {
        routes.handleGeneric(event, "Create and view invites", "invitesView.fxml");
    }
}