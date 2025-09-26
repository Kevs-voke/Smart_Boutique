package rtx.smart_boutique.Controllers.Employee;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import rtx.smart_boutique.Model.DatabaseDriver;
import rtx.smart_boutique.Model.Employee;
import rtx.smart_boutique.Model.Model;
import rtx.smart_boutique.Model.Sales;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

public class ViewSalesController implements Initializable {
    public ListView<String> viewSales_lstview;
    public DatePicker date_picker;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
      dateListener();
    }

    public void dateListener(){
     date_picker.valueProperty().addListener(new ChangeListener<LocalDate>() {
         @Override
         public void changed(ObservableValue<? extends LocalDate> observable,
                             LocalDate oldValue, LocalDate newValue) {
             try {
                 onSearch();
             } catch (Exception e) {
                 e.printStackTrace();
             }
         }
     });
 }

    public void onSearch() {
        DatabaseDriver driver = new DatabaseDriver();
        if(date_picker.getValue() == null){
            viewSales_lstview.getItems().clear();
           return;
        }
        String selectedDate = getCurrentDate(date_picker.getValue());
        List<Sales> sales = driver.QueryEmployeeSales(selectedDate, Model.getInstance().getUsername());
        ObservableList<String> salesList = FXCollections.observableArrayList();
        int index = 1;

        for (Sales sl : sales) {
            String currentTime = sl.timeProperty().get();
            DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("HH:mm:ss");
            LocalTime time = LocalTime.parse(currentTime, inputFormat);
            DateTimeFormatter outputFormat = DateTimeFormatter.ofPattern("hh:mm a");
            String formattedTime = time.format(outputFormat);
            formattedTime = formattedTime.replace("am", "Am").replace("pm", "Pm");
            salesList.add("\t" + index + ".\t" + "At" + "\t"+
                    formattedTime + "\t\t"+"You Sold" +  "\t\t"+
                    sl.quantityProperty().get() + "\t"+
                    sl.itemNameProperty().get() + "\t\t" + "At  Ksh." + "\t"+
                    sl.priceProperty().get() + "\t\t" + "Totalling to Ksh." + "\t"+
                    sl.totalSalesProperty().get());
            index++;
        }

        viewSales_lstview.setItems(salesList);
    }

    public static String getCurrentDate(LocalDate time) {
        LocalDate currentDate = time;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedDate = currentDate.format(formatter);
        return formattedDate;
    }
}