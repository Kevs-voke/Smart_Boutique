package rtx.smart_boutique.Model;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Sales {
    private final SimpleStringProperty Time;
    private  final SimpleStringProperty ItemName;
    private final SimpleStringProperty EmployeeName;
    private final SimpleDoubleProperty Price;
    private final SimpleStringProperty Date;
    private final SimpleIntegerProperty Quantity;
    private final SimpleDoubleProperty TotalSales;



    Sales(String time, String itemName, String employeeName, double price, String date, int quantity, double totalSales){
        this.Time = new SimpleStringProperty(time);
        this.ItemName = new SimpleStringProperty(itemName);
        this.EmployeeName = new SimpleStringProperty(employeeName);
        this.Price = new SimpleDoubleProperty(price);
        this.Date = new SimpleStringProperty(date);
        this.Quantity = new SimpleIntegerProperty(quantity);
        this.TotalSales = new SimpleDoubleProperty(totalSales);
    }

    public SimpleStringProperty timeProperty(){return Time;}
    public SimpleStringProperty itemNameProperty(){return  ItemName;}
    public SimpleStringProperty employeeNameProperty(){return EmployeeName;}
    public SimpleDoubleProperty priceProperty(){return  Price;}
    public SimpleStringProperty dateProperty(){return Date;}
    public SimpleIntegerProperty quantityProperty(){return Quantity;}
    public SimpleDoubleProperty totalSalesProperty(){return  TotalSales;}
}
