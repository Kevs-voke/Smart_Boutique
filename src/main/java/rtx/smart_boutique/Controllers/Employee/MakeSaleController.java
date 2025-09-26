package rtx.smart_boutique.Controllers.Employee;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import rtx.smart_boutique.Model.DatabaseDriver;
import rtx.smart_boutique.Model.ItemRecord;
import rtx.smart_boutique.Model.Model;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

public class MakeSaleController implements Initializable{

    @FXML
    private TextField Name_txt;

    @FXML
    private Button add_btn;

    @FXML
    private Button cancel_btn;

    @FXML
    private Button confirm_btn;

    @FXML
    private Button delete_btn;

    @FXML
    private TextField quantity_txt;

    @FXML


    private Label total_price_lbl;


    @FXML
    private Button update_btn;

    @FXML
    private TableView<ItemRecord> make_sale_tblview;

    @FXML
    private TableColumn<ItemRecord, String> name_column;

    @FXML
    private TableColumn<ItemRecord, Integer> quantity_column;
    @FXML
    private TableColumn<ItemRecord, Double> tprice_column;
    @FXML
    private TableColumn<ItemRecord, Double> Uprice_column;

    private ObservableList<ItemRecord> itemLists = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        binder();
        addListener();

    }
    public void addListener(){
        add_btn.setOnAction(event -> addNewRecord());
        delete_btn.setOnAction(event -> removeRecord());
        cpySaleRecord();
        update_btn.setOnAction(event -> updateSelectedItem());
        cancel_btn.setOnAction(event -> clearTable());
        confirm_btn.setOnAction(event -> saveData());
    }

    private void addNewRecord() {
        try {double unitPrice = 0.0;
            String name = Name_txt.getText().trim();
            int quantity = Integer.parseInt(quantity_txt.getText().trim());
            DatabaseDriver driver = new DatabaseDriver();
            List<ItemRecord> stockData = driver.QueryStockData(Name_txt.getText().trim());
            if (!stockData.isEmpty()) {
                 unitPrice  = stockData.get(0).unit_priceProperty().get();

            }
            double totalPrice = unitPrice * (double) quantity;
            if (name.isEmpty()) {
                throw new IllegalArgumentException("Name cannot be empty");
            }
            ItemRecord record = new ItemRecord(name, quantity, unitPrice, totalPrice);
            make_sale_tblview.getItems().add(record);

        } catch (NumberFormatException e) {
                System.err.println("Invalid input! Please enter a valid number.");
                quantity_txt.setText("");
            }
            Name_txt.clear();
            quantity_txt.clear();
        updateSum();
        }

    public void removeRecord(){
        ItemRecord itemRecordcopied = make_sale_tblview.getItems().get(make_sale_tblview.getSelectionModel().getSelectedIndex());
        double sum = 0.0;
        for (ItemRecord record : make_sale_tblview.getItems()) {
            sum += record.total_PriceProperty().get();
        }
        double price = itemRecordcopied.total_PriceProperty().get();
        double remainPrice = sum - price;
        total_price_lbl.setText(String.format(" %.2f", remainPrice));
        int selectedId= make_sale_tblview.getSelectionModel().getSelectedIndex();
        make_sale_tblview.getItems().remove(selectedId);
        Name_txt.setText("");
        quantity_txt.setText("");
    }



    public void binder(){
        name_column.setCellValueFactory(new PropertyValueFactory<>("name"));
        quantity_column.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        Uprice_column.setCellValueFactory(new PropertyValueFactory<>("unit_price"));
        tprice_column.setCellValueFactory(new PropertyValueFactory<>("total_Price"));
    }
    public void cpySaleRecord(){
        make_sale_tblview.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
            ItemRecord itemRecordcopied = make_sale_tblview.getItems().get(make_sale_tblview.getSelectionModel().getSelectedIndex());
                Name_txt.setText(String.valueOf(itemRecordcopied.nameProperty().get()));
                quantity_txt.setText(String.valueOf(itemRecordcopied.quantityProperty().get()));

            }
        });
    }

    public void updateSelectedItem() {
        double price;
        double totalPrice;
        int selectedIndex = make_sale_tblview.getSelectionModel().getSelectedIndex();
        if (selectedIndex != -1) {
            ItemRecord selectedItem = make_sale_tblview.getItems().get(selectedIndex);
            price = selectedItem.unit_priceProperty().get();
            int quantity = Integer.parseInt(quantity_txt.getText().trim());
            totalPrice = price * (double)quantity;
            selectedItem.setName(Name_txt.getText());
            selectedItem.setQuantity(Integer.parseInt(quantity_txt.getText()));
            selectedItem.setTotalPrice(totalPrice);
            updateSum();
            make_sale_tblview.refresh();
        }
    }
    public void clearTable() {
        make_sale_tblview.getItems().clear();
        total_price_lbl.setText("");
    }
    private void updateSum() {
        double sum = 0.0;
        for (ItemRecord record : make_sale_tblview.getItems()) {
            sum += record.total_PriceProperty().get();
        }
        total_price_lbl.setText(String.format(" %.2f", sum));
    }
public  void  saveData(){
  DatabaseDriver driver = new DatabaseDriver();
    for (ItemRecord record : make_sale_tblview.getItems()){
        driver.commitSalesDatabase(getCurrentTime(),record.nameProperty().get(), Model.getInstance().getUsername(), record.unit_priceProperty().get(),getCurrentDate(),record.quantityProperty().get(),record.total_PriceProperty().get());
    }
    clearTable();

}
    public static String getCurrentTime() {
        LocalTime currentTime = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return currentTime.format(formatter);
    }
   public static String getCurrentDate(){
       LocalDate currentDate = LocalDate.now();
       DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
       String formattedDate = currentDate.format(formatter);
       return formattedDate;
   }

}
