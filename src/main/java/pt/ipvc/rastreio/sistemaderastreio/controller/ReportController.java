package pt.ipvc.rastreio.sistemaderastreio.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import pt.ipvc.rastreio.sistemaderastreio.App;
import pt.ipvc.rastreio.sistemaderastreio.Routes.routes;
import pt.ipvc.rastreio.sistemaderastreio.backend.Project;
import pt.ipvc.rastreio.sistemaderastreio.backend.Task;
import pt.ipvc.rastreio.sistemaderastreio.backend.TaskState;
import pt.ipvc.rastreio.sistemaderastreio.backend.user;
import pt.ipvc.rastreio.sistemaderastreio.utils.Alerts;
import pt.ipvc.rastreio.sistemaderastreio.utils.loginRegisterExceptions.isEmptyException;
import pt.ipvc.rastreio.sistemaderastreio.utils.loginRegisterExceptions.matchException;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.ResourceBundle;
import static pt.ipvc.rastreio.sistemaderastreio.Data.data.*;
import static pt.ipvc.rastreio.sistemaderastreio.controller.ProjectItemController.addTaskToProject;

public class ReportController implements Initializable {
    @FXML
    private DatePicker DateId;
    @FXML
    private HBox Utilizadores = new HBox();
    @FXML
    public VBox container = new VBox();
    @FXML
    private Label name = new Label();
    @FXML
    private Label ReportMonth;
    @FXML
    private Label userName = new Label();
    @FXML
    private Label totalHoursMonth;
    public static LocalDate DatePicked;
    private HBox hBox;

    @FXML
    private Button ButtonSearch;

    private static int idUserLogged = 0;
    private int clicou = 0;
    private static int clicouButton = 0;
    @FXML
    private TextField idProject;
    private static String Project;
    @FXML
    private TextField ClientName;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        returnUserLogged();
        setVisibleUsers();
        addTaskToProject();

        idUserLogged = Objects.requireNonNull(userLogged()).getId();
        System.out.println(idUserLogged);
    }

    public int getClicouButton() {
        return clicouButton;
    }

    public String getProject() {
        return Project;
    }

    public void validator() throws isEmptyException, matchException {
        boolean nExiste = false;
        if(idProject.getText().isEmpty() || ClientName.getText().isEmpty()) throw new isEmptyException("Field is empty");
        for (Project p: projects) {
            if (Integer.parseInt(idProject.getText()) == p.getIdProject()
                    && p.getClientName().equals(ClientName.getText())) {
                System.out.println(ClientName.getText());
                System.out.println(p.getClientName());
                System.out.println(idProject.getText());
                System.out.println(p.getIdProject());
                nExiste = true;
            }
        }
        if(!nExiste) throw new matchException("This idProject does not have that Client name");
    }
    public String getDateSplited(){
        LocalDate datePicker = DatePicked;
        return datePicker.format(DateTimeFormatter.ofPattern("-MM-yyyy"));
    }
    public int getDayPicked(){
        LocalDate datePicker = DatePicked;
        return Integer.parseInt(datePicker.format(DateTimeFormatter.ofPattern("dd")));
    }
    public int getSumPicked(){
        LocalDate datePicker = DatePicked;
        return datePicker.lengthOfMonth();
    }
    public long getTotalHoursDay(int i){
        long Total = 0;
        long TotalProject = 0;
        for (Task t: tasks){
            LocalDateTime lol = t.getStartTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            int day = Integer.parseInt(lol.format(DateTimeFormatter.ofPattern("dd")));
            String comparasion = lol.format(DateTimeFormatter.ofPattern("-MM-yyyy"));
            if(clicouButton == 1){
                if(String.valueOf(t.getIdProject()).equals(Project)  && t.getidUser() == idUserLogged){
                    if (i == day && getDateSplited().equals(comparasion) && t.getState() == TaskState.FINALIZADO){
                        TotalProject += t.getDuration();
                    }
                }
            }else {
                if (userLogged().tipoUser == user.typeUser.admin) {
                    if (i == day && getDateSplited().equals(comparasion) && t.getState() == TaskState.FINALIZADO)
                        Total += t.getDuration();
                }else {
                    if (i == day && getDateSplited().equals(comparasion) && t.getState() == TaskState.FINALIZADO
                            && t.getidUser() == idUserLogged) {
                        Total += t.getDuration();
                    }
                }
            }
        }
        if(clicouButton == 1)
            return TotalProject;
        else
            return Total;
    }
    public long getTotalHoursMonth(){
        long Total = 0;
        for (int i = getDayPicked(); i <= getSumPicked(); i++){
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
        float Total = 0;
        float TotalProject = 0;
        for (Task t: tasks){
            LocalDateTime lol = t.getStartTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            int day = Integer.parseInt(lol.format(DateTimeFormatter.ofPattern("dd")));
            String comparasion = lol.format(DateTimeFormatter.ofPattern("-MM-yyyy"));
            if (clicouButton == 1){
                if(String.valueOf(t.getIdProject()).equals(Project)  && t.getidUser() == idUserLogged) {
                    if (i == day && getDateSplited().equals(comparasion) && t.getState() == TaskState.FINALIZADO) {
                        TotalProject += t.getPriceProject();
                    }
                }
            }else {
                if (userLogged().tipoUser == user.typeUser.admin) {
                    if (i == day && getDateSplited().equals(comparasion) && t.getState() == TaskState.FINALIZADO) {
                        Total += t.getPriceProject();
                    }
                }else{
                    if (i == day && getDateSplited().equals(comparasion) && t.getState() == TaskState.FINALIZADO
                         && t.getidUser() == idUserLogged) {
                        Total += t.getPriceProject();
                    }
                }
            }
        }
        if(clicouButton == 1)
            return TotalProject;
        else
            return Total;
    }
    public float getTotalPriceMonth(){
        long totalPriceMonth = 0;
        for (int i = getDayPicked(); i <= getSumPicked(); i++){
            totalPriceMonth += getTotalPriceDay(i);
        }
        return totalPriceMonth;
    }
    public void listDays(){
        container.getChildren().clear();
        for (int i = getDayPicked(); i <= getSumPicked(); i++) {
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
    public void listDaysProject(){
        container.getChildren().clear();
        for (int i = getDayPicked(); i <= getSumPicked(); i++) {
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
     public void DateOnAction() {
        Project = idProject.getText();
        System.out.println(DateId.getValue());
        clicouButton = 0;
        DatePicked = DateId.getValue();
        LocalDate datePicker = this.DateId.getValue();
        String NewDatePicker = datePicker.format(DateTimeFormatter.ofPattern("MM-yyyy"));
        ReportMonth.setText("Report of month: " + NewDatePicker);
        if (clicou == 0) {
            System.out.println("DateOnAction");
            setAnimaitionText();
            listDays();
        }
    }
    @FXML
    void showIds() {
        ClientName.setVisible(true);
        idProject.setVisible(true);
        ButtonSearch.setVisible(true);
        clicou = 1;
    }
    @FXML
    void searchProject() {
        try {
            validator();
            clicouButton = 1;
            Project = idProject.getText();
            setAnimaitionText();
            listDaysProject();
        }catch (isEmptyException e){
            Alerts.showAlert("Search fields are empty", "A search field is empty",e.getMessage(), Alert.AlertType.ERROR);
        }catch (matchException e){
            Alerts.showAlert("Project id and Client Name", "The project id does not have that client",e.getMessage(), Alert.AlertType.ERROR);
        }catch (NumberFormatException e){
            Alerts.showAlert("Project id field", "There are letters on the project id number",e.getMessage(), Alert.AlertType.ERROR);

        }
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
