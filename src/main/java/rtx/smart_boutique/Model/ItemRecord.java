package rtx.smart_boutique.Model;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class ItemRecord {
    private final SimpleStringProperty name;
    private final SimpleIntegerProperty quantity;
    private final SimpleDoubleProperty unit_price;
    private final SimpleStringProperty description;
    private final SimpleDoubleProperty total_Price;

    public ItemRecord(String description, String name, int quantity, double unit_price) {
        this.name = new SimpleStringProperty(name);
        this.quantity = new SimpleIntegerProperty(quantity);
        this.unit_price = new SimpleDoubleProperty(unit_price);
        this.description = new SimpleStringProperty(description);
        this.total_Price = new SimpleDoubleProperty();
    }

  public ItemRecord(String name, int quantity, double unit_price, double totalprice) {
        this.name = new SimpleStringProperty(name);
        this.quantity = new SimpleIntegerProperty(quantity);
        this.unit_price = new SimpleDoubleProperty(unit_price);
        this.total_Price = new SimpleDoubleProperty(totalprice);
         this.description = new SimpleStringProperty();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }
    public SimpleIntegerProperty quantityProperty() {
        return quantity;
    }
    public SimpleDoubleProperty unit_priceProperty() {
        return unit_price;
    }
    public SimpleDoubleProperty total_PriceProperty(){return total_Price;}
    public SimpleStringProperty descriptionProperty(){return  description;};
    public void setName(String newName) {
        name.set(newName);
    }
    public void setQuantity(int newQuantity) {
        quantity.set(newQuantity);
    }
    public void setUnitPrice(double newUnitPrice) {
        unit_price.set(newUnitPrice);
    }
    public void setTotalPrice(double newTotalPrice){
        total_Price.set(newTotalPrice);
    }

}