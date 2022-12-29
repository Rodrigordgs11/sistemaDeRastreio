package pt.ipvc.rastreio.sistemaderastreio.controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import pt.ipvc.rastreio.sistemaderastreio.backend.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import static pt.ipvc.rastreio.sistemaderastreio.Data.data.*;

public class ReportItemController extends ReportController{
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
    @FXML
    void ListTasks(ActionEvent event) {

    }
    public static int getId() {
        return id;
    }
    public static void setId(int id) {
        ReportItemController.id = id;
    }
    public void setData(int i){
        Date.setText(i + getDateSplited());
        Hours.setText(String.valueOf(getTotalHoursDay(i)));
        TotalPrice.setText(String.valueOf(getTotalPriceDay(i)));
        idDate.setText(String.valueOf(i));
    }
}