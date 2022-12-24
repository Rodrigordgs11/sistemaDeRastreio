package pt.ipvc.rastreio.sistemaderastreio.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import pt.ipvc.rastreio.sistemaderastreio.App;
import pt.ipvc.rastreio.sistemaderastreio.backend.user;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import static pt.ipvc.rastreio.sistemaderastreio.Data.data.userLogged;
import static pt.ipvc.rastreio.sistemaderastreio.controller.UserController.getIdLog;
import static pt.ipvc.rastreio.sistemaderastreio.Data.data.users;

public class DashboardController implements Initializable {
    @FXML
    private LineChart<String, Integer> lineChart;
    @FXML
    private PieChart pieChart;
    @FXML
    private Label name;
    @FXML
    private Label userName;

    private Scene scene;
    @FXML
    private Stage stage;
    @FXML
    private Parent parent;
    @FXML
    private ImageView imageSwitchMy;
    @FXML
    private HBox Utilizadores;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        iniLineChart();
        iniPieChart();
        returnUserLogged();
        setVisibleUsers();
    }
    public void handleImage(MouseEvent event) throws IOException {
        parent = FXMLLoader.load(Objects.requireNonNull(App.class.getResource("mySettings.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
        stage.setTitle("My Settings");
    }
    public void switchCreateTask(MouseEvent event) throws IOException {
        parent = FXMLLoader.load(Objects.requireNonNull(App.class.getResource("taskEditView.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
        stage.setTitle("Create task");
    }

    public void switchUser(MouseEvent event) throws IOException {
        parent = FXMLLoader.load(Objects.requireNonNull(App.class.getResource("userView.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
        stage.setTitle("Create task");
    }

    private void iniLineChart(){
        XYChart.Series series = new XYChart.Series();

        series.getData().add(new XYChart.Data<>("Monday", 8));
        series.getData().add(new XYChart.Data<>("Tuesday", 10));
        series.getData().add(new XYChart.Data<>("Wednesday", 12));
        series.getData().add(new XYChart.Data<>("Thursday", 14));
        series.getData().add(new XYChart.Data<>("Friday", 16));
        lineChart.getData().addAll(series);
        lineChart.lookup(".chart-plot-background").setStyle("-fx-background-color: transparent;");
        series.getNode().setStyle("-fx-stroke: #153250");
    }

    private void iniPieChart(){
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
            new PieChart.Data("Android", 15),
            new PieChart.Data("Android", 15),
            new PieChart.Data("Android", 15),
            new PieChart.Data("Android", 15),
            new PieChart.Data("Android", 15)
        );
        pieChart.setData(pieChartData);
    }
    private void returnUserLogged(){
        System.out.println(userLogged());
        name.setText(Objects.requireNonNull(userLogged()).getName());
        userName.setText(Objects.requireNonNull(userLogged()).getUsername());
    }

    private void setVisibleUsers(){
        if(!Objects.requireNonNull(userLogged()).getTipoUser().equals(user.typeUser.userStd))
            Utilizadores.setVisible(true);
        else
            Utilizadores.setVisible(false);
    }
}