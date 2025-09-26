package rtx.smart_boutique.Controllers.Employee;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.util.Duration;
import rtx.smart_boutique.Model.DatabaseDriver;
import rtx.smart_boutique.Model.Model;
import rtx.smart_boutique.Model.Sales;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Timer;
import java.util.TimerTask;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    public Label firstname_lbl;
    public Label dashboard_time_lbl;
    public Label date_lbl;
    public Label total_sales_lbl;
    public Label record_breaking_purchase_lbl;
    public Label breaking_time_lbl;
    public Label last_sale_amt_lbl;
    public Label last_sale_lbl;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        record_breaking_purchase_lbl.setText("Please wait...");
        breaking_time_lbl.setText("Please wait...");
        updateTime();
        startSalesTimer();
        updateDate();
        setFirstnameLabel();
    }

    public void updateTime() {
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm:a");
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            LocalTime currentTime = LocalTime.now();
            String formattedTime = currentTime.format(timeFormat);
            formattedTime = formattedTime.replace("am", "Am").replace("pm", "Pm");
            dashboard_time_lbl.setText(formattedTime);
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    public void updateDate() {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE, dd-MMMM-yyyy");
        String formattedDate = currentDate.format(formatter);
        date_lbl.setText(formattedDate);
    }

    public void setFirstnameLabel() {
        firstname_lbl.setText(Model.getInstance().getName());
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
                 Platform.runLater(() -> updateHighestSales());
            }
        }, 0, 60000);
    }

    private void updateHighestSales() {
        DatabaseDriver driver = new DatabaseDriver();
        Task<Sales> task = new Task<>() { // Task should return Sales object
            @Override
            protected Sales call() {
                return driver.updateEmpHighestSale(getCurrentDate(), Model.getInstance().getUsername());
            }
        };

        task.setOnSucceeded(e -> {
            Sales highestSale = task.getValue();
            if (highestSale != null) {
                record_breaking_purchase_lbl.setText("Ksh:" + String.format("%,.2f", highestSale.totalSalesProperty().get()));
                String currentTime = highestSale.timeProperty().get();
                DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("HH:mm:ss");
                LocalTime time = LocalTime.parse(currentTime, inputFormat);
                DateTimeFormatter outputFormat = DateTimeFormatter.ofPattern("hh:mm a");
                String formattedTime = time.format(outputFormat);
                formattedTime = formattedTime.replace("am", "Am").replace("pm", "Pm");
                breaking_time_lbl.setText(": " + formattedTime);
            } else {
                record_breaking_purchase_lbl.setText("$0.00");
                breaking_time_lbl.setText("No sales yet");
            }
        });
        task.setOnFailed(e -> {
            record_breaking_purchase_lbl.setText("Error fetching highest sale.");
            breaking_time_lbl.setText("Error fetching time.");
        });
        new Thread(task).start();
    }

    private void updateTotalSales() {
        DatabaseDriver driver = new DatabaseDriver();
        Task<Double> task = new Task<>() {
            @Override
            protected Double call() {
                System.out.println(driver.updateEmployeeRank(getCurrentDate(), Model.getInstance().getUsername()));
                return driver.updateEmployeeRank(getCurrentDate(), Model.getInstance().getUsername());
            }

        };

        task.setOnSucceeded(e -> total_sales_lbl.setText("Ksh" + String.format("%,.2f", task.getValue()))); // Format the double
        task.setOnFailed(e -> total_sales_lbl.setText("Error fetching total sales."));
        new Thread(task).start();
    }


}
