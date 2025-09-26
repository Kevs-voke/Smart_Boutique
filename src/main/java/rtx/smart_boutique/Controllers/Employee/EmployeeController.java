package rtx.smart_boutique.Controllers.Employee;

import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import rtx.smart_boutique.Model.Model;

import java.net.URL;
import java.util.ResourceBundle;

public class EmployeeController implements Initializable {
    public BorderPane employee_parent;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
            Model.getInstance().getViewFactory().getEmployeeSelectedItem().addListener((observableValue, oldVal, newVal) ->{
                switch (newVal){
                    case MAKE_SALE ->employee_parent.setCenter(Model.getInstance().getViewFactory().getMakeSaleView());
                    case VIEW_SALES -> employee_parent.setCenter(Model.getInstance().getViewFactory().getViewSaleView());
                    case PROFILE -> employee_parent.setCenter(Model.getInstance().getViewFactory().getProfileView());
                    default -> employee_parent.setCenter(Model.getInstance().getViewFactory().getDashboardView());
                }
            });
    }
}
