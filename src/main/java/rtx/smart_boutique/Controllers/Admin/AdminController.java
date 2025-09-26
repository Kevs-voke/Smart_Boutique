package rtx.smart_boutique.Controllers.Admin;

import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import rtx.smart_boutique.Model.Model;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminController implements Initializable {
    public BorderPane admin_parent;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Model.getInstance().getViewFactory().getAdminSelectedItem().addListener((observableValue, oldVal, newVal) -> {
            switch (newVal){
                case NEW_STOCK -> admin_parent.setCenter(Model.getInstance().getViewFactory().getNeWItemView());
                case VIEW_STOCK -> admin_parent.setCenter(Model.getInstance().getViewFactory().getViewStockview());
                case NEW_EMPLOYEE ->admin_parent.setCenter(Model.getInstance().getViewFactory().getNewEmployeeView());
                case VIEW_EMPLOYEE -> admin_parent.setCenter(Model.getInstance().getViewFactory().getviewEmployeeViews());
                case PROFILE -> admin_parent.setCenter(Model.getInstance().getViewFactory().getProfileView());
                default -> admin_parent.setCenter( Model.getInstance().getViewFactory().getaDashboardView());
            }
        } );

    }
}
