package rtx.smart_boutique.Controllers.Employee;

public class ItemRecord {
    private String name;
    private int quantity;
    private double unit_price;
    private double total_price;

    public ItemRecord(String name, int quantity, double unit_price, double total_price) {
        this.name = name;
        this.quantity = quantity;
        this.unit_price = unit_price;
        this.total_price = total_price;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getUnit_price() {
        return unit_price;
    }

    public double getTotal_price() {
        return total_price;
    }
}
