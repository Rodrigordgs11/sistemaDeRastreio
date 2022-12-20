package pt.ipvc.rastreio.sistemaderastreio.controller;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;

import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    @FXML
    private LineChart<String, Integer> lineChart;
    @FXML
    private PieChart pieChart;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        iniLineChart();
        iniPieChart();
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
}