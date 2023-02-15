package gov.iti.presentation.controller;

import java.net.Inet4Address;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.ResourceBundle;

import gov.iti.presistance.UsersInfo;
import gov.iti.presistance.connection.ClientServerConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

public class ServerController implements Initializable {

    public static ServerController instance;

    public ServerController() {
        instance = this;
    }

    @FXML
    private Circle appLogo;

    @FXML
    private Label appName;

    @FXML
    private BarChart<String, Number> barChart;

    @FXML
    private RadioButton offOption;

    @FXML
    private RadioButton onOption;

    @FXML
    private BarChart<?, ?> onlineBarchar;

    @FXML
    private PieChart pieChart;

    @FXML
    private Label serverIP;

    @FXML
    void startServer(ActionEvent event) {
        System.out.println("stating the server .............");
        ClientServerConnection.startServer();
        updateIP();
    }

    @FXML
    void stopServer(ActionEvent event) {
        System.out.println("Stoping the server ..");
        serverIP.setText("  Server IP :  xxxx.xxxx.xxxx.xxxx");
        ClientServerConnection.stopServer();
    }

    XYChart.Series<String, Number> maleSeries;
    XYChart.Series<String, Number> femaleSeries;
    CategoryAxis xAxis = new CategoryAxis();
    NumberAxis yAxis = new NumberAxis();

    XYChart.Series male;
    XYChart.Series female;

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        updateIP();

        appLogo.setFill(
                new ImagePattern(new Image(getClass().getClassLoader().getResource("logo.png").toExternalForm())));

        UsersInfo.updateList();

        // ------------------------------------------------------

        // onlineUserNumer.setText("Number of Online Users : " + UsersInfo.numOfOnline +
        // "/" + UsersInfo.usersList.size());

        // --------------------pie chart-----------------------------------

        pieChart.setTitle("Users States");
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("online", UsersInfo.numOfOnline),
                new PieChart.Data("offline", UsersInfo.numOfOffline));

        pieChart.getData().addAll(pieChartData);

        // ------------------- online barchart ------------------------------------

        onlineBarchar.setTitle("Gender Comparison");

        // Prepare XYChart.Series objects by setting data
        male = new XYChart.Series<>();
        male.setName("MALE");

        female = new XYChart.Series<>();
        female.setName("FEMALE");

        // male.getData().add(new XYChart.Data<>("ONLINE", 3));
        // male.getData().add(new XYChart.Data<>("OFFLINE", 3));

        // female.getData().add(new XYChart.Data<>("ONLINE", 7));
        // female.getData().add(new XYChart.Data<>("OFFLINE", 10));

        // Setting the data to bar chart
        onlineBarchar.getData().addAll(female, male);

        // ----------------------------------------------

        xAxis.setLabel("Countries");
        yAxis = new NumberAxis(0, UsersInfo.usersList.size(), 1);
        // yAxis.setLabel("Gender Number");

        // barChart = new BarChart<>(xAxis, yAxis);

        maleSeries = new XYChart.Series();
        maleSeries.setName("Male");
        femaleSeries = new XYChart.Series();
        femaleSeries.setName("Female");

        System.out.println("FEMALI ^__^ " + femaleSeries.getData());
        System.out.println("MALE ^__^ " + maleSeries.getData());

        barChart.getData().addAll(femaleSeries, maleSeries);

        updateCharts();

    }

    public void updateCharts() {
        updateBarChart();
        updatePieChart();
    }

    private void updateBarChart() {

        // -------------- online barchart ------------------

        male.getData().add(new XYChart.Data<>("ONLINE", UsersInfo.numOfonlineMale));
        male.getData().add(new XYChart.Data<>("All Male ", UsersInfo.maleUsers));

        female.getData().add(new XYChart.Data<>("ONLINE", UsersInfo.numOfOnline - UsersInfo.numOfonlineMale));
        female.getData().add(new XYChart.Data<>("All Female ", UsersInfo.usersList.size() - UsersInfo.maleUsers));

        // ------------------------------------------------

        femaleSeries.getData().clear();
        maleSeries.getData().clear();

        updateSeries();

        System.out.println("after adding data ... ");
        System.out.println("FEMALI ^__^ " + femaleSeries.getData());
        System.out.println("MALE ^__^ " + maleSeries.getData());
    }

    private void updateSeries() {
        for (var record : UsersInfo.statistics.entrySet()) {
            long numberOfMale = 0;
            long numberOfFemale = 0;
            String countryName = record.getKey();
            var row = record.getValue();
            for (var iner : row.entrySet()) {
                if (iner.getKey().equals("m")) {
                    numberOfMale = iner.getValue();
                }
                if (iner.getKey().equals("f")) {
                    numberOfFemale = iner.getValue();
                }
            }

            femaleSeries.getData().add(new XYChart.Data(countryName, numberOfFemale));
            maleSeries.getData().add(new XYChart.Data(countryName, numberOfMale));
        }
    }

    private void updatePieChart() {

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("online", UsersInfo.numOfOnline),
                new PieChart.Data("offline", UsersInfo.numOfOffline));

        pieChart.getData().clear();
        pieChart.getData().addAll(pieChartData);
    }


    private void updateIP() {
        try {
            serverIP.setText("  Server IP :  "+Inet4Address.getLocalHost().getHostAddress());
        } catch (UnknownHostException e) {
            serverIP.setText("  Server IP :  xxxx.xxxx.xxxx.xxxx");
        }
    }

}