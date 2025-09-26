package rtx.smart_boutique.Controllers.Employee;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class MakeSale {

    @FXML
    private TextField Name_txt;

    @FXML
    private TableColumn<?, ?> Uprice_column;

    @FXML
    private Button add_btn;

    @FXML
    private Button cancel_btn;

    @FXML
    private Button confirm_btn;

    @FXML
    private Button delete_btn;

    @FXML
    private TableView<?> make_sale_tblview;

    @FXML
    private TableColumn<?, ?> name_column;

    @FXML
    private TableColumn<?, ?> quantity_column;

    @FXML
    private TextField quantity_txt;

    @FXML
    private Label total_price_lbl;

    @FXML
    private TableColumn<?, ?> tprice_column;

    @FXML
    private Button update_btn;
}
