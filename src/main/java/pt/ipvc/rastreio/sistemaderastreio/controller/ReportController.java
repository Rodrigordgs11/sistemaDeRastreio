package pt.ipvc.rastreio.sistemaderastreio.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import pt.ipvc.rastreio.sistemaderastreio.App;
import pt.ipvc.rastreio.sistemaderastreio.Routes.routes;
import pt.ipvc.rastreio.sistemaderastreio.backend.Task;
import pt.ipvc.rastreio.sistemaderastreio.backend.TaskState;
import pt.ipvc.rastreio.sistemaderastreio.backend.user;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.ResourceBundle;

import static java.lang.Thread.sleep;
import static pt.ipvc.rastreio.sistemaderastreio.Data.data.*;

public class ReportController implements Initializable {
    @FXML
    private DatePicker DateId;
    @FXML
    private TextField EndTimeSearch;
    @FXML
    private Label ProjectName;
    @FXML
    private TextField StarteTimeSearch;
    @FXML
    private HBox Utilizadores = new HBox();
    @FXML
    private VBox container = new VBox();
    @FXML
    private Label name = new Label();
    @FXML
    private Label ReportMonth;
    @FXML
    private Label userName = new Label();
    @FXML
    private Label totalHoursMonth;

    private HBox hBox;
    private int id = 0;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        returnUserLogged();
        setVisibleUsers();
    }
    public String getDateSplited(){
        LocalDate datePicker = DateId.getValue();
        String NewDatePicker = datePicker.format(DateTimeFormatter.ofPattern("-MM-yyyy"));
        return NewDatePicker;
    }
    public int getDayPicked(){
        LocalDate datePicker = DateId.getValue();
        return Integer.parseInt(datePicker.format(DateTimeFormatter.ofPattern("dd")));
    }
    public int getSumPicked(){
        LocalDate datePicker = DateId.getValue();
        Integer day = Integer.parseInt(datePicker.format(DateTimeFormatter.ofPattern("dd")));
        Integer month = datePicker.lengthOfMonth();
        return (month - day);
    }
    public long getTotalHoursDay(int i){
        Calendar calendar = Calendar.getInstance();
        long Total = 0;
        for (Task t: tasks){
            calendar.setTime(t.getStartTime());
            if (i == calendar.get(Calendar.DAY_OF_MONTH) && t.getState() == TaskState.FINALIZADO){
                Total += t.getDuration();
            }
        }
        return Total;
    }
    public long getTotalHoursMonth(){
        long Total = 0;
        for (int i = getDayPicked(); i < getSumPicked(); i++){
            Total += getTotalHoursDay(i);
        }
        return Total;
    }

    public void setAnimaitionText() {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds( 4), evt -> totalHoursMonth.setText(getTotalHoursMonth() + " hours used on this month")),
                new KeyFrame(Duration.seconds( 7), evt -> totalHoursMonth.setText(getTotalPriceMonth() + " euros on this month")));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
    public float getTotalPriceDay(int i){
        Calendar calendar = Calendar.getInstance();
        long Total = 0;
        for (Task t: tasks){
            calendar.setTime(t.getStartTime());
            if (i == calendar.get(Calendar.DAY_OF_MONTH) && t.getState() == TaskState.FINALIZADO){
                Total += t.getPriceProject();
            }
        }
        return Total;
    }
    public float getTotalPriceMonth(){
        long totalPriceMonth = 0;
        for (int i = getDayPicked(); i < getSumPicked(); i++){
            totalPriceMonth += getTotalPriceDay(i);
        }
        return totalPriceMonth;
    }
    public void listDays(){
        for (int i = getDayPicked(); i < getSumPicked(); i++) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(App.class.getResource("reportItem.fxml"));
            try {
                hBox = fxmlLoader.load();
                ReportItemController reportItemController = fxmlLoader.getController();
                reportItemController.setData(i);
                container.getChildren().add(hBox);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    @FXML
    void DateOnAction(ActionEvent event) throws InterruptedException {
        LocalDate datePicker = DateId.getValue();
        String NewDatePicker = datePicker.format(DateTimeFormatter.ofPattern("MM-yyyy"));
        ReportMonth.setText("Report of month: " + NewDatePicker);
        setAnimaitionText();
    }
    @FXML
    void CreateInvite(ActionEvent event) throws IOException {
        routes.handleGeneric(event, "Create invite", "createinvite.fxml");
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
    void handleProject(MouseEvent event) throws IOException {
        routes.handleGeneric(event, "List Project", "projectView.fxml");
    }
    @FXML
    void handleTask(MouseEvent event) throws IOException {
        routes.handleGeneric(event, "List Tasks", "taskEditView.fxml");
    }
    private void returnUserLogged(){
        System.out.println(userLogged());
        name.setText(Objects.requireNonNull(userLogged()).getName());
        userName.setText(Objects.requireNonNull(userLogged()).getUsername());
    }
    private void setVisibleUsers(){
        Utilizadores.setVisible(!Objects.requireNonNull(userLogged()).getTipoUser().equals(user.typeUser.userStd));
    }
}
