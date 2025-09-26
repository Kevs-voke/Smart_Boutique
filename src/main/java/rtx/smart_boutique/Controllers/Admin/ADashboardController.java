package rtx.smart_boutique.Controllers.Admin;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.util.Duration;
import rtx.smart_boutique.Model.DatabaseDriver;
import rtx.smart_boutique.Model.Sales;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ADashboardController implements Initializable {

    public Label firstname_lbl;
    public Label dashboard_time_lbl;
    public Label date_lbl;
    public Label total_sales_lbl;
    public ListView rank_listview;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateTime();
        updateDate();
        startSalesTimer();
    }

    public void updateTime(){
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss");
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            LocalTime currentTime = LocalTime.now();
            dashboard_time_lbl.setText(currentTime.format(timeFormat));
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
    public  void updateDate(){
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE, dd-MMMM-yyyy");
        String formattedDate = currentDate.format(formatter);
        date_lbl.setText(formattedDate);
    }
    public static String getCurrentDate() {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedDate = currentDate.format(formatter);
        return formattedDate;
    }
    private void startSalesTimer() {
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> updateTotalSales());
                Platform.runLater(() ->  updateRankList());
            }
        }, 0, 60000);
    }
    private void updateTotalSales() {
        DatabaseDriver driver = new DatabaseDriver();
        Task<Double> task = new Task<>() {
            @Override
            protected Double call() {
                return driver.updateTotalSalesAdmin(getCurrentDate());
            }

        };

        task.setOnSucceeded(e -> total_sales_lbl.setText("Ksh" + String.format("%,.2f", task.getValue())));
        task.setOnFailed(e -> total_sales_lbl.setText("Error fetching total sales."));
        new Thread(task).start();
    }

    private void updateRankList() {
        DatabaseDriver driver = new DatabaseDriver();

        Task<List<String>> task = new Task<>() {
            @Override
            protected List<String> call() {
                List<Sales> salesList = driver.updateEmployeeRank(getCurrentDate());
           List<String> displayList = new ArrayList<>();
                int index = 1;
                for (Sales sale : salesList) {
                    displayList.add("\t" + index + ".\t" + "Employee with Username\t " + "( " +sale.employeeNameProperty().get()
                             + " )"+ "\t\t has a  made total Sale of:\t   Ksh." + sale.totalSalesProperty().get() + "\t on:\t   " + sale.dateProperty().get());
                }

                return displayList;
            }
        };

        task.setOnSucceeded(e -> {
            List<String> rankList = task.getValue();
            ObservableList<String> observableRankList = FXCollections.observableArrayList(rankList);
           rank_listview.setItems(observableRankList);
        });

        task.setOnFailed(e -> rank_listview.setItems(FXCollections.observableArrayList("Error fetching rank list.")));

        new Thread(task).start();
    }
}
