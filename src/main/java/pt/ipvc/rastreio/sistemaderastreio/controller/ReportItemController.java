package pt.ipvc.rastreio.sistemaderastreio.controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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
    private Button ListTasksId;
    @FXML
    private Label TotalPrice;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ListTask(new ActionEvent());
    }

    @FXML
    public void ListTasks(ActionEvent event) throws IOException {
        id = Integer.parseInt(idDate.getText());
        routes.handleGeneric(event, "Edit projects", "reportTasks.fxml");
    }
    public boolean exceedHours(){
        boolean exceed = false;

        return exceed;
    }
    public void ListTask(ActionEvent event) {
        container.getChildren().clear();
        for (Task t : tasks) {
            LocalDateTime lol = t.getStartTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            int day = Integer.parseInt(lol.format(DateTimeFormatter.ofPattern("dd")));
            if(day == getId()) {
                for (int i = getDayPicked(); i <= getSumPicked(); i++) {
                    if (getClicou() == 1) {
                        System.out.println("dwefgnhfbdferwedqfrgthytjguitfodrxsenkwrm tksjhlgfdvc");
                        if (String.valueOf(t.getIdProject()).equals(getIdProject()) && t.getidUser() == Objects.requireNonNull(userLogged()).getId()) {
                            if (getId() == i && t.getState() == TaskState.FINALIZADO) {
                                System.out.println(getIdProject());
                                System.out.println(getClicou());
                                System.out.println(day);
                                System.out.println("LDEGFH");
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
                            System.out.println(getIdProject());
                            System.out.println(getClicou());
                            System.out.println(day);
                            System.out.println("LDEGFH");
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
}