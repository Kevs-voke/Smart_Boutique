package rtx.smart_boutique.Controllers.Admin;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import rtx.smart_boutique.Model.DatabaseDriver;
import rtx.smart_boutique.Model.ItemRecord;
import rtx.smart_boutique.Model.Model;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class NewItemController implements Initializable {
    public TableView<ItemRecord> itemRecord_table;
    public TableColumn<ItemRecord, String> Name_tbl;
    public TableColumn<ItemRecord, Integer> quantity_tbl;
    public TableColumn<ItemRecord, Integer> description_tbl;
    public TableColumn<ItemRecord, Integer> price_tbl;
    public TextField Name_txt;
    public TextField price_txt;
    public TextArea description_txt;
    public Button add_btn;
    public Button update_btn;
    public Button delete_btn;
    public TextField search_txt;
    public TextField quantity_txt;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        binder();
        addListener();
    }

    public void addListener() {
        search_txt.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldVal, String newVal) {
                try {
                    onSearch();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        add_btn.setOnAction(event -> newItems());
        delete_btn.setOnAction(event -> removeStockRecord());
        update_btn.setOnAction(event -> updateStockRecord());
        cpyStockRecord();
    }

    public void binder() {
        Name_tbl.setCellValueFactory(new PropertyValueFactory<>("name"));
        quantity_tbl.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        price_tbl.setCellValueFactory(new PropertyValueFactory<>("unit_price"));
        description_tbl.setCellValueFactory(new PropertyValueFactory<>("description"));
    }

    public void newItems() {
        Model.getInstance().sendItmemData(Name_txt.getText(), Double.parseDouble(price_txt.getText()), Integer.parseInt(quantity_txt.getText()), description_txt.getText());
        Name_txt.setText("");
        price_txt.setText("");
        quantity_txt.setText("");
        description_txt.setText("");
    }

    public void onSearch() {
        DatabaseDriver driver = new DatabaseDriver();
        List<ItemRecord> stockData = driver.QueryStockData(search_txt.getText());
        ObservableList<ItemRecord> observableStockData = FXCollections.observableArrayList(stockData);

        itemRecord_table.setItems(observableStockData);
    }

    public void cpyStockRecord() {
        itemRecord_table.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                ItemRecord itemRecordcopied = itemRecord_table.getItems().get(itemRecord_table.getSelectionModel().getSelectedIndex());
                Name_txt.setText(String.valueOf(itemRecordcopied.nameProperty().get()));
                price_txt.setText(String.valueOf(itemRecordcopied.unit_priceProperty().get()));
                quantity_txt.setText(String.valueOf(itemRecordcopied.quantityProperty().get()));
                description_txt.setText(String.valueOf(itemRecordcopied.descriptionProperty().get()));
            }
        });

    }
    public void removeStockRecord(){
        ItemRecord itemRecordcopied = itemRecord_table.getItems().get(itemRecord_table.getSelectionModel().getSelectedIndex());
        int selectedId= itemRecord_table.getSelectionModel().getSelectedIndex();
        DatabaseDriver driver = new DatabaseDriver();
        driver.deleteItemrecord(itemRecordcopied.nameProperty().get());
        itemRecord_table.getItems().remove(selectedId);
        Name_txt.setText("");
        price_txt.setText("");
        quantity_txt.setText("");
        description_txt.setText("");
    }
    public  void  updateStockRecord(){
        ItemRecord itemRecordcopied = itemRecord_table.getItems().get(itemRecord_table.getSelectionModel().getSelectedIndex());
        String selectedNameRecord = itemRecordcopied.nameProperty().get();
        int selectedId= itemRecord_table.getSelectionModel().getSelectedIndex();
        if (selectedId >= 0) {
            DatabaseDriver driver = new DatabaseDriver();
            driver.updateItemrecord(Name_txt.getText(), Double.parseDouble(price_txt.getText()), Integer.parseInt(quantity_txt.getText()), description_txt.getText(), selectedNameRecord);
            Name_txt.setText("");
            price_txt.setText("");
            quantity_txt.setText("");
            description_txt.setText("");
        }else {
            Name_txt.setText("");
            price_txt.setText("");
            quantity_txt.setText("");
            description_txt.setText("");
        }
    }

}